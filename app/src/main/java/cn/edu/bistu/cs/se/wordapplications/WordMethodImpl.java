package cn.edu.bistu.cs.se.wordapplications;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;
//接口定义方法的具体实现
public class WordMethodImpl implements WordMethod {
    private static WordsDBHelper db;
    private Context context;
    public WordMethodImpl() {
        context = WordsApplication.getContext();
        db = new WordsDBHelper(context);
    }

    public WordMethodImpl(Context context) {
        this.context = context;
        db = new WordsDBHelper(context);
    }

    public int addWord(Word word) {
        String sql = "insert into words(word, meaning, sample) values(?,?,?)";
        SQLiteDatabase db = WordMethodImpl.db.getWritableDatabase();
        db.execSQL(sql, new String[]{word.getWord(), word.getMeaning(), word.getSample()});
        return 0;
    }

    public int delWord(int wordId) {
        String sql = "delete from words where _id=" + wordId;
        SQLiteDatabase db = WordMethodImpl.db.getReadableDatabase();
        db.execSQL(sql);
        return 0;
    }

    public int delWord(String wordContent) {
        SQLiteDatabase db = WordMethodImpl.db.getReadableDatabase();
        String sql = "delete from words where word='" + wordContent+"'";
        db.execSQL(sql);
        return 0;
    }

    public int updateWord(Word word) {
        SQLiteDatabase db = WordMethodImpl.db.getReadableDatabase();
        String sql = "update words set word=?,meaning=?,sample=? where _id=" + word.getId();
        db.execSQL(sql, new String[]{word.getWord(), word.getMeaning(), word.getSample(),});
        return 0;
    }

    public Word getWordById(int wordId) {
        Word w = null;
        SQLiteDatabase db = WordMethodImpl.db.getReadableDatabase();
        String sql = "select * from words where _ID=?";
        Cursor cursor = db.rawQuery(sql, new String[]{wordId + ""});
        if (cursor.moveToNext()) {
            w = new Word();
            w.setId(cursor.getInt(cursor.getColumnIndex(DB_words.T_word._ID)));
            w.setWord(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_WORD)));
            w.setMeaning(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_MEANING)));
            w.setSample(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_SAMPLE)));
        }
        close();
        return w;
    }

    @Override
    public Word getWordByContent(String word) {
        Word w = null;
        SQLiteDatabase db = WordMethodImpl.db.getReadableDatabase();
        String sql = "select * from words where word like ? order by word asc";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + word + "%"});
        if (cursor.moveToNext()) {
            w = new Word();
            w.setId(cursor.getInt(cursor.getColumnIndex(DB_words.T_word._ID)));
            w.setWord(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_WORD)));
            w.setMeaning(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_MEANING)));
            w.setSample(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_SAMPLE)));
        }
        close();
        return w;
    }
    public void close() {
        if (db != null)
            db.close();
    }

    @Override
    public List<Word> getAllWords() {
        SQLiteDatabase db = WordMethodImpl.db.getReadableDatabase();

        String[] projection = {
                DB_words.T_word._ID,
                DB_words.T_word.COLUMN_NAME_WORD,
                DB_words.T_word.COLUMN_NAME_MEANING,
                DB_words.T_word.COLUMN_NAME_SAMPLE,
        };

        //排序
        String sortOrder =
                DB_words.T_word.COLUMN_NAME_WORD + " ASC";

        Cursor c = db.query(
                DB_words.T_word.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        return Util.convertCursor2WordList(c);
    }

    public void UpdateUseSql(String strId, String strWord, String strMeaning, String strSample) {
        SQLiteDatabase db = WordMethodImpl.db.getReadableDatabase();
        String sql = "update words set word=?,meaning=?,sample=? where _id=?";
        db.execSQL(sql, new String[]{strWord, strMeaning, strSample, strId});
    }
}
