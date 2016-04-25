package cn.edu.jxnu.awesome_campus.database.table.life;

/**
 * Created by MummyDing on 16-4-23.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusATMTable {
    /**
     * ATM信息
     */
    public static final String NAME = "CampusATMTable";

    public static final String BANK_NAME = "bankName";

    public static final String BANK_LOCATION = "bankLocation";

    public static final String IMAGE_URL = "imageUrl";


    /**
     * 字段ID 数据库操作建立字段对应关系 从0开始
     */

    public static final int ID_BANK_NAME = 0;

    public static final int ID_BANK_LOCATION = 1;

    public static final int ID_IMAGE_URL = 2;


    public static final String CREATE_TABLE = "create table "+NAME+"("+
            BANK_NAME+" text primary key, "+
            BANK_LOCATION+" text, "+
            IMAGE_URL+" text)";
}
