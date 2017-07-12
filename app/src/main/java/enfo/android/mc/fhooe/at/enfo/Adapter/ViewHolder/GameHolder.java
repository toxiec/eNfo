package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import enfo.android.mc.fhooe.at.enfo.Entities.Game;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 * Holds the Information for Games in the RecyclerView
 */
public class GameHolder extends RecyclerView.ViewHolder {
    /**Displays the Round number*/
    private TextView mRound;
    /**Displays the first Opponent name*/
    private TextView mParticipant1;
    /**Display the second Opponent name*/
    private TextView mParticipant2;
    /**Displays the Score of the first Opponent*/
    private TextView mParticipant1Score;
    /**Displays the Score of the second Opponent*/
    private TextView mParticipant2Score;
    /**Displays the Map name, on which the Game was played on*/
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

    /**
     * Binds the Game Information to the item fields.
     * @param _game which data should be bind to the item fields.
     */
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
