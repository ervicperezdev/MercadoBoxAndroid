package com.designbyte.mercadobox.login;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;

import com.designbyte.mercadobox.models.db.Customer;
import com.designbyte.mercadobox.utils.MercadoBoxUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginInteractor {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference customer;
    interface OnLoginFinishedListener {
        void onUsernameError();
        void onPasswordError();
        void onSuccess();
        void onFailure();
    }

    public void login(final String username, final String password, final OnLoginFinishedListener listener){
        if(!MercadoBoxUtils.isValidEmail(username)){
            listener.onUsernameError();
            return;
        }
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            return;
        }

        database = FirebaseDatabase.getInstance();
        customer = database.getReference("Customers");
        mAuth = FirebaseAuth.getInstance();




        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Customer loginCustomer = new Customer();

                            customer.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Log.d("LoginInteractor", "signInWithEmail:success");
                            listener.onSuccess();
                        } else {
                            Log.w("LoginInteractor", "signInWithEmail:failure", task.getException());
                            listener.onFailure();
                        }

                    }
                });
    }


}
