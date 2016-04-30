package cn.edu.jxnu.awesome_campus.support.adapter.about;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.table.about.NotifyTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.IModel;
import cn.edu.jxnu.awesome_campus.model.about.NotifyModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.about.NotifyActivity;
import cn.edu.jxnu.awesome_campus.ui.education.ScoreDetailsDialog;

/**
 * Created by KevinWu on 16-4-28.
 */
public class NotifyAdapter extends BaseListAdapter<NotifyModel,NotifyAdapter.VH> {
    public NotifyAdapter(Context mContext, NotifyModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_notify_list,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        final NotifyModel model = getItem(position);
        holder.notifyTitle.setText(model.getTitle());
        holder.date.setText(model.getDate());
        if(model.getReaded() == 0){
            holder.readed.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 标记已读
                DatabaseHelper.exeSQL(NotifyTable.UPDATE_READED,"1",model.getTitle());
                holder.readed.setVisibility(View.GONE);

                Intent intent = new Intent(mContext,NotifyActivity.class);
                intent.putExtra(InitApp.AppContext.getString(R.string.id_title),model.getTitle());
                intent.putExtra(InitApp.AppContext.getString(R.string.id_type),model.getType());
                intent.putExtra(InitApp.AppContext.getString(R.string.id_data),model.getData());
                mContext.startActivity(intent);
            }
        });

    }


    class VH extends RecyclerView.ViewHolder{
        View itemView;
        TextView notifyTitle;
        TextView date;
        ImageView readed;

        public VH(View itemView) {
            super(itemView);
            this.itemView=itemView;
            notifyTitle=(TextView)itemView.findViewById(R.id.notify_title);
            date=(TextView)itemView.findViewById(R.id.notify_date);
            readed=(ImageView)itemView.findViewById(R.id.readedImg);
        }
    }
}
