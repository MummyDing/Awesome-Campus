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
import cn.edu.jxnu.awesome_campus.database.dao.leisure.ScienceDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.leisure.ScienceDetailsActivity;

/**
 * Created by MummyDing on 16-2-12.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class ScienceAdapter extends BaseListAdapter<ScienceModel,ScienceAdapter.VH>{


    public ScienceAdapter(Context mContext, ScienceModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_science, parent, false);
        VH vh = new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        ScienceModel model = getItem(position);
        holder.scienceTitle.setText(model.getTitle());
        holder.comment.setText(model.getReplies_count()+"");
        holder.scienceImage.setImageURI(Uri.parse(model.getImage_info().getUrl()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ScienceDetailsActivity.class);
                EventBus.getDefault().postSticky(new EventModel<ScienceModel>(EVENT.SEND_MODEL_DETAIL,getItem(position)));
                mContext.startActivity(intent);
            }
        });
    }

    class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView scienceTitle;
        TextView comment;
        SimpleDraweeView scienceImage;

        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            scienceTitle = (TextView) itemView.findViewById(R.id.science_title);
            comment = (TextView) itemView.findViewById(R.id.comment);
            scienceImage = (SimpleDraweeView) itemView.findViewById(R.id.science_image);
        }
    }
}
