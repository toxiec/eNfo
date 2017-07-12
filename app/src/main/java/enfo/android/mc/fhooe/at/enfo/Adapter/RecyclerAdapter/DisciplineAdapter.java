package enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder.DisciplineHolder;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;

/**
 *Provide a binding from Discipline to views that are displayed within a RecyclerView.
 */
public class DisciplineAdapter extends RecyclerView.Adapter<DisciplineHolder>{
    private Context mContext;
    private int mItemResource;

    public DisciplineAdapter(Context _context, int _itemResource){
        mContext = _context;
        mItemResource = _itemResource;
    }

    @Override
    public DisciplineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemResource, parent, false);
        return new DisciplineHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(DisciplineHolder holder, int position) {
        Discipline discipline = EntityManager.getInstance().getDisciplineList().get(position);
        holder.bindDiscipline(discipline);
    }

    @Override
    public int getItemCount() {
        return EntityManager.getInstance().getDisciplineList().size();
    }
}
