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
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.leisure.FilmModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.common.TextUtil;
import cn.edu.jxnu.awesome_campus.ui.leisure.FilmDetailsActivity;

/**
 * Created by MummyDing on 16-2-17.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class FilmAdapter extends BaseListAdapter<FilmModel,FilmAdapter.VH> {

    public FilmAdapter(Context mContext, FilmModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_film,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        final FilmModel model = getItem(position);
        if(TextUtil.isNull(model.getTopPic())){
            holder.fileImage.setVisibility(View.GONE);
        }else {
            holder.fileImage.setImageURI(Uri.parse(model.getTopPic()));
        }
        holder.readingCount.setText(model.getReadingCount());
        holder.fileTitle.setText(model.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(mContext,);
                Intent intent = new Intent(mContext, FilmDetailsActivity.class);
                EventBus.getDefault().postSticky(new EventModel<FilmModel>(EVENT.SEND_MODEL_DETAIL,getItem(position)));
                mContext.startActivity(intent);
            }
        });
    }

    class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView fileTitle;
        TextView readingCount;
        SimpleDraweeView fileImage;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            fileTitle = (TextView) itemView.findViewById(R.id.film_title);
            readingCount = (TextView) itemView.findViewById(R.id.reading_count);
            fileImage = (SimpleDraweeView) itemView.findViewById(R.id.film_image);
        }
    }
}
