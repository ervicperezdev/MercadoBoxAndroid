package com.designbyte.mercadobox.login;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.MainActivity;
import com.designbyte.mercadobox.signin.SigninActivity;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

import javax.net.ServerSocketFactory;

public class LoginActivity extends AppCompatActivity implements LoginView{
    ProgressBar progressBar;
    EditText username, password;
    Button btnLogin, btnSignin;
    LoginPresenter presenter;
    LoginInteractor loginInteractor;
    MercadoBoxPreferences mercadoBoxPreferences;
    TextView textViewForgotPassword;
    Activity activity;



    Dialog dialog1;
    View view;
    AlertDialog.Builder alertPasswordReset;
    EditText emailForgot;
    Button btnSendEmailReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        loginInteractor = new LoginInteractor();
        loginInteractor.context = this;
        activity = this;
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
        textViewForgotPassword= findViewById(R.id.forgotPassword);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { showDialogPasswordReset();
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

    @Override
    public void emailToResetPasswordSent(String email) {
        Toast.makeText(this,"Hemos enviado un correo a "+email+ " para restablcer tu contraseña",Toast.LENGTH_SHORT).show();
        dialog1.cancel();

    }

    @Override
    public void messageResetPasswordError(String messageError) {
        Toast.makeText(this,messageError,Toast.LENGTH_LONG).show();
        dialog1.cancel();
        hideProgress();


    }

    @Override
    public void setEmailError() {
        emailForgot.setError("Ingresa un correo válido");
    }

    public void showDialogPasswordReset(){
        view = getLayoutInflater().inflate(R.layout.dialog_password_reset,null,true);
        alertPasswordReset = new AlertDialog.Builder(this);
        emailForgot = view.findViewById(R.id.emailForgot);
        btnSendEmailReset = view.findViewById(R.id.sendEmailReset);
        alertPasswordReset.setView(view);
        dialog1 = alertPasswordReset.show();
        btnSendEmailReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSendEmailReset.setEnabled(false);
                hideKeyboard(activity);
                sendEmailToResetPassword(emailForgot.getText().toString());

            }});
    }
    public void sendEmailToResetPassword(String email){
        presenter.forgotPassword(email);

    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
