package com.vskubev.business.businessservice.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    /**
     * id
     */
    @Id
    @Column(name = "category_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long categoryId;

    /**
     * category name
     */
    @Column(name = "name", length = 255, nullable = false, unique = true)
    private String name;

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
     * owner
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category() {
    }

    public Category(String name, long createdById, Date createdAt, long updatedById, Date updatedAt, User owner) {
        this.name = name;
        this.createdById = createdById;
        this.createdAt = createdAt;
        this.updatedById = updatedById;
        this.updatedAt = updatedAt;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + categoryId +
                ", name='" + name + '\'' +
                ", createdById=" + createdById +
                ", createdAt=" + createdAt +
                ", updatedById=" + updatedById +
                ", updatedAt=" + updatedAt +
                ", owner=" + owner +
                '}';
    }
}