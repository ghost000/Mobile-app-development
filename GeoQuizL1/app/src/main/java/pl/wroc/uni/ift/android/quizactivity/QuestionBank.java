package pl.wroc.uni.ift.android.quizactivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neo on 11/6/17.
 */

public class QuestionBank {
    private static QuestionBank instance = null;

    private ArrayList<Question> mQuestionsBank;

    protected QuestionBank() {
        mQuestionsBank = new ArrayList<>();
        mQuestionsBank.add(new Question(R.string.question_stolica_polski, true));
        mQuestionsBank.add(new Question(R.string.question_stolica_dolnego_slaska, false));
        mQuestionsBank.add(new Question(R.string.question_sniezka, true));
        mQuestionsBank.add(new Question(R.string.question_wisla, true));
    }

    public static synchronized QuestionBank getInstance() {
        if (instance == null) {
            instance = new QuestionBank();
        }
        return instance;
    }

    public ArrayList<Question> getQuestions() {
        return mQuestionsBank;
    }

    public Question getQuestion(int index) {
        return mQuestionsBank.get(index);
    }

    public int size() {
        return mQuestionsBank.size();
    }
}

