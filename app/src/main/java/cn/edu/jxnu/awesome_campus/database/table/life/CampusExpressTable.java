package cn.edu.jxnu.awesome_campus.database.table.life;

/**
 * Created by MummyDing on 16-1-26.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusExpressTable {

    /**
     * 快递信息
     */
    public static final String NAME = "CampusExpressTable";

    public static final String EXP_TEXT_NAME = "expTextName";

    public static final String EXP_SPELL_NAME = "expSpellName";

    public static final String EXP_TEL = "expTel";

    public static final String EXP_LOC = "expLoc";

    public static final String WORK_TIME = "workTime";

    public static final String ITEM_IMAGE = "itemImage";

    public static final String TOP_IMAGE = "topImage";

    public static final String BODY = "body";

    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */
    public static final int ID_EXP_TEXT_NAME = 0;

    public static final int ID_EXP_SPELL_NAME = 1;

    public static final int ID_EXP_TEL = 2;

    public static final int ID_EXP_LOC = 3;

    public static final int ID_WORK_TIME = 4;

    public static final int ID_ITEM_IMAGE = 5;

    public static final int ID_TOP_IMAGE = 6;

    public static final int ID_BODY = 7;

    public static final String CREATE_TABLE = "create table "+NAME+"("+
            EXP_TEXT_NAME+" text primary key, "+
            EXP_SPELL_NAME+" text, "+
            EXP_TEL+" text, "+
            EXP_LOC+" text, "+
            WORK_TIME+" text, "+
            ITEM_IMAGE +" text, "+
            TOP_IMAGE+" text, "+
            BODY+" text)";



}
