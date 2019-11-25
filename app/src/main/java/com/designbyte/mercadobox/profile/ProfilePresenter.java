package com.designbyte.mercadobox.profile;

import com.designbyte.mercadobox.models.firebase.User;

public class ProfilePresenter implements ProfileInteractor.OnChangeFinishedListener {

    private ProfileView profileView;
    private ProfileInteractor profileInteractor;

    public ProfilePresenter(ProfileView profileView, ProfileInteractor profileInteractor) {
        this.profileView = profileView;
        this.profileInteractor = profileInteractor;
    }

    public void updateProfile(String name, String lastname, String email, String phoneNumber, String address, String responsable){
        if(profileView != null){
            profileView.showProgress();
        }
        profileInteractor.updateProfile(name,lastname,email,phoneNumber, address, responsable,this);
    }
    public void getDataUser(){
        profileInteractor.setDataProfile(this);
    }

    public void changePassword(String email,String oldPassword, String password, String passwordConfirmation){
        if(profileView != null){
            profileInteractor.changePassword(email,oldPassword,password,passwordConfirmation,this);
            profileView.showProgress();
        }
    }
    @Override
    public void onNameError() {
        if(profileView != null){
            profileView.setNameError();
            profileView.hideProgress();
        }
    }

    @Override
    public void onLastNameError() {
        if(profileView != null){
            profileView.setLastNameError();
            profileView.hideProgress();
        }
    }

    @Override
    public void onEmailError() {
        if(profileView != null){
            profileView.setEmailError();
            profileView.hideProgress();
        }
    }

    @Override
    public void onPhoneNumberError() {
        if(profileView != null){
            profileView.setPhoneNumberError();
            profileView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if(profileView != null){
            profileView.setPasswordError();
            profileView.hideProgress();
        }
    }

    @Override
    public void onPasswordConfirmError() {
        if(profileView != null){
            profileView.setPasswordConfirmError();
            profileView.hideProgress();
        }
    }

    @Override
    public void setPasswordNotEqualsError() {
        if(profileView != null){
            profileView.setPasswordNotEqualsError();
            profileView.hideProgress();
        }
    }

    @Override
    public void setDataUser(User user) {
        if(profileView != null){
            profileView.hideProgress();
            profileView.setDataUser(user);
        }
    }

    @Override
    public void onSuccess() {
        if(profileView != null){
            profileView.hideProgress();
            profileView.savedChanges();
        }
    }

    @Override
    public void onSuccessPasswordChangues() {
        if(profileView != null){
            profileView.hideProgress();
            profileView.savedPasswordChanges();
        }
    }


    public void onDestroy(){
        profileView = null;
    }

}
