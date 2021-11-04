package ru.academits.filimonov.phonebookspring.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Contact {
    private static Gson gson = new GsonBuilder().create();

    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean important;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public String toString(){
        return gson.toJson(this);
    }
}
