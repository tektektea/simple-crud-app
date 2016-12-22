package com.hfad.databasedemo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mine on 12/17/2016.
 */

public class CustomerDao extends  DatabaseManager {

    public CustomerDao(Context context) {
        super(context);

    }
    public boolean deleteCustomer(Customer customer){
        String whereClause="_id="+String.valueOf(customer.getId());
        int isDelete = getWritableDatabase().delete(DatabaseManager.TABLENAME, whereClause, null);
        if (isDelete>=1)
            return true;
        else
            return false;
    }
    public boolean createCustomer(Customer customer){
        ContentValues values=new ContentValues();
        values.put("NAME",customer.getName());
        values.put("CONTACT",customer.getContact());
        values.put("EMAIL",customer.getEmail());
        values.put("DOB",customer.getDob().getTime());
        try{
            getWritableDatabase().insert("CUSTOMERTABLE",null,values);
            return true;
        }catch (SQLException ex){
            return false;
        }

    }
    public List<Customer> getAllCustomers(){
        Cursor cursor=getReadableDatabase().query(DatabaseManager.TABLENAME,
                new String[]{"_ID","NAME","CONTACT","EMAIL","DOB"},
                null,null,null,null,null);
        List<Customer> list=new ArrayList<>();
        while (cursor.moveToNext()){
            Customer customer=new Customer();
            customer.setId(cursor.getInt(0));
            customer.setName(cursor.getString(1));
            customer.setContact(cursor.getString(2));
            customer.setEmail(cursor.getString(3));

            long dateString = cursor.getLong(4);
            Date date =new Date(dateString);
            customer.setDob(date);
            list.add(customer);

        }
        return list;
    }
    public Cursor getCursorForAll(){
        Cursor cursor = this.getReadableDatabase().query("CUSTOMERTABLE", new String[]{"_id","NAME"}, null, null, null, null, null);
        return cursor;
    }

    public Customer getCustomerById(int id) {
        String selection="_id = "+String.valueOf(id);
        Cursor query = this.getReadableDatabase()
                .query(TABLENAME, new String[]{"_id", "NAME", "CONTACT", "EMAIL", "DOB"}, selection, null, null, null, null);
        Customer c=new Customer();
        boolean isFound = query.moveToFirst();
        if (isFound){
            c.setId(query.getInt(0));
            c.setName(query.getString(1));
            c.setContact(query.getString(2));
            c.setEmail(query.getString(3));
            long dateLong = query.getLong(4);
            Date date=new Date(dateLong);
            c.setDob(date);
            return c;
        }
        return null;

    }

    public boolean editCustomer(Customer customer) {
        String whereClause="_id ="+customer.getId();
        ContentValues values=new ContentValues();
        values.put("NAME",customer.getName());
        values.put("CONTACT",customer.getContact());
        values.put("EMAIL",customer.getContact());
        values.put("DOB",customer.getDob().getTime());
        int rowEffected = this.getWritableDatabase().update(TABLENAME, values, whereClause, null);
        if (rowEffected>=1)
            return true;
        return false;
    }
}
