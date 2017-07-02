package enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.GameHolder;
import enfo.android.mc.fhooe.at.enfo.Entities.Game;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;

/**
 * Created by David on 02.07.2017.
 */

public class GameAdapter extends RecyclerView.Adapter<GameHolder> {
    private Context mContext;
    private int mItemResource;

    public GameAdapter(Context _context, int _itemResource){
        mContext = _context;
        mItemResource = _itemResource;
    }

    @Override
    public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new GameHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(GameHolder holder, int position) {
        Game game = EntityManager.getInstance().getGameList().get(position);
        holder.bindGame(game);
    }

    @Override
    public int getItemCount() {
        return EntityManager.getInstance().getGameList().size();
    }
}
