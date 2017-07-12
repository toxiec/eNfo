package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 * Created by David on 26.06.2017.
 */

/**
 * Holds the Information for Matches in the RecyclerView
 */
public class MatchHolder extends RecyclerView.ViewHolder{
    /**Displays the Match Group*/
    private TextView mMatchGroup;
    /**Displays the first Opponent Name*/
    private TextView mParticipant1;
    /**Displays the second Opponent Name*/
    private TextView mParticipant2;
    /**Displays the Score of the first Opponent*/
    private TextView mParticipant1Score;
    /**Displays the Score of the second Opponent*/
    private TextView mParticipant2Score;
    /**Display when the Match took place */
    private TextView mMatchDate;

    /**Current selected Tournament*/
    private Tournament mTournament;
    /**Current selected Match*/
    private Match mMatch;
    private Context mContext;

    public MatchHolder(Context _context, View _itemView) {
        super(_itemView);
        mContext = _context;
        mMatchGroup = (TextView) itemView.findViewById(R.id.tv_match_groupDetail);
        mParticipant1 = (TextView) itemView.findViewById(R.id.tv_match_participant1);
        mParticipant2 = (TextView) itemView.findViewById(R.id.tv_match_participant2);
        mParticipant1Score = (TextView) itemView.findViewById(R.id.tv_match_part1_score1);
        mParticipant2Score = (TextView) itemView.findViewById(R.id.tv_match_part1_score2);
        mMatchDate = (TextView) itemView.findViewById(R.id.tv_match_date);

    }

    /**
     * Binds the Match Information to the item fields.
     * @param _match which data should be bind to the item fields.
     */
    public void bindMatch(Match _match){
        mMatch = _match;

        if(mMatch.getmOpponentList()!=null){
            String group_rounds;
            StringBuilder builder = new StringBuilder();
            builder.append("Group "+mMatch.getmGroupNumber()+" Round "+mMatch.getmRoundNumber());
            group_rounds = builder.toString();
            mMatchGroup.setText(group_rounds);

            mParticipant1.setText(mMatch.getmOpponentList().get(0).getmParticipant().getmName());
            mParticipant2.setText(mMatch.getmOpponentList().get(1).getmParticipant().getmName());

            //Win
            if(mMatch.getmOpponentList().get(0).getmResult() == 1){
                mParticipant1Score.setBackgroundResource(R.drawable.win_rectangle);
            }else if(mMatch.getmOpponentList().get(0).getmResult() == 3){
                mParticipant1Score.setBackgroundResource(R.drawable.loss_rectangle);
            }else{
                mParticipant1Score.setBackgroundResource(R.drawable.win_rectangle);
            }


            if(mMatch.getmOpponentList().get(1).getmResult() == 1){
                mParticipant2Score.setBackgroundResource(R.drawable.win_rectangle);
            }else if(mMatch.getmOpponentList().get(1).getmResult() == 3){
                mParticipant2Score.setBackgroundResource(R.drawable.loss_rectangle);
            }else{
                mParticipant2Score.setBackgroundResource(R.drawable.win_rectangle);
            }

            mParticipant1Score.setText(String.valueOf(mMatch.getmOpponentList().get(0).getmScore()));
            mParticipant2Score.setText(String.valueOf(mMatch.getmOpponentList().get(1).getmScore()));

            if(mMatch.getmDate().equals("null")){
                mMatchDate.setText("-");
            }else{
                SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
                SimpleDateFormat toDate = new SimpleDateFormat("dd.MM.yyyy '-' HH:mm");
                String date = mMatch.getmDate();
                try {
                    String reformattedDate = toDate.format(fromDate.parse(date));
                    mMatchDate.setText(reformattedDate);
                }catch (Exception e){
                    mMatchDate.setText(date);
                }
            }
        }
    }
}
