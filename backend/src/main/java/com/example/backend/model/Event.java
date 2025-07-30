package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Getter
@Setter
@Entity
@Table(name = "tbl_event", indexes = {
        @Index(name = "organizer_id", columnList = "organizer_id"),
        @Index(name = "category_id", columnList = "category_id")
})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonBackReference
    private Organizer organizer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    private Category category;


    @Size(max = 200)
    @NotNull
    @Column(name = "event_title", nullable = false, length = 200)
    private String eventTitle;

    @Size(max = 20)
    @Column(name = "age_rating", length = 20)
    private String ageRating;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "banner_text")
    private String bannerText;

    @Size(max = 255)
    @Column(name = "header_image")
    private String headerImage;

    @Size(max = 255)
    @Column(name = "poster_image")
    private String posterImage;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @Size(max = 50)
    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Size(max = 50)
    @Column(name = "modified_by", length = 50)
    private String modifiedBy;

    @Lob
    @Column(name = "rejection_reason")
    private String rejectionReason;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<EventVoucher> tblEventVouchers = new LinkedHashSet<>();

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
//    private Set<Review> tblReviews = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "event")
    @JsonManagedReference
    private Set<ShowingTime> tblShowingTimes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Wishlist> wishlistedUsers = new LinkedHashSet<>();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    @JsonBackReference
    private EventStatus status;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TrackingEventUpcoming> trackingUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<WithdrawRequest> withdrawRequests;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (updatedAt == null) {
            updatedAt = Instant.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}