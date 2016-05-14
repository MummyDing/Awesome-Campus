package cn.edu.jxnu.awesome_campus.model.jxnugo;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.JxnuGoPeopleDao;

/**
 * Created by yzr on 16/5/14.
 */
public class JxnuGoPeopleLoad {

    JxnuGoPeopleDao.MODE mode;
    int id;
   public JxnuGoPeopleLoad(JxnuGoPeopleDao.MODE mode,int id){
        this.mode=mode;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public JxnuGoPeopleDao.MODE getMode() {
        return mode;
    }

    public void setMode(JxnuGoPeopleDao.MODE mode) {
        this.mode = mode;
    }
}
