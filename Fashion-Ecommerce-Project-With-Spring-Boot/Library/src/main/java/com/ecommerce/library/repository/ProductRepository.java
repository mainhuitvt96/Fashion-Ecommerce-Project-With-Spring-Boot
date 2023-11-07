package com.ecommerce.library.repository;

import com.ecommerce.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product  p")
    Page<Product> pageProduct(Pageable pageable);

    @Query("SELECT p FROM Product  p WHERE p.description LIKE %?1% OR p.name LIKE %?1%")
    Page<Product> searchProducts(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product  p WHERE p.description LIKE %?1% OR p.name LIKE %?1%")
    List<Product> searchProductsList(String keyword);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false and p.isActivated = true")
    List<Product> getAllProducts();
    /*For customer*/
    @Query(value = "SELECT * FROM products  p WHERE p.is_deleted = false  and p.is_activated = true order by rand() asc", nativeQuery = true)
    List<Product> listViewProducts();



    @Query(value = "SELECT * FROM products p inner join categories c ON c.category_id = p.category_id WHERE p.category_id =   ?1", nativeQuery = true)
    List<Product> getRelatedProducts(Long categoryId);

    @Query(value = "select p from  Product  p inner join  Category c on  c.id = p.category.id where c.id = ?1 and p.isDeleted = false and  p.isActivated = true ")
    List<Product> getProductsInCategory(Long categoryId);

    @Query("SELECT  p FROM Product  p WHERE p.isDeleted = false  and  p.isActivated=true " +
            "order by p.costPrice desc")
    List<Product> filterHighPrice();

    @Query("SELECT  p FROM Product  p WHERE p.isDeleted = false  and  p.isActivated=true order by p.costPrice")
    List<Product> filterLowPrice();
    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> searchProducts(String keyword);


}