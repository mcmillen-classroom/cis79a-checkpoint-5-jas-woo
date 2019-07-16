package jaswoo.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener
{
    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView mTextView;
    private TextView mFeedback;
    private TextView mScoreTextView;
    private EditText mEditText;

    private LinearLayout mTrueFalseContainer;
    private LinearLayout mFillTheBlankContainer;
    private LinearLayout mMultipleChoiceContainer;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private Button mHintButton;
    private Button mCheckButton;

    private RadioGroup mMCButtons;
    private RadioButton mAButton;
    private RadioButton mBButton;
    private RadioButton mCButton;
    private RadioButton mDButton;
    private Button mMCCheckButton;


    private Question[] mQuestions;
    private int mIndex;
    private int mScore;

    private Feedback[] mFeedbacks;
    private int mFIndex;

    private Button mCheatButton;
    private boolean mCheated;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);
        mFeedback = (TextView) findViewById(R.id.feedback_textview);
        mScoreTextView = (TextView) findViewById(R.id.score_text_view);
        mEditText = (EditText) findViewById(R.id.edit_text);

        mTrueFalseContainer = (LinearLayout) findViewById(R.id.true_false_container);
        mFillTheBlankContainer = (LinearLayout) findViewById(R.id.fill_the_blank_container);
        mMultipleChoiceContainer = (LinearLayout) findViewById(R.id.multiple_choice_container);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);
        mHintButton = (Button) findViewById(R.id.hint_button);
        mCheckButton = (Button) findViewById(R.id.check_button);

        mMCButtons = (RadioGroup) findViewById(R.id.mc_buttons);
        mAButton = (RadioButton) findViewById(R.id.a_button);
        mBButton = (RadioButton) findViewById(R.id.b_button);
        mCButton = (RadioButton) findViewById(R.id.c_button);
        mDButton = (RadioButton) findViewById(R.id.d_button);

        mCheatButton = (Button) findViewById(R.id.cheat_button);

        mTrueButton.setOnClickListener(this);
        mFalseButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
        mPreviousButton.setOnClickListener(this);
        mHintButton.setOnClickListener(this);
        mCheckButton.setOnClickListener(this);

        mMCButtons.setOnCheckedChangeListener(this);

        mCheatButton.setOnClickListener(this);

        //INITIALIZE AN ARRAY OF QUESTIONS
        mQuestions = new Question[7];
        mIndex = 0;
        mScore = 0;
        //INITIALIZE EACH SLOT IN THE ARRAY
        mQuestions[0] = new TrueFalseQuestion(R.string.question_1, R.string.question_1_hint, true);
        mQuestions[1] = new TrueFalseQuestion(R.string.question_2, R.string.question_2_hint, false);
        mQuestions[2] = new TrueFalseQuestion(R.string.question_3, R.string.question_3_hint, false);
        mQuestions[3] = new TrueFalseQuestion(R.string.question_4, R.string.question_4_hint, true);
        mQuestions[4] = new TrueFalseQuestion(R.string.question_5, R.string.question_5_hint, false);

        String[] q6Answers = getResources().getStringArray(R.array.question_6_answers);
        mQuestions[5] = new FillTheBlankQuestion(R.string.question_6, R.string.question_6_hint,q6Answers);

        mQuestions[6] = new MultipleChoiceQuestion(R.string.question_7, R.string.question_7_hint, R.array.question_7_answers, 2);

        //Set-up the first question
        setupQuestion();

        mFeedbacks = new Feedback[7];
        mFIndex = 0;

        mFeedbacks[0] = new Feedback(R.string.feedback_1);
        mFeedbacks[1] = new Feedback(R.string.feedback_2);
        mFeedbacks[2] = new Feedback(R.string.feedback_3);
        mFeedbacks[3] = new Feedback(R.string.feedback_4);
        mFeedbacks[4] = new Feedback(R.string.feedback_5);
        mFeedbacks[5] = new Feedback(R.string.feedback_5);
        mFeedbacks[6] = new Feedback(R.string.feedback_6);

        mFeedback.setText(mFeedbacks[mFIndex].getFeedbackResId());
        mFeedback.setVisibility(View.GONE);

        mScoreTextView.setText(getString(R.string.score, mScore));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {
        if (resultCode != RESULT_OK)
        {
            return;
        }

        if (resultCode == REQUEST_CODE_CHEAT && resultData != null)
        {
            mCheated = CheatActivity.didCheat(resultData);
        }
    }

    @Override
    public void onClick(View view)
    {

        if (view.getId() == R.id.true_button)
        {
            if (checkAnswer(true) == true)
            {
                mScore++;
                mScoreTextView.setText(getString(R.string.score, mScore));
            }
            mFeedback.setVisibility(View.VISIBLE);
        }
        else if (view.getId() == R.id.false_button)
        {
            if (checkAnswer(false))
            {
                mScore++;
                mScoreTextView.setText(getString(R.string.score, mScore));

            }
            mFeedback.setVisibility(View.VISIBLE);
        }
        else if (view.getId() == R.id.check_button)
        {
            if (checkAnswer(mEditText.getText().toString()))
            {
                mScore++;
                mScoreTextView.setText(getString(R.string.score, mScore));
            }
        }
        else if (view.getId() == R.id.next_button)
        {
            mFeedback.setVisibility(View.GONE);
            //CHANGE TO NEXT QUESTION
            //INCREMENT THE INDEX BY ONE

            mIndex++;
            mFIndex++;



            if (mIndex < mQuestions.length)
            {
                //DO IF STATEMENT HERE:
                //IF mINDEX IS GREATER THAN OR EQUAL TO mQUESTIONS.LENGTH
                // set mIndex to 0
                // -or- end the quiz
                //CREATE A SCORE COUNTER
                // could hide views at end of Quiz


                //CHANGE TEXT IN VIEW
                setupQuestion();
                mTextView.setText(mQuestions[mIndex].getTextResId());
                mFeedback.setText(mFeedbacks[mFIndex].getFeedbackResId());
            }
            else
            {
                mTrueFalseContainer.setVisibility(View.GONE);
                mFillTheBlankContainer.setVisibility(View.GONE);
                mMultipleChoiceContainer.setVisibility(View.GONE);
                mNextButton.setVisibility(View.GONE);
                mPreviousButton.setVisibility(View.GONE);
                mHintButton.setVisibility(View.GONE);
                mTextView.setText(R.string.question_end);
                mFeedback.setVisibility(view.VISIBLE);
                mFeedback.setText(R.string.feedback_end);
            }
        }
        else if (view.getId() == R.id.previous_button)
        {
            mFeedback.setVisibility(View.GONE);

            //CHANGE TO PREVIOUS QUESTION
            mIndex--;
            mFIndex--;

            setupQuestion();


            if (mIndex < 0)
            {
                mIndex = 0;
                mFIndex = 0;
            }
            mTextView.setText(mQuestions[mIndex].getTextResId());
            mFeedback.setText(mFeedbacks[mFIndex].getFeedbackResId());

        }
        else if (view.getId() == R.id.hint_button)
        {
            Toast myToast = Toast.makeText(this, mQuestions[mIndex].getHintTextResId(), Toast.LENGTH_LONG);
            myToast.show();
        }
        else if (view.getId() == R.id.cheat_button)
        {
            //TODO:: LAUNCH CHEAT ACTIVITY
            Intent cheatIntent = CheatActivity.newIntent(this, mQuestions[mIndex]);
            startActivityForResult(cheatIntent, REQUEST_CODE_CHEAT);

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
//        boolean checked = ((RadioButton) view).isChecked();

        if (checkedId == -1)
        {
            return;
        }

        boolean answerIsCorrect = false;

        switch (checkedId)
        {
            case R.id.a_button:
                answerIsCorrect = checkAnswer(0);
                    break;
            case R.id.b_button:
                answerIsCorrect = checkAnswer(1);
                    break;
            case R.id.c_button:
                answerIsCorrect = checkAnswer(2);
                break;
            case R.id.d_button:
                answerIsCorrect = checkAnswer(3);
                break;
        }

        if (answerIsCorrect)
        {
            mScore++;
            mScoreTextView.setText(getString(R.string.score, mScore));
        }
    }


    public void setupQuestion()
    {
        mTextView.setText(mQuestions[mIndex].getTextResId());

        if (mQuestions[mIndex].isTrueFalseQuestion())
        {
            mTrueFalseContainer.setVisibility(View.VISIBLE);
            mFillTheBlankContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        }
        else if (mQuestions[mIndex].isFillTheBlankQuestion())
        {
            mFillTheBlankContainer.setVisibility(View.VISIBLE);
            mTrueFalseContainer.setVisibility(View.GONE);
            mMultipleChoiceContainer.setVisibility(View.GONE);
        }
        else if (mQuestions[mIndex].isMultipleChoiceQuestion())
        {
            mMultipleChoiceContainer.setVisibility(View.VISIBLE);
            mTrueFalseContainer.setVisibility(View.GONE);
            mFillTheBlankContainer.setVisibility(View.GONE);

            int optionsResId = ((MultipleChoiceQuestion) mQuestions[mIndex]).getOptionsResId();
            String[] options = getResources().getStringArray(optionsResId);
            //USE OPTIONS ARRAY TO SET TEXT
            mAButton.setText(options[0]);
            mBButton.setText(options[1]);
            mCButton.setText(options[2]);
            mDButton.setText(options[3]);

        }
    }

    public boolean checkAnswer(int userInput)
    {
        if (mCheated)
        {
            Toast.makeText(this, R.string.cheat_shame, Toast.LENGTH_LONG);
            return false;
        }
        if (mQuestions[mIndex].checkAnswer(userInput))
        {
            Toast myToast = Toast.makeText(this, "YOU ARE CORRECT!", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
            return true;
        }
        else
        {
            Toast myToast = Toast.makeText(this, "You are incorrect😟", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
            return false;
        }
    }

    public boolean checkAnswer(boolean userInput)
    {
        if (mCheated)
        {
            Toast.makeText(this, R.string.cheat_shame, Toast.LENGTH_LONG);
            return false;
        }
        else if (mQuestions[mIndex].checkAnswer(userInput))
        {
            Toast myToast = Toast.makeText(this, "YOU ARE CORRECT!", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
            return true;
        }
        else
        {
            Toast myToast = Toast.makeText(this, "You are incorrect😟", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
            return false;
        }
    }
    public boolean checkAnswer(String userInput)
    {
        if (mCheated)
        {
            Toast.makeText(this, R.string.cheat_shame, Toast.LENGTH_LONG);
            return false;
        }
        else if (mQuestions[mIndex].checkAnswer(userInput))
        {
            Toast myToast = Toast.makeText(this, "YOU ARE CORRECT!", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
            return true;
        }
        else
        {
            Toast myToast = Toast.makeText(this, "You are incorrect😟", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.TOP, 0, 0);
            myToast.show();
            return false;
        }
    }
}

