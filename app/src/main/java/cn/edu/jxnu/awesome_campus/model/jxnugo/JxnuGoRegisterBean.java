package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by root on 16-5-12.
 */
public class JxnuGoRegisterBean {

    /**
     * userName :
     * userEmail :
     * passWord :
     */

    private String userName;
    private String userEmail;
    private String passWord;
    private String auth_token;
    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
