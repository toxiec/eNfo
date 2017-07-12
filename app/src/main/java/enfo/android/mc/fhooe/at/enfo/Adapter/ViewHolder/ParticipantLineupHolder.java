package enfo.android.mc.fhooe.at.enfo.Adapter.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import enfo.android.mc.fhooe.at.enfo.Objects.CountryID;
import enfo.android.mc.fhooe.at.enfo.Objects.Player;
import enfo.android.mc.fhooe.at.enfo.R;

/**
 * Created by David on 29.06.2017.
 */

/**
 * Holds the Information for Participant Lineup in the RecyclerView
 */
public class ParticipantLineupHolder  extends RecyclerView.ViewHolder {

    private Context mContext;
    /**Displays the Team Member Name*/
    private TextView mPlayerName;
    /**Displays the Team Member Country*/
    private TextView mPlayerCountry;
    /**Displays the Country Flag of the team members country */
    private ImageView mCountryFlag;

    public ParticipantLineupHolder(Context _context, View _itemView) {
        super(_itemView);
        mContext = _context;

        mPlayerName = (TextView) _itemView.findViewById(R.id.tv_player_playername);
        mPlayerCountry = (TextView) _itemView.findViewById(R.id.tv_player_country);
        mCountryFlag = (ImageView) _itemView.findViewById(R.id.img_player_country_flag);
    }

    /**
     * Binds the Player Information to the item fields.
     * @param _player which data should be bind to the item fields.
     */
    public void bindPlayer(Player _player){
        if(_player!=null){
            if(_player.getmCountry().equals("null")){
                mPlayerCountry.setText("");
            }else{
                mPlayerCountry.setText(_player.getmCountry().toString());
            }
            mPlayerName.setText(_player.getmName());

            switch (_player.getmCountry()){
                case CA:{
                    Glide.with(mContext).load(R.drawable.canada).into(mCountryFlag);
                    break;
                }
                case SE:{
                    Glide.with(mContext).load(R.drawable.sweden).into(mCountryFlag);
                    break;
                }
                case RU:{
                    Glide.with(mContext).load(R.drawable.russian).into(mCountryFlag);
                    break;
                }
                case UA:{
                    Glide.with(mContext).load(R.drawable.ukraine).into(mCountryFlag);
                    break;
                }
                case BR:{
                    Glide.with(mContext).load(R.drawable.brazil).into(mCountryFlag);
                    break;
                }
                case CZ:{
                    Glide.with(mContext).load(R.drawable.czech_republic).into(mCountryFlag);
                    break;
                }
                case ES:{
                    Glide.with(mContext).load(R.drawable.spain).into(mCountryFlag);
                    break;
                }
                case EE:{
                    Glide.with(mContext).load(R.drawable.estonia).into(mCountryFlag);
                    break;
                }
                case DK:{
                    Glide.with(mContext).load(R.drawable.denmark).into(mCountryFlag);
                    break;
                }
                case AU:{
                    Glide.with(mContext).load(R.drawable.australia).into(mCountryFlag);
                    break;
                }
                case AT:{
                    Glide.with(mContext).load(R.drawable.austria).into(mCountryFlag);
                    break;
                }
                case FR:{
                    Glide.with(mContext).load(R.drawable.france).into(mCountryFlag);
                    break;
                }
                case ZA:{
                    Glide.with(mContext).load(R.drawable.southafrica).into(mCountryFlag);
                    break;
                }
                case TW:{
                    Glide.with(mContext).load(R.drawable.taiwan).into(mCountryFlag);
                    break;
                }
                case TR:{
                    Glide.with(mContext).load(R.drawable.turkey).into(mCountryFlag);
                    break;
                }
                case CN:{
                    Glide.with(mContext).load(R.drawable.china).into(mCountryFlag);
                    break;
                }
                case RS:{
                    Glide.with(mContext).load(R.drawable.serbiayugoslavia).into(mCountryFlag);
                    break;
                }
                case MX:{
                    Glide.with(mContext).load(R.drawable.mexico).into(mCountryFlag);
                    break;
                }
                case GB:{
                    Glide.with(mContext).load(R.drawable.greatbritain).into(mCountryFlag);
                    break;
                }
                case JP:{
                    Glide.with(mContext).load(R.drawable.japan).into(mCountryFlag);
                    break;
                }
                case DZ:{
                    Glide.with(mContext).load(R.drawable.algeria).into(mCountryFlag);
                    break;
                }
                case US:{
                    Glide.with(mContext).load(R.drawable.usa).into(mCountryFlag);
                    break;
                }
                case FI:{
                    Glide.with(mContext).load(R.drawable.finland).into(mCountryFlag);
                    break;
                }
                case DE:{
                    Glide.with(mContext).load(R.drawable.germany).into(mCountryFlag);
                    break;
                }
                case NL:{
                    Glide.with(mContext).load(R.drawable.netherlands).into(mCountryFlag);
                    break;
                }
                case NO:{
                    Glide.with(mContext).load(R.drawable.norway).into(mCountryFlag);
                    break;
                }
                case KR:{
                    Glide.with(mContext).load(R.drawable.southkorea).into(mCountryFlag);
                    break;
                }
                case KZ:{
                    Glide.with(mContext).load(R.drawable.kazakhstan).into(mCountryFlag);
                    break;
                }
                case PL:{
                    Glide.with(mContext).load(R.drawable.poland).into(mCountryFlag);
                    break;
                }
                case BE:{
                    Glide.with(mContext).load(R.drawable.belgium).into(mCountryFlag);
                    break;
                }
                case IL:{
                    Glide.with(mContext).load(R.drawable.israel).into(mCountryFlag);
                    break;
                }
                case NZ:{
                    Glide.with(mContext).load(R.drawable.newzealand).into(mCountryFlag);
                    break;
                }

                case SG:{
                    Glide.with(mContext).load(R.drawable.singapore).into(mCountryFlag);
                    break;
                }
                case IT:{
                    Glide.with(mContext).load(R.drawable.italy).into(mCountryFlag);
                    break;
                }
                case PT:{
                    Glide.with(mContext).load(R.drawable.portugal).into(mCountryFlag);
                    break;
                }
                case VN:{
                    Glide.with(mContext).load(R.drawable.vietnam).into(mCountryFlag);
                    break;
                }
                case AR:{
                    Glide.with(mContext).load(R.drawable.argentina).into(mCountryFlag);
                    break;
                }
                case HK:{
                    Glide.with(mContext).load(R.drawable.hongkong).into(mCountryFlag);
                    break;
                }
                case RO:{
                    Glide.with(mContext).load(R.drawable.romania).into(mCountryFlag);
                    break;
                }
                case TH:{
                    Glide.with(mContext).load(R.drawable.thailand).into(mCountryFlag);
                    break;
                }

                default:{
                    Glide.with(mContext).load(R.color.text_color_white).into(mCountryFlag);
                    break;
                }
            }


        }
    }
}