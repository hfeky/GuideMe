package com.guideme.guideme.data.models;

public class EmergencyNumber {

    private String primaryPhone, secondaryPhone, title;

    public EmergencyNumber(String phone, String title) {
        this(phone, null, title);
    }

    public EmergencyNumber(String primaryPhone, String secondaryPhone, String title) {
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.title = title;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String phone) {
        this.primaryPhone = phone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String phone) {
        this.secondaryPhone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
