package com.example.wesley.mobileapp_bms;

import android.net.Uri;

/**
 * Created by Wesley Martins on 23.07.13.
 */
public class Info {

    private String _desc, _size, _price, _quantity, _location, _time, _type;
    private Uri _imageURI;
    private int _id;

    public Info(int id, String desc, String size, String price, String quantity, String location, String time, String type, Uri imageURI) {
        _id = id;
        _desc = desc;
        _size = size;
        _price = price;
        _quantity = quantity;
        _location = location;
        _time = time;
        _type = type;
        _location = location;
        _imageURI = imageURI;
    }

    public int getId() { return _id; }

    public String getDesc() {
        return _desc;
    }

    public String getSize() {
        return _size;
    }

    public String getPrice() {
        return _price;
    }

    public String getquantity() {
        return _quantity;
    }

    public String getLocation() {
        return _location;
    }

    public String getTime() {
        return _time;
    }

    public String getType() {
        return _type;
    }

    public Uri getImageURI() { return _imageURI; }
}
