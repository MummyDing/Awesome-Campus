package cn.edu.jxnu.awesome_campus.support.adapter.life;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ramotion.foldingcell.FoldingCell;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.life.CampusATMModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by MummyDing on 16-4-22.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusATMAdapter extends BaseListAdapter<CampusATMModel,CampusATMAdapter.VH>{


    public CampusATMAdapter(Context mContext, CampusATMModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_campus_atm,parent,false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        CampusATMModel model = getItem(position);
        holder.imageView.setImageURI(Uri.parse(model.getImageUrl()));
        holder.location.setText(model.getBankLocation());
        holder.bankName.setText(model.getBankName());
        holder.fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(false);
            }
        });
    }

    static class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView location;
        TextView bankName;
        SimpleDraweeView imageView;
        FoldingCell fc;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            location = (TextView) itemView.findViewById(R.id.location);
            bankName = (TextView) itemView.findViewById(R.id.bankName);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.image);
            fc=(FoldingCell)itemView.findViewById(R.id.folding_cell);
            fc.initialize(10, Color.TRANSPARENT, 0);
        }
    }
}
