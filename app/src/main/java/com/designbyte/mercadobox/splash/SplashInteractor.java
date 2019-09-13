package com.designbyte.mercadobox.splash;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SplashInteractor {
    FirebaseAuth mAuth;
    Context context;
    MercadoBoxPreferences mercadoBoxPreferences;
    interface OnSessionListener{
        void onSuccess();
        void onFailure();
    }

    public void sessionActived(final OnSessionListener listener, Context context){
        mAuth = FirebaseAuth.getInstance();
        this.context = context;
        mercadoBoxPreferences = new MercadoBoxPreferences(this.context);

        if(mAuth.getCurrentUser() != null){
            listener.onSuccess();
            Log.d("SplashInteractor","onSuccess");
        }else{
            Log.d("SplashInteractor","onFailure");
            if(mercadoBoxPreferences.readSharedSetting("logged",false)){
                String email = mercadoBoxPreferences.readSharedSetting("email","");
                String passwd = mercadoBoxPreferences.readSharedSetting("passwd","");
                mAuth = FirebaseAuth.getInstance();

                mAuth.signInWithEmailAndPassword(email, passwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("SplashInteractor", "signInWithEmail:success");
                                    listener.onSuccess();
                                } else {
                                    Log.w("SplashInteractor", "signInWithEmail:failure", task.getException());
                                    listener.onFailure();
                                }
                            }
                        });
            }else{
                listener.onFailure();
            }
        }
    }


}
