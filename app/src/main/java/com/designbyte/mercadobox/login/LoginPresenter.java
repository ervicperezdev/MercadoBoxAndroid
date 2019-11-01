package com.designbyte.mercadobox.login;

public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener{

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    LoginPresenter(LoginView loginView, LoginInteractor loginInteractor){
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    public void validateCredentials(String username, String password){
        if(loginView != null){
            loginView.showProgress();
        }
        loginInteractor.login(username,password,this);
    }
    public void forgotPassword(String email){
        if(loginView != null){
            loginInteractor.forgotPassword(email,this);
            loginView.showProgress();
        }
    }
    public void onDestroy(){
        loginView = null;
    }

    @Override
    public void onUsernameError() {
        if (loginView != null){
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null){
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onSuccess() {
        if(loginView != null){
            loginView.goToMain();
        }
    }

    @Override
    public void onFailure() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.showUserOrPasswordNotCorret();
        }
    }

    @Override
    public void setEmailToResetSuccess(String email) {
        if(loginView != null){
            loginView.hideProgress();
            loginView.emailToResetPasswordSent(email);
        }
    }

    @Override
    public void setEmailToResetError(String messageError) {
        if(loginView != null){
            loginView.hideProgress();
            loginView.messageResetPasswordError(messageError);
        }
    }

    @Override
    public void onEmailError() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.setEmailError();
        }
    }
}
