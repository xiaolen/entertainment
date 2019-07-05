package com.etoak.crawl.model;

/**
 * @Author: qinyuanyuan
 * @Date: 2018/11/6 14:59
 */
public class HouseIinformation {

    /**
     * 房子价格
     */
    private Integer price;

    /**
     * 租房方式
     */
    private String rentingStyle;

    /**
     * 租房类型
     */
    private String rentingType;

    /**
     *朝向楼层
     */
    private String towardheTfloor;

    /**
     * 所在小区
     */
    private String residentialQuarters;

    /**
     * 地址
     */
    private String address;


    /**
     * 房屋亮点
     */
    private String brightSpot;

    /**
     * 房屋描述
     */
    private String description;


    /**
     * 小区详情
     */
    private String detailsOfTheCommunity;

    /**
     *联系电话
     */
    private String phone;


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRentingStyle() {
        return rentingStyle;
    }

    public void setRentingStyle(String rentingStyle) {
        this.rentingStyle = rentingStyle;
    }

    public String getRentingType() {
        return rentingType;
    }

    public void setRentingType(String rentingType) {
        this.rentingType = rentingType;
    }

    public String getTowardheTfloor() {
        return towardheTfloor;
    }

    public void setTowardheTfloor(String towardheTfloor) {
        this.towardheTfloor = towardheTfloor;
    }

    public String getResidentialQuarters() {
        return residentialQuarters;
    }

    public void setResidentialQuarters(String residentialQuarters) {
        this.residentialQuarters = residentialQuarters;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getBrightSpot() {
        return brightSpot;
    }

    public void setBrightSpot(String brightSpot) {
        this.brightSpot = brightSpot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailsOfTheCommunity() {
        return detailsOfTheCommunity;
    }

    public void setDetailsOfTheCommunity(String detailsOfTheCommunity) {
        this.detailsOfTheCommunity = detailsOfTheCommunity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "HouseIinformation{" +
                "price=" + price +
                ", rentingStyle='" + rentingStyle + '\'' +
                ", rentingType='" + rentingType + '\'' +
                ", towardheTfloor='" + towardheTfloor + '\'' +
                ", residentialQuarters='" + residentialQuarters + '\'' +
                ", address='" + address + '\'' +
                ", brightSpot='" + brightSpot + '\'' +
                ", description='" + description + '\'' +
                ", detailsOfTheCommunity='" + detailsOfTheCommunity + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
