package com.designbyte.mercadobox.orderhistory;

import android.util.Log;

import androidx.annotation.NonNull;
import com.designbyte.mercadobox.models.firebase.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryInteractor {
    private FirebaseDatabase database;
    private DatabaseReference orderReference;
    interface OnOrderHistoryListener{
        void setDataOrder(List<Order> items);
    }

    public void getDataOrders(final OnOrderHistoryListener listener){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    GenericTypeIndicator<ArrayList<Order>> t = new GenericTypeIndicator<ArrayList<Order>>() {};
                    ArrayList<Order> yourStringArray = dataSnapshot.getValue(t);
                    listener.setDataOrder(yourStringArray);
                }catch (Exception e){
                    Log.e("OrderHistoryInteractor","onDataChanged"+e.getMessage());
                }finally {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("OrderHistoryInteractor","onCancelled"+databaseError.getMessage());

            }
        };
        database = FirebaseDatabase.getInstance();
        orderReference = database.getReference("OrderHistory");
        orderReference.addValueEventListener(valueEventListener);
    }
}
