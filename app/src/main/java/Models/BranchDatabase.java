package Models;

import android.util.Log;

import com.dighisoft.christocentric.AppDatabase;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.List;

@Table(name = "BRANCH", database = AppDatabase.class)
public class BranchDatabase extends Model {
    //
    @PrimaryKey
    private Long id;
    @Column(name = "name")

    private String name;
    @Column(name = "telephone")

    private String telephone;
    @Column(name = "address")

    private String address;
    @Column(name = "created_at")

    private String createdAt;
    @Column(name = "updated_at")

    private String updatedAt;
    @Column(name = "logoUrl")

    private String logoUrl;

    @Column(name = "churchId")

    private String churchId;

    @Column(name = "UserDBModel")

    private UserDBModel user;

    public static List<BranchDatabase> getUserBranches(Long userId) {


//        for (BranchDatabase b : Select.from(BranchDatabase.class).fetch()) {
//
//            if (b.getUsers().userid.equals(1)) {
//                Log.e("Branches Load" + b.getId(), b.getName());
//            }
//
//        }
        Log.e("Branches Size", String.valueOf(Select.from(BranchDatabase.class).fetch().size()));
        return Select.from(BranchDatabase.class).fetch();//Select.from(BranchDatabase.class).where("UserDBModel = ?", userId).fetch();

    }

    public static List<BranchDatabase> getAll() {
        return Select.from(BranchDatabase.class).fetch();
    }

    public UserDBModel getUsers() {
        return user;
    }

    public void setUsers(UserDBModel users) {
        this.user = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getChurchId() {
        return churchId;
    }

    public void setChurchId(String churchId) {
        this.churchId = churchId;
    }
}