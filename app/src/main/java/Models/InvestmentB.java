package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import Adapter.IAppnterface.CModel;

public class InvestmentB implements CModel {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvestmentB that = (InvestmentB) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription());
    }
}