package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentB {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("investment")
    @Expose
    private Investment investment;
    @SerializedName("branch")
    @Expose
    private BranchB branch;
//    @SerializedName("member")
//    @Expose
//    private Member member;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Investment getInvestment() {
        return investment;
    }

    public void setInvestment(Investment investment) {
        this.investment = investment;
    }

    public BranchB getBranch() {
        return branch;
    }

    public void setBranch(BranchB branch) {
        this.branch = branch;
    }

//    public Member getMember() {
//        return member;
//    }
//
//    public void setMember(Member member) {
//        this.member = member;
//    }

}
