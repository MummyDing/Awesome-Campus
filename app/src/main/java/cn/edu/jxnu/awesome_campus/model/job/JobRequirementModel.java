package cn.edu.jxnu.awesome_campus.model.job;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import cn.edu.jxnu.awesome_campus.model.IModel;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobRequirementModel implements IModel<JobRequirementModel>,Parcelable {
    public JobRequireProfessionBean[] getProfessional_id() {
        return professional_id;
    }

    public void setProfessional_id(JobRequireProfessionBean[] profession_id) {
        this.professional_id = profession_id;
    }

    public String getPosition_name_id() {
        return position_name_id;
    }

    public void setPosition_name_id(String position_name_id) {
        this.position_name_id = position_name_id;
    }

    public String getDegree_required() {
        return degree_required;
    }

    public void setDegree_required(String degree_require) {
        this.degree_required = degree_require;
    }

    public int getPosition_number() {
        return position_number;
    }

    public void setPosition_number(int position_number) {
        this.position_number = position_number;
    }

    public String getSalary_welfare() {
        return salary_welfare;
    }

    public void setSalary_welfare(String salary_welare) {
        this.salary_welfare = salary_welare;
    }

    public String getPosition_duty() {
        return position_duty;
    }

    public void setPosition_duty(String position_duty) {
        this.position_duty = position_duty;
    }

    private JobRequireProfessionBean[] professional_id;
    private String position_name_id;
    private String degree_required;
    private int position_number;
    private String salary_welfare;
    private String position_duty;

    @Override
    public boolean cacheAll(List<JobRequirementModel> list) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.professional_id, flags);
        dest.writeString(this.position_name_id);
        dest.writeString(this.degree_required);
        dest.writeInt(this.position_number);
        dest.writeString(this.salary_welfare);
        dest.writeString(this.position_duty);
    }

    public JobRequirementModel() {
    }

    protected JobRequirementModel(Parcel in) {
        this.professional_id = in.createTypedArray(JobRequireProfessionBean.CREATOR);
        this.position_name_id = in.readString();
        this.degree_required = in.readString();
        this.position_number = in.readInt();
        this.salary_welfare = in.readString();
        this.position_duty = in.readString();
    }

    public static final Parcelable.Creator<JobRequirementModel> CREATOR = new Parcelable.Creator<JobRequirementModel>() {
        @Override
        public JobRequirementModel createFromParcel(Parcel source) {
            return new JobRequirementModel(source);
        }

        @Override
        public JobRequirementModel[] newArray(int size) {
            return new JobRequirementModel[size];
        }
    };
}
