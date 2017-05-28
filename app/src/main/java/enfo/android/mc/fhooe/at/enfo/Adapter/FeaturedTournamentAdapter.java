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

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Entities.FeaturedTournament;
import enfo.android.mc.fhooe.at.enfo.R;


public class FeaturedTournamentAdapter extends ArrayAdapter {
    List<FeaturedTournament> mTournamentList = new ArrayList<>();

    /**
     * Adds a FeaturedTournament to the List which will be displayed in the ListView
     * @param _tournament
     */
    public void add(FeaturedTournament _tournament){
        mTournamentList.add(_tournament);
    }

    public FeaturedTournamentAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
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
        switch(featuredTournament.getmDiscipline()){
            case "counterstrike_go" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_csgo);
                break;
            }
            case "dota2" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_csgo);
                break;
            }
            case "hearthstone" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_csgo);
                break;
            }
            case "leagueoflegends" : {
                tournamentHolder.disciplineLogo.setImageResource(R.drawable.image_featured_tournament_csgo);
                break;
            }
        }
        tournamentHolder.disciplineName.setText(featuredTournament.getmName());
        tournamentHolder.date.setText((CharSequence) featuredTournament.getmDateStart());
        tournamentHolder.tournamentName.setText(featuredTournament.getmFullName());
        if(featuredTournament.getmLocation() != null){
            tournamentHolder.location.setText(featuredTournament.getmLocation());
        }else{
            tournamentHolder.location.setText("Undefined Location");
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
