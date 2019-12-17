package com.vskubev.business.businessservice.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "costs")
public class Cost {

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * date created category
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * date updated category
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * price of transaction
     */
    @Column(name = "price", nullable = false)
    private long price;

    /**
     * category id
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    /**
     * owner
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
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

    public Cost(Category category, LocalDateTime createdAt, LocalDateTime updatedAt, long price, User owner) {
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "id=" + id +
                ", category=" + category +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", price=" + price +
                ", owner=" + owner +
                '}';
    }
}