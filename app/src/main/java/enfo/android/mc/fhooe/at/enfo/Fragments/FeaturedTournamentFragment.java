package enfo.android.mc.fhooe.at.enfo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import enfo.android.mc.fhooe.at.enfo.Adapter.FeaturedTournamentAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.R;


public class FeaturedTournamentFragment extends Fragment {
    /**Key which is used to receive the passed Discipline Object from DisciplineActivity*/
    private static final String TAG = "FTF";
    private static final String DISCIPLINE_KEY = "discipline_key";
    private Discipline mDiscipline;
    private ListView mFeaturedMatchesListView;
    private FeaturedTournamentAdapter mFeaturedTournamentAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_featured_tournament, container, false);

        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.containsKey(DISCIPLINE_KEY)){
                mDiscipline = (Discipline) bundle.getSerializable(DISCIPLINE_KEY);
                Log.i(TAG, mDiscipline.getmFullname());
            }
        }
        //Log.i(TAG, mDiscipline.getmId());
        mFeaturedMatchesListView = (ListView) view.findViewById(R.id.lv_featuredMatches);
        mFeaturedTournamentAdapter = new FeaturedTournamentAdapter(getActivity(), R.layout.featured_tournament_row_layout);
        mFeaturedMatchesListView.setAdapter(mFeaturedTournamentAdapter);
        return view;
    }
}
