package com.touyun.model;

/**
 * Created by wenfeng on 2018/4/11.
 */
public class StoreInfo {
    private String storeId;
    private Double latitude;
    private Double longitude;
    private String storeAddress;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public StoreInfo() {
        this.latitude =0D;
        this.longitude =0D;
    }
}
