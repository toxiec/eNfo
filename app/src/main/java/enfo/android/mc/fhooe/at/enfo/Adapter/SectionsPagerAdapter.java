package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Get Fragment from position
     * @param position
     * @return Fragment
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Get number of Fragments in the List
     * @return
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /**
     * Add a Fragment to the List
     * @param _fragment which should be added
     * @param _title which will be displayed on Tab bar
     */
    public void addFragment(Fragment _fragment, String _title){
        mFragmentList.add(_fragment);
        mFragmentTitleList.add(_title);
    }

    /**
     * Get the Title from Fragment
     * @param position
     * @return Title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
