package com.sudo.repository;

import com.sudo.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>
{
    Optional<CategoryEntity> findByCategoryId(String categoryId);
}
