package com.designbyte.mercadobox.splash;


import android.content.Context;

public class SplashPresenter implements SplashInteractor.OnSessionListener {
    SplashView splashView;
    SplashInteractor splashInteractor;
    Context context;

    public SplashPresenter(SplashView splashView, SplashInteractor splashInteractor, Context context) {
        this.splashView = splashView;
        this.splashInteractor = splashInteractor;
        this.context = context;
    }

    public void onLogged(){
        if(splashView != null){

        }
        splashInteractor.sessionActived(this,this.context);

    }
    @Override
    public void onSuccess() {
        if(splashView != null){
            splashView.onSuccess();
        }
    }

    @Override
    public void onFailure() {
        if(splashView != null){
            splashView.onFailure();
        }

    }

    public void onDestroy(){
        splashView = null;
    }
}
