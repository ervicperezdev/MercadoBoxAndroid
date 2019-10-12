package com.designbyte.mercadobox.signin;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.db.Customer;
import com.designbyte.mercadobox.models.firebase.User;
import com.designbyte.mercadobox.utils.MercadoBoxUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mx.openpay.client.core.OpenpayAPI;
import mx.openpay.client.exceptions.OpenpayServiceException;
import mx.openpay.client.exceptions.ServiceUnavailableException;

public class SigninInteractor {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usuarios;
    Context context;
    Customer user;
    interface OnSigninFinishedListener {
        void onNameError();
        void onLastNameError();
        void onEmailError();
        void onPhoneNumberError();
        void onPasswordError();
        void onPasswordConfirmError();
        void setPasswordNotEqualsError();
        void onTermsConditions();
        void onWeakPassword();
        void onSuccess();
        void onUserAlreadyExist();
    }

    public void signin(final String name, final String lastname, final String email, final String phoneNumber, final String password, final String passwordConfirm, final Boolean termsConditions, final OnSigninFinishedListener listener){
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
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            return;
        }
        if(!password.equals(passwordConfirm)){
            listener.setPasswordNotEqualsError();
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)){
            listener.onPasswordConfirmError();
            return;
        }
        if(!termsConditions){
            listener.onTermsConditions();
            return;
        }
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usuarios = database.getReference("Customers");



        //Customer de Firebase y base de datos
        user = new Customer();
        user.name = name;
        user.lastName = lastname;
        user.email = email;
        user.phoneNumber = phoneNumber;
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            /*
                            OpenpayAPI openpayAPI = new OpenpayAPI(context.getString(R.string.location_url_openpay),context.getString(R.string.public_api_key_openpay),context.getString(R.string.merchant_id));
                            mx.openpay.client.Customer customerOpenPay = new mx.openpay.client.Customer();
                            customerOpenPay.email(email);
                            customerOpenPay.lastName(lastname);
                            customerOpenPay.name(name);
                            customerOpenPay.phoneNumber(phoneNumber);
                            try {
                                mx.openpay.client.Customer customerRequest = openpayAPI.customers().create(customerOpenPay);
                                    user.customerId = customerRequest.getId();
                            } catch (OpenpayServiceException e) {
                                e.printStackTrace();
                            } catch (ServiceUnavailableException e) {
                                e.printStackTrace();
                            }
*/
                            usuarios.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        listener.onSuccess();
                                    }else{
                                        Log.d("SigninInteractor",task.getException().getMessage());
                                    }
                                }
                            });
                        }else{
                                try {
                                    throw task.getException();
                                } catch(FirebaseAuthWeakPasswordException e) {
                                    listener.onWeakPassword();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    listener.onUserAlreadyExist();
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                        }
                    }
                });
    }

}
