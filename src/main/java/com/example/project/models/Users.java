package com.example.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;

import java.util.Objects;


//@Entity
//@Table(name = "shop_users")
@Entity(name = "shop_users")
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message="Логин не может быть пустым")
    @Size(min=2, message="Логин должен быть больше 2 символов")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Поле 'Пароль' не может быть пустым")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

//    @NotEmpty(message = "Поле 'Фамилия' не может быть пустым")
      @Size(min=2, max=30, message="Фамилия должна быть в диапазоне от 2 до 30 символов")
//    @Column (name = "surname", length = 30, nullable=false, columnDefinition = "text")
    @Column (name = "surname", length = 30, nullable=true, columnDefinition = "text")
    private String surname;

//    @NotEmpty(message = "Поле 'Имя' не может быть пустым")
      @Size(min=2, max=30, message="Имя должно быть в диапазоне от 2 до 30 символов")
//    @Column (name = "name", length = 30, nullable=false, columnDefinition = "text")
    @Column (name = "name", length = 30, nullable=true, columnDefinition = "text")
    private String name;


    @Column (name = "patronymic", length = 30, columnDefinition = "text")
    private String patronymic;

    @Min(value=0, message="Возраст не может быть отрицательным. Вы ещё не родились?")
    @Max(value=123, message="Жанна Кальман — французская сверхдолгожительница, старейшая из когда-либо живших на Земле людей, прожила 123 года. Если вам действительно больше, пожалуйста, обратитесь в 'Guinness Book of Records' и к администратору нашего сайта, прикрепив скан удостоверения личности")
//  @Column (name = "age", length = 3, nullable=false, columnDefinition="int")
    @Column (name = "age", length = 3, nullable=true, columnDefinition="int")
    private int age;

    @NotEmpty(message="Поле 'E-mail' является обязательным")
    @Email(message="Вы ввели недопустимые символы")
    @Column (name = "email", length = 30, nullable=false, unique = true, columnDefinition = "text")
    private String email;

    @NotEmpty(message="Поле 'Номер телефона' не может быть пустым")
    @Pattern(regexp = "^((\\+7|7|8)+([0-9]){10})$", message = "Номер телефона должен быть в формате +7/7/8-XXX-XX-XX")
    @Column (name = "phone", length = 12, nullable=false, unique = true, columnDefinition = "text")
    private String phone;

    private String filePic;

    public Users(int id, String surname, String name, String patronymic, int age, String email, String phone, String filePic) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.filePic = filePic;
    }

    public Users() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFilePic() {
        return filePic;
    }

    public void setFilePic(String filePic) {
        this.filePic = filePic;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return id == users.id && Objects.equals(login, users.login) && Objects.equals(password, users.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}


