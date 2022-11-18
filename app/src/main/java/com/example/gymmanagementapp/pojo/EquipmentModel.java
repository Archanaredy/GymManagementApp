package com.example.gymmanagementapp.pojo;

import com.google.gson.annotations.SerializedName;

public class EquipmentModel {

    @SerializedName("image")
    private String image;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("__v")
    private int V;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("_id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("updatedAt")
    private String updatedAt;

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

    public String getDescription(){
        return description;
    }

    public String getId(){
        return id;
    }

    public String getStatus(){
        return status;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setV(int v) {
        V = v;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "EquipmentModel{" +
                "image='" + image + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", V=" + V +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
