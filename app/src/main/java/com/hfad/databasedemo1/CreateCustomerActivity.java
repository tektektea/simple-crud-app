package com.hfad.databasedemo1;

import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by mine on 12/17/2016.
 */

public class CreateCustomerActivity extends AppCompatActivity {
    private EditText nameText;
    private EditText contactText;
    private EditText emailText;
    private EditText dobText;
    private Handler handler;
    private CustomerDao customerDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_customer);

        handler=new Handler();
        customerDao=new CustomerDao(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameText=(EditText) findViewById(R.id.cust_name);
        contactText=(EditText) findViewById(R.id.phone);
        emailText=(EditText) findViewById(R.id.email);
        dobText=(EditText) findViewById(R.id.dob);

        getSupportActionBar().setTitle("Create Customer");
    }

    public void onSaveButtonClick(View view) {
        final Customer customer=new Customer();
        customer.setName(nameText.getText().toString().trim());
        customer.setContact(contactText.getText().toString().trim());
        customer.setEmail(emailText.getText().toString().trim());
        String pattern =dobText.getText().toString().trim();

        Date date= new Date(pattern);
        customer.setDob(date);
        handler.post(new Runnable() {
            @Override
            public void run() {
                boolean isCreated = customerDao.createCustomer(customer);
                if (isCreated)
                    Toast.makeText(CreateCustomerActivity.this,"New customer created",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(CreateCustomerActivity.this,"Insertion failed,try again...",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void onClearButtonClick(View view) {
        clear();
    }
    private void clear(){
        emailText.getText().clear();
        nameText.getText().clear();
        contactText.getText().clear();
        dobText.getText().clear();
    }
}
