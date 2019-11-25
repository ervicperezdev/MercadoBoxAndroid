package com.designbyte.mercadobox.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.firebase.User;
import com.designbyte.mercadobox.utils.MercadoBoxPreferences;

public class ProfileActivity extends AppCompatActivity implements ProfileView{
    EditText name, lastName, email, phone, address, responsable;
    Button btnUpdate;
    ProfilePresenter profilePresenter;
    ProgressBar progressBar;
    MercadoBoxPreferences mercadoBoxPreferences;
    TextView btnChangePassword;


    Dialog dialog1;
    View view;
    AlertDialog.Builder alertPasswordChange;
    EditText passwordChange, passwordChangeConfirm;
    Button btnSendPasswordChange;
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        profilePresenter = new ProfilePresenter(this,new ProfileInteractor());
        mercadoBoxPreferences = new MercadoBoxPreferences(this);
        activity = this;
        getDataProfile();
    }

    public void initViews(){
        name = findViewById(R.id.name);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        responsable = findViewById(R.id.responsable);
        progressBar = findViewById(R.id.progressBar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPasswordReset();
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
        passwordChange.setError("Ingresa una contraseña");
    }

    @Override
    public void setPasswordConfirmError() {
        passwordChangeConfirm.setError("Ingresa una contraseña");
    }

    @Override
    public void setPasswordNotEqualsError() {
        passwordChangeConfirm.setError("La contraseña no coincide");
    }

    @Override
    public void setDataUser(User user) {
        name.setText(user.name);
        lastName.setText(user.lastName);
        email.setText(user.email);
        phone.setText(user.phoneNumber);
        address.setText(user.address);
        responsable.setText(user.responsable);
    }
    public void getDataProfile(){
        profilePresenter.getDataUser();
    }
    @Override
    public void savedChanges() {
        Toast.makeText(this, "Se han guardado los cambios",Toast.LENGTH_LONG).show();
        mercadoBoxPreferences.saveSharedSetting("name",name.getText().toString()+" "+lastName.getText().toString());
        mercadoBoxPreferences.saveSharedSetting("email",name.getText().toString());
        mercadoBoxPreferences.saveSharedSetting("passwd",passwordChange.getText().toString());
    }

    @Override
    public void savedPasswordChanges() {
        Toast.makeText(this, "Se han guardado los cambios",Toast.LENGTH_LONG).show();
        mercadoBoxPreferences.saveSharedSetting("passwd",passwordChange.getText().toString());
        dialog1.cancel();
    }

    public void updateProfile(){
        profilePresenter.updateProfile(name.getText().toString(),lastName.getText().toString(),email.getText().toString(),phone.getText().toString(),address.getText().toString(),responsable.getText().toString());
    }

    @Override
    protected void onDestroy() {
        profilePresenter.onDestroy();
        super.onDestroy();
    }

    public void showDialogPasswordReset(){
        view = getLayoutInflater().inflate(R.layout.dialog_password_change,null,true);
        alertPasswordChange = new AlertDialog.Builder(this);
        passwordChange = view.findViewById(R.id.passwordChange);
        passwordChangeConfirm = view.findViewById(R.id.passwordChangeConfirm);

        btnSendPasswordChange = view.findViewById(R.id.sendPasswordChange);
        alertPasswordChange.setView(view);
        dialog1 = alertPasswordChange.show();
        btnSendPasswordChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSendPasswordChange.setEnabled(false);
                hideKeyboard(activity);
                sendEmailToChangePassword(email.getText().toString(),passwordChange.getText().toString(),passwordChangeConfirm.getText().toString());

            }});
    }
    public void sendEmailToChangePassword(String email, String password, String passwordConfirmation){
        profilePresenter.changePassword(email,mercadoBoxPreferences.readSharedSetting("passwd",""), password, passwordConfirmation);
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
