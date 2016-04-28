package cn.edu.jxnu.awesome_campus.model.about;

/**
 * Created by MummyDing on 16-4-28.
 * GitHub: https://github.com/MummyDing
 * Blog: http://blog.csdn.net/mummyding
 */
public class NotifyBean {
    private String notifyVersion;
    private NotifyModel [] msgList;

    public String getNotifyVersion() {
        return notifyVersion;
    }

    public void setNotifyVersion(String notifyVersion) {
        this.notifyVersion = notifyVersion;
    }

    public NotifyModel[] getMsgList() {
        return msgList;
    }

    public void setMsgList(NotifyModel[] msgList) {
        this.msgList = msgList;
    }
}
