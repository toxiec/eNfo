package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import enfo.android.mc.fhooe.at.enfo.Entities.Participant;
import enfo.android.mc.fhooe.at.enfo.R;
/**
 * Holds the Information for Participant in the RecyclerView
 */
public class ParticipantHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    /**Display the Name of the Participant*/
    private TextView mParticipantName;
    /**Displays the Logo of the Participant */
    private ImageView mParticipantLogo;

    public ParticipantHolder(Context _context, View _itemView) {
        super(_itemView);
        mContext = _context;

        mParticipantName = (TextView) _itemView.findViewById(R.id.tv_participant_name);
        mParticipantLogo = (ImageView) _itemView.findViewById(R.id.iv_participant_logo);
    }

    /**
     * Binds the Participant Information to the item fields.
     * @param _participant which data should be bind to the item fields.
     */
    public void bindTournament(Participant _participant){
        if(_participant!=null){
            Glide.with(mContext)
                    .load(_participant.getmParticipantLogo().getmMedium_small_square())
                    .into(mParticipantLogo);
            mParticipantName.setText(_participant.getmName());
        }
    }
}
