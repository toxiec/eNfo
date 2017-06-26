package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 * Created by David on 26.06.2017.
 */

public class MatchHolder extends RecyclerView.ViewHolder{
    private TextView mMatchGroup;
    private TextView mParticipant1;
    private TextView mParticipant2;
    private TextView mParticipant1Score;
    private TextView mParticipant2Score;
    private TextView mMatchDate;

    private Tournament mTournament;
    private Discipline mDiscipline;
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

    public void bindMatch(Match _match, Discipline _discipline){
        mDiscipline = _discipline;
        mMatch = _match;

        if(mMatch.getmOpponentList()!=null){
            mParticipant1.setText(mMatch.getmOpponentList().get(0).getmParticipant().getmName());
            mParticipant2.setText(mMatch.getmOpponentList().get(1).getmParticipant().getmName());
            mParticipant1Score.setText(String.valueOf(mMatch.getmOpponentList().get(0).getmResult()));
            mParticipant1Score.setText(String.valueOf(mMatch.getmOpponentList().get(1).getmResult()));
            mMatchDate.setText(mMatch.getmDate());

        }
    }
}
