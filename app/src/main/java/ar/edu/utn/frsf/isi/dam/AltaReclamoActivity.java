package ar.edu.utn.frsf.isi.dam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import ar.edu.utn.frsf.isi.dam.reclamosonline.model.Reclamo;

public class AltaReclamoActivity extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnAgregar;
    private EditText txtDescripcion;
    private EditText txtMail;
    private EditText txtTelefono;
    private LatLng ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        ubicacion = (LatLng) extras.get("coordenadas");
        setContentView(R.layout.activity_alta_reclamo);
        btnAgregar = (Button) findViewById(R.id.btnReclamar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        txtDescripcion = (EditText) findViewById(R.id.reclamoTexto);
        txtTelefono= (EditText) findViewById(R.id.reclamoTelefono);
        txtMail= (EditText) findViewById(R.id.reclamoMail);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = getIntent().getExtras();
                LatLng ubicación = (LatLng) extras.get("coordenadas");

                Reclamo rec = new Reclamo();

                //setear en reclos valores (latitud, longitud, texto, tel, mail
                rec.setLatitud(ubicación.latitude);
                rec.setLongitud(ubicación.longitude);
                rec.setEmail(txtMail.getText().toString());
                rec.setTelefono(txtTelefono.getText().toString());
                rec.setTitulo(txtDescripcion.getText().toString());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",rec);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // COMPLETAR
            }
        });
    }


}