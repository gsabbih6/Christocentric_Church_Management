package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BranchB {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
//    @SerializedName("church")
//    @Expose
//    private Integer church;
//    @SerializedName("payment")
//    @Expose
//    private Integer payment;
    @SerializedName("user")
    @Expose
    private Integer user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

//    public Integer getChurch() {
//        return church;
//    }
//
//    public void setChurch(Integer church) {
//        this.church = church;
//    }
//
//    public Integer getPayment() {
//        return payment;
//    }
//
//    public void setPayment(Integer payment) {
//        this.payment = payment;
//    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

}