package com.example.project.services;

import com.example.project.models.UserModel;

import com.example.project.repositories.UsersRepository;
import com.example.project.security.UsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //находит пользователя по юзернэйму
    @Override
    public UserDetails loadUserByUsername(String username)  throws UsernameNotFoundException {
        // Получаем пользователя из таблицы по логину с формы аутентификации
        Optional<UserModel> user = usersRepository.findByLogin(username);
        // Если пользователь не был найден
        if(user.isEmpty()){
            // Выбрасываем исключение что данный пользователь не найден
            // Данное исключение будет поймано Spring Security и сообщение будет выведено на страницу
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new UsersDetails(user.get());
    }
}
