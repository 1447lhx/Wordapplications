package cn.edu.bistu.cs.se.wordapplications;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class WordsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "wordsdb";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_DATABASE = "CREATE TABLE " +
            DB_words.T_word.TABLE_NAME + "(" +
            DB_words.T_word._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DB_words.T_word.COLUMN_NAME_WORD + " TEXT" + "," +
            DB_words.T_word.COLUMN_NAME_MEANING + " TEXT" + "," +
            DB_words.T_word.COLUMN_NAME_SAMPLE + " TEXT" + " )";

    private final static String SQL_DELETE_DATABASE =
            "DROP TABLE IF EXISTS " + DB_words.T_word.TABLE_NAME;
    private SQLiteDatabase db;

    public WordsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_DATABASE);
        Log.d("HYL", "onCreate: databases");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_DATABASE);
        onCreate(sqLiteDatabase);
    }


}
