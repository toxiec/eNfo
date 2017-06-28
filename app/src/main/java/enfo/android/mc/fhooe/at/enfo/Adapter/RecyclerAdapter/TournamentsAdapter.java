package enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.TournamentHolder;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentType;


public class TournamentsAdapter extends RecyclerView.Adapter<TournamentHolder> {
    private Context mContext;
    private int mItemResource;
    private Discipline mDiscipline;
    private TournamentType mType;

    public TournamentsAdapter(Context _context, int _itemResource, Discipline _discipline, TournamentType _type){
        mContext = _context;
        mItemResource = _itemResource;
        mDiscipline = _discipline;
        mType = _type;
    }

    @Override
    public TournamentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new TournamentHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(TournamentHolder holder, int position) {
        Tournament tournament = EntityManager.getInstance().getTournamentList(mType).get(position);
        holder.bindTournament(tournament, mDiscipline);
    }

    @Override
    public int getItemCount() {
        return EntityManager.getInstance().getTournamentList(mType).size();
    }

}
