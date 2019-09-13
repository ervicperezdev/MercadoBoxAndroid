package com.designbyte.mercadobox.models.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

    @Database(entities = {Cart.class}, version = 2, exportSchema = false)
    public abstract class AppDatabase extends RoomDatabase {
        public abstract CartDao cartDao();
    }
