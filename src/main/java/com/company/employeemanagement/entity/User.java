package com.company.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    @OneToMany(mappedBy = "performedBy", cascade = CascadeType.ALL)
    private Set<ActivityLog> activityLogs;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private String createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private String updatedAt;
    
}
