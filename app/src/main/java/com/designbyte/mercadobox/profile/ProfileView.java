package com.designbyte.mercadobox.profile;

import com.designbyte.mercadobox.models.firebase.User;

public interface ProfileView {
    void showProgress();
    void hideProgress();
    void setNameError();
    void setLastNameError();
    void setEmailError();
    void setPhoneNumberError();
    void setPasswordError();
    void setPasswordConfirmError();
    void setPasswordNotEqualsError();
    void setDataUser(User user);
    void savedChanges();
}
