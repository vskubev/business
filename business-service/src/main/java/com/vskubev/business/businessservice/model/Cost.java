package com.vskubev.business.businessservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author skubev
 */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", nullable = false, unique = false)
    private LocalDateTime createdAt;

    /**
     * date updated category
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at", nullable = false, unique = false)
    private LocalDateTime updatedAt;

    /**
     * price of transaction
     */
    @Column(name = "price", nullable = false, unique = false, precision = 19, scale = 4)
    private BigDecimal price;

    /**
     * category id
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "category_id", nullable = false, unique = false)
    private Category category;

    /**
     * owner
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "owner_id", nullable = false, unique = false)
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Cost(LocalDateTime createdAt, LocalDateTime updatedAt, BigDecimal price, Category category, User owner) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
        this.category = category;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Cost{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", price=" + price +
                ", category=" + category +
                ", owner=" + owner +
                '}';
    }
}