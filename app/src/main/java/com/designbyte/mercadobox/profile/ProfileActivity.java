package com.designbyte.mercadobox.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.firebase.User;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

public class ProfileActivity extends AppCompatActivity implements ProfileView{
    EditText name, lastName, email, phone, password, passwordConfirm;
    Button btnUpdate;
    ProfilePresenter profilePresenter;
    ProgressBar progressBar;
    MercadoBoxPreferences mercadoBoxPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        profilePresenter = new ProfilePresenter(this,new ProfileInteractor());
        mercadoBoxPreferences = new MercadoBoxPreferences(this);
        getDataProfile();
    }

    public void initViews(){
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        progressBar = findViewById(R.id.progressBar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
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
    public void setNameError() {
        profilePresenter.onNameError();
    }

    @Override
    public void setLastNameError() {
        profilePresenter.onLastNameError();
    }

    @Override
    public void setEmailError() {
        profilePresenter.onEmailError();
    }

    @Override
    public void setPhoneNumberError() {
        profilePresenter.onPhoneNumberError();
    }

    @Override
    public void setPasswordError() {
        profilePresenter.onPasswordError();
    }

    @Override
    public void setPasswordConfirmError() {
        profilePresenter.onPasswordConfirmError();
    }

    @Override
    public void setPasswordNotEqualsError() {
        profilePresenter.setPasswordNotEqualsError();
    }

    @Override
    public void setDataUser(User user) {
        name.setText(user.name);
        lastName.setText(user.lastName);
        email.setText(user.email);
        phone.setText(user.phoneNumber);
        String passwd = mercadoBoxPreferences.readSharedSetting("passwd","");
        password.setText(passwd);
        passwordConfirm.setText(passwd);
    }
    public void getDataProfile(){
        profilePresenter.getDataUser();
    }
    @Override
    public void savedChanges() {
        Toast.makeText(this, "Se han guardado los cambios",Toast.LENGTH_LONG).show();
    }

    public void updateProfile(){
        profilePresenter.updateProfile(name.getText().toString(),lastName.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),passwordConfirm.getText().toString());
    }

    @Override
    protected void onDestroy() {
        profilePresenter.onDestroy();
        super.onDestroy();
    }
}
