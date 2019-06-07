package Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Adapter.IAppnterface.CModel;

public class UserDTO  implements CModel{

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("confirmed")
    @Expose
    private Boolean confirmed;
    @SerializedName("blocked")
    @Expose
    private Boolean blocked;
//    @SerializedName("role")
//    @Expose
//    private Role role;
    @SerializedName("church")
    @Expose
    private Integer church;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }

    public Integer getChurch() {
        return church;
    }

    public void setChurch(Integer church) {
        this.church = church;
    }

    @Override
    public void destroy() {

    }
}