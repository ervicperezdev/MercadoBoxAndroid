package com.designbyte.mercadobox.detailorder;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.designbyte.mercadobox.models.db.AppDatabase;
import com.designbyte.mercadobox.models.firebase.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.room.Room.databaseBuilder;

public class DetailInteractor {
    long maxId = 0;
    FirebaseAuth mAuth;
    private DatabaseReference orderHistory;
    private FirebaseDatabase database;
    private AppDatabase db;
    interface OnDetailListener {
        void onAddressError();
        void onDateError();
        void onTimeError();
        void onNameCardError();
        void onNumberCardError();
        void onLastNameError();
        void onMMCardError();
        void onAACardError();
        void onCVVCardError();
        void onAceptedError();
        void orderShipmentCompleted();
    }

    void sendClientsOrder(
            String address, String date, String time, Boolean confirmationCall,
            String noteOrder, String numberCard, String nameCard,
            String lastNameCard, String monthExpirationCard,
            String yearExpirationCard, String cvvCard, Boolean accepted, Context context, final OnDetailListener listener) {

        if (TextUtils.isEmpty(address)) {
            listener.onAddressError();
            return;
        }
        if (TextUtils.isEmpty(date)) {
            listener.onDateError();
            return;
        }
        if (TextUtils.isEmpty(time)) {
            listener.onTimeError();
            return;
        }
        if (!confirmationCall) {
        }
        if (TextUtils.isEmpty(noteOrder)) {
        }
        if (TextUtils.isEmpty(numberCard)) {
            listener.onNumberCardError();
            return;
        }
        if (TextUtils.isEmpty(nameCard)) {
            listener.onNameCardError();
            return;
        }
        if (TextUtils.isEmpty(lastNameCard)) {
            listener.onLastNameError();
            return;
        }
        if (TextUtils.isEmpty(monthExpirationCard)) {
            listener.onMMCardError();
            return;
        }
        if (TextUtils.isEmpty(yearExpirationCard)) {
            listener.onAACardError();
            return;
        }
        if (TextUtils.isEmpty(cvvCard)) {
            listener.onCVVCardError();
            return;
        }
        if (!accepted) {
            listener.onAceptedError();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        orderHistory = database.getReference("OrderHistory");
        orderHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxId = dataSnapshot.getChildrenCount();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        Order order = new Order();
        order.date = date;
        order.name = nameCard;
        order.status = 0;
        order.products = db.cartDao().getItemsCart();
        order.idOrder = Integer.valueOf(String.valueOf(maxId +1));
        orderHistory.child(String.valueOf(maxId + 1)).setValue(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    db.cartDao().deleteAll();
                    listener.orderShipmentCompleted();
                }
            }
        });


    }
}
