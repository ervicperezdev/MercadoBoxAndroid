package com.designbyte.mercadobox.orderhistory;

import com.designbyte.mercadobox.models.firebase.Order;

import java.util.List;

public class OrderHistoryPresenter implements OrderHistoryInteractor.OnOrderHistoryListener {
    OrderHistoryView orderHistoryView;
    OrderHistoryInteractor orderHistoryInteractor;

    public OrderHistoryPresenter(OrderHistoryView orderHistoryView, OrderHistoryInteractor orderHistoryInteractor) {
        this.orderHistoryView = orderHistoryView;
        this.orderHistoryInteractor = orderHistoryInteractor;
    }

    @Override
    public void setDataOrder(List<Order> items) {
        if(orderHistoryView != null){
            orderHistoryView.hideProgress();
            orderHistoryView.loadOrderHistory(items);
        }
    }

    public void loadDataOrderHistory(){
        if(orderHistoryView != null){
            orderHistoryView.showProgress();
        }
        orderHistoryInteractor.getDataOrders(this);
    }


}
