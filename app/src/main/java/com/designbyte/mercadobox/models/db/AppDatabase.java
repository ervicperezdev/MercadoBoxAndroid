package com.designbyte.mercadobox.models.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

    @Database(entities = {Cart.class, Customer.class}, version = 3, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract CartDao cartDao();
        public abstract CustomerDao customerDao();
    }
