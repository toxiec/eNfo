package enfo.android.mc.fhooe.at.enfo.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.DisciplineAdapter;
import enfo.android.mc.fhooe.at.enfo.Adapter.SectionsPagerAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.AsyncTask.JSONTask;
import enfo.android.mc.fhooe.at.enfo.Fragments.DisciplineFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.FavoriteFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.FeaturedTournamentFragment;
import enfo.android.mc.fhooe.at.enfo.Fragments.RunningFragment;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.Support.ItemClickSupport;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 *  Class which contains a Tab Layout to Display Disciplines and Favorite Tournaments
 */
public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";

    /**Adapter which returns Fragment for Tabs*/
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**The {@link ViewPager} that will host the section contents.*/
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(NetworkCheck.isNetworkAvailable(this)){
            mViewPager = (ViewPager) findViewById(R.id.container_main_activity);
            setUpViewPager(mViewPager);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabL_activity_main);
            tabLayout.setupWithViewPager(mViewPager);
        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * Create SectionsPagerAdapter and add Fragments to it
     * @param _viewPager
     */
    private void setUpViewPager(ViewPager _viewPager){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        DisciplineFragment disciplineFragment = new DisciplineFragment();
        mSectionsPagerAdapter.addFragment(disciplineFragment, "Discipline");

        FavoriteFragment favoriteFragment = new FavoriteFragment();
        mSectionsPagerAdapter.addFragment(favoriteFragment, "Favorites");

        _viewPager.setAdapter(mSectionsPagerAdapter);
    }
}

