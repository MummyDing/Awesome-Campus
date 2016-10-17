package cn.edu.jxnu.awesome_campus.model.job;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zpauly on 2016/10/16.
 */

public class JobDetailBean implements Parcelable {
    private String company_name;
    private String introduction;
    private String address;
    private String phone;
    private String homepage;
    private String company_attribute_id;
    private String name;
    private String principal;
    private String principal_info;
    private String publish_time;
    private String create_time;
    private String recruitment_time;
    private String recruitment_place;
    private String process;
    private String attention;
    private String supplement;
    private String email;
    private int rty;
    private String type;
    private JobRequirementModel[] id;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getCompany_attribute_id() {
        return company_attribute_id;
    }

    public void setCompany_attribute_id(String company_attribute_id) {
        this.company_attribute_id = company_attribute_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getPrincipal_info() {
        return principal_info;
    }

    public void setPrincipal_info(String principal_info) {
        this.principal_info = principal_info;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getRecruitment_time() {
        return recruitment_time;
    }

    public void setRecruitment_time(String recruitment_time) {
        this.recruitment_time = recruitment_time;
    }

    public String getRecruitment_place() {
        return recruitment_place;
    }

    public void setRecruitment_place(String recruitment_place) {
        this.recruitment_place = recruitment_place;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getSupplement() {
        return supplement;
    }

    public void setSupplement(String supplement) {
        this.supplement = supplement;
    }

    public int getRty() {
        return rty;
    }

    public void setRty(int rty) {
        this.rty = rty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JobRequirementModel[] getId() {
        return id;
    }

    public void setId(JobRequirementModel[] id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.company_name);
        dest.writeString(this.introduction);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.homepage);
        dest.writeString(this.company_attribute_id);
        dest.writeString(this.name);
        dest.writeString(this.principal);
        dest.writeString(this.principal_info);
        dest.writeString(this.publish_time);
        dest.writeString(this.create_time);
        dest.writeString(this.recruitment_time);
        dest.writeString(this.recruitment_place);
        dest.writeString(this.process);
        dest.writeString(this.attention);
        dest.writeString(this.supplement);
        dest.writeString(this.email);
        dest.writeInt(this.rty);
        dest.writeString(this.type);
        dest.writeTypedArray(this.id, flags);
    }

    public JobDetailBean() {
    }

    protected JobDetailBean(Parcel in) {
        this.company_name = in.readString();
        this.introduction = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
        this.homepage = in.readString();
        this.company_attribute_id = in.readString();
        this.name = in.readString();
        this.principal = in.readString();
        this.principal_info = in.readString();
        this.publish_time = in.readString();
        this.create_time = in.readString();
        this.recruitment_time = in.readString();
        this.recruitment_place = in.readString();
        this.process = in.readString();
        this.attention = in.readString();
        this.supplement = in.readString();
        this.email = in.readString();
        this.rty = in.readInt();
        this.type = in.readString();
        this.id = in.createTypedArray(JobRequirementModel.CREATOR);
    }

    public static final Parcelable.Creator<JobDetailBean> CREATOR = new Parcelable.Creator<JobDetailBean>() {
        @Override
        public JobDetailBean createFromParcel(Parcel source) {
            return new JobDetailBean(source);
        }

        @Override
        public JobDetailBean[] newArray(int size) {
            return new JobDetailBean[size];
        }
    };
}
