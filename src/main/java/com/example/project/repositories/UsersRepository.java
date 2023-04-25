package com.example.project.repositories;

import com.example.project.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    //Поиск пользователей по параметрам
    Optional<Users> findByLogin(String login);
    List<Users> findByEmail (String email);
    List<Users> findByPhone (String phone);
    List<Users> findBySurnameOrderByAge(String surname);
    List<Users> findBySurnameStartingWith(String startingWith);

    @Query(value = "select * from shop_users where shop_users.surname=?1 order by age desc", nativeQuery = true)
    List<Users> findBySurnameOrderByAgeDesc (String surname);

}
