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

/**
 * Holds the Information for Tournaments in the RecyclerView
 */
public class TournamentHolder extends RecyclerView.ViewHolder{
    /**Displays the Discipline Logo */
    private ImageView mDisciplineLogo;
    /**Displays the Discipline Name*/
    private TextView mDisciplineName;
    /**Displays the Tournament Start Date */
    private TextView mDate;
    /**Displays the Tournament Name */
    private TextView mTournamentName;
    /**Displays the Location where the Tournament takes place*/
    private TextView mLocation;

    /**Current selected Tournament*/
    private Tournament mTournament;
    /**Current selected Discipline*/
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
    }

    /**
     * Binds the Tournament Information to the item fields.
     * @param _discipline which data should be bind to the item fields.
     * @param _tournament which data should be bind to the item fields.
     */
    public void bindTournament(Tournament _tournament, Discipline _discipline){
        mTournament = _tournament;
        mDiscipline = _discipline;

        switch(mTournament.getmDiscipline()){
            case "counterstrike_go" : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_csgo).into(mDisciplineLogo);
                break;
            }
            case "dota2" : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_dota2).into(mDisciplineLogo);
                break;
            }
            case "hearthstone" : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_hs).into(mDisciplineLogo);
                break;
            }
            case "leagueoflegends" : {
                Glide.with(mContext).load(R.drawable.image_featured_tournament_lol).into(mDisciplineLogo);
                break;
            }
            case "starcraft2_lotv":{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_sc2_lotv).into(mDisciplineLogo);
                break;
            }
            case "overwatch":{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_ow).into(mDisciplineLogo);
                break;
            }
            case "heroesofthestorm":{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_hots).into(mDisciplineLogo);
                break;
            }
            case "rainbowsix_siege":{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_rss).into(mDisciplineLogo);
                break;
            }
            case "halo5_guardians":{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_halo5).into(mDisciplineLogo);
                break;
            }
            default:{
                Glide.with(mContext).load(R.drawable.image_featured_tournament_dota2).into(mDisciplineLogo);
            }

        }
        if(mDiscipline != null){
            mDisciplineName.setText(mDiscipline.getmName());
        }else{
            mDisciplineName.setText(mTournament.getmDiscipline());
        }
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
}
