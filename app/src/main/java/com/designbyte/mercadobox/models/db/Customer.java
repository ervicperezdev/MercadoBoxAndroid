package com.designbyte.mercadobox.models.db;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Customer {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "phoneNumber")
    public String phoneNumber;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "responsable")
    public String responsable;

    @ColumnInfo(name = "customerId")
    public String customerId;


}
