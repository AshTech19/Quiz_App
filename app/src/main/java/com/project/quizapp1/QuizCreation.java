package com.project.quizapp1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project.quizapp1.data.QuizContract.QuestionEntry;

public class QuizCreation extends AppCompatActivity {
    private EditText questionText, btnText1, btnText2, btnText3;
    private Spinner actualAns;
    Button addBtn, doneBtn;

    String Question,opt1, opt2, opt3;

    private int mAns = QuestionEntry.CHOOSE_ANSWER;

    public static void start(Context context){
        Intent intent = new Intent(context, QuizCreation.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        questionText = findViewById(R.id.questionBox);
        btnText1 = findViewById(R.id.textOption1);
        btnText2 = findViewById(R.id.textOption2);
        btnText3 = findViewById(R.id.textOption3);
        actualAns = findViewById(R.id.spinner_answer);

        doneBtn = findViewById(R.id.btnDone);
        doneBtn.setOnClickListener(v -> homeScreen());

        addBtn = findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(v -> insertQuestion());

        setupSpinner();

    }

    private void setupSpinner(){
        ArrayAdapter<CharSequence> answerSpinnerAdapter = ArrayAdapter.createFromResource(this,R.array.array_answer_options, android.R.layout.simple_spinner_item);
        answerSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        actualAns.setAdapter(answerSpinnerAdapter);

        actualAns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals(getString(R.string.answer_1))){
                       mAns = QuestionEntry.ANSWER_1;
                    } else if(selection.equals(getString(R.string.answer_2))){
                        mAns = QuestionEntry.ANSWER_2;
                    } else if(selection.equals(getString(R.string.answer_3))){
                        mAns = QuestionEntry.ANSWER_3;
                    } else {
                        mAns = QuestionEntry.CHOOSE_ANSWER;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mAns = QuestionEntry.CHOOSE_ANSWER;
            }
        });
    }
    private boolean sanityCheck(){

        if(TextUtils.isEmpty(Question)){
            Toast.makeText(this, getString(R.string.insert_question_missing), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(opt1) || TextUtils.isEmpty(opt2) || TextUtils.isEmpty(opt3)){
            Toast.makeText(this, getString(R.string.insert_options_missing), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!QuestionEntry.isValidAnswer(mAns)){
            Toast.makeText(this, getString(R.string.insert_answer_missing), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void insertQuestion(){

        Question = questionText.getText().toString().trim();
        opt1 = btnText1.getText().toString().trim();
        opt2 = btnText2.getText().toString().trim();
        opt3 = btnText3.getText().toString().trim();

        if(sanityCheck()){
            ContentValues values = new ContentValues();
            values.put(QuestionEntry.COLUMN_QUESTION,Question);
            values.put(QuestionEntry.COLUMN_OPTION1,opt1);
            values.put(QuestionEntry.COLUMN_OPTION2,opt2);
            values.put(QuestionEntry.COLUMN_OPTION3,opt3);
            values.put(QuestionEntry.COLUMN_ANSWER,mAns);

            Uri newUri = getContentResolver().insert(QuestionEntry.CONTENT_URI,values);

            if(newUri == null){
                Toast.makeText(this, getString(R.string.insert_question_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_question_success), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.insert_field_missing), Toast.LENGTH_SHORT).show();
        }
    }

    private void homeScreen(){
        MainActivity.start(this);
    }

}
