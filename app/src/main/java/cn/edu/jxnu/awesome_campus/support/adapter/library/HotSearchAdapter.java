package cn.edu.jxnu.awesome_campus.support.adapter.library;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.moxun.tagcloudlib.view.TagsAdapter;

import java.util.List;

import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.ui.library.BookSearchActivity;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class HotSearchAdapter extends TagsAdapter {
    private List<String> tags;
    private Context mContext;

    public HotSearchAdapter(List<String> tags, Context context) {
        this.tags = tags;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setText(tags.get(position));
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Tag " + position + " clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, BookSearchActivity.class);
                intent.putExtra(mContext.getString(R.string.id_search),tags.get(position));
                mContext.startActivity(intent);
            }
        });
        return tv;
    }

    @Override
    public Object getItem(int position) {
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
