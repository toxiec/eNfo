package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import enfo.android.mc.fhooe.at.enfo.Entities.Game;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 * Created by David on 02.07.2017.
 */

public class GameHolder extends RecyclerView.ViewHolder {
    private TextView mRound;
    private TextView mParticipant1;
    private TextView mParticipant2;
    private TextView mParticipant1Score;
    private TextView mParticipant2Score;
    private TextView mMap;
    private Context mContext;

    public GameHolder(Context _context, View _itemView) {
        super(_itemView);
        mContext = _context;
        mRound = (TextView) itemView.findViewById(R.id.tv_game_round);
        mParticipant1 = (TextView) itemView.findViewById(R.id.tv_game_participant1);
        mParticipant2 = (TextView) itemView.findViewById(R.id.tv_game_participant2);
        mParticipant1Score = (TextView) itemView.findViewById(R.id.tv_game_participant1_score);
        mParticipant2Score = (TextView) itemView.findViewById(R.id.tv_game_participant2_score);
        mMap = (TextView) itemView.findViewById(R.id.tv_game_mapName);
    }

    public void bindGame(Game _game){
        if(_game != null){
            mRound.setText(String.valueOf(_game.getmNumber()));
            mParticipant1.setText(_game.getmOpponentList().get(0).getmParticipant().getmName());

            if(_game.getmOpponentList().get(0).getmResult() == 3){
                mParticipant1Score.setTextColor(mContext.getResources().getColor(R.color.loss_color_red));
            }else{
                mParticipant1Score.setTextColor(mContext.getResources().getColor(R.color.win_color_green));
            }
            mParticipant1Score.setText(String.valueOf(_game.getmOpponentList().get(0).getmScore()));

            mParticipant2.setText(_game.getmOpponentList().get(1).getmParticipant().getmName());

            if(_game.getmOpponentList().get(1).getmResult() == 3){
                mParticipant2Score.setTextColor(mContext.getResources().getColor(R.color.loss_color_red));
            }else{
                mParticipant2Score.setTextColor(mContext.getResources().getColor(R.color.win_color_green));
            }
            mParticipant2Score.setText(String.valueOf(_game.getmOpponentList().get(1).getmScore()));

            if(_game.getmMap().equals("null") && _game.getmMap().equals("")){
                mMap.setText("-");
            }else{
                mMap.setText(_game.getmMap());
            }

        }
    }
}
