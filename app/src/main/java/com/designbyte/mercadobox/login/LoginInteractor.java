package com.designbyte.mercadobox.login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.designbyte.mercadobox.models.db.AppDatabase;
import com.designbyte.mercadobox.models.db.Customer;
import com.designbyte.mercadobox.signin.SigninInteractor;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;
import com.designbyte.mercadobox.utils.MercadoBoxUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

import static androidx.room.Room.databaseBuilder;

public class LoginInteractor {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference customer;
    Context context;
    AppDatabase db;
    interface OnLoginFinishedListener {
        void onUsernameError();
        void onPasswordError();
        void onSuccess();
        void onFailure();
        void setEmailToResetSuccess(String email);
        void setEmailToResetError(String messageError);
        void onEmailError();
    }
    public void forgotPassword(final String email, final LoginInteractor.OnLoginFinishedListener listener){
        if(!MercadoBoxUtils.isValidEmail(email)){
            listener.onEmailError();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("es");
        mAuth.useAppLanguage();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    listener.setEmailToResetSuccess(email);

                }else{
                    listener.setEmailToResetError(String.format("%s ",task.getException().getMessage()));

                }
            }
        });
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
        final MercadoBoxPreferences mercadoBoxPreferences = new MercadoBoxPreferences(context);

        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();


        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            customer.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){
                                        Customer customer = dataSnapshot.getValue(Customer.class);
                                        db.customerDao().deleteAll();
                                        db.customerDao().insertItem(customer);
                                        mercadoBoxPreferences.saveSharedSetting("name",customer.name+" "+customer.lastName);
                                    }
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
        db.close();

    }


}
