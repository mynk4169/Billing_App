package com.sudo.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name="tbl_category")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String categoryId;
    @Column(unique=true)
    private String name;
    private String description;
    private String bgColor;
    private String imgUrl;
    @CreationTimestamp
    private Timestamp  createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

}
