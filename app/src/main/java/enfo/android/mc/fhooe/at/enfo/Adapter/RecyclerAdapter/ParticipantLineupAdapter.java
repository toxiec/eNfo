package enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.ParticipantLineupHolder;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;

/**
 *Provide a binding from ParticipantLineup to views that are displayed within a RecyclerView.
 */
public class ParticipantLineupAdapter extends RecyclerView.Adapter<ParticipantLineupHolder> {
    private Context mContext;
    private int mItemResource;

    public ParticipantLineupAdapter(Context _context, int _itemResource){
        mContext = _context;
        mItemResource = _itemResource;
    }
    @Override
    public ParticipantLineupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new ParticipantLineupHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ParticipantLineupHolder holder, int position) {
        Player player = EntityManager.getInstance().getPlayerList().get(position);
        holder.bindPlayer(player);
    }

    @Override
    public int getItemCount() {
        return EntityManager.getInstance().getPlayerList().size();
    }
}
