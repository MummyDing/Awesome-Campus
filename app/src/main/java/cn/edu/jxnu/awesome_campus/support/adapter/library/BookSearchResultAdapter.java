package cn.edu.jxnu.awesome_campus.support.adapter.library;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;
import cn.edu.jxnu.awesome_campus.support.adapter.BaseListAdapter;
import cn.edu.jxnu.awesome_campus.ui.library.BookSearchDetailsActivity;

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
    public void onBindViewHolder(final VH holder, int position) {
        final BookSearchResultModel model =getItem(position); //new BookSearchResultModel("");

        String titleHtml = model.getBookTitle().replaceAll(keyword,"<font color=\"red\">"+keyword+"</font>");
        holder.bookTitle.setText(Html.fromHtml(titleHtml));
        holder.bookTitleContent.setText(model.getBookTitle());
        holder.publisher.setText(model.getBookPublisher());
        holder.bookClass.setText(model.getBookClass());
        holder.author.setText(model.getBookAuthor());
        holder.bookCount.setText(model.getBookCount());
        holder.bookLeft.setText(model.getBookLeft());
        holder.bookNumber.setText(model.getBookNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.fc.toggle(false);
            }
        });
        holder.moreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookSearchDetailsActivity.class);
                Log.d("--","URL为"+model.getUrl());
                intent.putExtra("URL",model.getUrl());
                intent.putExtra("TITLE",model.getBookTitle());
                mContext.startActivity(intent);
            }
        });
    }

    class VH extends RecyclerView.ViewHolder{
        View itemView;
        TextView bookTitle;
        TextView bookTitleContent;
        TextView bookNumber;//条形码
        TextView author;
        TextView bookClass;//书类别
        TextView publisher;//出版社
        TextView bookLeft;
        TextView bookCount;
        AppCompatButton moreDetail;
        FoldingCell fc;
        public VH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            bookTitleContent=(TextView)itemView.findViewById(R.id.bookTitleContent);
            bookNumber=(TextView)itemView.findViewById(R.id.bookNumber);
            author=(TextView)itemView.findViewById(R.id.author);
            bookClass=(TextView)itemView.findViewById(R.id.bookClass);
            publisher=(TextView)itemView.findViewById(R.id.publisher);
            bookLeft=(TextView)itemView.findViewById(R.id.bookLeft);
            bookCount=(TextView)itemView.findViewById(R.id.bookCount);
            moreDetail=(AppCompatButton)itemView.findViewById(R.id.moreDetail);
            fc=(FoldingCell)itemView.findViewById(R.id.folding_cell);
            fc.initialize(10, Color.TRANSPARENT, 0);

        }
    }

    @Override
    public void addKeyword(String keyword) {
        this.keyword = keyword;
    }
}
