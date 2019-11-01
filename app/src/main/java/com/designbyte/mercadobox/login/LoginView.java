package com.designbyte.mercadobox.login;

public interface LoginView {
    void showProgress();
    void hideProgress();
    void setUsernameError();
    void setPasswordError();
    void goToMain();
    void showUserOrPasswordNotCorret();
    void emailToResetPasswordSent(String email);
    void messageResetPasswordError(String messageError);
    void setEmailError();
}
