package enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.MatchHolder;
import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.TournamentHolder;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;

/**
 * Created by David on 26.06.2017.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchHolder> {
    List<Match> mMatchList;
    private Context mContext;
    private int mItemResource;
    private Discipline mDiscipline;

    public MatchAdapter(Context _context, int _itemResource,List<Match> _list){
        mContext = _context;
        mItemResource = _itemResource;
        mMatchList = _list;
    }

    @Override
    public MatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new MatchHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(MatchHolder holder, int position) {
        Match match = mMatchList.get(position);
        holder.bindMatch(match, mDiscipline);
    }

    @Override
    public int getItemCount() {
        return mMatchList.size();
    }
}
