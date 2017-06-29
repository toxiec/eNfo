package enfo.android.mc.fhooe.at.enfo.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.ParticipantLineupAdapter;
import enfo.android.mc.fhooe.at.enfo.Adapter.SectionsPagerAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Fragments.ParticipantLineUpFragment;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

public class ParticipantActivity extends AppCompatActivity {
    private Tournament mTournament;
    private Discipline mDiscipline;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);
        if (NetworkCheck.isNetworkAvailable(this)) {
            mTournament = EntityManager.getInstance().getCurrentTournament();
            mDiscipline = EntityManager.getInstance().getCurrentDiscipline();

            mViewPager = (ViewPager) findViewById(R.id.container_participant_activity);
            setUpViewPager(mViewPager);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabL_participant);
            tabLayout.setupWithViewPager(mViewPager);
            getSupportActionBar().setTitle(EntityManager.getInstance().getCurrentParticipant().getmName());

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpViewPager(ViewPager _viewPager){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ParticipantLineUpFragment lineUpFragment = new ParticipantLineUpFragment();
        mSectionsPagerAdapter.addFragment(lineUpFragment, "Lineup");

        _viewPager.setAdapter(mSectionsPagerAdapter);
        //todo Matches des Teams anzeigen
        //todo Mitspieler anzeigen

    }
}
