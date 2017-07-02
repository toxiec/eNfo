package enfo.android.mc.fhooe.at.enfo.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import enfo.android.mc.fhooe.at.enfo.Adapter.SectionsPagerAdapter;
import enfo.android.mc.fhooe.at.enfo.Database.DatabaseHandler;
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
import enfo.android.mc.fhooe.at.enfo.Support.AndroidDatabaseManager;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

public class TournamentActivity extends AppCompatActivity{
    private static final String TAG = "Tournament Activity";
    private static final String TOURNAMENT_KEY = "tournament_key";
    private Menu mMenu;
    private MenuItem mMenuItem;
    private Tournament mTournament;
    private boolean tournamentIsInDatabase;


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

            mTournament = EntityManager.getInstance().getCurrentTournament();
                    //Log.i(TAG, mTournament.getmName());

            mDiscipline = EntityManager.getInstance().getCurrentDiscipline();
            mDisciplineImage = (ImageView) findViewById(R.id.app_bar_image_tournament_activity);
            mViewPager = (ViewPager) findViewById(R.id.container_tournament_activity);
            setUpViewPager(mViewPager);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabL_tournament);
            tabLayout.setupWithViewPager(mViewPager);

            getSupportActionBar().setTitle(EntityManager.getInstance().getCurrentTournament().getmName());
            setUpImageView();
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mMenu = menu;
        mMenuItem = menu.findItem(R.id.action_favorite);
        DatabaseHandler db_local = new DatabaseHandler(this);
        Tournament tournament = db_local.getTournament(EntityManager.getInstance().getCurrentTournament().getmID());
        if(tournament!=null){
            tournamentIsInDatabase = true;
            mMenuItem.setIcon(R.drawable.ic_favorite_white);
        }else{
            tournamentIsInDatabase = false;
            mMenuItem.setIcon(R.drawable.ic_favorite_border_white);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DatabaseHandler db_local = new DatabaseHandler(this);
        switch (item.getItemId()){
            case R.id.action_favorite: {
                if(tournamentIsInDatabase){
                    db_local.deleteRow(EntityManager.getInstance().getCurrentTournament().getmID());
                    mMenuItem.setIcon(R.drawable.ic_favorite_border_white);
                    tournamentIsInDatabase = false;
                    Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show();

                }else{
                    db_local.addTournament(EntityManager.getInstance().getCurrentTournament());
                    tournamentIsInDatabase=true;
                    mMenuItem.setIcon(R.drawable.ic_favorite_white);
                    Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show();

                }
                break;
            }
            case R.id.db_helper: {
                Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        }
        return true;
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
                case rainbowsix_siege:{
                    Glide.with(this).load(R.drawable.app_bar_image_rss).into(mDisciplineImage);
                    break;
                }
                case halo5_guardians:{
                    Glide.with(this).load(R.drawable.app_bar_image_halo5).into(mDisciplineImage);
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
     */
    private void setUpViewPager(ViewPager _viewPager){

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        TournamentInformationFragment tournamentInformationFragment = new TournamentInformationFragment();
        mSectionsPagerAdapter.addFragment(tournamentInformationFragment, "Info");

        ParticipantFragment participantFragment = new ParticipantFragment();
        mSectionsPagerAdapter.addFragment(participantFragment, "Participants");

        MatchFragment matchFragment = new MatchFragment();
        mSectionsPagerAdapter.addFragment(matchFragment, "Matches");
        _viewPager.setAdapter(mSectionsPagerAdapter);
    }
}
