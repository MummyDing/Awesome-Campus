package cn.edu.jxnu.awesome_campus.ui.jxnugo;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import cn.edu.jxnu.awesome_campus.InitApp;
import cn.edu.jxnu.awesome_campus.R;
import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoPeopleDao;
import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoUserDAO;
import cn.edu.jxnu.awesome_campus.event.EVENT;
import cn.edu.jxnu.awesome_campus.event.EventModel;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleLoad;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoUserBean;
import cn.edu.jxnu.awesome_campus.support.spkey.JxnuGoStaticKey;
import cn.edu.jxnu.awesome_campus.support.utils.common.SPUtil;
import cn.edu.jxnu.awesome_campus.support.utils.jxnugo.JxnugoFollowUtil;
import cn.edu.jxnu.awesome_campus.ui.base.BaseToolbarActivity;

/**
 * @author Thereisnospon
 * 2016-5-11
 * 跳转方法:通过发布 JXNUGO_USERINFO_LOAD_USER 的 String 的sticky事件,给定id,加载相应数据
 */

public class JxnuGoUserinfoActivity extends BaseToolbarActivity implements View.OnClickListener{


    private  int id;
    private TextView userDesc;
    TextView userPostNum;
    TextView userCollectNum;
    TextView userFollowerdNum;
    TextView userFollowingNum;
    TextView userLocate;
    ProgressBar progressBar;
    SimpleDraweeView userImg;
    private MenuItem editUserInfoMenu;//修改个人信息菜单

    final String TAG="JXNU_GO";

    private MenuItem favorite, favorite_select;

    public void initView(){
        userImg=(SimpleDraweeView)findViewById(R.id.jxnugo_user_img);
        userDesc=(TextView)findViewById(R.id.jxnugo_user_desc);
        userPostNum=(TextView)findViewById(R.id.jxnugo_user_posts);
        userCollectNum=(TextView)findViewById(R.id.jxnugo_user_collection);
        userFollowerdNum = (TextView)findViewById(R.id.jxnugo_user_followerd);
        userFollowingNum=(TextView)findViewById(R.id.jxnugo_user_following);
        userLocate=(TextView)findViewById(R.id.jxnugo_user_locate);
        View locate=findViewById(R.id.jxnugo_user_card_local);
        View posts=findViewById(R.id.jxnugo_user_card_posts);
        View collect=findViewById(R.id.jxnugo_user_card_collect);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        locate.setOnClickListener(this);
        posts.setOnClickListener(this);
        collect.setOnClickListener(this);
        userFollowingNum.setOnClickListener(this);
        userFollowerdNum.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jxnugo_userinfo);
        EventBus.getDefault().register(this);
        initToolbar();
        loadUserInfo();
        initView();

