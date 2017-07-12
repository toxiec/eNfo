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
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Objects.MatchType;

/**
 *Provide a binding from Match to views that are displayed within a RecyclerView.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchHolder> {
    private Context mContext;
    private int mItemResource;

    public MatchAdapter(Context _context, int _itemResource){
        mContext = _context;
        mItemResource = _itemResource;
    }

    @Override
    public MatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new MatchHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(MatchHolder holder, int position) {
        Match match = EntityManager.getInstance().getMatchesList().get(position);
        holder.bindMatch(match);
    }

    @Override
    public int getItemCount() {
        return EntityManager.getInstance().getMatchesList().size();
    }
}
