package com.designbyte.mercadobox.detailorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.designbyte.mercadobox.R;
import com.designbyte.mercadobox.adapter.CommonPagerAdapter;
import com.designbyte.mercadobox.detailorder.helper.LockableViewPager;

import java.util.Calendar;

import static com.designbyte.mercadobox.utils.Constants.BARRA;
import static com.designbyte.mercadobox.utils.Constants.CERO;
import static com.designbyte.mercadobox.utils.Constants.DOS_PUNTOS;

public class DetailActivity extends AppCompatActivity {
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


    TextView numCard, nameCard, lastNameCard, monthExpiration, yearExpiration, cvvCard;
    EditText totalCart;

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    //Variables para obtener la hora hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
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
}
