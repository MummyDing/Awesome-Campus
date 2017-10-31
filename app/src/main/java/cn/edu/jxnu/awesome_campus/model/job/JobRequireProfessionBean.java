package cn.edu.jxnu.awesome_campus.model.job;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zpauly on 2016/10/17.
 */

public class JobRequireProfessionBean implements Parcelable {
    private String professional;

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getProfessional() {
        return professional;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.professional);
    }

    public JobRequireProfessionBean() {
    }

    protected JobRequireProfessionBean(Parcel in) {
        this.professional = in.readString();
    }

    public static final Parcelable.Creator<JobRequireProfessionBean> CREATOR = new Parcelable.Creator<JobRequireProfessionBean>() {
        @Override
        public JobRequireProfessionBean createFromParcel(Parcel source) {
            return new JobRequireProfessionBean(source);
        }

        @Override
        public JobRequireProfessionBean[] newArray(int size) {
            return new JobRequireProfessionBean[size];
        }
    };
}
