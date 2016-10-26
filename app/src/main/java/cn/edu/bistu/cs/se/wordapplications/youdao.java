package cn.edu.bistu.cs.se.wordapplications;
import android.content.Context;
 import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
 import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
 import org.json.JSONException;
 import org.json.JSONObject;
 import cz.msebera.android.httpclient.Header;

public class youdao {
         private MainActivity m;
         private WordMethod db;
         private Word word;
    public youdao(MainActivity m) {
                 this.m = m;
                 db = new WordMethodImpl((Context) m);
             }
                 public void addWord(Word word) {
                 this.word = word;
                 //TODO:询问是否使用网络解释
                 AsyncHttpClient client = new AsyncHttpClient();
                 String url = "http://fanyi.youdao.com/openapi.do?keyfrom=haobaoshui&key=1650542691&type=data&doctype=json&version=1.1&q=";
                 url += word.getWord();
                 client.get(url, new AsyncHttpResponseHandler() {
                         public void onStart() {
                             }
                         public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                                 String json = new String(response);
                                 parseJson(json);
                             }
                         public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                             }
                         public void onRetry(int retryNo) {
                             }
                     });
             }
                 private boolean parseJson(String json) {
                 try {
                         JSONObject result = new JSONObject(json);
                         if (0 != (int) result.get("errorCode")) return false;
                         JSONArray translations = (JSONArray) result.get("translation");
                         String translation = "";
                         for (int i = 0; i < translations.length(); i++) {
                                 translation += translations.get(i);
                             }
                         Log.d("net meaning", "parseJson: meaning" + translation);
                         word.setMeaning(translation);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 db.addWord(word);
                m.refreshWordListFragment();
                 return true;
             }


                 public Word getWordByContent(String wordContent) {
                 return db.getWordByContent(wordContent);
             }
     }
