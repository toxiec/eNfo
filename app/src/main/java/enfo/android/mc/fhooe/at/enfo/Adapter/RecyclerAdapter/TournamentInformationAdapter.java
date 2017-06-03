package enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.TournamentInformationHolder;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;

public class TournamentInformationAdapter extends RecyclerView.Adapter<TournamentInformationHolder> {
    List<TournamentInformationItem> mTournamentInformationList;
    private Context mContext;
    private int mItemResource;
    private Discipline mDiscipline;

    public TournamentInformationAdapter(Context _context, int _itemResource, Discipline _discipline, List<TournamentInformationItem> _list){
        mContext = _context;
        mItemResource = _itemResource;
        mTournamentInformationList = _list;
        mDiscipline = _discipline;
    }

    @Override
    public TournamentInformationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new TournamentInformationHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(TournamentInformationHolder holder, int position) {
        TournamentInformationItem item = mTournamentInformationList.get(position);
        holder.bindTournament(item, mDiscipline);
    }

    @Override
    public int getItemCount() {
        return mTournamentInformationList.size();
    }
}
