package jaswoo.quizapp;

import android.content.Context;

public class TrueFalseQuestion extends Question
{
    private boolean mAnswer;

    public TrueFalseQuestion(int textResId, int hintTextResId, boolean ans)
    {
        super(textResId, hintTextResId);
        mAnswer = ans;
    }

    @Override
    public boolean checkAnswer(boolean ans)
    {
        return mAnswer == ans;
    }
    public boolean isTrueFalseQuestion()
    {
        return true;
    }
    @Override
    public String getAnswerText(Context ctx)
    {
        return "" + mAnswer;
    }

}
