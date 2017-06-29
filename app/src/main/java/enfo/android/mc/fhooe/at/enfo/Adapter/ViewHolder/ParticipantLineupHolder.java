package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import enfo.android.mc.fhooe.at.enfo.Objects.Player;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 * Created by David on 29.06.2017.
 */

public class ParticipantLineupHolder  extends RecyclerView.ViewHolder {
    private Context mContext;
    private TextView mPlayerName;
    private TextView mPlayerCountry;

    public ParticipantLineupHolder(Context _context, View _itemView) {
        super(_itemView);
        mContext = _context;

        mPlayerName = (TextView) _itemView.findViewById(R.id.tv_player_playername);
        mPlayerCountry = (TextView) _itemView.findViewById(R.id.tv_player_country);
    }

    public void bindPlayer(Player _player){
        if(_player!=null){
            mPlayerName.setText(_player.getmName());
            mPlayerCountry.setText(_player.getmCountry());
        }
    }
}