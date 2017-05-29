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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.FeaturedTournament;
import enfo.android.mc.fhooe.at.enfo.Fragments.FeaturedTournamentFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.RunningFragment;

public class DisciplineActivity extends AppCompatActivity {

    private static final String TAG = "DisciplineActivity";
    private static final String DISCIPLINE_KEY = "discipline_key";
    //private static final String DISCIPLINE_KEY = "discipline_key";
    /** Discipline which was selected from MainActivity ListView*/
    Discipline mDiscipline;
    ImageView disciplineImage;
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

        if(NetworkCheck.isNetworkAvailable(this)){
            Bundle bundle = getIntent().getExtras();

            if(bundle!=null){
                if(bundle.containsKey(DISCIPLINE_KEY)){
                    mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                    //Log.i(TAG, mDiscipline.getmFullname());
                }
            }
            //CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.CTL_discipline);
            //collapsingToolbarLayout.setTitle(mDiscipline.getmName());
            disciplineImage = (ImageView) findViewById(R.id.app_bar_image);
            /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);*/
            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            setUpViewPager(mViewPager, bundle);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabL_discipline);
            tabLayout.setupWithViewPager(mViewPager);
            setUpImageView();
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpImageView(){
        if(mDiscipline != null){
            switch(mDiscipline.getmId()){
                case "counterstrike_go":{
                    disciplineImage.setImageResource(R.drawable.app_bar_image_csgo);
                    break;
                }
                case "leagueoflegends":{
                    disciplineImage.setImageResource(R.drawable.app_bar_image_lol);
                    break;
                }
                case "dota2":{
                    disciplineImage.setImageResource(R.drawable.app_bar_image_dota2);
                    break;
                }
                case "hearthstone":{
                    disciplineImage.setImageResource(R.drawable.app_bar_image_hearthstone);
                    break;
                }
                case "starcraft2_lotv":{
                    disciplineImage.setImageResource(R.drawable.app_bar_image_sc2);
                    break;
                }

                default:{
                    disciplineImage.setImageResource(R.drawable.app_bar_image_csgo);
                    break;
                }
            }
        }
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

        RunningFragment runningTournaments = new RunningFragment();
        runningTournaments.setArguments(bundle);
        mSectionsPagerAdapter.addFragment(runningTournaments, "Running");
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
