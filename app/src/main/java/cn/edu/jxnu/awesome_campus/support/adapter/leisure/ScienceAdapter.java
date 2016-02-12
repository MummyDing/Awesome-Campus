package cn.edu.jxnu.awesome_campus.support.adapter.leisure;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;


import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.leisure.ScienceModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

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
    public void onBindViewHolder(VH holder, int position) {
        ScienceModel model = getItem(position);
        holder.scienceTitle.setText(model.getTitle());
        holder.comment.setText(model.getReplies_count()+"");
        holder.scienceImage.setImageURI(Uri.parse(model.getImage_info().getUrl()));
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
