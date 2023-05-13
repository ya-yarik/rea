package com.example.project.repositories;
import com.example.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends JpaRepository<Product, Integer> {


    // Поиск всех продуктов по части наименования продукта в не зависимости от регистра
    List<Product> findByNameContainingIgnoreCase(String name);

    // Поиск по наименованию и фильтрация по диапазону цены
    @Query(value = "select * from shop_products where ((lower(name) LIKE %?1%) or (lower(name) LIKE '?1%') OR (lower(name) LIKE '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByNameAndPriceGreaterThanEqualAndPriceLessThanEqual(String name, float up, float to);

    // Поиск по наименованию и фильтрация по диапазону цены, а также сортировка по возрастанию цены
    @Query(value = "select * from shop_products where (lower(name) LIKE %?1%) or (lower(name) LIKE '?1%') OR (lower(name) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price",nativeQuery = true)
    List<Product> findByNameOrderByPriceAsc(String name, float up, float to);

    // Поиск по наименованию и фильтрация по диапазону цены, а также сортировка по убыванию цены
    @Query(value = "select * from shop_products where (lower(name) LIKE %?1%) or (lower(name) LIKE '?1%') OR (lower(name) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price desc",nativeQuery = true)
    List<Product> findByNameOrderByPriceDesc(String name, float up, float to);

    // Поиск по наименованию и фильтрация по диапазону цены, сортировка по возрастанию цены,  а также фильтрация по категории
    @Query(value = "select * from shop_products where category_id = ?4 and(lower(name) LIKE %?1%) or (lower(name) LIKE '?1%') OR (lower(name) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price",nativeQuery = true)
    List<Product> findByNameAndCategoryOrderByPriceAsc(String name, float up, float to, Integer category);

    // Поиск по наименованию и фильтрация по диапазону цены, сортировка по убыванию цены,  а также фильтрация по категории
    @Query(value = "select * from shop_products where category_id = ?4 and(lower(name) LIKE %?1%) or (lower(name) LIKE '?1%') OR (lower(name) LIKE '%?1') and (price >= ?2 and price <= ?3) order by price desc",nativeQuery = true)

    List<Product> findByNameAndCategoryOrderByPriceDesc(String name, float up, float to, Integer category);
    @Query(value = "select * from shop_products where category_id = ?1 and name= ?2",nativeQuery = true)
    List<Product> findByNameAndCategory (String name, Integer category);
    List<Product> findByNameOrderByPriceAsc (String name);
    List<Product> findByNameOrderByPriceDesc (String name);

    @Query(value = "select * from shop_products where category_id = ?2 and(lower(name) LIKE %?1%) or (lower(name) LIKE '?1%') OR (lower(name) LIKE '%?1') order by price asc",nativeQuery = true)
    List<Product> findByNameAndCategoryAndPriceOrderByPriceAsc (String name, Integer categoriest);

    // Поиск по наименованию и фильтрация по диапазону цены, сортировка по убыванию цены,  а также фильтрация по категории
    @Query(value = "select * from shop_products where category_id = ?2 and(lower(name) LIKE %?1%) or (lower(name) LIKE '?1%') OR (lower(name) LIKE '%?1') order by price desc",nativeQuery = true)

    List<Product> findByNameAndCategoryAndPriceOrderByPriceDesc(String name, Integer categoriest);

}