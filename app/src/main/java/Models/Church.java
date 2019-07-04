package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Church {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vision")
    @Expose
    private String vision;
    @SerializedName("mission")
    @Expose
    private String mission;
    @SerializedName("telephone")
    @Expose
    private String telephone;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("address")
    @Expose
    private String address;
//    @SerializedName("user")
//    @Expose
//    private List<UserDTO> user = null;
//    @SerializedName("country")
//    @Expose
//    private Country country;
//    @SerializedName("logo")
    @Expose
    private Logo logo;
//    @SerializedName("branches")
//    @Expose
//    private List<Branch> branches = null;
//    @SerializedName("investments")
    @Expose
    private List<Investment> investments = null;

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

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public List<UserDTO> getUser() {
//        return user;
//    }
//
//    public void setUser(List<UserDTO> user) {
//        this.user = user;
//    }

//    public Country getCountry() {
//        return country;
//    }
//
//    public void setCountry(Country country) {
//        this.country = country;
//    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

//    public List<Branch> getBranches() {
//        return branches;
//    }
//
//    public void setBranches(List<Branch> branches) {
//        this.branches = branches;
//    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public void setInvestments(List<Investment> investments) {
        this.investments = investments;
    }
}