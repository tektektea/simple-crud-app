package com.hfad.databasedemo1;

import java.util.Date;

/**
 * Created by mine on 12/17/2016.
 */

public class Customer {
    private  int id;
    private String name;
    private String contact;
    private String email;
    private Date dob;

    public Customer() {

    }

    public Customer(int id, String name, String contact, String email, Date dob) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.dob = dob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id == customer.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return " id = "+id+" name = "+name;
    }
}
