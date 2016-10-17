package cn.edu.jxnu.awesome_campus.model.job;

/**
 * Created by yzr on 16/10/17.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class JobPage {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("posts")
    @Expose
    private List<Post> posts = new ArrayList<Post>();
    @SerializedName("next_url")
    @Expose
    private String nextUrl;
    @SerializedName("prev_url")
    @Expose
    private Object prevUrl;

    /**
     *
     * @return
     * The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     * @param count
     * The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     *
     * @return
     * The posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     *
     * @param posts
     * The posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     *
     * @return
     * The nextUrl
     */
    public String getNextUrl() {
        return nextUrl;
    }

    /**
     *
     * @param nextUrl
     * The next_url
     */
    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    /**
     *
     * @return
     * The prevUrl
     */
    public Object getPrevUrl() {
        return prevUrl;
    }

    /**
     *
     * @param prevUrl
     * The prev_url
     */
    public void setPrevUrl(Object prevUrl) {
        this.prevUrl = prevUrl;
    }

}