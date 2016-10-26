package cn.edu.bistu.cs.se.wordapplications;

import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static boolean isLand(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static List<Word> convertCursor2WordList(Cursor cursor) {
        List<Word> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            Word w = new Word();
            w.setId(cursor.getInt(cursor.getColumnIndex(DB_words.T_word._ID)));
            w.setWord(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_WORD)));
            w.setMeaning(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_MEANING)));
            w.setSample(cursor.getString(cursor.getColumnIndex(DB_words.T_word.COLUMN_NAME_SAMPLE)));
            result.add(w);
        }
        return result;
    }
}
