package com.designbyte.mercadobox.signin;

public class SigninPresenter implements SigninInteractor.OnSigninFinishedListener{

    private SigninView signinView;
    private SigninInteractor signinInteractor;

    SigninPresenter(SigninView signinView, SigninInteractor signinInteractor){
        this.signinView = signinView;
        this.signinInteractor = signinInteractor;
    }

    public void signinProcess(String name, String lastname, String email, String phone, String password, String passwordConfirm, Boolean termsConditions){
        if (signinView != null){
            signinView.showProgress();
        }
        signinInteractor.signin(name,lastname,email,phone,password, passwordConfirm, termsConditions,this);
    }
    @Override
    public void onNameError() {
        if(signinView != null){
            signinView.setNameError();
            signinView.hideProgress();
        }
    }

    @Override
    public void onLastNameError() {
        if(signinView != null){
            signinView.setLastNameError();
            signinView.hideProgress();
        }
    }

    @Override
    public void onEmailError() {
        if(signinView != null){
            signinView.setEmailError();
            signinView.hideProgress();
        }
    }

    @Override
    public void onPhoneNumberError() {
        if(signinView != null){
            signinView.setPhoneNumberError();
            signinView.hideProgress();

        }
    }

    @Override
    public void onPasswordError() {
        if(signinView != null){
            signinView.setPasswordError();
            signinView.hideProgress();

        }
    }

    @Override
    public void onPasswordConfirmError() {
        if(signinView != null){
            signinView.setPasswordConfirmError();
            signinView.hideProgress();
        }
    }

    @Override
    public void setPasswordNotEqualsError() {
        if(signinView != null){
            signinView.setPasswordNotEqualsError();
            signinView.hideProgress();
        }
    }

    @Override
    public void onTermsConditions() {
        if(signinView != null){
            signinView.setTermsConditionsError();
            signinView.hideProgress();
        }
    }

    @Override
    public void onWeakPassword() {
        if(signinView != null){
            signinView.showAuthWeakPassword();
            signinView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if(signinView != null){
            signinView.hideProgress();
            signinView.goToMain();
        }
    }

    @Override
    public void onUserAlreadyExist() {
        if(signinView != null){
            signinView.hideProgress();
            signinView.showUserAlreadyExist();
        }
    }

    public void onDestroy(){
        signinView = null;
    }
}
