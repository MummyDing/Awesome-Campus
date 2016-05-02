package cn.edu.jxnu.awesome_campus.ui.library;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.Config;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.education.CourseScoreModel;
import cn.edu.jxnu.awesome_campus.model.library.BookSearchResultModel;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;

public class SearchResultDialog extends Activity {
    private static final String TAG="SearchResultDialog";
    private TextView bookTitle;
    private TextView bookNumber;
    private TextView bookClass;
    private TextView bookAuthor;
    private TextView bookPublisher;
    private TextView bookLeft;
    private TextView bookCount;

    private ImageButton closeBtn;
    private BookSearchResultModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setTheme(ThemeConfig.themeDialogStyle[Config.themeSelected]);
        setContentView(R.layout.dialog_search_result);
        EventBus.getDefault().register(this);

    }


    private void initView(){
        bookTitle = (TextView) findViewById(R.id.bookTitle);
        bookNumber = (TextView) findViewById(R.id.bookNumber);
        bookClass = (TextView) findViewById(R.id.bookClass);
        bookAuthor = (TextView) findViewById(R.id.author);
        bookPublisher = (TextView) findViewById(R.id.publisher);
        bookLeft = (TextView) findViewById(R.id.bookLeft);
        bookCount = (TextView) findViewById(R.id.bookCount);
        closeBtn = (ImageButton) findViewById(R.id.btn_close);

        bookTitle.setText(model.getBookTitle());
        bookNumber.setText(model.getBookNumber());
        bookClass.setText(model.getBookClass());
        bookAuthor.setText(model.getBookAuthor());
        bookPublisher.setText(model.getBookPublisher());
        bookLeft.setText(model.getBookLeft());
        bookCount.setText(model.getBookCount());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        if(eventModel.getEventCode() == EVENT.BOOK_RESULT_DETAILS){
            model = (BookSearchResultModel) eventModel.getData();
            initView();
        }
    }

}
