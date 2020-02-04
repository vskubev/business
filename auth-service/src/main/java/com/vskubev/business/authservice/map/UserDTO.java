package com.vskubev.business.authservice.map;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author skubev
 */
public class UserDTO {
    private final long id;
    private final String login;
    private final String password;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserDTO(long id,
                   String login,
                   String password,
                   String name,
                   String email,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id &&
                Objects.equals(login, userDTO.login) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(email, userDTO.email) &&
                Objects.equals(createdAt, userDTO.createdAt) &&
                Objects.equals(updatedAt, userDTO.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, name, email, createdAt, updatedAt);
    }
}
