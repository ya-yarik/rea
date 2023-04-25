package com.example.project.services;


import com.example.project.models.Users;
import com.example.project.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class UsersServices {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    //хеширование пароля - включение энкодера

    @Autowired
    public UsersServices(UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        this.usersRepository=usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users findByLogin(Users users){
        Optional<Users> users_db = usersRepository.findByLogin(users.getLogin());
        return users_db.orElse(null);
    }

    @Transactional
    public void register(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole("ROLE_USER");
        usersRepository.save(users);
    }

    ////
    public List<Users> getAllUser(){
        return usersRepository.findAll();
    }
    public Users getUserId(int id){
        Optional<Users> thatUser = usersRepository.findById(id);
        return thatUser.orElse(null);
    }

    @Transactional
    public void addUserC(Users user){
        usersRepository.save(user);
    }

    @Transactional
    public void editUser(int id, Users user){
        user.setId(id);
        usersRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id){
        usersRepository.deleteById(id);
    }
    ////////////
    public List<Users> getUserEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public List<Users> getUserPhone(String phone){
        return usersRepository.findByPhone(phone);
    }

    public List<Users> getUserSurnameOrderByAge(String surname){
        return usersRepository.findBySurnameOrderByAge(surname);
    }
    public List<Users> getUserSurnameStartingWith(String startingWith){
        return usersRepository.findBySurnameStartingWith(startingWith);
    }
    public List<Users> findBySurnameOrderByAgeDesc (String surname){
        return usersRepository.findBySurnameOrderByAgeDesc(surname);
    }
}
