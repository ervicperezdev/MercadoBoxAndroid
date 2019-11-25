package com.designbyte.mercadobox.profile;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.designbyte.mercadobox.models.db.Customer;
import com.designbyte.mercadobox.models.firebase.User;
import com.designbyte.mercadobox.utils.MercadoBoxUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileInteractor  {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference customer;

    interface OnChangeFinishedListener {
        void onNameError();
        void onLastNameError();
        void onEmailError();
        void onPhoneNumberError();
        void onPasswordError();
        void onPasswordConfirmError();
        void setPasswordNotEqualsError();
        void setDataUser(User user);
        void onSuccess();
        void onSuccessPasswordChangues();
    }
    public void setDataProfile(final OnChangeFinishedListener listener){

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        customer = database.getReference("Customers");

        customer.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        Log.d("ProfileInteractor",String.format("%s , %s, %s",user.name, user.lastName, user.email));
                        listener.setDataUser(user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    public void updateProfile(final String name, final String lastname, final String email, final String phoneNumber, final String address, final String responsable, final OnChangeFinishedListener listener){
        if(TextUtils.isEmpty(name)){
            listener.onNameError();
            return;
        }
        if(TextUtils.isEmpty(lastname)){
            listener.onLastNameError();
            return;
        }
        if(!MercadoBoxUtils.isValidEmail(email)){
            listener.onEmailError();
            return;
        }
        if(!MercadoBoxUtils.isValidPhoneNumber(phoneNumber)){
            listener.onPhoneNumberError();
            return;
        }


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        customer = database.getReference("Customers");
        final Customer user = new Customer();
        user.name = name;
        user.lastName = lastname;
        user.phoneNumber = phoneNumber;
        user.email = email;
        user.address = address;
        user.responsable = responsable;

        customer.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                            listener.onSuccess();

                }
            }
        });

    }

    public void changePassword(String email, String oldPassword, final String password, String passwordConfirm, final OnChangeFinishedListener listener){
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            return;
        }
        if(TextUtils.isEmpty(passwordConfirm)){
            listener.onPasswordConfirmError();
            return;
        }
        if(!password.equals(passwordConfirm)){
            Log.e("Contraseña",password);
            Log.e("Contraseña Confirmar",passwordConfirm);
            listener.setPasswordNotEqualsError();
            return;
        }


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, oldPassword);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("Contraseña", "Password updated");
                                    } else {
                                        Log.d("Contraseña", "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            Log.d("Contraseña", "Error auth failed");
                        }
                    }
                });
    }
}
