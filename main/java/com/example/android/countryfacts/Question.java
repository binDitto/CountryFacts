package com.example.android.countryfacts;

import java.lang.reflect.Array;

/**
 * Created by Bin on 10/03/2018.
 */

public class Question {

    public String[] answers;
    private boolean radioButton, checkBox, editText;
    private String question;
    private int image;

    // CONSTRUCTOR
    public Question(String[] a, String q) {
        answers = a;
        question = q;

        // set answer types
        if (answers.length == 4) {
            radioButton = true;
        } else if (answers.length == 5) {
            checkBox = true;
        } else if (answers.length == 1) {
            editText = true;
        }
    }

    public boolean isRadioButton(){
        return radioButton;
    }

    public boolean isCheckBox(){
        return checkBox;
    }

    public boolean isEditText(){ return editText; }

    public String getQuestion(){
        return question;
    }

    public void setImage(int i) { image = i; }

    public boolean hasImage() {
        if (image != 0) {
            return true;
        } else {
            return false;
        }
    }

    public int getImage() { return image; }


}
