package com.example.dudelo.Model;

public class Products {


    private String   pid,productNamee, productPricee, time,  description, date, catagory, image;


    public Products() {

    }

    public Products(String pid, String productNamee, String productPricee, String time, String description, String date, String catagory, String image) {
        this.pid = pid;
        this.productNamee = productNamee;
        this.productPricee = productPricee;
        this.time = time;
        this.description = description;
        this.date = date;
        this.catagory = catagory;
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductNamee() {
        return productNamee;
    }

    public void setProductNamee(String productNamee) {
        this.productNamee = productNamee;
    }

    public String getProductPricee() {
        return productPricee;
    }

    public void setProductPricee(String productPricee) {
        this.productPricee = productPricee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCatagory() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory = catagory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}