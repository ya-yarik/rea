package com.example.project.dao;

import com.example.project.models.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdminDao {
    private int id;
    private List<Users> usersBase=new ArrayList<>();

    public void addUser (Users userAdd){
        usersBase.add(userAdd);
        userAdd.setId(++id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Users> getUsersBase() {
        return usersBase;
    }

    public void setUsersBase(List<Users> usersBase) {
        this.usersBase = usersBase;
    }

    public Users getUserId(int id){
        return usersBase.stream().filter(user -> user.getId() == id).findAny().orElse(null);
    }

    public void userDataUpdate(int id, Users user){
        Users userUpdating=getUserId(id);
        userUpdating.setSurname(user.getSurname());
        userUpdating.setName(user.getName());
        userUpdating.setPatronymic(user.getPatronymic());
        userUpdating.setAge(user.getAge());
        userUpdating.setEmail(user.getEmail());
        userUpdating.setPhone(user.getPhone());
    }

    public void userDataDelete(int id){
        usersBase.removeIf(user -> user.getId()==id);
    }
}