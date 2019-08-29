package com.example.mywhatsappclone.UserSelection.user;

import java.io.Serializable;

public class UserItem implements Serializable {
    private String name,phone,uid;
    private String notificationKey;

    public UserItem(String uid) {
        this.uid=uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public UserItem(String uid,String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setNotificationKey(String notificationKey) {
        this.notificationKey = notificationKey;
    }

    public String getNotificationKey() {
        return notificationKey;
    }
}
