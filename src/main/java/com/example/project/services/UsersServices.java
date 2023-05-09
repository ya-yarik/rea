package com.example.project.services;


import com.example.project.models.UserModel;
import com.example.project.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServices {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    //хеширование пароля - включение энкодера
    @Autowired
    public UsersServices(UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        this.usersRepository=usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserModel findByLogin(UserModel users){
        Optional<UserModel> users_db = usersRepository.findByLogin(users.getLogin());
        return users_db.orElse(null);
    }

    @Transactional
    public void register(UserModel users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setRole("ROLE_USER");
        usersRepository.save(users);
    }

    public List<UserModel> getAllUser(){
        return usersRepository.findAll();
    }
    public UserModel getUserId(int id){
        Optional<UserModel> thatUser = usersRepository.findById(id);
        return thatUser.orElse(null);
    }

    @Transactional
    public void addUserC(UserModel user){
        usersRepository.save(user);
    }

    @Transactional
    public void editUser(int id, UserModel user){
        user.setId(id);
        usersRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id){
        usersRepository.deleteById(id);
    }

    public List<UserModel> getUserEmail(String email){
        return usersRepository.findByEmail(email);
    }

    public List<UserModel> getUserPhone(String phone){
        return usersRepository.findByPhone(phone);
    }

    public List<UserModel> getUserSurnameOrderByAge(String surname){
        return usersRepository.findBySurnameOrderByAge(surname);
    }
    public List<UserModel> getUserSurnameStartingWith(String startingWith){
        return usersRepository.findBySurnameStartingWith(startingWith);
    }
    public List<UserModel> findBySurnameOrderByAgeDesc (String surname){
        return usersRepository.findBySurnameOrderByAgeDesc(surname);
    }

    @Transactional
    public void changeRole(int id, UserModel user){
        user.setId(id);
        usersRepository.save(user);
    }
}
