package com.designbyte.mercadobox.detailorder;

import android.content.Context;

public class DetailPresenter implements DetailInteractor.OnDetailListener {

    DetailView detailView;
    DetailInteractor detailInteractor;

    public DetailPresenter(DetailView detailView, DetailInteractor detailInteractor) {
        this.detailView = detailView;
        this.detailInteractor = detailInteractor;
    }

    public void sendClientsOrder(String address, String date, String time, Boolean confirmationCall,
                                 String noteOrder, String numberCard, String nameCard,
                                 String lastNameCard, String monthExpirationCard,
                                 String yearExpirationCard, String cvvCard, Boolean accepted, Context context){
        if(detailView != null){
            detailView.showProgress();
            detailInteractor.sendClientsOrder(address,date,time,confirmationCall, noteOrder, numberCard, nameCard, lastNameCard, monthExpirationCard, yearExpirationCard, cvvCard,accepted,context, this);
        }
    }
    @Override
    public void onAddressError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setAddressError();
        }
    }

    @Override
    public void onDateError() {
        if(detailView != null){

        }
    }

    @Override
    public void onTimeError() {
        if(detailView != null){

        }
    }

    @Override
    public void onNameCardError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setNameCardError();
        }
    }

    @Override
    public void onNumberCardError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setNumberCardError();
        }
    }

    @Override
    public void onLastNameError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setLastNameError();
        }
    }

    @Override
    public void onMMCardError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setMMCardError();
        }
    }

    @Override
    public void onAACardError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setAACardError();
        }
    }

    @Override
    public void onCVVCardError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setCVVCardError();
        }
    }

    @Override
    public void onAceptedError() {
        if (detailView != null){
            detailView.hideProgress();
            detailView.setAceptedError();
        }
    }

    @Override
    public void orderShipmentCompleted() {
        if(detailView != null){
            detailView.onOrderShipmentCompleted();
        }
    }

    @Override
    public void chargeCard(String address, String numberCard, String nameCard, String lastNameCard, String monthExpirationCard, String yearExpirationCard, String cvvCard, Context context) {
        if(detailView != null){
            detailInteractor.chargeCard(address,numberCard,nameCard,lastNameCard,monthExpirationCard,yearExpirationCard,cvvCard,context,this);
            detailView.hideProgress();
        }
    }
}
