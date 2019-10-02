package com.designbyte.mercadobox.detailorder;

public interface DetailView {
    void showProgress();
    void hideProgress();
    void setAddressError();
    void setNameCardError();
    void setNumberCardError();
    void setLastNameError();
    void setMMCardError();
    void setAACardError();
    void setCVVCardError();
    void setAceptedError();
    void onOrderShipmentCompleted();

    void showMessage(String str);
}
