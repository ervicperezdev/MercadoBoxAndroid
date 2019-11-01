package com.designbyte.mercadobox.models.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Cart {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idCategory")
    public String idCategory;

    @ColumnInfo(name = "idProduct")
    public String idProduct;

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

    public String getUnityText(){
        String text = "";
        switch (Integer.valueOf(this.unity)){
            case  0:  text = "kg(s)";
            break;
            case 1: text = "g(s)";
            break;
            case 2: text = "oz";
            break;
            case 3: text = "pza";
            break;
            case 4: text = "l(s)";
            break;

        }
        return text;
    }

}
