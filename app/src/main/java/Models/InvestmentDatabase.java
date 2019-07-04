package Models;

import com.dighisoft.christocentric.AppDatabase;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

@Table(name = "INVESTMENT", database = AppDatabase.class)
public class InvestmentDatabase extends Model {

    @PrimaryKey
    private Long id;
    @Column(name = "name")

    private String name;
    @Column(name = "description")

    private String description;
    @Column(name = "created_at")

    private Integer createdAt;
    @Column(name = "updated_at")

    private Integer updatedAt;

    public static InvestmentDatabase getInvestmentById(Long id) {
        return Select.from(InvestmentDatabase.class).where("id = ?", id).fetchSingle();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }
}
