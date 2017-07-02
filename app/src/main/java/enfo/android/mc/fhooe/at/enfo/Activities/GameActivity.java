package enfo.android.mc.fhooe.at.enfo.Activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.GameAdapter;
import enfo.android.mc.fhooe.at.enfo.Model.ChangeEvent;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Model.ModelChangeListener;
import enfo.android.mc.fhooe.at.enfo.R;
import enfo.android.mc.fhooe.at.enfo.Support.NetworkCheck;

public class GameActivity extends AppCompatActivity implements ModelChangeListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private GameAdapter mGameAdapter;
    private RecyclerView mGameRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(NetworkCheck.isNetworkAvailable(this)){
            EntityManager.getInstance().addModelChangeListener(this);

            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_games);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    EntityManager.getInstance().requestGame();
                }
            });
            mGameRecycleView = (RecyclerView) findViewById(R.id.rv_games);
            EntityManager.getInstance().requestGame();
            mGameAdapter = new GameAdapter(this, R.layout.item_game);
            mGameRecycleView.setAdapter(mGameAdapter);
            mGameRecycleView.setLayoutManager(new LinearLayoutManager(this));

        }else{
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChangeOccured(ChangeEvent e) {
        switch (e.mEventType){
            case startDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isGameDownloading());
                break;
            }
            case finishDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isGameDownloading());
                mGameAdapter.notifyDataSetChanged();
                break;
            }
            case errorOnDownload: {
                mSwipeRefreshLayout.setRefreshing(EntityManager.getInstance().isGameDownloading());
                Toast.makeText(this, "Error on Download", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
