package Models;

import android.util.Log;

import com.dighisoft.christocentric.AppDatabase;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

@Table(name = "PAYMENT", database = AppDatabase.class)

public class PaymentDatabase extends Model {
    @PrimaryKey
    private Long id;
    @Column(name = "investmentid")
    private Long investmentid;
    @Column(name = "branchId")
    private Long branchId;
    @Column(name = "memberId")
    private Long memberId;
    @Column(name = "amount")
    public Double amount;
    @Column(name = "created_at")

    private String createdAt;
    @Column(name = "updated_at")

    private String updatedAt;
    @Column(name = "branch")
    private Long branch;
    @Column(name = "member")

    private Long member;

    public static List<PaymentDatabase> getPaymentsByBranchId(Integer branchId) {
        return Select.from(PaymentDatabase.class).where("branch = ?", branchId).fetch();
    }

    public static List<PaymentDatabase> getPaymentsByUser() {

        List<PaymentDatabase> ls = new ArrayList<>();
//
        for (BranchDatabase b : BranchDatabase.getUserBranches(UserDBModel.getUser().get(0).userid)) {

            ls.addAll(
                    Select.from(

                            PaymentDatabase.class)

                            .where(

                                    "branch = ?", b.getId())


                            .fetch());
            Log.d("TAG", String.valueOf(ls.size()));
            Log.d("ID", String.valueOf(b.getId()));


        }
        Log.e("TAGGY", String.valueOf(ls.size()));
        return ls;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvestmentid() {
        return investmentid;
    }

    public void setInvestmentid(Long investmentid) {
        this.investmentid = investmentid;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public Long getBranch() {
        return branch;
    }

    public void setBranch(Long branch) {
        this.branch = branch;
    }

    public Long getMember() {
        return member;
    }

    public void setMember(Long member) {
        this.member = member;
    }
}
