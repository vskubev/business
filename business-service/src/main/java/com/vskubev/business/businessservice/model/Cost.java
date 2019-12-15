package com.vskubev.business.businessservice.model;

import com.vskubev.business.businessservice.model.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "costs")
@NoArgsConstructor
@AllArgsConstructor
public class Cost {

    /**
     * id
     */
    @Id
    @Column(name = "cost_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long costId;

    /**
     * category id
     */
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="category", nullable = false)
    private Category category;

    /**
     * created by id
     */
    @Column(name = "created_by_id", nullable = false)
    private long createdById;

    /**
     * date created category
     */
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    /**
     * updated by id
     */
    @Column(name = "updated_by_id", nullable = false)
    private long updatedById;

    /**
     * date updated category
     */
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    /**
     * price of transaction
     */
    @Column(name = "price", length = 10, nullable = false)
    private long price;

    /**
     * owner
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    public long getCostId() {
        return costId;
    }

    public void setCostId(long costId) {
        this.costId = costId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(long updatedById) {
        this.updatedById = updatedById;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Cost() {
    }

    public Cost(Category category, long createdById, Date createdAt, long updatedById, Date updatedAt, long price, User owner) {
        this.category = category;
        this.createdById = createdById;
        this.createdAt = createdAt;
        this.updatedById = updatedById;
        this.updatedAt = updatedAt;
        this.price = price;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "id=" + costId +
                ", category=" + category +
                ", createdById=" + createdById +
                ", createdAt=" + createdAt +
                ", updatedById=" + updatedById +
                ", updatedAt=" + updatedAt +
                ", price=" + price +
                ", owner=" + owner +
                '}';
    }
}