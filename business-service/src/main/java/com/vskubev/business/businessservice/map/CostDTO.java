package com.vskubev.business.businessservice.map;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author skubev
 */
public class CostDTO {

    private final long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final BigDecimal price;
    private final long categoryId;
    private final long ownerId;

    public long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public CostDTO(long id,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   BigDecimal price,
                   long categoryId,
                   long ownerId) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
        this.categoryId = categoryId;
        this.ownerId = ownerId;
    }
}
