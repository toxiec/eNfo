package enfo.android.mc.fhooe.at.enfo;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.FeaturedTournament;
import enfo.android.mc.fhooe.at.enfo.Fragments.FeaturedTournamentFragment;

public class DisciplineActivity extends AppCompatActivity {

    private static final String TAG = "DisciplineActivity";

    private static final String DISCIPLINE_KEY = "discipline_key";
    /** Discipline which was selected from MainActivity ListView*/
    Discipline mDiscipline;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline);

        Bundle bundle = getIntent().getExtras();
        //Intent i = getIntent();
        //mDiscipline = (Discipline) i.getSerializableExtra("discipline");

        //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CTL_discipline);
        //collapsingToolbarLayout.setTitle(mDiscipline.getmName());
        //ImageView disciplineImage = (ImageView) findViewById(R.id.app_bar_image);
        //disciplineImage.setImageResource(R.drawable.app_bar_image_csgo);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager, bundle);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabL_discipline);
        tabLayout.setupWithViewPager(mViewPager);


    }

    /**
     * Create SectionsPagerAdapter and add Fragments to it
     * @param _viewPager
     */
    private void setUpViewPager(ViewPager _viewPager, Bundle bundle){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        FeaturedTournamentFragment featuredTournamentFragment = new FeaturedTournamentFragment();
        featuredTournamentFragment.setArguments(bundle);
        mSectionsPagerAdapter.addFragment(featuredTournamentFragment, "Featured");


        //mSectionsPagerAdapter.addFragment(featuredTournamentFragment, "Public");
        //mSectionsPagerAdapter.addFragment(featuredTournamentFragment, "Finished");
        _viewPager.setAdapter(mSectionsPagerAdapter);
    }

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

        @Override
        public Fragment getItem(int position) {
            /*switch (position){
                case 0:
                    return new FeaturedTournamentFragment();
                case 1:
                    return new FeaturedTournamentFragment();
                case 2:
                    return new FeaturedTournamentFragment();
                default:
                    return null;
            }*/
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            //return 3;
            return mFragmentList.size();
        }

        public void addFragment(Fragment _fragment, String _title){
            mFragmentList.add(_fragment);
            mFragmentTitleList.add(_title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            /*switch (position){
                case 0:
                    return "Featured";
                case 1:
                    return "Public";
                case 2:
                    return "Ended";
                default:
                    return null;
            }*/
            return mFragmentTitleList.get(position);
        }
    }
}
