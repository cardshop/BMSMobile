package com.example.wesley.mobileapp_bms;

import android.net.Uri;

/**
 * Created by Wesley Martins on 23.07.13.
 */
public class Info {

    private String _desc, _lname, _phone, _email, _address;
    private Uri _imageURI;
    private int _id;

    public Info(int id, String fname, String lname, String phone, String email, String address, Uri imageURI) {
        _id = id;
        _desc = fname;
        _lname = lname;
        _phone = phone;
        _email = email;
        _address = address;
        _imageURI = imageURI;
    }

    public int getId() { return _id; }

    public String getDesc() {
        return _desc;
    }

    public String getLName() {
        return _lname;
    }

    public String getPhone() {
        return _phone;
    }

    public String getEmail() {
        return _email;
    }

    public String getAddress() {
        return _address;
    }

    public Uri getImageURI() { return _imageURI; }
}
