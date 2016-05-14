package cn.edu.jxnu.awesome_campus.model.jxnugo;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoPeopleDao;
import cn.edu.jxnu.awesome_campus.model.jxnugo.JxnuGoPeopleBean;

/**
 * Created by yzr on 16/5/14.
 */
public class JxnuGoPeopleListBean {

   JxnuGoPeopleModel[] followed;
   JxnuGoPeopleModel[] followers;

    JxnuGoPeopleListBean(){
        followed=new JxnuGoPeopleModel[0];
        followers=new JxnuGoPeopleModel[0];
    }

    public JxnuGoPeopleModel[] getFollowed() {
        return followed;
    }

    public void setFollowed(JxnuGoPeopleModel[] followed) {
        this.followed = followed;
    }

    public JxnuGoPeopleModel[] getFollowers() {
        return followers;
    }

    public void setFollowers(JxnuGoPeopleModel[] followers) {
        this.followers = followers;
    }
}
