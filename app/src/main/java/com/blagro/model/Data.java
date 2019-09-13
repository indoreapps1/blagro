package com.blagro.model;

public class Data {

    private int pro_id;

    private int qrt;
    private String order_no;

    public int getProductId() {
        return pro_id;
    }

    public void setProductId(int productId) {
        this.pro_id = productId;
    }

    public int getProductQty() {
        return qrt;
    }

    public void setProductQty(int productQty) {
        this.qrt = productQty;
    }

    public String getOrderNo() {
        return order_no;
    }

    public void setOrderNo(String order_no) {
        this.order_no = order_no;
    }
}
