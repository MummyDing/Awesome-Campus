package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

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
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.jxnugo.JxnuGoUserinfoActivity;

/**
 * Created by yzr on 16/5/14.
 */
public class JxnuGoPeopleAdapter extends BaseListAdapter<JxnuGoPeopleModel,JxnuGoPeopleAdapter.VH>{


    public JxnuGoPeopleAdapter(Context mContext, JxnuGoPeopleModel model) {
        super(mContext, model);
    }


    @Override
    protected void updateView() {
            notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.cards_jxnugo_user,parent,false);
        VH vh=new VH(view);
        return  vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final JxnuGoPeopleModel  people=getItem(position);
        final Integer id=Integer.parseInt(people.getUserId());
        holder.userName.getPaint().setFakeBoldText(true);
        holder.img.setImageURI(Uri.parse(people.getUserAvatar()));
        holder.desc.setText(people.getAboutMe());
        holder.userName.setText(people.getUserName());
        holder.itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new EventModel<Integer>(EVENT.JXNUGO_USERINFO_LOAD_USER,id));
                Intent intent=new Intent(mContext, JxnuGoUserinfoActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    public   class  VH extends RecyclerView.ViewHolder{
        SimpleDraweeView img;
        TextView desc;
        TextView userName;
        View itemview;
        public VH(View itemView) {
            super(itemView);
            this.img=(SimpleDraweeView)itemView.findViewById(R.id.jxnugo_user_img);
            this.desc=(TextView)itemView.findViewById(R.id.jxnugo_user_desc);
            this.userName=(TextView)itemView.findViewById(R.id.jxnugo_user_name);
            this.itemview=itemView;
        }
    }
}
