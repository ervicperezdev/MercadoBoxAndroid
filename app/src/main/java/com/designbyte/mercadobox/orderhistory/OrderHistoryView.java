package com.designbyte.mercadobox.orderhistory;

import com.designbyte.mercadobox.models.firebase.Order;
import java.util.List;

public interface OrderHistoryView {
    void showProgress();
    void hideProgress();
    void loadOrderHistory(List<Order> items);
}
