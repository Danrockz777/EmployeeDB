package com.example.employeedb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText inputName, inputSalary;
    Spinner inputSpinnerDept;
    Button buttonAdd, buttonView;
    DatabaseManager mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase= new DatabaseManager(this);
        inputName=(EditText) findViewById(R.id.etName);
        inputSalary=(EditText) findViewById(R.id.etSalary);
        inputSpinnerDept=(Spinner) findViewById(R.id.spinnerDepartment);

        buttonAdd=(Button) findViewById(R.id.btAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmployee();
            }
        });

        buttonView=(Button) findViewById(R.id.btView);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewData();
            }
        });

    }


    private  void addEmployee(){
        String name= inputName.getText().toString();
        String dept= inputSpinnerDept.getSelectedItem().toString();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM--dd");
        String joiningDate= sdf.format(cal.getTime());
        String salary= inputSalary.getText().toString();

        //validation
        if(name.isEmpty()){
            inputName.setError("Customer Name cant be empty");
            inputName.requestFocus();
            return;
        }
        if(salary.isEmpty()){
            inputSalary.setError("Customer Payment cant be empty");
            inputSalary.requestFocus();
            return;
        }
        if(mDatabase.addEmployee(name,dept,joiningDate,Double.parseDouble(salary)))
            Toast.makeText(this,"Customer Reservation Added",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Could not Add Customer ",Toast.LENGTH_SHORT).show();



    }
    public void viewData(){
        Intent intent = new Intent(this, EmployeeActivity.class);
        startActivity(intent);
    }





}