        progressBar.setVisibility(View.VISIBLE);
    }

    public  void jumpToFollowed(){
        JxnuGoPeopleLoad load=new JxnuGoPeopleLoad(JxnuGoPeopleDao.MODE.FOLLOWED,id);
        Log.d(TAG,"LOAD ID"+id);
        EventBus.getDefault().postSticky(new EventModel<JxnuGoPeopleLoad>(EVENT.JUMP_TO_JXNUGO_LOAD_POEPLE,load));
        Intent intent=new Intent(this,JxnuGoPeopleActivity.class);
        startActivity(intent);
    }
    public  void  jumpToFollowers(){
        JxnuGoPeopleLoad load=new JxnuGoPeopleLoad(JxnuGoPeopleDao.MODE.FOLLOWERS,id);
        Log.d(TAG,"LOAD ID"+id);
        EventBus.getDefault().postSticky(new EventModel<JxnuGoPeopleLoad>(EVENT.JUMP_TO_JXNUGO_LOAD_POEPLE,load));
        Intent intent=new Intent(this,JxnuGoPeopleActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Log.d("JXNU_GO","view onclick");
        switch (v.getId())
        {
            case R.id.jxnugo_user_card_collect:
                Log.d(TAG,"跳转到用户收藏帖子信息");
                break;
            case R.id.jxnugo_user_card_local:
                Log.d(TAG,"点击用户地址信息");
                break;
            case R.id.jxnugo_user_card_posts:
                Log.d(TAG,"跳转到用户发布帖子信息");
                break;
            case R.id.jxnugo_user_followerd:
                Log.d(TAG,"跳转到用户粉丝信息");
                jumpToFollowed();
                break;
            case R.id.jxnugo_user_following:
                Log.d(TAG,"跳转到用户关注人信息");
                jumpToFollowers();
                break;
            default:
                break;
        }
    }

    //接受加载用户信息事件(sticky),加载相应id的用户数据
    public  void loadUserInfo(){
        EventModel model= EventBus.getDefault().getStickyEvent(EventModel.class);
        if(model!=null&&model.getEventCode()==EVENT.JXNUGO_USERINFO_LOAD_USER){
            Integer userId=(Integer) model.getData();
            Log.d("JXNU_GO","load sticky login bean"+userId);
            JxnuGoUserDAO dao=new JxnuGoUserDAO(userId);
            dao.loadFromNet();
        }else if(model!=null&&model.getEventCode()==EVENT.JXNUGO_USERINFO_LOAD_LOGIN_USER){
            SPUtil sp = new SPUtil(InitApp.AppContext);
            int userId=sp.getIntSP(JxnuGoStaticKey.SP_FILE_NAME,JxnuGoStaticKey.USERID);
//            Log.d("JXNU_GO",""+userId);
            JxnuGoUserDAO dao=new JxnuGoUserDAO(userId);
            dao.loadFromNet();
        }
    }

    //用户信息数据更新到ui
    public  void loadInfo(EventModel eventModel){
        JxnuGoUserBean bean=(JxnuGoUserBean)eventModel.getData();
        setToolbarTitle(bean.getName()+"");
        userImg.setImageURI(Uri.parse(bean.getAvatar()));
        userDesc.setText(bean.getAbout_me()+"");
        userLocate.setText(bean.getLocation()+"");
        userFollowerdNum.setText(bean.getFollowed()+"");
        userFollowingNum.setText(bean.getFollowers()+"");
        userCollectNum.setText(bean.getCollectionPostCount()+"");
        userPostNum.setText(bean.getPostCount()+"");
        progressBar.setVisibility(View.GONE);
        id=bean.getUserId();
        Log.d(TAG,"progress"+progressBar.getVisibility());
    }

    @Subscribe
    public void onEventMainThread(EventModel eventModel){
       switch (eventModel.getEventCode())
       {
           case  EVENT.JXNUGO_USERINFO_LOAD_USER:
               break;
           case EVENT.JXNUGO_USERINFO_LOAD_USER_SUCCESS:
               Log.d(TAG,"load userinfo sueccess");
               loadInfo(eventModel);
               break;
           case EVENT.JXNUGO_USERINFO_LOAD_USER_FALURE:
               break;
           case EVENT.JXNUGO_FOLLOW_SUCCESS:
               makeSnack("关注成功");
               break;
           case EVENT.JXNUGO_FOLLOW_FAILURE:
               makeSnack("关注失败");
               break;
           case EVENT.JXNUGO_UNFOLLOW_SUCCESS:
               makeSnack("取消关注成功");
               break;
           case EVENT.JXNUGO_UNFOLLOW_FAILURE:
               makeSnack("取消关注失败");
               break;
           default:
               break;
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG,"创建菜单");
        getMenuInflater().inflate(R.menu.menu_jxnugo_userinfo, menu);
        favorite = menu.findItem(R.id.jxnugo_follow);
        favorite_select = menu.findItem(R.id.jxnugo_follow_selector);
        boolean isLoginUser=judgeUserType();
        editUserInfoMenu=menu.findItem(R.id.jxnugo_userinfo_edit);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 判断用户类型，如果是登录的用户返回true，不是登录的返回false
     * @return
     */
    private boolean judgeUserType() {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.jxnugo_follow:
                favorite.setVisible(false);
                favorite_select.setVisible(true);
                setFavorite(true);
                break;
            case R.id.jxnugo_follow_selector:
                favorite.setVisible(true);
                favorite_select.setVisible(false);
                setFavorite(false);
                break;
            case R.id.jxnugo_userinfo_edit:
                Intent intent=new Intent(this,JxnuGoUserInfoETActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setFavorite(boolean b) {
        JxnugoFollowUtil util=new JxnugoFollowUtil();
        if(b){
            util.followed(id);
        }else {
            util.unFollow(id);
        }
    }

    public  void makeSnack(String msg){
        Snackbar.make(userPostNum, msg, Snackbar.LENGTH_LONG).show();
    }



}
