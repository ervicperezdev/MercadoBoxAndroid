package com.designbyte.mercadobox.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.main.MainActivity;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

public class SigninActivity extends AppCompatActivity implements SigninView {
    EditText name, lastName, email, phone, password, passwordConfirm;
    CheckBox checkBoxTermsConditions;
    Button btnSignin;
    ProgressBar progressBar;
    SigninPresenter presenter;
    SigninInteractor signinInteractor;
    MercadoBoxPreferences mercadoBoxPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        initViews();
        signinInteractor = new SigninInteractor();
        signinInteractor.context = this;
        presenter = new SigninPresenter(this, signinInteractor);
        mercadoBoxPreferences = new MercadoBoxPreferences(this);
    }

    public void initViews(){
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        progressBar = findViewById(R.id.progressBar);
        btnSignin = findViewById(R.id.btnSignin);
        checkBoxTermsConditions = findViewById(R.id.checkboxTermsConditions);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
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
    public void setPasswordNotEqualsError() {
        passwordConfirm.setError("La contraseña debe ser la misma");
    }

    @Override
    public void setNameError() {
        name.setError("Ingresa tu nombre");
    }

    @Override
    public void setLastNameError() {
        lastName.setError("Ingresa tus apellidos");
    }

    @Override
    public void setEmailError() {
        email.setError("Ingresa un correo válido");
    }

    @Override
    public void setPhoneNumberError() {
        phone.setError("Ingresa tu número de telefono");
    }

    @Override
    public void setPasswordError() {
        password.setError("Ingresa una contraseña");
    }

    @Override
    public void setPasswordConfirmError() {
        password.setError("Ingresa una contraseña");
    }

    @Override
    public void setTermsConditionsError() {
        checkBoxTermsConditions.setError("Debes de aceptar los términos y condiciones");
    }

    @Override
    public void goToMain() {
        mercadoBoxPreferences.saveSharedSetting("logged",true);
        mercadoBoxPreferences.saveSharedSetting("email",email.getText().toString());
        mercadoBoxPreferences.saveSharedSetting("passwd",password.getText().toString());
        mercadoBoxPreferences.saveSharedSetting("name",name.getText().toString()+" "+lastName.getText().toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(SigninActivity.this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(new Intent(SigninActivity.this, MainActivity.class));

        }

        finish();
    }

    @Override
    public void showUserAlreadyExist() {
        Toast.makeText(this,"El usuario con ese correo ya existe, por favor iniciar sesión para continuar",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAuthWeakPassword() {
        Toast.makeText(this,"La contraseña debe tener al menos 6 carácteres",Toast.LENGTH_LONG).show();
    }

    public void signin(){
        presenter.signinProcess(name.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                phone.getText().toString(),
                password.getText().toString(),
                passwordConfirm.getText().toString(),
                checkBoxTermsConditions.isChecked()
        );
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
