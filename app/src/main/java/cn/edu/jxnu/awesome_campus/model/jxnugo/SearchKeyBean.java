package cn.edu.jxnu.awesome_campus.model.jxnugo;

/**
 * Created by KevinWu on 16-5-25.
 */
public class SearchKeyBean {
    private String keyWords;
    public SearchKeyBean(String keyWords){
        this.keyWords=keyWords;
    }
    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }



}
