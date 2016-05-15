package cn.edu.jxnu.awesome_campus.support.adapter.jxnugo;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
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
import cn.edu.jxnu.awesome_campus.model.jxnugo.CommentModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;

/**
 * Created by KevinWu on 16-5-13.
 */
public class CommentListAdapter extends BaseListAdapter<CommentModel,CommentListAdapter.VH>{
    public CommentListAdapter(Context mContext, CommentModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public CommentListAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_goods_comment, parent, false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(CommentListAdapter.VH holder, int position) {
        final CommentModel model=getItem(position);
        holder.author.setText(model.getAuthor());
        holder.body.setText(model.getBody());
        holder.timestamp.setText(model.getTimestamp());
        if(model.getAuthorAvatar()!=null)
        holder.authorAvatar.setImageURI(Uri.parse(model.getAuthorAvatar()));
        final Handler handler = new Handler(Looper.getMainLooper());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(new EventModel<CommentModel>(EVENT.COMMENT_TRIGGER,model));
                    }
                });
            }
        });
    }
    static class VH extends RecyclerView.ViewHolder {
        View itemView;
        TextView author;
        TextView timestamp;
        TextView body;
        SimpleDraweeView authorAvatar;

        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            body=(TextView)itemView.findViewById(R.id.comment);
            author=(TextView)itemView.findViewById(R.id.username);
            timestamp=(TextView)itemView.findViewById(R.id.goods_time);
            authorAvatar=(SimpleDraweeView)itemView.findViewById(R.id.authorAvatar);

        }
    }
}
