package pl.wroc.uni.ift.android.quizactivity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;

    private ImageButton mPrevImgButton;

    // na potrzeby własne
    private boolean[] mFlag;
    private int mClickedPoint;
    private int mCurrentIndex = 0;
    private int mPoints = 0;

    private TextView mPointsTextView;
    private TextView mAPILevel;
    private TextView mQuestionTextView;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mFlag = new boolean[mQuestionsBank.length];

        setTitle(R.string.app_name);
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt("index");
            mPoints = savedInstanceState.getInt("points");
            mClickedPoint = savedInstanceState.getInt("clickedpoint");
            mFlag = savedInstanceState.getBooleanArray("flag");
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

        mPrevImgButton = (ImageButton) findViewById(R.id.previous_img_button);
        mPrevImgButton.setOnClickListener(
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
        super.onSaveInstanceState(outState);
    }

    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();


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
        String toastMessageId = "Question is answered";

        mClickedPoint++;
        if ((userPressedTrue == mQuestionsBank[mCurrentIndex].isAnswerTrue()) && (mFlag[mCurrentIndex] == false)) {
            toastMessageId = "Correct answer";
            mPoints++;
            mFlag[mCurrentIndex] = true;
        } else {
            toastMessageId = "Incorrect answer";
            mPoints--;
            mFlag[mCurrentIndex] = true;
        }

        Toast toast = Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -500);
        toast.show();
    }
}
