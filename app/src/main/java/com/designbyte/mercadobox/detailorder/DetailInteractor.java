package com.designbyte.mercadobox.detailorder;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.models.db.AppDatabase;
import com.designbyte.mercadobox.models.firebase.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mx.openpay.android.Openpay;
import mx.openpay.android.OperationCallBack;
import mx.openpay.android.OperationResult;
import mx.openpay.android.exceptions.OpenpayServiceException;
import mx.openpay.android.exceptions.ServiceUnavailableException;
import mx.openpay.android.model.Card;
import mx.openpay.android.model.Token;
import mx.openpay.client.core.OpenpayAPI;

import static androidx.room.Room.databaseBuilder;

public class DetailInteractor {
    long maxId = 0;
    FirebaseAuth mAuth;
    private DatabaseReference orderHistory;
    private FirebaseDatabase database;
    private AppDatabase db;
    interface OnDetailListener {
        void onAddressError();
        void onDateError();
        void onTimeError();
        void onNameCardError();
        void onNumberCardError();
        void onLastNameError();
        void onMMCardError();
        void onAACardError();
        void onCVVCardError();
        void onAceptedError();
        void orderShipmentCompleted();
        void chargeCard(String address,String numberCard, String nameCard,
                        String lastNameCard, String monthExpirationCard,
                        String yearExpirationCard, String cvvCard , Context context);
    }

    void chargeCard(String address, String numberCard, String nameCard,
                    String lastNameCard, String monthExpirationCard,
                    String yearExpirationCard, String cvvCard , final Context context, OnDetailListener listener){



        Card card = new Card();
        card.cardNumber(numberCard);
        card.holderName(nameCard+" "+lastNameCard);
        card.expirationMonth(Integer.valueOf(monthExpirationCard));
        card.expirationYear(Integer.valueOf(yearExpirationCard));
        card.cvv2(cvvCard);
        OpenpayAPI openpayAPI = new OpenpayAPI(context.getString(R.string.location_url_openpay),context.getString(R.string.public_api_key_openpay),context.getString(R.string.merchant_id));


    }
    void sendClientsOrder(
            final String address, String date, String time, Boolean confirmationCall,
            String noteOrder, final String numberCard, final String nameCard,
            final String lastNameCard, final String monthExpirationCard,
            final String yearExpirationCard, final String cvvCard, Boolean accepted, final Context context, final OnDetailListener listener) {

        if (TextUtils.isEmpty(address)) {
            listener.onAddressError();
            return;
        }
        if (TextUtils.isEmpty(date)) {
            listener.onDateError();
            return;
        }
        if (TextUtils.isEmpty(time)) {
            listener.onTimeError();
            return;
        }
        if (!confirmationCall) {
        }
        if (TextUtils.isEmpty(noteOrder)) {
        }
        if (TextUtils.isEmpty(numberCard)) {
            listener.onNumberCardError();
            return;
        }
        if (TextUtils.isEmpty(nameCard)) {
            listener.onNameCardError();
            return;
        }
        if (TextUtils.isEmpty(lastNameCard)) {
            listener.onLastNameError();
            return;
        }
        if (TextUtils.isEmpty(monthExpirationCard)) {
            listener.onMMCardError();
            return;
        }
        if (TextUtils.isEmpty(yearExpirationCard)) {
            listener.onAACardError();
            return;
        }
        if (TextUtils.isEmpty(cvvCard)) {
            listener.onCVVCardError();
            return;
        }
        if (!accepted) {
            listener.onAceptedError();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        orderHistory = database.getReference("OrderHistory");
        orderHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    maxId = dataSnapshot.getChildrenCount();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        db = databaseBuilder(context,
                AppDatabase.class, "mbdb").allowMainThreadQueries().build();
        Order order = new Order();
        order.date = date;
        order.name = nameCard;
        order.status = 0;
        order.products = db.cartDao().getItemsCart();
        order.idOrder = Integer.valueOf(String.valueOf(maxId +1));
        orderHistory.child(String.valueOf(maxId + 1)).setValue(order)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    db.cartDao().deleteAll();
                    listener.chargeCard( address, numberCard,  nameCard,
                             lastNameCard,  monthExpirationCard,
                             yearExpirationCard,  cvvCard , context);
                }
            }
        });
    }


    public void onErrorException(OpenpayServiceException error, Context context) {
        error.printStackTrace();
        int desc = 0;
        switch( error.getErrorCode()){
            case 3001:
                desc = R.string.declined;
                break;
            case 3002:
                desc = R.string.expired;
                break;
            case 3003:
                desc = R.string.insufficient_funds;
                break;
            case 3004:
                desc = R.string.stolen_card;
                break;
            case 3005:
                desc = R.string.suspected_fraud;
                break;

            case 2002:
                desc = R.string.already_exists;
                break;
            default:
                desc = R.string.error_creating_card;
        }
        //DialogFragment fragment = MessageDialogFragment.newInstance(R.string.error, this.getString(desc));
        //fragment.show(this.getFragmentManager(), "Error");
        Log.e("DetailActivity","onError:"+context.getString(desc));
    }

}
