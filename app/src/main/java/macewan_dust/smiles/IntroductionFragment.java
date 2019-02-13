package macewan_dust.smiles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class IntroductionFragment extends Fragment {

    TextView mTitle;
    TextView mSubtitleTitle;
    TextView mBodyText;
    Button mNextButton;
    Button mBackButton;

    int i;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // prevents instance of the fragment from being destroyed on rotation.
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_introduction, container, false);

        getActivity().setTitle(R.string.title_introduction);

        mTitle = v.findViewById(R.id.introduction_title);
        mSubtitleTitle = v.findViewById(R.id.introduction_subtitle);
        mBodyText = v.findViewById(R.id.introduction_explanation);
        mNextButton = v.findViewById(R.id.button_introduction_next);
        mBackButton = v.findViewById(R.id.button_introduction_back);

        i = 0;
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i<3){
                    i++;
                }
                updateScreen();
            }
        });

        i = 0;
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i>0){
                    i--;
                }
                updateScreen();
            }
        });

        v.invalidate();
        return v;
    }


    private void updateScreen (){
        switch(i){
            case 0:
                mTitle.setText(R.string.introduction_title_smiles);
                mSubtitleTitle.setVisibility(View.VISIBLE);
                mSubtitleTitle.setText(R.string.introduction_subtitle_smiles);
                mBodyText.setText(R.string.introduction_explanation_smiles);
                break;
            case 1:
                mTitle.setText(R.string.introduction_title_stress1);
                mSubtitleTitle.setVisibility(View.INVISIBLE);
                mBodyText.setText(R.string.introduction_explanation_stress1);
                break;
            case 2:
                mTitle.setText(R.string.introduction_title_stress1);
                mSubtitleTitle.setVisibility(View.INVISIBLE);
              //  mSubtitleTitle.setText("");
                mBodyText.setText(R.string.introduction_explanation_stress2);
                break;
            case 3:
                mTitle.setText(R.string.introduction_title_balance);
                mSubtitleTitle.setVisibility(View.INVISIBLE);
                mBodyText.setText(R.string.introduction_explanation_balance);
                break;
        }

    }


}
