package macewan_dust.smiles;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.util.UUID;

public class InputLaughterFragment extends Fragment {

    private static final String TAG = "InputLaughterFragment";
    private static final int NO_SELECTION = 104000;

    ImageView mIcon1;
    ImageView mIcon2;
    ImageView mIcon3;
    ImageView mIcon4;
    ImageView mIcon5;
    Button mButton;
    TextView mResults;

    int mQuestion_A_index;

    Score mScore;
    CountDownTimer mExitCountDownTimer;

    /**
     * new instance constructor
     *
     * @return InputSleepFragment
     */
    public static InputLaughterFragment newInstance() {
        return new InputLaughterFragment();
    }

    /**
     * Android will call this function when a view is created. If the view is not destroyed, this
     * remains in effect (not that rotating destroys a view.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.

    }

    /**
     * Android calls this method whenever the view is created. So if the view was on the back
     * stack, this code will be called again when it becomes visible again and calls this code again.
     *
     * @param inflater           - infaltes the view (brings it into memory)
     * @param container          - parent view
     * @param savedInstanceState - holds data
     * @return view
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_daily_laughter_questions, container, false);

    //    getActivity().setTitle(R.string.title_quest_laughter);

        mButton = v.findViewById(R.id.score_button);

        mIcon1 = v.findViewById(R.id.icon_1a);
        mIcon2 = v.findViewById(R.id.icon_1b);
        mIcon3 = v.findViewById(R.id.icon_1c);
        mIcon4 = v.findViewById(R.id.icon_1d);
        mIcon5 = v.findViewById(R.id.icon_1e);

        mQuestion_A_index = NO_SELECTION;

        mIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 1;
                mIcon1.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 2;
                mIcon2.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 3;
                mIcon3.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 4;
                mIcon4.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelected();
                mQuestion_A_index = 5;
                mIcon5.setBackground(getResources().getDrawable(R.drawable.border_image_selected));
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mQuestion_A_index == NO_SELECTION) {
                    mResults.setText(getString(R.string.feedback_unselected));

                } else {

                    int score = ScoringAlgorithms.scoreLaughter(mQuestion_A_index);

                    int scoreStringID = 1000; // just here to initialize
                    Log.d(TAG, "Score: " + score);
                    switch (score) {

                        case ScoringAlgorithms.SCORE_OVER:
                            scoreStringID = R.string.score_high;
                            break;
                        case ScoringAlgorithms.SCORE_UNDER:
                            scoreStringID = R.string.score_low;
                            break;
                        case ScoringAlgorithms.SCORE_BALANCED:
                            scoreStringID = R.string.score_balanced;
                            break;
                        case ScoringAlgorithms.SCORE_UNBALANCED:
                            scoreStringID = R.string.score_unbalanced;
                            break;
                        case ScoringAlgorithms.SCORE_ERROR:
                            Toast t1 = new Toast(getContext());
                            t1.setText("Error returned from scoring algorithm");
                            t1.show();
                            break;
                        default:
                            Toast t2 = new Toast(getContext());
                            t2.setText("Error in scoring");
                            t2.show();
                    }

                    // if score exists, update it, else make a new one and save it
                    if (ScoringLab.get(getActivity()).isScore(new Date())){
                        // get score object to use its data
                        mScore = ScoringLab.get(getActivity()).getScore(new Date());
                        // set new score for this category
                        mScore.setLaughterScore(score);
                        // save score to database
                        ScoringLab.get(getActivity()).updateScore(mScore);
                        //
                    } else {
                        Log.d(TAG, "score NOT found by date: " + Score.timelessDate(new Date()));
                        mScore = new Score();
                        mScore.setLaughterScore(score);
                        ScoringLab.get(getActivity()).addScore(mScore);
                    }
                    mResults.setText(getString(R.string.quest_results, getString(scoreStringID)));
                    exitOnLastScore();
                }
            }
        });

        mResults = v.findViewById(R.id.text_score); // note this object is invisible
        mResults.setVisibility(View.VISIBLE);

        return v;
    }

    /**
     * exits out of the question fragment if all questions have been answered.
     */
    private void exitOnLastScore(){
        if (ScoringLab.get(getActivity()).getScore(new Date()).isAllScored()){
            Log.d(TAG, "all questions answered. popping out");

            mExitCountDownTimer = new CountDownTimer(3000, 3000) {

                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
            }.start();
        }
    }

    /**
     * cancel timer if the fragment is exited
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "stopped");
        if (mExitCountDownTimer != null) {
            mExitCountDownTimer.cancel();
            Log.d(TAG, "cancelling exit timer");
        }
    }

    private void clearSelected() {
        mIcon1.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon2.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon3.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon4.setBackground(getResources().getDrawable(R.drawable.border_image));
        mIcon5.setBackground(getResources().getDrawable(R.drawable.border_image));
    }
};
