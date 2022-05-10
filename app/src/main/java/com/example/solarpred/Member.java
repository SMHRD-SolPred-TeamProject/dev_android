package com.example.solarpred;

public class Member {
    private String mem_id;
    private String mem_pw;
    private String mem_name;
    private String mem_addr;
    private String mem_addrDetail;
    private String mem_phone;
    private String mem_purchase;
    private String mem_type;

    public Member(){}

    public Member(String mem_id, String mem_pw, String mem_name, String mem_addr, String mem_addrDetail, String mem_phone, String mem_purchase, String mem_type) {
        this.mem_id = mem_id;
        this.mem_pw = mem_pw;
        this.mem_name = mem_name;
        this.mem_addr = mem_addr;
        this.mem_addrDetail = mem_addrDetail;
        this.mem_phone = mem_phone;
        this.mem_purchase = mem_purchase;
        this.mem_type = mem_type;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getMem_pw() {
        return mem_pw;
    }

    public void setMem_pw(String mem_pw) {
        this.mem_pw = mem_pw;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_addr() {
        return mem_addr;
    }

    public void setMem_addr(String mem_addr) {
        this.mem_addr = mem_addr;
    }

    public String getMem_addrDetail() {
        return mem_addrDetail;
    }

    public void setMem_addrDetail(String mem_addrDetail) {
        this.mem_addrDetail = mem_addrDetail;
    }

    public String getMem_phone() {
        return mem_phone;
    }

    public void setMem_phone(String mem_phone) {
        this.mem_phone = mem_phone;
    }

    public String getMem_purchase() {
        return mem_purchase;
    }

    public void setMem_purchase(String mem_purchase) {
        this.mem_purchase = mem_purchase;
    }

    public String getMem_type() {
        return mem_type;
    }

    public void setMem_type(String mem_type) {
        this.mem_type = mem_type;
    }
}
