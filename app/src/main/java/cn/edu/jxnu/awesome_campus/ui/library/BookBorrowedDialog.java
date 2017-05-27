package cn.edu.jxnu.awesome_campus.ui.library;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Headers;
import com.tendcloud.tenddata.TCAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.Config;
import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.DatabaseHelper;
import cn.edu.jxnu.awesome_campus.database.table.library.BookBorrowedTable;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;
import cn.edu.jxnu.awesome_campus.support.spkey.LibraryStaticKey;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;
import cn.edu.jxnu.awesome_campus.support.config.Urlconfig;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.common.TimeUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.NetManageUtil;
import cn.edu.jxnu.awesome_campus.support.utils.net.callback.StringCallback;

public class BookBorrowedDialog extends Activity {
    private TextView bookCode;
    private TextView bookTitle;
    private TextView author;
    private TextView borrowDate;
    private TextView dueDate;
    private TextView renewBtn;
    private TextView bookLocation;
    private ImageButton closeBtn;
    private Button againBtn;//续借按钮

    private BookBorrowedModel model;
    private static final String TAG="BookBorrowedDialog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TCAgent.onPageStart(InitApp.AppContext, TAG);
        setTheme(ThemeConfig.themeDialogStyle[Config.themeSelected]);
        setContentView(R.layout.dialog_book_borrowed);
        EventBus.getDefault().register(this);
    }

    private void initView(){
        bookCode = (TextView) findViewById(R.id.bookCode);
        bookTitle = (TextView) findViewById(R.id.bookTitle);
        author = (TextView) findViewById(R.id.author);
        borrowDate = (TextView) findViewById(R.id.borrowedDate);
        dueDate = (TextView) findViewById(R.id.due_date);
        renewBtn = (TextView) findViewById(R.id.renew);
        bookLocation = (TextView) findViewById(R.id.book_location);
        closeBtn = (ImageButton) findViewById(R.id.btn_close);
        againBtn=(Button)findViewById(R.id.againBtn);

        bookCode.setText(model.getBookCode());
        bookTitle.setText(model.getBookTitle());
        author.setText(model.getAuthor());
        borrowDate.setText(model.getBorrowTime());
        dueDate.setText(model.getShouldBackTime());



        bookLocation.setText(model.getBookLocation());

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (model.getAgainTimes().equals("0")){
            renewBtn.setText("未续借");
        }

//        当剩余还书时间小于15
        if(Integer.parseInt(TimeUtil.getTimeDiff(TimeUtil.getYearMonthDay(),model.getShouldBackTime()))<15
                &&model.getAgainTimes().equals("0")){
            againBtn.setVisibility(View.VISIBLE);
            againBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    renewBook();
                }
            });
        }


    }

    /**
     * 续借方法
     */
    private void renewBook() {
        final Handler handler = new Handler(Looper.getMainLooper());
        SPUtil spu = new SPUtil(InitApp.AppContext);
        String cookies = spu.getStringSP(LibraryStaticKey.SP_FILE_NAME, LibraryStaticKey.COOKIE);
        String timeStamp=TimeUtil.getTimestamp()+"";
        String bookCode=model.getBookCode();
        String fullURL= Urlconfig.Library_Book_Renew_URL+"bar_code="+bookCode+"&time="+timeStamp;
        NetManageUtil.post(fullURL)
                .addTag(TAG)
                .addHeader("Cookie", cookies)
                .addParams("bar_code",bookCode)
                .addParams("time",timeStamp)
                .enqueue(new StringCallback() {
                    @Override
                    public void onSuccess(final String result, Headers headers) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if(result.indexOf("续借成功")>0){
                                    EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.LIBRARY_RENEW_SUCCESS));
                                }else{
                                    EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.LIBRARY_RENEW_FAILURE));
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.LIBRARY_RENEW_FAILURE));
                    }
                });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        if(eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL){
            model = (BookBorrowedModel) eventModel.getData();
            initView();
        }else if(eventModel.getEventCode() == EVENT.LIBRARY_RENEW_SUCCESS){
            DatabaseHelper.exeSQL(BookBorrowedTable.UPDATE_RENEW,model.getBookCode());
            //通知刷新已借图书列表
            EventBus.getDefault().post(new EventModel<BookBorrowedModel>(EVENT.BOOK_BORROWED_LOAD_CACHE_FAILURE));
            //finish当前列表
            finish();

        }
        else if(eventModel.getEventCode() == EVENT.LIBRARY_RENEW_SUCCESS){
            Toast.makeText(getApplicationContext(),"续借失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        TCAgent.onPageEnd(InitApp.AppContext, TAG);
        super.onDestroy();
    }
}
