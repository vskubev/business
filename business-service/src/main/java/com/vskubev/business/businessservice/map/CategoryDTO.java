package com.vskubev.business.businessservice.map;

import java.time.LocalDateTime;

/**
 * @author skubev
 */
public class CategoryDTO {

    private final long id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final long ownerId;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public CategoryDTO(long id, String name, LocalDateTime createdAt, LocalDateTime updatedAt, long ownerId) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownerId = ownerId;
    }
}
