package com.example.android.countryfacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {
    public int score;
    public String name;
    private String[] comps = new String[2];
    public TextView compText, congratsText, nameText, scoreText, scoreWords;
    public LinearLayout mainLin;
    public Button tryAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Intent getScore = getIntent();

        // Init views
        mainLin = (LinearLayout) findViewById(R.id.main_linear);
        compText = (TextView) findViewById(R.id.compliment_text);
        congratsText = (TextView) findViewById(R.id.congrats_text);
        nameText = (TextView) findViewById(R.id.name_text);
        scoreText = (TextView) findViewById(R.id.score_text);
        scoreWords = (TextView) findViewById(R.id.score_words_text);
        tryAgainButton = (Button) findViewById(R.id.try_again_button);

        // Set list of compliments array
        for (int i = 0; i < 2; i++) {
            comps[i] = getResources().getString(getResources().getIdentifier("c" + i, "string", getPackageName()));
        }

        tryAgainButton.setVisibility(View.GONE);
        score = getScore.getIntExtra("score", 0);
        name = getScore.getStringExtra("name");
        scoreText.setText(score + "/10");
        nameText.setText(name + "!");
        giveComp();
    }

    // On virtual back button press, override to route to Name Activity
    @Override
    public void onBackPressed() {
        Intent nameActivity = new Intent(this, TitleActivity.class);
        startActivity(nameActivity);
    }

    // Give compliment based on quiz scores
    private void giveComp() {
        if (score > 7 && score < 11) {
            mainLin.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            compText.setText(comps[0]);
            congratsText.setText("Excellent job");
        } else if (score > 5 && score < 8) {
            mainLin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            compText.setText(comps[1]);
            congratsText.setText("Pretty good");
            tryAgainButton.setVisibility(View.VISIBLE);
            tryAgainButton.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            mainLin.setBackgroundColor(getResources().getColor(R.color.secondaryText));
            compText.setText(comps[2]);
            congratsText.setText("Hmm...");
            tryAgainButton.setVisibility(View.VISIBLE);
            tryAgainButton.setTextColor(getResources().getColor(R.color.secondaryText));
        }
    }

    // Try again button
    public void tryAgain(View v) {
        Intent quiz = new Intent(this, QuizActivity.class);
        startActivity(quiz);
    }

}
