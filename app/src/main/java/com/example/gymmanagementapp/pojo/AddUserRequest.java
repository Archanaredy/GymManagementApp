package com.example.gymmanagementapp.pojo;

public class AddUserRequest {
    private String name;
    private String number;
    private String age;
    private String height;
    private String weight;
    private String image;

    public AddUserRequest(String name, String number, String age, String height, String weight, String image) {
        this.name = name;
        this.number = number;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
