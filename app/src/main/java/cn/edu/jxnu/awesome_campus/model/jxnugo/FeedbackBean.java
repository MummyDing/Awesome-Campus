package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-22.
 */
public class FeedbackBean {
    private String Respondent;
    private String Email;
    private String Body;


    public FeedbackBean(String respondent,String email,String body){
        this.Respondent=respondent;
        this.Email=email;
        this.Body=body;
    }
    public String getRespondent() {
        return Respondent;
    }

    public void setRespondent(String respondent) {
        Respondent = respondent;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }




}
