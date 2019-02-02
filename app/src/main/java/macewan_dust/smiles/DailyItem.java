package macewan_dust.smiles;

import android.support.v4.app.Fragment;

public class DailyItem {

    private int mIconID;
    private String mTitle;
    private String mSubtitle;
    private int mBackgroundID;
    private Fragment mFragment;

    /**
     * constructor
     * @param iconID
     * @param title
     * @param subtitle
     * @param fragment
     */
    public DailyItem(int iconID, int backgroundID, String title, String subtitle, Fragment fragment) {
        mIconID = iconID;
        mTitle = title;
        mSubtitle = subtitle;
        mBackgroundID = backgroundID;
        mFragment = fragment;
    }

    public int getIconID() {
        return mIconID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public int getBackgroundID() {
        return mBackgroundID;
    }

    public Fragment getFragment() {
        return mFragment;
    }
}
