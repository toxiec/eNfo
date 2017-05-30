package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Tournament;
import enfo.android.mc.fhooe.at.enfo.R;

public class TournamentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView mDisciplineLogo;
    private TextView mDisciplineName;
    private TextView mDate;
    private TextView mTournamentName;
    private TextView mLocation;

    private Tournament mTournament;
    private Discipline mDiscipline;
    private Context mContext;

    public TournamentHolder(Context _context, View _itemView) {
        super(_itemView);
        mContext = _context;
        mDisciplineLogo = (ImageView) _itemView.findViewById(R.id.img_tournament_discipline);
        mDisciplineName = (TextView) _itemView.findViewById(R.id.tv_discipline);
        mDate = (TextView) _itemView.findViewById(R.id.tv_date);
        mTournamentName = (TextView) _itemView.findViewById(R.id.tv_tournament_name);
        mLocation = (TextView) _itemView.findViewById(R.id.tv_location);
        _itemView.setOnClickListener(this);
    }


    public void bindTournament(Tournament _tournament, Discipline _discipline){
        mTournament = _tournament;
        mDiscipline = _discipline;

        switch(mDiscipline.getmId()){
            case "counterstrike_go" : {
                mDisciplineLogo.setImageResource(R.drawable.image_featured_tournament_csgo);
                break;
            }
            case "dota2" : {
                mDisciplineLogo.setImageResource(R.drawable.image_featured_tournament_dota2);
                break;
            }
            case "hearthstone" : {
                mDisciplineLogo.setImageResource(R.drawable.image_featured_tournament_hs);
                break;
            }
            case "leagueoflegends" : {
                mDisciplineLogo.setImageResource(R.drawable.image_featured_tournament_lol);
                break;
            }
            case "starcraft2_lotv":{
                mDisciplineLogo.setImageResource(R.drawable.image_discipline_sc2_lotv);
                break;
            }
        }
        mDisciplineName.setText(mDiscipline.getmName());
        //Convert the Date to dd.MM.yyyy
        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(_tournament.getmDateStart());
        mDate.setText(date);
        mTournamentName.setText(_tournament.getmName());
        if(_tournament.getmLocation().equals("null")){
            mLocation.setText("Undefined Location");
        }else{
            mLocation.setText(_tournament.getmLocation());
        }
    }

    @Override
    public void onClick(View v) {
        if (mTournament != null) {
            Toast.makeText(mContext, "Clicked on " + mTournament.getmName(), Toast.LENGTH_SHORT ).show();
        }
    }
}
