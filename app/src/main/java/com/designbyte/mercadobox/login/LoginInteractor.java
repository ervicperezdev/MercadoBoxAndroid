package com.designbyte.mercadobox.login;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;

import com.designbyte.mercadobox.utils.MercadoBoxUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginInteractor {
    private FirebaseAuth mAuth;
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

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
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
