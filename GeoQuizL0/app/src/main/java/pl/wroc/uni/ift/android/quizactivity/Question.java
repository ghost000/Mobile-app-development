package pl.wroc.uni.ift.android.quizactivity;

/**
 * Created by neo on 10/7/17.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean AnswerTrue) {
        mTextResId = textResId;
        mAnswerTrue = AnswerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }


    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }


    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }


    public void setAnswerTrue(boolean AnswerTrue) {
        mAnswerTrue = AnswerTrue;
    }


}
