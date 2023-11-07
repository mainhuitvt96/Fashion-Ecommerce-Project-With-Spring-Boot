package com.ecommerce.library.repository;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category  c WHERE c.isActivated = true  and c.isDeleted = false")
    List<Category> findAllByActivated();

    /* Customer */
    @Query("select new com.ecommerce.library.dto.CategoryDto(c.id, c.name, c.image, count(p.category.id)) " +
            "from Category  c inner join Product  p on p.category.id = c.id " +
            "where c.isActivated = true  and  c.isDeleted = false group by  c.id")
    List<CategoryDto> getCategoryAndProduct();

}
