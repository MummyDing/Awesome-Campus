package cn.edu.jxnu.awesome_campus.model.jxnugo;

import java.util.List;

import cn.edu.jxnu.awesome_campus.database.dao.jxnugo.CommentDAO;
import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-5-13.
 */
public class CommentModel implements IModel<CommentModel> {

    private CommentDAO dao;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String author;
    private String authorAvatar;
    private int authorId;
    private String body;
    private String timestamp;

    public CommentModel(){
        dao=new CommentDAO();
    }
    public CommentModel(String author,String authorAvatar,int authorId,String body,String timestamp){
        this.author=author;
        this.authorAvatar=authorAvatar;
        this.authorId=authorId;
        this.body=body;
        this.timestamp=timestamp;
    }

    @Override
    public boolean cacheAll(List<CommentModel> list) {
        return dao.cacheAll(list);

    }

    @Override
    public boolean clearCache() {
        return dao.clearCache();
    }

    @Override
    public void loadFromCache() {
        dao.loadFromCache();
    }

    @Override
    public void loadFromNet() {
        dao.loadFromNet();
    }
}
