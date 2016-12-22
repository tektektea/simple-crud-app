package com.hfad.databasedemo1;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Created by mine on 12/20/2016.
 */

public class CustomerListActivity extends AppCompatActivity {
    private static final int EDIT_REQUEST = 1;
    private Cursor cursor;
    private CustomerAdapter adapter;
    private CustomerDao customerDao;
    private ListView listView;
    private List<Customer> allCustomers;
    private ArrayList<Customer> selectedItems;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        selectedItems = new ArrayList<>();
        allCustomers = new ArrayList<>();

        listView = (ListView) findViewById(R.id.customer_listView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customerDao = new CustomerDao(this);
        allCustomers = customerDao.getAllCustomers();
        adapter=new CustomerAdapter(this,R.layout.customer_row,selectedItems);

        for (Customer c:allCustomers){
            adapter.insert(c,adapter.getCount());
        }
        listView.setAdapter(adapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                Customer c = (Customer) adapter.getItem(position);
                if (checked) {
                    selectedItems.add(c);
                    adapter.notifyDataSetChanged();
                } else {
                    selectedItems.remove(c);
                    adapter.notifyDataSetChanged();
                }
                Log.i(String.valueOf(selectedItems.size())," itemsss");
                mode.invalidate();
            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.customer_action_mode, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                if (selectedItems.size() ==1) {
                    menu.setGroupVisible(R.id.singleOnly, true);
                } else {
                    menu.setGroupVisible(R.id.singleOnly, false);
                }
                return true;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.delete_customer:
                        deleteCustomers(selectedItems);
                        mode.finish();
                        return true;
                    case R.id.edit_customer:
                        Customer customer = selectedItems.get(0);
                        Log.i(String.valueOf(selectedItems.size())," selected item size");

                        Intent intent=new Intent(CustomerListActivity.this,EditCustomerActivity.class);
                        intent.putExtra(EditCustomerActivity.CUSTOMER_ID,customer.getId());
                        startActivityForResult(intent,EDIT_REQUEST);
                        mode.finish();
                        selectedItems.clear();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                mode=null;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==EDIT_REQUEST){
            if (resultCode== Activity.RESULT_OK){
                Log.i("update listview","heelooo");
                adapter.clear();
                adapter.addAll(customerDao.getAllCustomers());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.customer_createMenu:
                Intent intent = new Intent(this, CreateCustomerActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customerDao.close();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
;
    }


    public void deleteCustomers(Iterable<Customer> selectedCustomers) {
        adapter.setNotifyOnChange(false);
        for (Customer c:selectedCustomers){
            customerDao.deleteCustomer(c);
            adapter.remove(c);
        }
        adapter.setNotifyOnChange(true);
        adapter.notifyDataSetChanged();
    }

    public void editCustomer(Customer customer) {
        Toast.makeText(this, "Edit customer", Toast.LENGTH_SHORT).show();
    }
}
