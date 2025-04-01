package com.example.UserMS;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String fullName;
    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    @Email(message="Invalid email format")
    private String email;

    @Column(nullable=false)
//    @Size(min=4,max=15,message="Password must me 4 to 15 characters long!!!" )
    private String password;

    private String role;
    private String dp;

    private String about;

    private LocalDateTime createdAt;



    @PrePersist
    protected void onCreate()
    {
        this.createdAt = LocalDateTime.now();
        this.dp = null;
        this.about = null;
    }


}
