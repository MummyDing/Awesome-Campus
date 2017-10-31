package cn.edu.jxnu.awesome_campus.support.adapter.life;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ramotion.foldingcell.FoldingCell;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.weather.WeatherConfig;

/**
 * Created by KevinWu on 16-4-30.
 */
public class WeatherListAdapter extends BaseListAdapter<WeatherInfoModel, WeatherListAdapter.VH> {


    public WeatherListAdapter(Context mContext, WeatherInfoModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_weather_info, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        WeatherInfoModel model = getItem(position);

        holder.date.setText(model.getDate());
        holder.dayInfo.setText(model.getInfo().getDay()[1]
                + "\n"
                + model.getInfo().getDay()[2] + "°C \n"
                + model.getInfo().getDay()[3] + "\n" +
                model.getInfo().getDay()[4]);
        holder.nightInfo.setText(model.getInfo().getNight()[1]
                + "\n"
                + model.getInfo().getNight()[2] + "°C \n"
                + model.getInfo().getNight()[3] + "\n" +
                model.getInfo().getNight()[4]);
        int nowTime=Integer.parseInt(model.getInfo().getNight()[5].substring(0, 2)+model.getInfo().getNight()[5].substring(3, 5));
        if(TimeUtil.getHourMinute()<nowTime){
            holder.dateTitle.setText(model.getDate()+"\n"
                    +model.getInfo().getDay()[1]
                    +"\n"+model.getInfo().getDay()[2] + "°C ~ "+model.getInfo().getNight()[2] + "°C");
            holder.weatherIconSmall.setBackgroundResource(
                    WeatherConfig.WeatherPic
                            [
                            WeatherConfig.getWeatherPicNum(model.getInfo().getDay()[1])
                            ]);
        }else{
            holder.dateTitle.setText(model.getDate()+"\n"
                    +model.getInfo().getNight()[1]
                    +"\n"+model.getInfo().getDay()[2] + "°C ~ "+model.getInfo().getNight()[2] + "°C");
            holder.weatherIconSmall.setBackgroundResource(
                    WeatherConfig.WeatherPic
                            [
                            WeatherConfig.getWeatherPicNum(model.getInfo().getNight()[1])
                            ]);
        }

        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(false);
            }
        });
    }

    class VH extends RecyclerView.ViewHolder {
        View itemView;
        TextView dateTitle;
        TextView date;
        TextView dayInfo;
        TextView nightInfo;
        SimpleDraweeView weatherIconSmall;
        LinearLayout linearLayout;
        FoldingCell fc;

        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            dateTitle = (TextView) itemView.findViewById(R.id.date_title);
            date = (TextView) itemView.findViewById(R.id.date);
            dayInfo = (TextView) itemView.findViewById(R.id.dayInfo);
            nightInfo = (TextView) itemView.findViewById(R.id.nightInfo);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.weather_icon_background);
            weatherIconSmall = (SimpleDraweeView) itemView.findViewById(R.id.weather_icon_small);
            fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);
            fc.initialize(10, Color.TRANSPARENT, 0);
            GradientDrawable myGrad = (GradientDrawable) linearLayout.getBackground();
            TypedArray array = mContext.getTheme().obtainStyledAttributes(new int[]{
                    android.R.attr.colorPrimary,
            });
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            myGrad.setColor(array.getColor(0, 0xFFFFFF));
            array.recycle();
        }
    }
}
