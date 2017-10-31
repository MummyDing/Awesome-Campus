package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-13.
 */
public class CommentBean {

    public CommentModel[] getComments() {
        return comments;
    }

    public void setComments(CommentModel[] comments) {
        this.comments = comments;
    }

    private CommentModel [] comments;

}
