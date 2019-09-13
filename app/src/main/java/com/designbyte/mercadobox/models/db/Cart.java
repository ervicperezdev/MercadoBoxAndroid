package com.designbyte.mercadobox.models.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Cart {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idCategory")
    public int idCategory;

    @ColumnInfo(name = "idProduct")
    public int idProduct;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "quantity")
    public int quantity;

    @ColumnInfo(name = "unity")
    public String unity;

    @ColumnInfo(name = "step")
    public int step;

    @ColumnInfo(name = "costByUnit")
    public float costByUnit;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "description")
    public String description;

}
