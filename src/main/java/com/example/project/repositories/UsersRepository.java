package com.example.project.repositories;

import com.example.project.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UserModel, Integer> {
    //Поиск пользователей по параметрам
    Optional<UserModel> findByLogin(String login);
    List<UserModel> findByEmail (String email);
    List<UserModel> findByPhone (String phone);
    List<UserModel> findBySurnameOrderByAge(String surname);
    List<UserModel> findBySurnameStartingWith(String startingWith);

    @Query(value = "select * from shop_users where shop_users.surname=?1 order by age desc", nativeQuery = true)
    List<UserModel> findBySurnameOrderByAgeDesc (String surname);

}
