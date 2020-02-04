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
public class SuccessfulRegistrationMessage {
    private String email;
    private String name;
}
