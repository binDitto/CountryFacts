package com.example.android.countryfacts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class QuizActivity extends AppCompatActivity {

    // Quiz user name
    public String name;
    // Card Views
    RelativeLayout nameCard, questionCard;
    // Lists of 2d Answers
    String[][] answers = new String[10][];
    // List of Question Objects
    Question[] questions = new Question[10];
    // List of Check Boxes
    CheckBox[] checkBoxes = new CheckBox[5];
    // List of Radio Buttons
    RadioButton[] radioButtons = new RadioButton[4];
    // Current Question Index
    private int questionIndex = 0;
    // Score Keeper
    private int score = 0;
    // Progress Bar
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        // Init views
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        nameCard = (RelativeLayout) findViewById(R.id.name_card_layout);
        questionCard = (RelativeLayout) findViewById(R.id.question_card_layout);

        progress.setVisibility(View.GONE);
        questionCard.setVisibility(View.GONE);
        nameCard.setVisibility(View.VISIBLE);
        setAnswers();
        setQuestions();

        Log.v("Question3", questions[0].getQuestion() + questions[0].answers[0] + questions[0].isRadioButton() + questions[0].answers.length);
    }

    // Display a toast
    private void displayToast(CharSequence text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 100);
        toast.show();
    }
    // Name card's next button that saves the name and shows the questions card on click
    public void onStartQuiz(View v){
        clearFields();
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_input);
        // Save entered name into name variable
        name = nameEditText.getText().toString();
        // Validate name before starting quiz
        if (name.length() == 0) {
            displayToast("Please enter your name before starting the quiz");
        } else if (name.length() < 2){
            displayToast("Name has to be at least 2 characters long");
        } else {
            onHideKeyboard(v);
            displayToast("Alright " + name + ",\nGood Luck!");
            nameCard.setVisibility(View.GONE);
            questionCard.setVisibility(View.VISIBLE);
            setQuestion();
        }
    }

    // Creates a two-dimensional array of answers
    private void setAnswers(){
        for ( int i = 0; i < 10; i++ ) {
            if (i == 0 || i == 1 || i == 4 || i == 7 || i == 9){
                // Init length of 'radio button' answers array
                answers[i] = new String[4];
                // If radio button answer
                for (int e = 0; e < 4; e++) {
                    answers[i][e] = getResources().getString(getResources().getIdentifier("question" + i + "_answer_" + e, "string", getPackageName()));
                }
            } else if (i == 3 || i == 5 || i == 8) {
                // Init length of 'check box' answers array
                answers[i] = new String[5];
                // If check box answer
                for(int e = 0; e < 5; e++) {
                    answers[i][e] = getResources().getString(getResources().getIdentifier("question" + i + "_answer_" + e, "string", getPackageName()));
                }
            } else {
                // Init length of 'edit text' answer array
                answers[i] = new String[1];
                // If edit text answer
                answers[i][0] = getResources().getString(getResources().getIdentifier("question" + i + "_answer_" + 0, "string", getPackageName()));
            }
        }
    }

    // Set list of Question Objects
    private void setQuestions(){
        for (int i = 0; i < 10; i++) {
            questions[i] = new Question(
                    answers[i],
                    getResources().getString(getResources().getIdentifier("question" + i, "string", getPackageName()))
            );
            // Set image according to questions
            if (i == 1 || i == 4 ) {
                questions[i].setImage(getResources().getIdentifier("country_" + i, "drawable", getPackageName()));
            }
        }
    }

    // Toggle Question Type Visibility
    private void toggleVisibility(){
        LinearLayout checkBox = (LinearLayout) findViewById(R.id.checkbox_layout);
        LinearLayout radioButton = (LinearLayout) findViewById(R.id.radio_button_layout);
        LinearLayout editText = (LinearLayout) findViewById(R.id.edit_text_layout);
        // If Radio Button
        if (questions[questionIndex].isRadioButton()) {
            radioButton.setVisibility(View.VISIBLE);
            setRandomRadioButtons();
        } else {
            radioButton.setVisibility(View.GONE);
        }
        // If EditText
        if (questions[questionIndex].isEditText()) {
            editText.setVisibility(View.VISIBLE);
        } else {
            editText.setVisibility(View.GONE);
        }
        // If CheckBox
        if (questions[questionIndex].isCheckBox()) {
            checkBox.setVisibility(View.VISIBLE);
            setRandomCheckBoxes();
        } else {
            checkBox.setVisibility(View.GONE);
        }
    }


    private void setRandomCheckBoxes(){
        // Randomize choices
        ArrayList<Integer> listOfInts = new ArrayList<Integer>();
        // Make the arraylist range of numbers from 0-4 = 5 for the length of the Checkbox array list
        for (int i = 0; i < 5; i++) {
            listOfInts.add(Integer.valueOf(i));
        }
        // Shuffle numbers in listOfInts
        Collections.shuffle(listOfInts);

        // Takes the integer value of each listOfInt index to set checkbox choices
        for (int loi = 0; loi < 5; loi++) {
            checkBoxes[listOfInts.get(loi)] = (CheckBox) findViewById(getResources().getIdentifier("checkbox_" + listOfInts.get(loi), "id", getPackageName()));
            checkBoxes[listOfInts.get(loi)].setText(questions[questionIndex].answers[loi]);
        }
    }

    private void setRandomRadioButtons() {
        // Randomize choices
        ArrayList<Integer> listOfInts = new ArrayList<Integer>();
        // Set range to be from 0-3 = 4 length of RadioButtons array
        for (int i = 0; i < 4; i++ ) {
            listOfInts.add(Integer.valueOf(i));
        }
        // Shuffle values
        Collections.shuffle(listOfInts);
        // Use value for checkbox index and questions - answers index
        for (int loi = 0; loi < 4; loi++) {
            radioButtons[listOfInts.get(loi)] = (RadioButton) findViewById(getResources().getIdentifier("radio_" + listOfInts.get(loi), "id", getPackageName()));
            radioButtons[listOfInts.get(loi)].setText(questions[questionIndex].answers[loi]);
        }
    }

    // Fill in Question Card
    private void setQuestion(){
        TextView questionNumber = (TextView) findViewById(R.id.question_number_title);
        TextView questionText = (TextView) findViewById(R.id.question_text);
        ImageView questionImage = (ImageView) findViewById(R.id.question_image);
        View questionImageScreen = (View) findViewById(R.id.image_screen);
                 // Display question number
                 questionNumber.setText("QUESTION " + (questionIndex + 1));
                 // Display question
                 questionText.setText(questions[questionIndex].getQuestion());
                 // Display progress bar
                 progress.setVisibility(View.VISIBLE);
                 progress.setProgress(questionIndex + 1);
        // If Question Object contains an image
        if (questions[questionIndex].hasImage()) {
            // Display ImageView
            questionImage.setVisibility(View.VISIBLE);
            questionImageScreen.setVisibility(View.VISIBLE);
            questionImage.setImageResource(questions[questionIndex].getImage());
        } else {
            // Hide ImageView
            questionImage.setVisibility(View.GONE);
            questionImageScreen.setVisibility(View.GONE);
        }
        toggleVisibility();
    }

    // Next Button
    public void onNext(View v) {
        onHideKeyboard(v);
        if ( questions[questionIndex].isRadioButton()) {
            if (!onRadio()) {
                return;
            }
        }
        if ( questions[questionIndex].isEditText()) {
            if (!onEdit()) {
                return;
            }
        }
        if ( questions[questionIndex].isCheckBox()) {
            if (!onCheckBox()) {
               return;
            }
        }
        clearFields();
        questionIndex++;
        // If not all questions displayed we set next question
        if (questionIndex <= 9) {
            setQuestion();
            // If last question we show submit button
            if (questionIndex == 9) {
                Button nextButton = (Button) findViewById(R.id.next_question_button);
                nextButton.setText("Submit");
            }
        }
        // If all questions answered we submit user score to SubmitActivity and reset score and index
        if(questionIndex == 10) {
            submitAnswers();
            score = 0;
            questionIndex = 0;
        }
    }

    private boolean onCheckBox() {
        int totalCheckBoxes = checkBoxes.length;
        boolean questionAnswered = false;
        int numberChecked = 0;
        int numberCorrect = 0;

        for (int count = 0; count < totalCheckBoxes; count++ ) {
            // If check box checked we retrieve the text and increment numberChecked
            if (checkBoxes[count].isChecked()) {
                numberChecked++;
                String text = checkBoxes[count].getText().toString();
                // If the text retrieved matches any of the 3 correct answers of the currently displayed Question Object, we increment numberCorrect
                if (text == questions[questionIndex].answers[0] || text == questions[questionIndex].answers[1] || text == questions[questionIndex].answers[2] ) {
                    numberCorrect++;
                }
            }
        }
        // VALIDATIONS
            // If 3 checked and those 3 are correct
            if ( numberCorrect == 3 && numberChecked == 3) {
                questionAnswered = true;
                score++;
                displayToast("Good job, +1");
                // If 3 checked but aren't correct
            } else if (numberCorrect != 3 && numberChecked == 3) {
                questionAnswered = true;
                displayToast("Hmm...not quite");
            }
            // If more or less than 3 checkboxes were checked - we throw a validation error
            if ( numberChecked > 3 || numberChecked < 3 ) {
                displayToast("Select 3 answers");
                for (int count = 0; count < totalCheckBoxes; count++ ) {
                    checkBoxes[count].setChecked(false);
                }
            }
        return questionAnswered;
    }

    public boolean onRadio() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group);
        int id = group.getCheckedRadioButtonId();
        RadioButton button = (RadioButton) group.findViewById(id);
        String text;
        boolean questionAnswered = false;
        // If a radio button is checked
        if (button != null ) {
            text = button.getText().toString();
            // If text of checked radio button matches correct answer
            if ( text == questions[questionIndex].answers[0]) {
                score++;
                displayToast("Right on!");
            }
            questionAnswered = true;
        } else {
            // If no buttons checked
            displayToast("An answer must be chosen.");
            questionAnswered = false;
        }

        return questionAnswered;
    }

    private boolean onEdit() {
        EditText textField = (EditText) findViewById(R.id.edit_text_input);
        String chosenAnswer = textField.getText().toString().trim().toLowerCase();
        String answer = questions[questionIndex].answers[0].trim().toLowerCase();
        boolean questionAnswered = false;
        // If EditText isn't blank
        if(!chosenAnswer.matches("") ) {
            // If EditText answer matches actual answer
            if (chosenAnswer.matches(answer)){
                score++;
                displayToast("Perrfecto, +1");
            }
            questionAnswered = true;
        } else {
            // Answer is blank
            displayToast("Answer cannot be blank.");
            questionAnswered = false;
        }
        return questionAnswered;
    }

    // Hides soft keyboard on click
    public void onHideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                           imm.hideSoftInputFromWindow(v.getWindowToken(),0);
    }

    // Clears all checked fields and text entered fields
    private void clearFields() {
        for (int i = 0; i < checkBoxes.length; i++) {
            if(checkBoxes[i] != null) {
                if(checkBoxes[i].isChecked()){
                    checkBoxes[i].setChecked(false);
                }
            }
        }
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group);
        group.clearCheck();
        EditText textField = (EditText) findViewById(R.id.edit_text_input);
        textField.setText("");
    }

    private void submitAnswers() {
        Intent toSubmitScreen = new Intent(this, SubmitActivity.class);
                toSubmitScreen.putExtra("score", score);
                toSubmitScreen.putExtra("name", name);

        startActivity(toSubmitScreen);
    }
}
