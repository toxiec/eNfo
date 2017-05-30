package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.R;

public class DisciplineAdapter extends ArrayAdapter {
    List<Discipline> mDisciplineList = new ArrayList<>();

    public DisciplineAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Discipline object) {
        super.add(object);
        mDisciplineList.add(object);
    }

    @Override
    public int getCount() {
        return mDisciplineList.size();
    }

    @Override
    public Object getItem(int _position) {
        return mDisciplineList.get(_position);
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        ViewHolder disciplineHolder;

        if(_convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = layoutInflater.inflate(R.layout.disciple_row_layout, _parent, false);
            disciplineHolder = new ViewHolder();
            disciplineHolder.disciplineLogo = (ImageView) _convertView.findViewById(R.id.image_disciplineLogo);
            disciplineHolder.disciplineFullName = (TextView) _convertView.findViewById(R.id.txt_discipline_Fullname);
            _convertView.setTag(disciplineHolder);

        }else{
            disciplineHolder = (ViewHolder) _convertView.getTag();
        }

        //Set the Values for the List Element
        Discipline discipline = (Discipline) this.getItem(_position);
        switch(discipline.getmId()){
            case "counterstrike_go" : {
                disciplineHolder.disciplineLogo.setImageResource(R.drawable.image_discipline_csgo);
                break;
            }
            case "dota2" : {
                disciplineHolder.disciplineLogo.setImageResource(R.drawable.image_discipline_dota2);
                break;
            }
            case "hearthstone" : {
                disciplineHolder.disciplineLogo.setImageResource(R.drawable.image_discipline_hearthstone);
                break;
            }
            case "leagueoflegends" : {
                disciplineHolder.disciplineLogo.setImageResource(R.drawable.image_discipline_lol);
                break;
            }
            case "starcraft2_lotv" :{
                disciplineHolder.disciplineLogo.setImageResource(R.drawable.image_discipline_sc2_lotv);
            }
        }
        disciplineHolder.disciplineFullName.setText(discipline.getmFullname());
        return _convertView;
    }

    private static class ViewHolder{
        ImageView disciplineLogo;
        TextView disciplineFullName;
    }
}
