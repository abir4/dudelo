package com.example.dudelo.Model;

public class Cart {


    private String pid,productNamee,productPricee,quantity,discount;

    public Cart() {
    }

    public Cart(String pid, String productNamee, String productPricee, String quantity, String discount) {
        this.pid = pid;
        this.productNamee = productNamee;
        this.productPricee = productPricee;
        this.quantity = quantity;
        this.discount = discount;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
