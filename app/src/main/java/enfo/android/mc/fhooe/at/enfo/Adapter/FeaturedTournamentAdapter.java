package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.FeaturedTournament;
import enfo.android.mc.fhooe.at.enfo.R;


public class FeaturedTournamentAdapter extends ArrayAdapter {
    List<FeaturedTournament> mTournamentList = new ArrayList<>();
    Discipline mDiscipline;
    public FeaturedTournamentAdapter(@NonNull Context context, @LayoutRes int resource, Discipline _discipline) {
        super(context, resource);
        mDiscipline = _discipline;
    }

    /**
     * Adds a FeaturedTournament to the List which will be displayed in the ListView
     * @param _tournament
     */
    public void add(FeaturedTournament _tournament) {
        super.add(_tournament);
        mTournamentList.add(_tournament);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return mTournamentList.get(position);
    }

    @Override
    public int getCount() {
        return mTournamentList.size();
    }

    @NonNull
    @Override
    public View getView(int _position, @Nullable View _convertView, @NonNull ViewGroup _parent) {
        ViewHolder tournamentHolder;

        if(_convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = layoutInflater.inflate(R.layout.featured_tournament_row_layout, _parent, false);
            tournamentHolder = new ViewHolder();
            tournamentHolder.disciplineLogo = (ImageView) _convertView.findViewById(R.id.img_tournament_discipline);
            tournamentHolder.disciplineName = (TextView) _convertView.findViewById(R.id.tv_discipline);
            tournamentHolder.date = (TextView) _convertView.findViewById(R.id.tv_date);
            tournamentHolder.tournamentName = (TextView) _convertView.findViewById(R.id.tv_tournament_name);
            tournamentHolder.location = (TextView) _convertView.findViewById(R.id.tv_location);
            _convertView.setTag(tournamentHolder);

        }else{
            tournamentHolder = (ViewHolder) _convertView.getTag();
        }

        //Set the Values for the List Element
        FeaturedTournament featuredTournament = (FeaturedTournament) this.getItem(_position);
        switch(mDiscipline.getmId()){
            case "counterstrike_go" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_csgo);
                break;
            }
            case "dota2" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_dota2);
                break;
            }
            case "hearthstone" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_hs);
                break;
            }
            case "leagueoflegends" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_lol);
                break;
            }
            case "starcraft2_lotv":{
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_discipline_sc2_lotv);
                break;
            }
        }
        tournamentHolder.disciplineName.setText(mDiscipline.getmName());

        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(featuredTournament.getmDateStart());
        tournamentHolder.date.setText(date);

        tournamentHolder.tournamentName.setText(featuredTournament.getmName());
        if(featuredTournament.getmLocation().equals("null")){
            tournamentHolder.location.setText("Undefined Location");
        }else{
            tournamentHolder.location.setText(featuredTournament.getmLocation());
        }
        return _convertView;
    }

    private static class ViewHolder{
        ImageView disciplineLogo;
        TextView disciplineName;
        TextView date;
        TextView tournamentName;
        TextView location;
    }
}
