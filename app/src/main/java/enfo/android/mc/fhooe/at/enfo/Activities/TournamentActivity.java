package enfo.android.mc.fhooe.at.enfo.Activities;

import android.app.ActionBar;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import enfo.android.mc.fhooe.at.enfo.Adapter.SectionsPagerAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Fragments.FeaturedTournamentFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.MatchFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.ParticipantFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.RunningFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.TournamentInformationFragment;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

public class TournamentActivity extends AppCompatActivity{
    private static final String TAG = "Tournament Activity";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private Tournament mTournament;
    /**
     * Key to get Discipline Data from the Bundle
     */
    private static final String DISCIPLINE_KEY = "discipline_key";
    /**
     * Discipline which was selected from MainActivity ListView
     */
    Discipline mDiscipline;
    /**
     * App Bar Image
     */
    ImageView mDisciplineImage;
    /**
     * Adapter which returns Fragment for Tabs
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        if (NetworkCheck.isNetworkAvailable(this)) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                if (bundle.containsKey(TOURNAMENT_KEY)) {
                    mTournament = (Tournament) bundle.getSerializable(TOURNAMENT_KEY);
                    //Log.i(TAG, mTournament.getmName());

                }if(bundle.containsKey(DISCIPLINE_KEY)){
                    mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                }
            }

            mDisciplineImage = (ImageView) findViewById(R.id.app_bar_image_tournament_activity);
            mViewPager = (ViewPager) findViewById(R.id.container_tournament_activity);
            setUpViewPager(mViewPager, bundle);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabL_tournament);
            tabLayout.setupWithViewPager(mViewPager);

            getSupportActionBar().setTitle(EntityManager.getInstance().getCurrentTournament().getmName());
            setUpImageView();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets the Image view in the Toolbar to the right Picture, depending on which game was selected
     */
    private void setUpImageView() {
        if (mDiscipline != null) {
            switch (mDiscipline.getmId()) {
                case counterstrike_go: {
                    Glide.with(this).load(R.drawable.app_bar_image_csgo).into(mDisciplineImage);
                    break;
                }
                case leagueoflegends: {
                    Glide.with(this).load(R.drawable.app_bar_image_lol).into(mDisciplineImage);
                    break;
                }
                case dota2: {
                    Glide.with(this).load(R.drawable.app_bar_image_dota2).into(mDisciplineImage);
                    break;
                }
                case hearthstone: {
                    Glide.with(this).load(R.drawable.app_bar_image_hearthstone).into(mDisciplineImage);
                    break;
                }
                case starcraft2_lotv: {
                    Glide.with(this).load(R.drawable.app_bar_image_sc2).into(mDisciplineImage);
                    break;
                }
                case overwatch: {
                    Glide.with(this).load(R.drawable.app_bar_image_ow).into(mDisciplineImage);
                    break;
                }
                case heroesofthestorm: {
                    Glide.with(this).load(R.drawable.app_bar_image_hots).into(mDisciplineImage);
                    break;
                }

                default: {
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

        TournamentInformationFragment tournamentInformationFragment = new TournamentInformationFragment();
        tournamentInformationFragment.setArguments(_bundle);
        mSectionsPagerAdapter.addFragment(tournamentInformationFragment, "Info");

        ParticipantFragment participantFragment = new ParticipantFragment();
        participantFragment.setArguments(_bundle);
        mSectionsPagerAdapter.addFragment(participantFragment, "Participants");

        MatchFragment matchFragment = new MatchFragment();
        matchFragment.setArguments(_bundle);
        mSectionsPagerAdapter.addFragment(matchFragment, "Matches");
        _viewPager.setAdapter(mSectionsPagerAdapter);
    }
}
