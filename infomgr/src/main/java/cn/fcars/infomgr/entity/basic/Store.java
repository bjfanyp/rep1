package cn.fcars.infomgr.entity.basic;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by fanyp on 2018/11/25.
 */
public class Store {
    private String id;
    @NotNull(message = "不为空")
    @Size(max = 20,message = "长度不超过20")
    private String storeName;

    private String storeFzr;

    private String storeFzrDh;

    private String storeAddressProvince;

    private String storeAddressProvinceID;

    private String storeAddressCity;

    private String storeAddressCityID;

    private String storeAddressDistrict;

    private String storeAddressDistrictID;

    private String storeAddress;

    private String isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreFzr() {
        return storeFzr;
    }

    public void setStoreFzr(String storeFzr) {
        this.storeFzr = storeFzr;
    }

    public String getStoreFzrDh() {
        return storeFzrDh;
    }

    public void setStoreFzrDh(String storeFzrDh) {
        this.storeFzrDh = storeFzrDh;
    }

    public String getStoreAddressProvince() {
        return storeAddressProvince;
    }

    public void setStoreAddressProvince(String storeAddressProvince) {
        this.storeAddressProvince = storeAddressProvince;
    }

    public String getStoreAddressProvinceID() {
        return storeAddressProvinceID;
    }

    public void setStoreAddressProvinceID(String storeAddressProvinceID) {
        this.storeAddressProvinceID = storeAddressProvinceID;
    }

    public String getStoreAddressCity() {
        return storeAddressCity;
    }

    public void setStoreAddressCity(String storeAddressCity) {
        this.storeAddressCity = storeAddressCity;
    }

    public String getStoreAddressCityID() {
        return storeAddressCityID;
    }

    public void setStoreAddressCityID(String storeAddressCityID) {
        this.storeAddressCityID = storeAddressCityID;
    }

    public String getStoreAddressDistrict() {
        return storeAddressDistrict;
    }

    public void setStoreAddressDistrict(String storeAddressDistrict) {
        this.storeAddressDistrict = storeAddressDistrict;
    }

    public String getStoreAddressDistrictID() {
        return storeAddressDistrictID;
    }

    public void setStoreAddressDistrictID(String storeAddressDistrictID) {
        this.storeAddressDistrictID = storeAddressDistrictID;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
