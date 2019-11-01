package com.designbyte.mercadobox.signin;

public interface SigninView {
    void showProgress();
    void hideProgress();
    void setPasswordNotEqualsError();
    void setNameError();
    void setLastNameError();
    void setEmailError();
    void setPhoneNumberError();
    void setPasswordError();
    void setPasswordConfirmError();
    void setTermsConditionsError();
    void goToMain();
    void showUserAlreadyExist();
    void showAuthWeakPassword();

}
