package cn.edu.jxnu.awesome_campus.support.adapter.library;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.model.library.HotSearchModel;
import cn.edu.jxnu.awesome_campus.ui.library.BookSearchActivity;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HotSearchAdapter extends TagsAdapter {
    private List<HotSearchModel> tags;
    private Context mContext;

    public HotSearchAdapter(Context context) {
        this.tags = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(getItem(position).getTag());
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BookSearchActivity.class);
                intent.putExtra(mContext.getString(R.string.id_search),getItem(position).getTag());
                mContext.startActivity(intent);
            }
        });
        return tv;
    }


    public void newTags(List<HotSearchModel> tags){
        if (tags == null || tags.isEmpty()) return;
        this.tags.clear();
        this.tags.addAll(tags);
        notifyDataSetChanged();
    }

    @Override
    public HotSearchModel getItem(int position) {
        return tags.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position%7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
        ((TextView) view).setTextColor(themeColor);
    }
}
