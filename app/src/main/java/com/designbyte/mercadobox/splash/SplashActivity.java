package com.designbyte.mercadobox.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.login.LoginActivity;
import com.designbyte.mercadobox.main.MainActivity;
import com.designbyte.mercadobox.signin.SigninActivity;

public class SplashActivity extends AppCompatActivity implements SplashView{
    SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashPresenter = new SplashPresenter(this, new SplashInteractor(),this);

        isLogged();
    }

    private void isLogged() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashPresenter.onLogged();
            }
        },3000);

    }

    @Override
    public void onSuccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(SplashActivity.this, MainActivity.class));

        }

        finish();
    }

    @Override
    public void onFailure() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        splashPresenter.onDestroy();
        super.onDestroy();
    }
}
