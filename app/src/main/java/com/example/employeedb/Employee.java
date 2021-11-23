package com.example.employeedb;

public class Employee {

    int id;
    String name, dept, joiningDate,phone, address, time;
    double salary;

    public Employee(int id, String name, String phone, String address, String time, String dept, String joiningDate, double salary ){
        this.id = id;
        this.name=name;
        this.phone=phone;
        this.time=time;
        this.address=address;
        this.dept=dept;
        this.joiningDate=joiningDate;
        this.salary=salary;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }
    public String getPhone(){
        return phone;
    }
    public String getTime(){
        return time;
    }

    public String getDept(){
        return dept;
    }

    public String getJoiningDate(){
        return joiningDate;
    }

    public  double getSalary(){
        return salary;
    }
}
