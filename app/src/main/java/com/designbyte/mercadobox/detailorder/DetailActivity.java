package com.designbyte.mercadobox.detailorder;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.adapter.CommonPagerAdapter;
import com.designbyte.mercadobox.detailorder.helper.LockableViewPager;
import java.util.Calendar;
import mx.openpay.android.Openpay;
import mx.openpay.android.OperationCallBack;
import mx.openpay.android.OperationResult;
import mx.openpay.android.exceptions.OpenpayServiceException;
import mx.openpay.android.exceptions.ServiceUnavailableException;
import mx.openpay.android.model.Card;
import mx.openpay.android.model.Token;
import static com.designbyte.mercadobox.utils.Constants.BARRA;
import static com.designbyte.mercadobox.utils.Constants.CERO;
import static com.designbyte.mercadobox.utils.Constants.DOS_PUNTOS;

public class DetailActivity extends AppCompatActivity implements DetailView{
    LockableViewPager viewPager;
    CommonPagerAdapter commonPagerAdapter;
    ImageView previous, next;
    TextView textPrevious, textNext;
    TextView textDate, textTime;
    ImageView btnDate, btnTime;
    TextView tvAddress;
    EditText etAddress;
    CheckBox checkboxConfirmOrder;
    EditText noteOrder;
    EditText numberCard, nameCard, lastNameCard, monthExpirationCard, yearExpirationCard, cvvCard;
    TextView totalCart;
    CheckBox checkboxTermsConditions;
    ProgressBar progressBar;
    Button btnSendOrder;
    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();
    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);
    DetailPresenter detailPresenter;
    DetailInteractor detailInteractor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
        detailInteractor = new DetailInteractor();
        detailInteractor.activity = this;
        detailInteractor.context = this;
        detailPresenter = new DetailPresenter(this,detailInteractor);
        commonPagerAdapter = new CommonPagerAdapter();
        commonPagerAdapter.insertViewId(R.id.firstPage);
        commonPagerAdapter.insertViewId(R.id.secondPage);
        viewPager.setAdapter(commonPagerAdapter);

        showNext();
        viewPager.setSwipeLocked(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItemofviewpager(+1), true);
                showPrevious();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(getItemofviewpager(-1), true);
                showNext();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha();
            }
        });
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerHora();
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvAddress.setText(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder();
            }
        });
    }

    public void showPrevious(){
        textNext.setVisibility(View.GONE);
        next.setVisibility(View.GONE);
        previous.setVisibility(View.VISIBLE);
        textPrevious.setVisibility(View.VISIBLE);
    }
    public void showNext(){
        textNext.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        previous.setVisibility(View.GONE);
        textPrevious.setVisibility(View.GONE);
    }

    public void initViews(){
        viewPager = findViewById(R.id.viewPager);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);
        textDate = findViewById(R.id.textDate);
        textTime = findViewById(R.id.textTime);
        btnDate = findViewById(R.id.btnDate);
        btnTime = findViewById(R.id.btnTime);
        textNext = findViewById(R.id.textNext);
        textPrevious = findViewById(R.id.textPrevious);
        etAddress = findViewById(R.id.etAddress);
        tvAddress = findViewById(R.id.tvAddress);
        checkboxConfirmOrder = findViewById(R.id.checkboxConfirmOrder);
        noteOrder = findViewById(R.id.noteOrder);

        numberCard = findViewById(R.id.numCard);
        nameCard = findViewById(R.id.nameCard);
        lastNameCard = findViewById(R.id.lastNameCard);
        monthExpirationCard = findViewById(R.id.monthExpiration);
        yearExpirationCard = findViewById(R.id.yearExpiration);
        cvvCard = findViewById(R.id.cvvCard);
        totalCart = findViewById(R.id.totalCart);
        checkboxTermsConditions = findViewById(R.id.checkboxTermsConditions);

        progressBar = findViewById(R.id.progressBar);
        btnSendOrder = findViewById(R.id.btnSendOrder);
    }
    private int getItemofviewpager(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                textDate.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que usted desee
             */
        },anio, mes, dia);
        recogerFecha.getDatePicker().setMinDate(System.currentTimeMillis()+ 86400000);
        //Muestro el widget
        recogerFecha.show();

    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf(CERO + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                textTime.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);

        recogerHora.show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setAddressError() {
        etAddress.setError("Ingresa una dirección");
        viewPager.setCurrentItem(getItemofviewpager(-1), true);
        showNext();
    }

    @Override
    public void setNameCardError() {
        nameCard.setError("Ingresa nombre de titular");
        viewPager.setCurrentItem(getItemofviewpager(+1), true);
        showPrevious();
    }

    @Override
    public void setNumberCardError() {
        numberCard.setError("Ingresa una tarjeta valida");
        viewPager.setCurrentItem(getItemofviewpager(+1), true);
        showPrevious();
    }

    @Override
    public void setLastNameError() {
        lastNameCard.setError("Ingresa el apellido");
        viewPager.setCurrentItem(getItemofviewpager(+1), true);
        showPrevious();
    }

    @Override
    public void setMMCardError() {
        monthExpirationCard.setError("Mes de expiración");
        viewPager.setCurrentItem(getItemofviewpager(+1), true);
        showPrevious();
    }

    @Override
    public void setAACardError() {
        yearExpirationCard.setError("Año de expiración");
        viewPager.setCurrentItem(getItemofviewpager(+1), true);
        showPrevious();
    }

    @Override
    public void setCVVCardError() {
        cvvCard.setError("Código de seguridad");
        viewPager.setCurrentItem(getItemofviewpager(+1), true);
        showPrevious();
    }

    @Override
    public void setAceptedError() {
        checkboxTermsConditions.setError("Debes de aceptar los términos y condiciones");
        viewPager.setCurrentItem(getItemofviewpager(+1), true);
        showPrevious();
    }

    @Override
    public void onOrderShipmentCompleted() {
        finish();
    }

    @Override
    public void showMessage(String str) {
        Toast.makeText(this,str,Toast.LENGTH_LONG).show();
    }

    public void sendOrder(){
        pay();
        detailPresenter.sendClientsOrder(etAddress.getText().toString(),textDate.getText().toString(),textTime.getText().toString(),checkboxConfirmOrder.isChecked(),noteOrder.getText().toString(),numberCard.getText().toString(),nameCard.getText().toString(),lastNameCard.getText().toString(),monthExpirationCard.getText().toString(),yearExpirationCard.getText().toString(),cvvCard.getText().toString(),checkboxTermsConditions.isChecked(),getApplicationContext());
    }

    public void pay(){


    }

}
