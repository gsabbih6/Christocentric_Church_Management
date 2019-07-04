package Models;

import com.dighisoft.christocentric.AppDatabase;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.List;

@Table(name = "MEMBER" ,database = AppDatabase.class)
public class MemberDatabase extends Model {

    @PrimaryKey
    private Long id;
    @Column(name = "firstname")

    private String firstname;
    @Column(name = "lastname")

    private String lastname;
    @Column(name = "othername")

    private String othername;
    @Column(name = "address")

    private String address;
    @Column(name = "email")

    private String email;
    @Column(name = "special_notes")

    private String specialNotes;
    @Column(name = "status")

    private Boolean status;
    @Column(name = "marital_status")

    private String maritalStatus;
    @Column(name = "occupation")

    private String occupation;
    @Column(name = "school")

    private String school;
    @Column(name = "dob")

    private String dob;
    @Column(name = "dom")

    private String dom;
    @Column(name = "created_at")

    private String createdAt;
    @Column(name = "updated_at")

    private String updatedAt;
    @Column(name = "branch")

    private Long branchId;
    //    @Column(name="payment")
// 
//    private Payment payment;
    @Column(name = "telephone")

    private String telephone;
    @Column(name = "photo")

    private String photoUrl;

    public static List<MemberDatabase> getByBranchId(Long branchId){

        return Select.from(MemberDatabase.class).where("branch = ?",branchId).fetch();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public void setSpecialNotes(String specialNotes) {
        this.specialNotes = specialNotes;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
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

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
