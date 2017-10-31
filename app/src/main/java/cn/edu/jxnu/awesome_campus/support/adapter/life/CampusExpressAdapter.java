package cn.edu.jxnu.awesome_campus.support.adapter.life;

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
import cn.edu.jxnu.awesome_campus.model.life.CampusExpressModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.home.CampusNewsDetailsActivity;
import cn.edu.jxnu.awesome_campus.ui.life.CampusExpressDetailsActivity;

/**
 * Created by MummyDing on 16-2-20.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusExpressAdapter extends BaseListAdapter<CampusExpressModel,CampusExpressAdapter.VH> {

    public CampusExpressAdapter(Context mContext, CampusExpressModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_campus_express,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final CampusExpressModel model = getItem(position);
        holder.expressName.setText(model.getExpTextName());
        holder.workTime.setText(model.getWorkTime());
        holder.expressLoc.setText(model.getExpLoc());
        holder.expressTel.setText(model.getExpTel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CampusExpressDetailsActivity.class);
                mContext.startActivity(intent);
                EventBus.getDefault().postSticky(new EventModel<CampusExpressModel>(EVENT.SEND_MODEL_DETAIL,model));

            }
        });
    }

    static class VH extends RecyclerView.ViewHolder{
        View itemView;
        TextView expressName;
        TextView workTime;
        TextView expressLoc;
        TextView expressTel;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            expressName = (TextView) itemView.findViewById(R.id.expressName);
            workTime = (TextView) itemView.findViewById(R.id.workTime);
            expressLoc = (TextView) itemView.findViewById(R.id.expressLoc);
            expressTel = (TextView) itemView.findViewById(R.id.expressTel);
        }
    }
}
