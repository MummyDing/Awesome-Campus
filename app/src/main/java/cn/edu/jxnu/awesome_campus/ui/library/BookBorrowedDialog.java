package cn.edu.jxnu.awesome_campus.ui.library;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.edu.jxnu.awesome_campus.Config;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.library.BookBorrowedModel;
import cn.edu.jxnu.awesome_campus.support.theme.ThemeConfig;

public class BookBorrowedDialog extends Activity {

    private TextView bookCode;
    private TextView bookTitle;
    private TextView author;
    private TextView borrowDate;
    private TextView dueDate;
    private Button renewBtn;
    private TextView bookLocation;
    private ImageButton closeBtn;

    private BookBorrowedModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        renewBtn = (Button) findViewById(R.id.renewBtn);
        bookLocation = (TextView) findViewById(R.id.book_location);
        closeBtn = (ImageButton) findViewById(R.id.btn_close);

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
            renewBtn.setText(R.string.renew);
            renewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 续借逻辑
                    Toast.makeText(getBaseContext(), "续借成功", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            renewBtn.setEnabled(false);
        }



    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 1)
    public void onEventMainThread(EventModel eventModel) {
        if(eventModel.getEventCode() == EVENT.SEND_MODEL_DETAIL){
            model = (BookBorrowedModel) eventModel.getData();
            initView();
        }
    }
}
