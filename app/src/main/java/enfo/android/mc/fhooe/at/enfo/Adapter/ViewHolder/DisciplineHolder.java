package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import enfo.android.mc.fhooe.at.enfo.Adapter.RecyclerAdapter.DisciplineAdapter;
import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Model.EntityManager;
import enfo.android.mc.fhooe.at.enfo.R;

public class DisciplineHolder extends RecyclerView.ViewHolder{
    private ImageView mDisciplineImage;
    private Context mContext;
    private Discipline mDiscipline;

    public DisciplineHolder(Context _context , View _itemView) {
        super(_itemView);
        mContext = _context;
        mDisciplineImage = (ImageView) _itemView.findViewById(R.id.iv_discipline);
    }

    public void bindDiscipline(Discipline _discipline){
        mDiscipline = _discipline;

        if(mDiscipline.getmId() == null){
            mDisciplineImage.setImageDrawable(null);
        }

        switch (mDiscipline.getmId()){
            case  counterstrike_go: {
                Glide.with(mContext).load(R.drawable.img_discipline_csgo).into(mDisciplineImage);
                //mDisciplineImage.setImageResource(R.drawable.img_discipline_csgo);
                break;
            }
            case dota2 : {
                Glide.with(mContext).load(R.drawable.img_discipline_dota2).into(mDisciplineImage);
                break;
            }
            case hearthstone : {
                Glide.with(mContext).load(R.drawable.img_discipline_hs).into(mDisciplineImage);
                break;
            }
            case leagueoflegends : {
                Glide.with(mContext).load(R.drawable.img_discipline_lol).into(mDisciplineImage);
                break;
            }
            case starcraft2_lotv:{
                Glide.with(mContext).load(R.drawable.img_discipline_sc2).into(mDisciplineImage);
                break;
            }
            case overwatch:{
                Glide.with(mContext).load(R.drawable.img_discipline_ow).into(mDisciplineImage);
                break;
            }
            case heroesofthestorm :{
                Glide.with(mContext).load(R.drawable.img_discipline_hots).into(mDisciplineImage);
                break;
            }
            default:
                mDisciplineImage.setImageDrawable(null);
                break;
        }

    }

}
