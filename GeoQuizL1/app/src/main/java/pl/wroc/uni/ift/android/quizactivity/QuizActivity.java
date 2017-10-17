package pl.wroc.uni.ift.android.quizactivity;

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
    private int mPoints = 0;
    private boolean[] mFlag;
    private boolean[] mClikedFlag;
    private TextView mPointsTextView;

    private TextView mQuestionTextView;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_stolica_polski, true),
            new Question(R.string.question_stolica_dolnego_slaska, false),
            new Question(R.string.question_sniezka, true),
            new Question(R.string.question_wisla, true)
    };

    private int mCurrentIndex = 0;

    //    Bundles are generally used for passing data between various Android activities.
    //    It depends on you what type of values you want to pass, but bundles can hold all
    //    types of values and pass them to the new activity.
    //    see: https://stackoverflow.com/questions/4999991/what-is-a-bundle-in-an-android-application
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mFlag = new boolean[mQuestionsBank.length];
        mClikedFlag = new boolean[mQuestionsBank.length];
        mPointsTextView = (TextView) findViewById(R.id.ClickedButton);

        setTitle(R.string.app_name);
        // inflating view objects
        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt("index");
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(true);
                        mClikedFlag[mCurrentIndex] = true;
                    }
                }
        );

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer(false);
                        mClikedFlag[mCurrentIndex] = false;
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
                        if(mCurrentIndex == 0){
                            mCurrentIndex = mQuestionsBank.length - 1;
                        }
                        else {
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
                        if(mCurrentIndex == 0){
                            mCurrentIndex = mQuestionsBank.length - 1;
                        }
                        else {
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


        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("index", mCurrentIndex);
        super.onSaveInstanceState(outState);
    }

    private void updateQuestion() {
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

            if(mFlag[mCurrentIndex]){

                mFalseButton.setEnabled(false);
                mTrueButton.setEnabled(false);

                //mPointsTextView.setText("Clicked button is " + Boolean.toString(mClikedFlag[mCurrentIndex]));
                //mPointsTextView.setEnabled(true);
                //mPointsTextView.show();

            }
    }

    private void checkAnswer(boolean userPressedTrue) {

        String toastMessageId = "Question is answered";

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
