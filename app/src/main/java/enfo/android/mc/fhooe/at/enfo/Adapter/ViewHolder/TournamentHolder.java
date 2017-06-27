package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.Format;
import java.text.SimpleDateFormat;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.Match;
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
            case counterstrike_go : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_csgo).into(mDisciplineLogo);
                break;
            }
            case dota2 : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_dota2).into(mDisciplineLogo);
                break;
            }
            case hearthstone : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_hs).into(mDisciplineLogo);
                break;
            }
            case leagueoflegends : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_lol).into(mDisciplineLogo);
                break;
            }
            case starcraft2_lotv:{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_sc2_lotv).into(mDisciplineLogo);
                break;
            }
            case overwatch:{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_ow).into(mDisciplineLogo);
                break;
            }
            case heroesofthestorm:{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_hots).into(mDisciplineLogo);
                break;
            }
            default:{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_dota2).into(mDisciplineLogo);
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
            //Toast.makeText(mContext, "Clicked on " + mTournament.getmName(), Toast.LENGTH_SHORT ).show();
        }
    }
}
