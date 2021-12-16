package com.example.fee_management.ModelClass;

public class StudentModel {
    private String rollno,fullname,stream,year;
    public  StudentModel(){

    }

    public StudentModel(String rollno, String fullname, String stream, String year) {
        this.rollno = rollno;
        this.fullname = fullname;
        this.stream = stream;
        this.year = year;
    }

    public String getRollno() {
        return rollno;
    }

    public String getFullname() {
        return fullname;
    }

    public String getStream() {
        return stream;
    }

    public String getYear() {
        return year;
    }

}
