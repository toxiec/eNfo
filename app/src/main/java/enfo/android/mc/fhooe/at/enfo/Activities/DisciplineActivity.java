package enfo.android.mc.fhooe.at.enfo.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Fragments.FeaturedTournamentFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.RunningFragment;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 * Class which contains a Tab Layout to Display Featured, Running and Completed Tournaments for
 * a specific Discipline
 */
public class DisciplineActivity extends AppCompatActivity {

    private static final String TAG = "DisciplineActivity";

    /**Key to get Discipline Data from the Bundle */
    private static final String DISCIPLINE_KEY = "discipline_key";
    /** Discipline which was selected from MainActivity ListView*/
    Discipline mDiscipline;
    /**App Bar Image*/
    ImageView mDisciplineImage;
    /**Adapter which returns Fragment for Tabs*/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**The {@link ViewPager} that will host the section contents.*/
    private ViewPager mViewPager;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discipline);

        //Check if Network Connection is available
        if(NetworkCheck.isNetworkAvailable(this)){
            Bundle bundle = getIntent().getExtras();

            if(bundle!=null){
                if(bundle.containsKey(DISCIPLINE_KEY)){
                    mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                    //Log.i(TAG, mDiscipline.getmFullname());
                }
            }


            mDisciplineImage = (ImageView) findViewById(R.id.app_bar_image);
            mViewPager = (ViewPager) findViewById(R.id.container);
            setUpViewPager(mViewPager, bundle);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabL_discipline);
            tabLayout.setupWithViewPager(mViewPager);

            //setupCollapsingToolbar();
            //setupToolbar();
            setUpImageView();
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets the Image view in the Toolbar to the right Picture, depending on which game was selected
     */
    private void setUpImageView(){
        if(mDiscipline != null){
            switch(mDiscipline.getmId()){
                case "counterstrike_go":{
                    Glide.with(this).load(R.drawable.app_bar_image_csgo).into(mDisciplineImage);
                    break;
                }
                case "leagueoflegends":{
                    Glide.with(this).load(R.drawable.app_bar_image_lol).into(mDisciplineImage);
                    break;
                }
                case "dota2":{
                    Glide.with(this).load(R.drawable.app_bar_image_dota2).into(mDisciplineImage);
                    break;
                }
                case "hearthstone":{
                    Glide.with(this).load(R.drawable.app_bar_image_hearthstone).into(mDisciplineImage);
                    break;
                }
                case "starcraft2_lotv":{
                    Glide.with(this).load(R.drawable.app_bar_image_sc2).into(mDisciplineImage);
                    break;
                }
                case "overwatch":{
                    Glide.with(this).load(R.drawable.app_bar_image_ow).into(mDisciplineImage);
                    break;
                }
                case "heroesofthestorm":{
                    Glide.with(this).load(R.drawable.app_bar_image_hots).into(mDisciplineImage);
                    break;
                }

                default:{
                    Glide.with(this).load(R.drawable.app_bar_image_csgo).into(mDisciplineImage);
                    break;
                }
            }
        }
    }

    /**
     * Create SectionsPagerAdapter and add Fragments to it
     * @param _viewPager
     * @param _bundle which contains a Discipline Object from the MainActivity
     */
    private void setUpViewPager(ViewPager _viewPager, Bundle _bundle){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        FeaturedTournamentFragment featuredTournamentFragment = new FeaturedTournamentFragment();
        featuredTournamentFragment.setArguments(_bundle);
        mSectionsPagerAdapter.addFragment(featuredTournamentFragment, "Featured");

        RunningFragment runningTournaments = new RunningFragment();
        runningTournaments.setArguments(_bundle);
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
}
