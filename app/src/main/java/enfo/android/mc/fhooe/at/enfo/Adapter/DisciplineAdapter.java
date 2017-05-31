package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;

public class DisciplineAdapter extends RecyclerView.Adapter<DisciplineHolder>{
    List<Discipline> mDisciplineList;
    private Context mContext;
    private int mItemResource;
    private ClickListener mClickListener;

    public DisciplineAdapter(Context _context, int _itemResource, List<Discipline> _list){
        mContext = _context;
        mItemResource = _itemResource;
        mDisciplineList = _list;
    }

    @Override
    public DisciplineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new DisciplineHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(DisciplineHolder holder, int position) {
        Discipline discipline = mDisciplineList.get(position);
        holder.bindDiscipline(discipline, mClickListener);
    }

    @Override
    public int getItemCount() {
        return mDisciplineList.size();
    }

    public interface ClickListener{
        public void itemClicked(View view, int position);
    }

    public void setClickListener(ClickListener _clickListener){
        mClickListener = _clickListener;
    }
}
