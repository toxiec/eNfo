package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import enfo.android.mc.fhooe.at.enfo.Entities.Discipline;
import enfo.android.mc.fhooe.at.enfo.Objects.TournamentInformationItem;
import enfo.android.mc.fhooe.at.enfo.R;


public class TournamentInformationHolder extends ViewHolder {
    private TextView mInformationHeader;
    private TextView mInformationText;
    private Context mContext;

    public TournamentInformationHolder(Context _context, View _itemView) {
        super(_itemView);
        mContext = _context;

        mInformationHeader = (TextView) itemView.findViewById(R.id.lv_tournament_info_header);
        mInformationText = (TextView) itemView.findViewById(R.id.lv_tournament_info_text);
    }

    public void bindTournament(TournamentInformationItem _item, Discipline _discipline) {
        if(_item!=null) {
            if(_item.getmText().equals("null")){
                mInformationText.setText("");
            }else{
                if (_item.getmHeadLine().equals("Discipline")) {
                    mInformationText.setText(_discipline.getmFullname());
                } else {
                    mInformationText.setText(_item.getmText());
                }
                if(_item.getmHeadLine().equals("Start Date") || _item.getmHeadLine().equals("End Date")){
                    String dateStart = _item.getmText();
                    SimpleDateFormat fromDate = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    SimpleDateFormat toDate = new SimpleDateFormat("dd.MM.yyyy");
                    try {
                        String reformattedDate = toDate.format(fromDate.parse(dateStart));
                        mInformationText.setText(reformattedDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
            mInformationHeader.setText(_item.getmHeadLine());
        }else{
            Toast.makeText(mContext, "No Information Available", Toast.LENGTH_SHORT).show();
        }
    }
}

