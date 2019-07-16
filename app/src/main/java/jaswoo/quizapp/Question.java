package jaswoo.quizapp;

import android.content.Context;

public class Question {
    private int mTextResId;
    private int mHintTextResId;


    public Question(int textResId, int hintTextResId) {
        mTextResId = textResId;
        mHintTextResId = hintTextResId;
    }

    public int getTextResId()
    {
        return mTextResId;
    }

    public void setTextResId(int textResId)
    {
        mTextResId = textResId;
    }

    public int getHintTextResId()
    {
        return mHintTextResId;
    }

    public void setHintTextResId(int hintTextResId)
    {
        mHintTextResId = hintTextResId;
    }

    public String getText(Context ctx)
    {
        return ctx.getString(mTextResId);
    }
    //STUB
    public String getAnswerText(Context cxt)
    {
        return "";
    }

    public boolean checkAnswer(boolean boolResponse)
    {
        return false;
    }

    public boolean checkAnswer(String userAnswer)
    {
        return false;
    }

    public boolean checkAnswer(int ans)
    {
        return false;
    }
    //stub method; call this method to determine whether question is a TF question
    //Read more on STUBS


    public boolean isTrueFalseQuestion()
    {
        return false;
    }
    public boolean isFillTheBlankQuestion()
    {
        return false;
    }

    public boolean isMultipleChoiceQuestion()
    {
        return false;
    }

}
