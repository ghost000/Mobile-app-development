package pl.wroc.uni.ift.android.quizactivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;

    private TextView mQuestionTextView;
    private TextView mPointsTextView;

    private int mCurrentIndex = 0;

    private Question[] mQuettionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    // na potrzeby własne
    private int mPoints = 0;
    private boolean[] mFlag;

    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlag = new boolean[mQuettionsBank.length];
        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mPointsTextView = (TextView) findViewById(R.id.points);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                    }
                });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(false);
                    }
                });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateQuestion();
                    }
                });
    }

    private void updateQuestion() {

        if (mCurrentIndex >= 3) { // zahardkodowane bo nie wiem jak wyciągnąć wielkość mQuettionsBank
            mCurrentIndex = 0;
            mPoints = 0;
            mPointsTextView.setText(Integer.toString(mPoints));
        } else {
            mCurrentIndex++;
        }

        mFlag[mCurrentIndex] = false;
        mQuestionTextView.setText(mQuettionsBank[mCurrentIndex].getTextResId());
    }


    private void checkAnswer(boolean userPressedTrue) {

        if (userPressedTrue == mQuettionsBank[mCurrentIndex].isAnswerTrue()) {
            if (mFlag[mCurrentIndex] == false) {
                Toast.makeText(QuizActivity.this,
                        "Correct answer",
                        Toast.LENGTH_LONG).show();
                mPoints++;
                mFlag[mCurrentIndex] = true;
                mPointsTextView.setText(Integer.toString(mPoints));
            }
        } else {
            if (mFlag[mCurrentIndex] == false) {
                Toast.makeText(QuizActivity.this,
                        "Incorrect answer",
                        Toast.LENGTH_LONG).show();
                mPoints--;
                mFlag[mCurrentIndex] = true;
                mPointsTextView.setText(Integer.toString(mPoints));
            }

        }
    }
}
