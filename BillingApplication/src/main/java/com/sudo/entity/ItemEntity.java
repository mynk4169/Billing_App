package com.sudo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name="Items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemEntity
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String itemId;
    private String name;
    private BigDecimal price;
    private String description;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp UpdateAt;

    private String imgUrl;
    @ManyToOne
    @JoinColumn(name="category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private CategoryEntity category;

}
