package com.leon.springbootleon.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        schema = "leon_web",
        name = "post_category",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_post_category_path", columnNames = {"category_path"})
        },
        indexes = {
                @Index(name = "idx_post_category_enabled", columnList = "enabled"),
                @Index(name = "idx_post_category_sort_order", columnList = "sort_order")
        }
)
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_title", length = 100, nullable = false)
    private String title;

    @Column(name = "category_icon", length = 50)
    private String icon;

    @Column(name = "category_path", length = 100, nullable = false)
    private String path;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder = 0;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}
