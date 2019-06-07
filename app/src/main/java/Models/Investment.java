package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Adapter.IAppnterface.CModel;

public class Investment implements CModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
//    @SerializedName("created_at")
//    @Expose
//    private Integer createdAt;
//    @SerializedName("updated_at")
//    @Expose
//    private Integer updatedAt;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Integer getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Integer createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Integer getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Integer updatedAt) {
//        this.updatedAt = updatedAt;
//    }

    @Override
    public void destroy() {

    }
}