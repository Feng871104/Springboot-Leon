package com.leon.springbootleon.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Entity
@Builder
@Table(
        name = "post",
        schema = "leon_web",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_post_path", columnNames = {"path"})
        },
        indexes = {
                @Index(name = "idx_post_category_sort", columnList = "category_id, sort_order"),
                @Index(name = "idx_post_enabled", columnList = "enabled")
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "post_title", length = 200, nullable = false)
    private String title;

    @Column(name = "path", length = 200, nullable = false)
    private String path;

    @Column(name = "post_content")
    private String content;

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
