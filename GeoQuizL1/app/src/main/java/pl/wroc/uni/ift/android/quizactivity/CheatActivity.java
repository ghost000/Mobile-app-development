package pl.wroc.uni.ift.android.quizactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private TextView mAnswere;
    private boolean flag = false;

    public static Intent newIntent(Context context, boolean answerIsTrue) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra("answer", answerIsTrue);
        return intent;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            QuizActivity.mTokens = savedInstanceState.getInt("tokens");
            flag = savedInstanceState.getBoolean("flag");
        }

        Intent intent = getIntent();
        boolean message = intent.getBooleanExtra("answer", false);

        if (!flag) {
            flag = true;
            QuizActivity.mTokens--;
        }
        mAnswere = (TextView) findViewById(R.id.text_answer);
        if (message) {
            mAnswere.append("Prawda");
        } else {
            mAnswere.append("Fałsz");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("tokens", QuizActivity.mTokens);
        outState.putBoolean("flag", flag);
        super.onSaveInstanceState(outState);
    }
}
