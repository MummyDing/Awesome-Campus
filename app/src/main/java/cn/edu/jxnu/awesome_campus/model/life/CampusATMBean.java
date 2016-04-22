package cn.edu.jxnu.awesome_campus.model.life;

import java.util.List;

/**
 * Created by MummyDing on 16-4-22.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class CampusATMBean {
    private List<CampusATMModel> CampusATM;

    public List<CampusATMModel> getCampusATM() {
        return CampusATM;
    }

    public void setCampusATM(List<CampusATMModel> campusATM) {
        CampusATM = campusATM;
    }
}
