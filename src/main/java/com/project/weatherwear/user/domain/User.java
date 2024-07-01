package com.project.weatherwear.user.domain;

import com.project.weatherwear.common.BaseTimeEntity;
import com.project.weatherwear.user.exception.UserException;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private boolean isSocial;

    @Column(nullable = false)
    private String role;

    @Transient
    public static final String EMAIL_REG = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n";
    @Transient
    public static final String PASSWORD_REG = "^(?=.*[A-Za-z])(?=.*\\d|.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,10}$|^(?=.*[!@#$%^&*])(?=.*\\d)[A-Za-z\\d!@#$%^&*]{8,10}$\n";
    @Transient
    public static final String NICKNAME_REG = "^[A-Za-z0-9]{1,7}$\n";


    public User(String email, String password, String name, String nickname, boolean isSocial, String role){
        validation(email, password, name, nickname);
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.isSocial = isSocial;
        this.role = role;
    }
    public void validation(String email, String password, String name, String nickname){
        isEmailNull(email);
        checkEmailRegex(email);

        isPasswordNull(password);
        checkPasswordRegex(password);

        isNameNull(name);

        isNickNameNull(nickname);
        checkNickNameRegex(nickname);
    }

    public void isEmailNull(String email){
        if(email == null){
            throw new IllegalArgumentException(UserException.EMAIL_IS_NULL_EXCEPTION.getMessage());
        }
    }

    public void checkEmailRegex(String email){
        if(!email.matches(EMAIL_REG)){
            throw new IllegalArgumentException(UserException.EMAIL_INVALID_EXCEPTION.getMessage());
        }
    }

    public void isPasswordNull(String password){
        if(password == null){
            throw new IllegalArgumentException(UserException.PASSWORD_IS_NULL_EXCEPTION.getMessage());
        }
    }

    public void checkPasswordRegex(String password){
        if(!password.matches(PASSWORD_REG)){
            throw new IllegalArgumentException(UserException.PASSWORD_INVALID_EXCEPTION.getMessage());
        }
    }

    public void isNameNull(String name){
        if(name == null){
            throw new IllegalArgumentException(UserException.NAME_IS_NULL_EXCEPTION.getMessage());
        }
    }

    public void isNickNameNull(String nickname){
        if(nickname == null){
            throw new IllegalArgumentException(UserException.NICKNAME_IS_NULL_EXCEPTION.getMessage());
        }
    }

    public void checkNickNameRegex(String nickname){
        if(!nickname.matches(NICKNAME_REG)){
            throw new IllegalArgumentException(UserException.NICKNAME_INVALID_EXCEPTION.getMessage());
        }
    }

    public void isRegularLogin(){
        this.isSocial = false;
    }

    public void isSocialLogin(){
        this.isSocial = true;
    }
}