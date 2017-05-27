package cn.edu.jxnu.awesome_campus.model.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MummyDing on 2017/5/25.
 */

public class TermSelectInfoBean {

    private int currentSelectIndex = -1;

    private List<TermInfo> termList = new ArrayList<>();

    public void addTermInfo(TermInfo termInfo) {
        termList.add(termInfo);
    }

    public void removeTermInfo() {
        termList.clear();
    }

    public void setCurrentSelectIndex(int currentSelectIndex) {
        this.currentSelectIndex = currentSelectIndex;
    }

    public int getCurrentSelectIndex() {
        return currentSelectIndex;
    }

    public String getCurrentTermString() {
        if (currentSelectIndex == -1) {
            return "";
        }
        TermInfo termInfo = termList.get(currentSelectIndex);
        if (termInfo != null) {
            return termInfo.getTermDate();
        }
        return "";
    }

    public String getTermYearByIndex(int index) {
        if (index < 0) {
            return "";
        }
        TermInfo termInfo = termList.get(index);
        if (termInfo != null) {
            return termInfo.getTermYear();
        }
        return "";
    }

    public List<TermInfo> getTermList() {
        return termList;
    }

    public static class TermInfo {

        private String termDate;

        private String termYear;

        private String termOrder;

        public TermInfo(String termDate, String termYear, String termOrder) {
            this.termDate = termDate;
            this.termYear = termYear;
            this.termOrder = termOrder;
        }

        public String getTermDate() {
            return termDate;
        }

        public String getTermYear() {
            return termYear;
        }

        public String getTermOrder() {
            return termOrder;
        }
    }
}
