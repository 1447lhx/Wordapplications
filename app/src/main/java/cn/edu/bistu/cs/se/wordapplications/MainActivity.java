package cn.edu.bistu.cs.se.wordapplications;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements WordListFragment.itemListener {
    private youdao yd;
    private WordMethod db;
    private android.view.View addWordDialog;
    private android.view.View youdaoDialog;
    private android.view.View searchWordDialog;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        yd = new youdao(this);
        db = new WordMethodImpl(this);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemClick(String wordContent) {
        Word w = yd.getWordByContent(wordContent);
        if (Util.isLand(this)) {
            updateDetailFragment(w);
        } else {
            Intent intent = new Intent(MainActivity.this, WordDetailActivity.class);
            intent.putExtra("word", w);
            startActivity(intent);
        }

    }

    public void updateDetailFragment(Word w) {
        Bundle args = new Bundle();
        args.putSerializable("word", w);
        WordDetailFragment fragment = new WordDetailFragment();
        fragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.frag_word_detail, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret = false;
        if (item.getItemId() == R.id.add_word) {
            showAddWordDialog();
            ret = true;
        }
       else if (item.getItemId() == R.id.search_word) {
            searchDialog();
            ret = true;
        }
        else if (item.getItemId() == R.id.word_youdao) {
            youdaoDialog();
            ret = true;
        }
        return ret;
    }

    public void showAddWordDialog() {
        addWordDialog = getLayoutInflater().inflate(R.layout.word_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新增单词");//标题
        builder.setView(addWordDialog);//设置视图
        //确定按钮及其动作
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText text_word = (EditText) addWordDialog.findViewById(R.id.text_dialog_word);
                EditText text_meaning = (EditText) addWordDialog.findViewById(R.id.text_dialog_meaning);
                EditText text_sample = (EditText) addWordDialog.findViewById(R.id.text_dialog_sample);
                db.addWord(new Word(0, text_word.getText() + "", text_meaning.getText() + "", text_sample.getText() + ""));
                //单词已经插入到数据库，更新显示列表
                refreshWordListFragment();

            }
        });
        //取消按钮及其动作
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();//创建对话框
        builder.show();//显示对话框
    }
    public void youdaoDialog() {
        youdaoDialog = getLayoutInflater().inflate(R.layout.youdao, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("有道添加");//标题

        builder.setView(youdaoDialog);//设置视图
        //确定按钮及其动作
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText text_word = (EditText) youdaoDialog.findViewById(R.id.text_dialog_word);
                EditText text_meaning = (EditText) youdaoDialog.findViewById(R.id.text_dialog_meaning);
                EditText text_sample = (EditText) youdaoDialog.findViewById(R.id.text_dialog_sample);
                yd.addWord(new Word(0, text_word.getText() + "", text_meaning.getText() + "", text_sample.getText() + ""));
                refreshWordListFragment();

            }
        });
        //取消按钮及其动作
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();//创建对话框
        builder.show();//显示对话框
    }
    private void searchDialog() {
        searchWordDialog = getLayoutInflater().inflate(R.layout.searchterm, null);
        new AlertDialog.Builder(this)
                .setTitle("查找单词")//标题
                .setView(searchWordDialog)//设置视图
                //确定按钮及其动作
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String txtSearchWord = ((EditText) searchWordDialog.findViewById(R.id.txtSearchWord)).getText().toString();
                        Word items = yd.getWordByContent(txtSearchWord);
                        if (items != null) {
                            Intent intent = new Intent(MainActivity.this, WordDetailActivity.class);
                            intent.putExtra("word", items);
                            startActivity(intent);
                        } else
                            Toast.makeText(MainActivity.this, "没有找到", Toast.LENGTH_LONG).show();


                    }
                })
                //取消按钮及其动作
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()//创建对话框
                .show();

    }

    /**
     * 更新单词列表
     */

    public void refreshWordListFragment() {
        WordListFragment wordListFragment = (WordListFragment) getFragmentManager().findFragmentById(R.id.frag_word_list);
        wordListFragment.refreshList();
    }

    /**
     * 更新单词列表
     */
    public void refreshWordListFragment(String wordContent) {
        WordListFragment wordListFragment = (WordListFragment) getFragmentManager().findFragmentById(R.id.frag_word_list);
        wordListFragment.refreshList(wordContent);
    }
}
