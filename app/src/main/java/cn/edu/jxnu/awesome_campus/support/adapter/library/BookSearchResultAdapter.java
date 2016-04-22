package cn.edu.jxnu.awesome_campus.support.adapter.library;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.library.SearchResultDialog;

/**
 * Created by MummyDing on 16-2-19.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class BookSearchResultAdapter extends BaseListAdapter<BookSearchResultModel,BookSearchResultAdapter.VH> {


    private String keyword;

    public BookSearchResultAdapter(Context mContext, BookSearchResultModel model) {
        super(mContext, model);
    }

    @Override
    protected void updateView() {
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_book_result,parent,false);
        VH vh = new VH(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        final BookSearchResultModel model =getItem(position); //new BookSearchResultModel("");

        String titleHtml = model.getBookTitle().replaceAll(keyword,"<font color=\"red\">"+keyword+"</font>");
        holder.bookTitle.setText(Html.fromHtml(titleHtml));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchResultDialog.class);
                EventBus.getDefault().postSticky(new EventModel<BookSearchResultModel>(EVENT.BOOK_RESULT_DETAILS,model));
                mContext.startActivity(intent);
            }
        });
    }

    class VH extends RecyclerView.ViewHolder{
        View itemView;
        TextView bookTitle;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
        }
    }

    @Override
    public void addKeyword(String keyword) {
        this.keyword = keyword;
    }
}
