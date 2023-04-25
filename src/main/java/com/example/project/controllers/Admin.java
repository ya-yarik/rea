package com.example.project.controllers;

import com.example.project.models.Users;
import com.example.project.services.UsersServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class Admin {

    @Value("${upload.path}")
    private String uploadPath;
    private final UsersServices userOperations;

    @Autowired
    public Admin(UsersServices userOperations) {
        this.userOperations = userOperations;
    }

    @GetMapping("")
    public String admin() {
        return "admin";
    }


    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("userForm", userOperations.getAllUser());
        return "users";
    }

    @GetMapping("users/newuser")
    public String addUserC(Model model) {
        model.addAttribute("userForm", new Users());
        return "newuser";
    }

    @PostMapping("users/newuser")
    public String addUserC(@ModelAttribute("userForm") @Valid Users user, BindingResult scan, Model model, @RequestParam("file")MultipartFile multipartFile) {
        if (scan.hasErrors()) {
            return "newuser";
        }

        for (Users users:
        userOperations.getAllUser()){
            if(users.getEmail().equals(user.getEmail())){
                model.addAttribute("error", "Пользователь с таким e-mail уже существует");

                ObjectError error = new ObjectError("error", "Пользователь с таким e-mail уже существует");
                scan.addError(error);

                return "newuser";
            }
            else if (users.getPhone().equals(user.getPhone())){
                model.addAttribute("error", "Пользователь с таким номером телефона уже существует");

                ObjectError error = new ObjectError("error", "Пользователь с таким номером телефона уже существует");
                scan.addError(error);

                return "newuser";
            }
        }
        if(multipartFile == null){
            userOperations.addUserC(user);
        }
        else{
            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку
            if(!uploadDir.exists()){
                uploadDir.mkdir();//создание директории, если она не сущ.
            }
            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
            String resultFileName = uuid+" "+ multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(new File(uploadPath + resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
            } catch (IOException e) {
                userOperations.addUserC(user);
            }
            user.setFilePic(resultFileName);//установление имени файла имени пользователя
        }

        userOperations.addUserC(user);
        return "redirect:/admin/users";
    }

    @GetMapping("users/{id}")
    public String aboutUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("userForm", userOperations.getUserId(id));
        return "personaldata";
    }

    @GetMapping("users/edit/{id}")
    public String editUser(Model model, @PathVariable("id") int id) {
        model.addAttribute("userForm", userOperations.getUserId(id));
        return "edituser";
    }

    @PostMapping("users/edit/{id}")
    public String editUser(@ModelAttribute("userForm") @Valid Users user, BindingResult scanEdit, @PathVariable("id") int id, Model model, @RequestParam("file")MultipartFile multipartFile) {
        if (scanEdit.hasErrors()) {
            return "edituser";
        }

        for (Users users:
                userOperations.getAllUser()){
            if(users.getEmail().equals(user.getEmail())){
                model.addAttribute("error", "Пользователь с таким e-mail уже существует");

                ObjectError error = new ObjectError("error", "Пользователь с таким e-mail уже существует");
                scanEdit.addError(error);

                return "newuser";
            }
            else if (users.getPhone().equals(user.getPhone())){
                model.addAttribute("error", "Пользователь с таким номером телефона уже существует");

                ObjectError error = new ObjectError("error", "Пользователь с таким номером телефона уже существует");
                scanEdit.addError(error);

                return "newuser";
            }
        }

        if (multipartFile == null) {userOperations.editUser(id, user);}

        else{
            File uploadDir = new File(uploadPath);//создание директории, куда будет загружаться файл, при условии, что пользователь решил загрузить картинку
            if(!uploadDir.exists()){
                uploadDir.mkdir();//создание директории, если она не сущ.
            }
            String uuid = UUID.randomUUID().toString();//uuid - создаёт уникальное имя файла
            String resultFileName = uuid+" "+ multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(new File(uploadPath + resultFileName));//загрузка по описанному выше пути, наверху обработка исключения throw, если загрузка файла не произойдёт
            } catch (IOException e) {
                userOperations.editUser(id, user);
            }
            user.setFilePic(resultFileName);//установление имени файла имени пользователя
        }

        userOperations.editUser(id, user);
        return "redirect:/admin/users/" + id;
    }

    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userOperations.deleteUser(id);
        return "redirect:/admin/users";
    }

    ////
    @GetMapping("users/sortuser")
    public String sortingAndSearching(){
        return "sortuser";
    }

    @PostMapping("users/sortuser")
    public String sortingAndSearching(@RequestParam("sort_filter") String sorting, @RequestParam("sort") String sortSubmit, Model model){
        if(sorting.equals("email")){
            model.addAttribute("usersT", userOperations.getUserEmail(sortSubmit));
        }
        else if(sorting.equals("phone")){
            model.addAttribute("usersT", userOperations.getUserPhone(sortSubmit));
        }

        else if(sorting.equals("surname")){
            model.addAttribute("usersT", userOperations.getUserSurnameOrderByAge(sortSubmit));
        }
        else if(sorting.equals("surnameDesc")) {
            model.addAttribute("usersT", userOperations.findBySurnameOrderByAgeDesc(sortSubmit));
        }

        else if(sorting.equals("surnameStart")){
            model.addAttribute("usersT", userOperations.getUserSurnameStartingWith(sortSubmit));
        }
        return "sortuser";
    }
}
