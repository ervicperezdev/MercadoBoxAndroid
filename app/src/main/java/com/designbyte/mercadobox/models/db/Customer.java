package com.designbyte.mercadobox.models.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity
public class Customer {

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "phoneNumber")
    public String phoneNumber;

    @ColumnInfo(name = "customerId")
    public String customerId;

}
