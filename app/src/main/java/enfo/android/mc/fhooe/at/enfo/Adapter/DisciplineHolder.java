package enfo.android.mc.fhooe.at.enfo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.R;

public class DisciplineHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView mDisciplineImage;
    private DisciplineAdapter.ClickListener mClickListener;
    private Context mContext;
    private Discipline mDiscipline;

    public DisciplineHolder(Context _context , View _itemView) {
        super(_itemView);
        mContext = _context;
        _itemView.setOnClickListener(this);
        mDisciplineImage = (ImageView) _itemView.findViewById(R.id.iv_discipline);
    }

    public void bindDiscipline(Discipline _discipline, DisciplineAdapter.ClickListener _clickListener){
        mDiscipline = _discipline;
        mClickListener = _clickListener;
        switch (mDiscipline.getmId()){
            case "counterstrike_go" : {
                mDisciplineImage.setImageResource(R.drawable.img_discipline_csgo);
                break;
            }
            case "dota2" : {
                mDisciplineImage.setImageResource(R.drawable.img_discipline_dota2);
                break;
            }
            case "hearthstone" : {
                mDisciplineImage.setImageResource(R.drawable.img_discipline_hs);
                break;
            }
            case "leagueoflegends" : {
                mDisciplineImage.setImageResource(R.drawable.img_discipline_lol);
                break;
            }
            case "starcraft2_lotv":{
                mDisciplineImage.setImageResource(R.drawable.img_discipline_sc2);
                break;
            }
            case "overwatch":{
                mDisciplineImage.setImageResource(R.drawable.img_discipline_ow);
                break;
            }
            case "heroesofthestorm" :{
                mDisciplineImage.setImageResource(R.drawable.img_discipline_hots);
                break;
            }
        }

    }
    @Override
    public void onClick(View v) {
        if(mClickListener != null){
            mClickListener.itemClicked(v, getAdapterPosition());
        }
    }
}
