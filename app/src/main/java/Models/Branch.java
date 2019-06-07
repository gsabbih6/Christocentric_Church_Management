package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import Adapter.IAppnterface.CModel;

public class Branch implements CModel {

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
//    private Church church;
    @SerializedName("payments")
    @Expose
    private List<PaymentB> payments;
    @SerializedName("user")
    @Expose
    private List<UserDTO> UserDTO;
    @SerializedName("logo")
    @Expose
    private Logo logo;
    @SerializedName("members")
    @Expose
    private List<Member> members = null;

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
//
//    public Church getChurch() {
//        return church;
//    }
//
//    public void setChurch(Church church) {
//        this.church = church;
//    }

    public  List<PaymentB> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentB> payments) {
        this.payments = payments;
    }

    public List<UserDTO> getUserDTO() {
        return UserDTO;
    }

    public void setUserDTO(List<UserDTO> UserDTO) {
        this.UserDTO = UserDTO;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }
//
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }


    @Override
    public void destroy() {

    }
}