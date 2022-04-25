package com.project.quizapp1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.quizapp1.data.QuizContract.QuestionEntry;

public class QuizDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = QuizDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Quiz.db";
    private static final int DATABASE_VERSION = 1;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionEntry.TABLE_NAME + " ("
                + QuestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QuestionEntry.COLUMN_QUESTION + " TEXT NOT NULL, "
                + QuestionEntry.COLUMN_OPTION1 + " TEXT NOT NULL, "
                + QuestionEntry.COLUMN_OPTION2 + " TEXT NOT NULL, "
                + QuestionEntry.COLUMN_OPTION3 + " TEXT NOT NULL , "
                + QuestionEntry.COLUMN_ANSWER + " TEXT NOT NULL);";
        db.execSQL(CREATE_QUESTIONS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS questions");
        onCreate(db);
    }

}
