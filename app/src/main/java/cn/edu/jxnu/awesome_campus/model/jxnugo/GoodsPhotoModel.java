package cn.edu.jxnu.awesome_campus.model.jxnugo;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by KevinWu on 16-5-14.
 */
public class GoodsPhotoModel implements Serializable,IModel<GoodsPhotoModel> {

    private int photoId;
    private String photoPath;
    //private String thumbPath;
    private int width;
    private int height;

    public GoodsPhotoModel(int photoId,String photoPath,int width,int height){
        this.photoId=photoId;
        this.photoPath=photoPath;
        this.width=width;
        this.height=height;
    }
    public GoodsPhotoModel() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
    //
    //public String getThumbPath() {
    //    return thumbPath;
    //}
    //
    //public void setThumbPath(String thumbPath) {
    //    this.thumbPath = thumbPath;
    //}


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof GoodsPhotoModel)) {
            return false;
        }
        GoodsPhotoModel info = (GoodsPhotoModel) o;
        if (info == null) {
            return false;
        }

        return TextUtils.equals(info.getPhotoPath(), getPhotoPath());
    }

    @Override
    public boolean cacheAll(List<GoodsPhotoModel> list) {
        return false;
    }

    @Override
    public boolean clearCache() {
        return false;
    }

    @Override
    public void loadFromCache() {

    }

    @Override
    public void loadFromNet() {

    }
}
