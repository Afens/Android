package afens.pr008cardview;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final float D_CUENTA = 0.0f;
    private final int D_PORCENTAJE = 2;
    private final int D_COMENSALES = 2;


    private TextView lblCuenta;
    private TextView lblPorcentaje;
    private TextView lblComensales;
    private EditText txtCuenta;
    private EditText txtPorcentaje;
    private EditText txtPropina;
    private EditText txtTotal;
    private EditText txtComensales;
    private EditText txtTotalComensales;
    private Button btnTotal;
    private Button btnLimpiarTotal;
    private Button btnComensales;
    private Button btnLimpiarComensales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        lblCuenta = (TextView) findViewById(R.id.lblCuenta);
        lblPorcentaje = (TextView) findViewById(R.id.lblPorcentaje);
        lblComensales = (TextView) findViewById(R.id.lblComensales);
        txtCuenta = (EditText) findViewById(R.id.txtCuenta);
        txtPorcentaje = (EditText) findViewById(R.id.txtPorcentaje);
        txtPropina = (EditText) findViewById(R.id.txtPropina);
        txtTotal = (EditText) findViewById(R.id.txtTotal);
        txtComensales = (EditText) findViewById(R.id.txtComensales);
        txtTotalComensales = (EditText) findViewById(R.id.txtTotalComensales);
        btnTotal = (Button) findViewById(R.id.btnTotal);
        btnLimpiarTotal = (Button) findViewById(R.id.btnLimpiarTotal);
        btnComensales = (Button) findViewById(R.id.btnComensales);
        btnLimpiarComensales = (Button) findViewById(R.id.btnLimpiarComensales);
        txtCuenta.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblCuenta, hasFocus);
            }
        });
        txtCuenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()))
                    txtCuenta.setText(String.format("%.2f", D_CUENTA));
                calcular();
            }
        });
        txtPorcentaje.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblPorcentaje, hasFocus);
            }
        });
        txtPorcentaje.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()))
                    txtPorcentaje.setText(String.format("%d", D_PORCENTAJE));
                calcular();
            }
        });
        txtComensales.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setColorSegunFoco(lblComensales, hasFocus);
            }
        });
        txtComensales.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString()) || Integer.parseInt(s.toString()) == 0)
                    txtComensales.setText(String.format("%d", D_COMENSALES));
                calcularComensales();
            }
        });
        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redondear();
            }
        });
        btnLimpiarTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarTotal();
            }
        });
        btnComensales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redondearCom();
            }
        });
        btnLimpiarComensales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limpiarCom();
            }
        });

        limpiarTotal();
        limpiarCom();
    }

    private void limpiarCom() {
        txtComensales.setText(String.format("%d",D_COMENSALES));
    }

    private void redondearCom() {
        float total= Float.parseFloat(txtTotalComensales.getText().toString());
        float redondeo=(float) Math.floor(total);
        if(redondeo!=total)
            redondeo+=1;

        txtTotalComensales.setText(String.format("%.2f",redondeo));
    }

    private void limpiarTotal() {
        txtCuenta.setText(String.format("%.2f",D_CUENTA));
        txtPorcentaje.setText(String.format("%d",D_PORCENTAJE));
    }

    private void redondear() {
        float total= Float.parseFloat(txtTotal.getText().toString());
        float redondeo=(float) Math.floor(total);
        if(redondeo !=total)
            redondeo+=1;

        txtTotal.setText(String.format("%.2f",redondeo));

        calcularComensales();
    }

    private void calcular() {
        float cuenta= Float.parseFloat(txtCuenta.getText().toString());
        float porcentaje= Integer.parseInt(txtPorcentaje.getText().toString());
        float propina=cuenta*(porcentaje/100);
        float total=cuenta+propina;

        txtPropina.setText(String.format("%.2f",propina));
        txtTotal.setText(String.format("%.2f",total));
        calcularComensales();
    }
    private void calcularComensales() {
        float total= Float.parseFloat(txtTotal.getText().toString());
        float comensales= Integer.parseInt(txtComensales.getText().toString());
        float totalComensales=total/comensales;

        txtTotalComensales.setText(String.format("%.2f",totalComensales));
    }

    // Establece el color y estilo del TextView dependiendo de si el
    // EditText correspondiente tiene el foco o no.
    private void setColorSegunFoco(TextView lbl, boolean hasFocus) {
        if (hasFocus) {
            lbl.setTextColor(getResources().getColor(R.color.accent));
            lbl.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            lbl.setTextColor(getResources().getColor(R.color.primary_text));
            lbl.setTypeface(Typeface.DEFAULT);
        }
    }


}
