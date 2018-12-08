package com.retrofitjavalibrary.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CallsInfo extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private String phone;
    private String details;
    private String image;
    private boolean call_with_apps;
    private String recurring;
    private boolean is_deleted;
    private String is_deleted_by;
    private boolean is_missed_call;
    private boolean is_schedule_call;
    private boolean time_schedule;
    private String exp_date;
    private String exp_time;
    private String create_date;
    private boolean is_phono_user;
    private String last_updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isCall_with_apps() {
        return call_with_apps;
    }

    public void setCall_with_apps(boolean call_with_apps) {
        this.call_with_apps = call_with_apps;
    }

    public String getRecurring() {
        return recurring;
    }

    public void setRecurring(String recurring) {
        this.recurring = recurring;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getIs_deleted_by() {
        return is_deleted_by;
    }

    public void setIs_deleted_by(String is_deleted_by) {
        this.is_deleted_by = is_deleted_by;
    }

    public boolean isIs_missed_call() {
        return is_missed_call;
    }

    public void setIs_missed_call(boolean is_missed_call) {
        this.is_missed_call = is_missed_call;
    }

    public boolean isIs_schedule_call() {
        return is_schedule_call;
    }

    public void setIs_schedule_call(boolean is_schedule_call) {
        this.is_schedule_call = is_schedule_call;
    }

    public boolean isTime_schedule() {
        return time_schedule;
    }

    public void setTime_schedule(boolean time_schedule) {
        this.time_schedule = time_schedule;
    }

    public String getExp_date() {
        return exp_date;
    }

    public void setExp_date(String exp_date) {
        this.exp_date = exp_date;
    }

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public boolean isIs_phono_user() {
        return is_phono_user;
    }

    public void setIs_phono_user(boolean is_phono_user) {
        this.is_phono_user = is_phono_user;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }
}
