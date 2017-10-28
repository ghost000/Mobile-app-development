package pl.wroc.uni.ift.android.quizactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    public static int mTokens = 2;
    private Button mTrueButton;
    private Button mFalseButton;

    // na potrzeby własne
    private boolean[] mFlag;
    private Button mCheatButton;
    private int mClickedPoint;
    private int mCurrentIndex = 0;
    private int mPoints = 0;
    private TextView mQuestionTextView;
    private TextView mPointsTextView;
    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Button mNextButton;
        Button mPrevButton;
        TextView mAPILevel;
        mFlag = new boolean[mQuestionsBank.length];

        setTitle(R.string.app_name);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt("index");
            mPoints = savedInstanceState.getInt("points");
            mClickedPoint = savedInstanceState.getInt("clickedpoint");
            mFlag = savedInstanceState.getBooleanArray("flag");
            mTokens = savedInstanceState.getInt("token");
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                        updateQuestion();
                    }
                }
        );

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(false);
                        updateQuestion();
                    }
                });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                        updateQuestion();
                    }
                });

        mPrevButton = (Button) findViewById(R.id.previous_button);
        mPrevButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mCurrentIndex == 0) {
                            mCurrentIndex = mQuestionsBank.length - 1;
                        } else {
                            mCurrentIndex = (mCurrentIndex - 1) % mQuestionsBank.length;
                        }
                        updateQuestion();
                    }
                });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateQuestion();
                        boolean currentAnswer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                        Intent intent = CheatActivity.newIntent(QuizActivity.this, currentAnswer);
                        startActivityForResult(intent, mTokens);
                    }
                }
        );

        mQuestionTextView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
                        updateQuestion();
                    }
                }
        );

        mAPILevel = (TextView) findViewById(R.id.APILEVEL);
        mAPILevel.setText(" Android Version : " + Build.VERSION.RELEASE + " and API Level : " + Build.VERSION.SDK);

        updateQuestion();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mCurrentIndex);
        outState.putInt("points", mPoints);
        outState.putBooleanArray("flag", mFlag);
        outState.putInt("clickedpoint", mClickedPoint);
        outState.putInt("token", mTokens);
        super.onSaveInstanceState(outState);
    }

    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();

        if (mTokens <= 0) {
            mCheatButton.setVisibility(View.INVISIBLE);
        }

        mQuestionTextView.setText(question);

        if (mFlag[mCurrentIndex]) {

            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);

        } else {

            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);

        }
        if (mClickedPoint == mQuestionsBank.length) {
            mPointsTextView = (TextView) findViewById(R.id.ClickedButton);
            mPointsTextView.setText("Your score : " + Integer.toString(mPoints));
            mPointsTextView.setEnabled(true);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        mClickedPoint++;
        if ((userPressedTrue == mQuestionsBank[mCurrentIndex].isAnswerTrue()) && (mFlag[mCurrentIndex] == false)) {
            showToast("Correct answer");
            mPoints++;
            mFlag[mCurrentIndex] = true;
        } else {
            showToast("Incorrect answer");
            mPoints--;
            mFlag[mCurrentIndex] = true;
        }
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -500);
        toast.show();
    }
}
