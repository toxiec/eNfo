package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.R;

public class DiscipleAdapter extends ArrayAdapter {
    List<Discipline> mDiscipleList = new ArrayList<>();

    /*public DiscipleAdapter(Context context, int resource, ArrayList<Discipline> _list) {
        super(context, resource);
        mDiscipleList = _list;
    }*/

    public DiscipleAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Discipline object) {
        super.add(object);
        mDiscipleList.add(object);
    }

    @Override
    public int getCount() {
        return mDiscipleList.size();
    }

    @Override
    public Object getItem(int _position) {
        return mDiscipleList.get(_position);
    }

    @Override
    public View getView(int _position, View _convertView, ViewGroup _parent) {
        ViewHolder disciplineHolder;

        if(_convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            _convertView = layoutInflater.inflate(R.layout.disciple_row_layout, _parent, false);
            disciplineHolder = new ViewHolder();
            disciplineHolder.disciplineName = (TextView) _convertView.findViewById(R.id.txt_discipline_name);
            disciplineHolder.disciplineFullName = (TextView) _convertView.findViewById(R.id.txt_discipline_Fullname);
            _convertView.setTag(disciplineHolder);

        }else{
            disciplineHolder = (ViewHolder) _convertView.getTag();
        }
        Discipline discipline = (Discipline) this.getItem(_position);
        disciplineHolder.disciplineName.setText(discipline.getmId());
        disciplineHolder.disciplineFullName.setText(discipline.getmFullname());

        return _convertView;
    }

    private static class ViewHolder{
        TextView disciplineName;
        TextView disciplineFullName;
    }
}
