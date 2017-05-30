package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;


public class TournamentsAdapter extends RecyclerView.Adapter<TournamentHolder> {
    List<Tournament> mTournamentList;
    private Context mContext;
    private int mItemResource;
    private Discipline mDiscipline;

    public TournamentsAdapter(Context _context, int _itemResource, Discipline _discipline, List<Tournament> _list){
        mContext = _context;
        mItemResource = _itemResource;
        mDiscipline = _discipline;
        mTournamentList = _list;
    }

    @Override
    public TournamentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new TournamentHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(TournamentHolder holder, int position) {
        Tournament tournament = mTournamentList.get(position);
        holder.bindTournament(tournament, mDiscipline);
    }

    @Override
    public int getItemCount() {
        return mTournamentList.size();
    }

}
