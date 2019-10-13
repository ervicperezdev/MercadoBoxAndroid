package com.designbyte.mercadobox.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.MainActivity;
import com.designbyte.mercadobox.signin.SigninActivity;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

public class LoginActivity extends AppCompatActivity implements LoginView{
    ProgressBar progressBar;
    EditText username, password;
    Button btnLogin, btnSignin;
    LoginPresenter presenter;
    LoginInteractor loginInteractor;
    MercadoBoxPreferences mercadoBoxPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        loginInteractor = new LoginInteractor();
        loginInteractor.context = this;
        presenter = new LoginPresenter(this,loginInteractor);
        mercadoBoxPreferences = new MercadoBoxPreferences(this);
    }

    public void initViews(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignin = findViewById(R.id.btnSignin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCredentials();
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSigin();
            }
        });
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        username.setError("Ingresa un correo válido");
    }

    @Override
    public void setPasswordError() {
        password.setError("Escribe tu contraseña");
    }

    @Override
    public void goToMain() {
        mercadoBoxPreferences.saveSharedSetting("logged",true);
        mercadoBoxPreferences.saveSharedSetting("email",username.getText().toString());
        mercadoBoxPreferences.saveSharedSetting("passwd",password.getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }
        finish();
    }

    @Override
    public void showUserOrPasswordNotCorret() {

        Toast.makeText(this,"Usuario/Contraseña son incorrectos",Toast.LENGTH_LONG).show();
    }

    public void goToSigin(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(LoginActivity.this, SigninActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(LoginActivity.this, SigninActivity.class));

        }
    }

    public void validateCredentials(){
        presenter.validateCredentials(username.getText().toString(),password.getText().toString());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
