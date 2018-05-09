package com.kilic.quiz;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

// Quiz Application


public class MainActivity extends Activity {

    // TODO: Declare constants here

    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mScore = 0;
    static int mIndex;
    int mQuestion;


    private TrueFalse[] mQuestionBank = new TrueFalse[]{
            new TrueFalse (R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13, true)
    };

    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("Index");

        } else {
            mScore = 0;
            mIndex = 0;
        }


        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);

        int question = mQuestionBank[mIndex].getmQuestionID();
        mQuestionTextView.setText(question);
        mScoreTextView = (TextView) findViewById(R.id.score);


        View.OnClickListener myListener = new View.OnClickListener() {

            public void onClick(View v) {
               /* Log.d("Quizzler", "true button pressed");
                Toast.makeText(getApplicationContext(), "True pressed", Toast.LENGTH_SHORT).show();*/
                checkAnswer(true);
                update();
            }

        };
        mTrueButton.setOnClickListener(myListener);


        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Log.d("Quizzler", "false button pressed");
                Toast.makeText(getApplicationContext(), "False button pressed", Toast.LENGTH_SHORT).show();*/
                checkAnswer(false);
                update();
            }
        });


        TrueFalse exampleQuestion = new TrueFalse(R.string.question_1, true);

    }


    public void update() {
        if (mIndex < 12) {
            if (mIndex == 0) {
                mQuestion = mQuestionBank[0].getmQuestionID();
                mQuestionTextView.setText(mQuestion);
                mIndex = 1;
                mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
                mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
                return;

            }

            mQuestion = mQuestionBank[mIndex].getmQuestionID();
            mQuestionTextView.setText(mQuestion);
            mIndex++;
        }

        if (mIndex == 12) {
            mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
            mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points");
            alert.setPositiveButton("Close Application ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });


            alert.show();

        }
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);

    }


    public void checkAnswer(boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].ismAnswer();

        if (correctAnswer == userSelection) {
            Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mScore++;
        } else
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Index", mIndex);
        outState.putInt("ScoreKey", mScore);


    }


}
