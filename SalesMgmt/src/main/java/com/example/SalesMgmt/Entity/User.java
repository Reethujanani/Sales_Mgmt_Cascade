package com.example.SalesMgmt.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "User")
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname" )
    private String lastname;

    @Column(name = "mobile_no")
    private String mobile_no;

    @Column(name = "is_active")
    private int is_active;

    @Column(name  = "is_deleted")
    private int is_deleted;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp created_at;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Timestamp modified_at;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<UserRole> roleList;

    public User(User user) {
        this.username = user.username;
        this.password = user.password;
    }

    public User() {

    }
}

