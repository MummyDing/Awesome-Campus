package cn.edu.jxnu.awesome_campus.support.adapter.library;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.w3c.dom.Text;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.library.SelfStudySeatLeftModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by MummyDing on 16-4-24.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class SelfStudySeatsAdapter extends BaseListAdapter<SelfStudySeatLeftModel,SelfStudySeatsAdapter.VH>{


    public SelfStudySeatsAdapter(Context mContext, SelfStudySeatLeftModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_self_study_seat_left,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final VH holder, int position) {
        SelfStudySeatLeftModel model=getItem(position);
        holder.roomInfoTitle.setText(model.getRoomInfo());
        holder.roomInfo.setText(model.getRoomInfo());
        holder.freeSeat.setText(model.getFreeSeat());
        holder.temporaryLeave.setText(model.getTemporaryLeave());
        holder.totalSeat.setText(model.getTotalSeat());
        holder.bePresent.setText(model.getBePresent());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(false);
            }
        });
    }

    static class VH extends RecyclerView.ViewHolder{
        View itemView;
        TextView roomInfo;
        TextView roomInfoTitle;
        TextView bePresent;
        TextView totalSeat;
        TextView temporaryLeave;
        TextView freeSeat;
        FoldingCell fc;
        public VH(View itemView) {
            super(itemView);
            this.itemView=itemView;
            roomInfo=(TextView)itemView.findViewById(R.id.roomInfo);
            roomInfoTitle=(TextView)itemView.findViewById(R.id.roomInfoTitle);
            bePresent=(TextView)itemView.findViewById(R.id.bePresent);
            totalSeat=(TextView)itemView.findViewById(R.id.totalSeat);
            temporaryLeave=(TextView)itemView.findViewById(R.id.temporaryLeave);
            freeSeat=(TextView)itemView.findViewById(R.id.freeSeat);
            fc=(FoldingCell)itemView.findViewById(R.id.folding_cell);
            fc.initialize(10, Color.TRANSPARENT, 0);
        }
    }
}
