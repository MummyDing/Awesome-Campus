package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleBean;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

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
        JxnuGoPeopleModel  people=getItem(position);
        holder.userName.getPaint().setFakeBoldText(true);
        holder.img.setImageURI(Uri.parse(people.getUserAvatar()));
        holder.desc.setText(people.getAboutMe());
        holder.userName.setText(people.getUserName());
    }

    public static  class  VH extends RecyclerView.ViewHolder{

        SimpleDraweeView img;
        TextView desc;
        TextView userName;
        public VH(View itemView) {
            super(itemView);
            this.img=(SimpleDraweeView)itemView.findViewById(R.id.jxnugo_user_img);
            this.desc=(TextView)itemView.findViewById(R.id.jxnugo_user_desc);
            this.userName=(TextView)itemView.findViewById(R.id.jxnugo_user_name);
        }
    }
}
