package com.vskubev.business.businessservice.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * id
     */
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    /**
     * login
     */
    @Column(name = "login", length = 50, nullable = false, unique = true)
    private String login;

    /**
     * encrypted password
     */
    @Column(name = "hash_password", length = 255, nullable = false, unique = false)
    private String hashPassword;

    /**
     * user name
     */
    @Column(name = "name", length = 255, nullable = false, unique = false)
    private String name;

    /**
     * user email
     */
    @Column(name = "email", length = 100, nullable = false, unique = true)
    @Email
    private String email;

    /**
     * date user created
     */
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    /**
     * date user updated
     */
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User() {
    }

    public User(String login, String hashPassword, String name, @Email String email, Date createdAt, Date updatedAt) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", login='" + login + '\'' +
                ", hashPassword='" + hashPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}