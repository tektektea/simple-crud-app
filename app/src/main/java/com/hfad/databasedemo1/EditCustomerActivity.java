package com.hfad.databasedemo1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mine on 12/21/2016.
 */
public class EditCustomerActivity extends AppCompatActivity implements View.OnClickListener {
    public static  String CUSTOMER_ID="Customer_id";
    private TextView idText;
    private EditText nameText;
    private EditText contactText;
    private EditText emailText;
    private EditText dobText;
    private Button saveBtn;
    private Button cancelBtn;
    private CustomerDao customerDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);

        idText=(TextView) findViewById(R.id.editId);
        nameText=(EditText) findViewById(R.id.editName);
        contactText=(EditText) findViewById(R.id.editContact);
        emailText=(EditText) findViewById(R.id.editEmail);
        dobText=(EditText) findViewById(R.id.editDob);

        customerDao=new CustomerDao(this);

        int id = getIntent().getExtras().getInt(CUSTOMER_ID);

        Customer customer = customerDao.getCustomerById(id);

        idText.setText((String.valueOf(customer.getId())));
        nameText.setText(customer.getName());
        contactText.setText(customer.getContact().toString());
        emailText.setText(customer.getEmail().toString());

        saveBtn=(Button)findViewById(R.id.edit_save);
        cancelBtn=(Button) findViewById(R.id.edit_clear);

        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String format = dateFormat.format(customer.getDob());
        dobText.setText(format);

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.edit_save:
                Customer customer=new Customer();
                customer.setId(Integer.parseInt(idText.getText().toString()));
                customer.setName(nameText.getText().toString().trim());
                customer.setContact(contactText.getText().toString().trim());
                customer.setEmail(emailText.getText().toString().trim());
                customer.setDob(new Date(dobText.getText().toString().trim()));

                boolean isEditted = customerDao.editCustomer(customer);
                if (isEditted)
                    Toast.makeText(this,"updated customer",Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this,"update failed",Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent(this,CustomerListActivity.class);
                setResult(RESULT_OK);
                this.finish();
                break;
            case R.id.edit_clear:
                this.finish();
                break;
            default:
                this.finish();
        }
    }
}
