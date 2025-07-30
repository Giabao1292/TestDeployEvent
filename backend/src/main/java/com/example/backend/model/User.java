package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicInsert
@Table(name = "tbl_user", uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = {"email"})
})
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 100)
    @Column(name = "fullname", length = 100)
    private String fullName;

    @Size(max = 255)
    @Column(name = "profile_url")
    private String profileUrl;

    @Size(max = 255)
    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 100)
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @ColumnDefault("0")
    @Column(name = "score")
    private Integer score;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "createAt")
    private Instant createAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updateAt")
    private Instant updateAt;

    @Size(max = 50)
    @Column(name = "createdby", length = 50)
    private String createdby;

    @Size(max = 50)
    @Column(name = "modifiedby", length = 50)
    private String modifiedby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Booking> tblBookings = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Organizer organizer;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Review> tblReviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> tblUserRoles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserVoucher> tblUserVouchers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Notification> tblNotifications = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "tbl_user_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> tblEvents = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (tblUserRoles == null) {
            return new HashSet<>();
        }
        return tblUserRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getRoleName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Wishlist> wishlist = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TrackingEventUpcoming> trackingEvents = new LinkedHashSet<>();

    public User(Integer id) {
        this.id = id;
    }

}