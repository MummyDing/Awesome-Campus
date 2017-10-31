package cn.edu.jxnu.awesome_campus.support.adapter.leisure;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.DailyModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.leisure.DailyDetailsActivity;

/**
 * Created by MummyDing on 16-2-12.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class DailyAdapter extends BaseListAdapter<DailyModel,DailyAdapter.VH> {


    public DailyAdapter(Context mContext, DailyModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_daily, parent, false);
        VH vh = new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final DailyModel model = getItem(position);
        holder.dailyTitle.setText(model.getTitle());
        holder.dailyImage.setImageURI(Uri.parse(model.getImages()[0]));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DailyDetailsActivity.class);
                EventBus.getDefault().postSticky(new EventModel<DailyModel>(EVENT.SEND_MODEL_DETAIL,model));
                mContext.startActivity(intent);
                // 这里要加动画
            }
        });
    }


    class VH extends RecyclerView.ViewHolder{
        TextView dailyTitle;
        SimpleDraweeView dailyImage;
        View itemView;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            dailyTitle = (TextView) itemView.findViewById(R.id.daily_title);
            dailyImage = (SimpleDraweeView) itemView.findViewById(R.id.daily_image);
        }
    }

}
