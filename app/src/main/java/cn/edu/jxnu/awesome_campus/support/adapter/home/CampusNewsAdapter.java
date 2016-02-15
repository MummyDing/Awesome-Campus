package cn.edu.jxnu.awesome_campus.support.adapter.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.home.CampusNewsModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.home.CampusNewsDetailsActivity;

/**
 * Created by MummyDing on 16-2-9.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusNewsAdapter extends BaseListAdapter<CampusNewsModel,CampusNewsAdapter.VH> {

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    public CampusNewsAdapter(Context mContext, CampusNewsModel model) {
        super(mContext, model);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_campus_news, parent, false);
        VH vh = new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        CampusNewsModel model = getItem(position);
        holder.news_title.setText(model.getNewsTitle());
        holder.news_date.setText(model.getNewsTime());

        /**
         * 测试用 非正式代码 ！！！！
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CampusNewsDetailsActivity.class);
                mContext.startActivity(intent);
                EventBus.getDefault().postSticky(new EventModel<CampusNewsModel>(EVENT.SEND_MODEL_DETAIL,getItem(position)));
            }
        });
    }

    class VH extends RecyclerView.ViewHolder{

         TextView news_title;
         TextView news_date;
         View itemView;
         VH(View itemView) {
             super(itemView);
             this.itemView = itemView;
             news_title = (TextView) itemView.findViewById(R.id.news_title);
             news_date = (TextView) itemView.findViewById(R.id.news_date);
         }
    }
}
