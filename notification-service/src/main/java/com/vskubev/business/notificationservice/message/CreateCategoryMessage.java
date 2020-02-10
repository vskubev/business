package com.vskubev.business.notificationservice.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author skubev
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryMessage {
    private String name;
    private long ownerId;
}
