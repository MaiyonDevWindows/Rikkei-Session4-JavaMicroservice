package com.maiyon.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.maiyon.model.entity.enums.ActiveStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(nullable = false, unique = true)
    private String username;
    private String email;
    @Column(nullable = false)
    private String fullName;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_status", columnDefinition = "BIT(1)")
    private ActiveStatus userStatus;
    @Column(nullable = false)
    private String password;
    private String avatar;
    @Column(unique = true)
    private String phone;
    @Column(nullable = true)
    private String address;
    @CreationTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date createAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date updateAt;
    // User - Role => UserRole: N - N.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    @PrePersist
    public void prePersist(){
        this.userStatus = (this.userStatus == null || this.userStatus == ActiveStatus.ACTIVE) ?
                ActiveStatus.ACTIVE : ActiveStatus.INACTIVE;
    }
}