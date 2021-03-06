package com.example.employeedb;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Employee> {

    Context mCtx;
    int layoutRes;
    List<Employee> employeeList;
    DatabaseManager mDatabase;

    public EmployeeAdapter(Context mCtx, int layoutRes, List<Employee> employeeList, DatabaseManager mDatabase) {
        super(mCtx, layoutRes, employeeList);
        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.employeeList = employeeList;
        this.mDatabase = mDatabase;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(layoutRes, null);
        TextView textViewName = view.findViewById(R.id.tvName);

        TextView textViewPhone = view.findViewById(R.id.tvPhone);
        TextView textViewAddress = view.findViewById(R.id.tvAddress);
        TextView textViewTime = view.findViewById(R.id.tvTime);
        TextView textViewDept = view.findViewById(R.id.tvDepartment);
        TextView textViewSalary = view.findViewById(R.id.tvSalary);
        TextView textViewJoinDate = view.findViewById(R.id.tvJoinDate);
        final Employee employee = employeeList.get(position);

        textViewName.setText(employee.getName());
        textViewPhone.setText(employee.getPhone());
        textViewAddress.setText(employee.getAddress());
        textViewTime.setText(employee.getTime());
        textViewDept.setText(employee.getDept());
        textViewSalary.setText(String.valueOf(employee.getSalary()));
        textViewJoinDate.setText(employee.getJoiningDate());

        view.findViewById(R.id.btnDeleteEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEmployee(employee);
            }
        });

        view.findViewById(R.id.btnEditEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAnEmployee(employee);
            }
        });
        return view;
    }

    private void updateAnEmployee(final Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextName = view.findViewById(R.id.etName);

        final EditText editTextPhone = view.findViewById(R.id.etPhone);
        final EditText editTextAddress = view.findViewById(R.id.etAddress);
        final EditText editTextTime = view.findViewById(R.id.etTime);

        final EditText editTextSalary = view.findViewById(R.id.etSalary);
        final Spinner spinner = view.findViewById(R.id.spinnerDepartment);

        editTextName.setText(employee.getName());
        editTextSalary.setText(String.valueOf(employee.getSalary()));

        view.findViewById(R.id.btAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String address = editTextAddress.getText().toString();
                String time = editTextTime.getText().toString();

                String salary = editTextSalary.getText().toString();
                String dept = spinner.getSelectedItem().toString();

                if (name.isEmpty()) {
                    editTextName.setError("Customer Name can't be empty");
                    editTextName.requestFocus();
                    return;
                }

                if(address.isEmpty()){
                    editTextAddress.setError("Customer Address cant be empty");
                    editTextAddress.requestFocus();
                    return;
                }

                if(phone.isEmpty()){
                    editTextPhone.setError("Customer Phone cant be empty");
                    editTextPhone.requestFocus();
                    return;
                }

                if(time.isEmpty()){
                    editTextTime.setError("Customer Time cant be empty");
                    editTextTime.requestFocus();
                    return;
                }
                if (salary.isEmpty()) {
                    editTextSalary.setError("Payment can't be empty");
                    editTextSalary.requestFocus();
                    return;
                }
                //calling the update method from database manager instance
                if (mDatabase.updateEmployee(employee.getId(), name, address, phone, time, dept, Double.valueOf(salary))) {
                    Toast.makeText(mCtx, "Reservation Updated", Toast.LENGTH_SHORT).show();
                    loadEmployeesFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }// update employee

    private void deleteEmployee(final Employee employee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                if (mDatabase.deleteEmployee(employee.getId()))
                    loadEmployeesFromDatabaseAgain();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadEmployeesFromDatabaseAgain() {
        Cursor cursor = mDatabase.getAllEmployees();

        employeeList.clear();
        if (cursor.moveToFirst()) {
            do {
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getDouble(7)
                ));
            } while (cursor.moveToNext());
            notifyDataSetChanged();
        }
    }

}
