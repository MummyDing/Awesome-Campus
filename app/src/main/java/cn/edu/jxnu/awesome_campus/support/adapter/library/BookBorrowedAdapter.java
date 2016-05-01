package cn.edu.jxnu.awesome_campus.support.adapter.library;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.ui.library.BookBorrowedDialog;

/**
 * Created by MummyDing on 16-2-19.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookBorrowedAdapter extends BaseListAdapter<BookBorrowedModel,BookBorrowedAdapter.VH> {


    public BookBorrowedAdapter(Context mContext, BookBorrowedModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_book_borrowed,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final BookBorrowedModel model = getItem(position);
        holder.bookTitle.setText(model.getBookTitle());
        holder.restDays.setText(TimeUtil.getTimeDiff(TimeUtil.getYearMonthDay(),model.getShouldBackTime()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookBorrowedDialog.class);
                EventBus.getDefault().postSticky(new EventModel<BookBorrowedModel>(EVENT.SEND_MODEL_DETAIL,model));
                mContext.startActivity(intent);
            }
        });
    }


    class VH extends RecyclerView.ViewHolder{

        View itemView;
        TextView bookTitle;
        TextView restDays;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            restDays = (TextView) itemView.findViewById(R.id.restDays);
            GradientDrawable myGrad=(GradientDrawable)restDays.getBackground();
            TypedArray array = mContext.getTheme().obtainStyledAttributes(new int[] {
                    android.R.attr.colorAccent,
            });
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            myGrad.setColor(array.getColor(0,0xFFFFFF));
            array.recycle();
        }
    }
}
