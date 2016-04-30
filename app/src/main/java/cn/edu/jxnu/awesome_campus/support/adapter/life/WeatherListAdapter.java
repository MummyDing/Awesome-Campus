package cn.edu.jxnu.awesome_campus.support.adapter.life;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.life.WeatherInfoModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

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
        holder.dateTitle.setText(model.getDate());
        holder.date.setText(model.getDate());
        holder.dayInfo.setText(model.getDay()[1]
                + "\n"
                + model.getDay()[2] + "°C \n"
                + model.getDay()[3] + "\n" +
                model.getDay()[4]);
        holder.nightInfo.setText(model.getNight()[1]
                + "\n"
                + model.getNight()[2] + "°C \n"
                + model.getNight()[3] + "\n" +
                model.getNight()[4]);
        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(false);
            }
        });
    }

    static class VH extends RecyclerView.ViewHolder {
        View itemView;
        TextView dateTitle;
        TextView date;
        TextView dayInfo;
        TextView nightInfo;
        ImageView weatherIconSmall;
        FoldingCell fc;

        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            dateTitle = (TextView) itemView.findViewById(R.id.date_title);
            date = (TextView) itemView.findViewById(R.id.date);
            dayInfo = (TextView) itemView.findViewById(R.id.dayInfo);
            nightInfo = (TextView) itemView.findViewById(R.id.nightInfo);
            weatherIconSmall = (ImageView) itemView.findViewById(R.id.weather_icon_small);
            fc = (FoldingCell) itemView.findViewById(R.id.folding_cell);
            fc.initialize(10, Color.TRANSPARENT, 0);
        }
    }
}
