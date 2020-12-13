package com.developer.milla.Ice;

public class Users {
    private String id, name,email, password, age;

    public Users() {
    }

    public Users(String id, String name, String email, String password, String age) {
        this.id = id;//id
        this.name = name;//nombre helado
        this.email = email;// Imagen del helado
        this.password = password;// Precio
        this.age = age;// sabor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
