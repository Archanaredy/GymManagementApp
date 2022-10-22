package com.example.gymmanagementapp.pojo;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("number")
    private String number;

    @SerializedName("image")
    private String image;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("__v")
    private int V;

    @SerializedName("name")
    private String name;

    @SerializedName("weight")
    private String weight;

    @SerializedName("_id")
    private String id;

    @SerializedName("age")
    private String age;

    @SerializedName("height")
    private String height;

    @SerializedName("updatedAt")
    private String updatedAt;

    public String getNumber(){
        return number;
    }

    public String getImage(){
        return image;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public int getV(){
        return V;
    }

    public String getName(){
        return name;
    }

    public String getWeight(){
        return weight;
    }

    public String getId(){
        return id;
    }

    public String getAge(){
        return age;
    }

    public String getHeight(){
        return height;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }
}
