package jaswoo.quizapp;

import android.content.Context;

public class MultipleChoiceQuestion extends Question
{
    private int mOptionsResId;
    private int mAnswer;


    public MultipleChoiceQuestion(int textResId, int hintResId, int optionsResId, int ans)
    {
        super(textResId, hintResId);
        mOptionsResId = optionsResId;
        mAnswer = ans;
    }

    @Override
    public boolean checkAnswer(int answer)
    {
        boolean foundAnswer = false;
        if (mAnswer == answer)
        {
            return true;
        }
        return false;
    }

    public int getOptionsResId()
    {
        return mOptionsResId;
    }

    @Override
    public boolean isMultipleChoiceQuestion()
    {
        return true;
    }

    @Override
    public String getAnswerText(Context ctx)
    {
        String[] options = ctx.getResources().getStringArray(mOptionsResId);
        return options[mAnswer];
    }
}

