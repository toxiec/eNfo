package enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.ParticipantHolder;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantHolder>{
    private Context mContext;
    private int mItemResource;
    private List<Participant> mParticipantList;
    public ParticipantAdapter(Context _context, int _itemResource, List<Participant> _list){
        mContext = _context;
        mItemResource = _itemResource;
        mParticipantList = _list;
    }

    @Override
    public ParticipantHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new ParticipantHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ParticipantHolder holder, int position) {
        Participant participant = mParticipantList.get(position);
        holder.bindTournament(participant);
    }

    @Override
    public int getItemCount() {
        return mParticipantList.size();
    }
}