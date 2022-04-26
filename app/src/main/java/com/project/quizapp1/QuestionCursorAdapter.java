package com.project.quizapp1;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cursoradapter.widget.CursorAdapter;

import com.project.quizapp1.data.QuizContract.QuestionEntry;

public class QuestionCursorAdapter extends CursorAdapter {
    public QuestionCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.question_item,parent,false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView viewQuestion = view.findViewById(R.id.questionView);
        RadioButton btn1 = view.findViewById(R.id.Option1);
        RadioButton btn2 = view.findViewById(R.id.Option2);
        RadioButton btn3 = view.findViewById(R.id.Option3);
        RadioGroup rbGroup = view.findViewById(R.id.groupBtn);
        Button submitBtn = view.findViewById(R.id.btnSubmit);
        TextView result = view.findViewById(R.id.score);



        int questionColumnIndex = cursor.getColumnIndex(QuestionEntry.COLUMN_QUESTION);
        int option1ColumnIndex = cursor.getColumnIndex(QuestionEntry.COLUMN_OPTION1);
        int option2ColumnIndex = cursor.getColumnIndex(QuestionEntry.COLUMN_OPTION2);
        int option3ColumnIndex = cursor.getColumnIndex(QuestionEntry.COLUMN_OPTION3);
        int answerColumnIndex = cursor.getColumnIndex(QuestionEntry.COLUMN_ANSWER);

        String question = cursor.getString(questionColumnIndex);
        String option1 = cursor.getString(option1ColumnIndex);
        String option2 = cursor.getString(option2ColumnIndex);
        String option3 = cursor.getString(option3ColumnIndex);
        int answer = cursor.getInt(answerColumnIndex);

        viewQuestion.setText(question);
        btn1.setText(option1);
        btn2.setText(option2);
        btn3.setText(option3);

        submitBtn.setOnClickListener(v-> {
            if(btn1.isChecked() || btn2.isChecked() || btn3.isChecked()){
                RadioButton rbSelected = view.findViewById(rbGroup.getCheckedRadioButtonId());
                int answerNo = rbGroup.indexOfChild(rbSelected) + 1;
                if(answerNo == answer){
                    result.setText(R.string.correct_answer);
                    result.setTextColor(Color.GREEN);
                } else {
                    result.setText(R.string.wrong_answer);
                    result.setTextColor(Color.RED);
                }
            } else {
                Toast.makeText(context, "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
