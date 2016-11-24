package ar.edu.utn.frsf.isi.dam.reclamosonline;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

import ar.edu.utn.frsf.isi.dam.AltaReclamoActivity;
import ar.edu.utn.frsf.isi.dam.R;
import ar.edu.utn.frsf.isi.dam.reclamosonline.model.Reclamo;

public class ReclamoActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private GoogleMap myMap;
    private static final Integer CODIGO_RESULTADO_ALTA_RECLAMO = 999;

    private List<Reclamo> reclamos;
    List<Polyline> polylines = new ArrayList<Polyline>();

    WeakHashMap<Marker, Object> haspMap = new WeakHashMap<Marker, Object>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        reclamos = new ArrayList<Reclamo>();


        //reclamos = new ArrayList<>();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        myMap.setOnMapLongClickListener(this);
        iniciarMapa();

        myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(final Marker marker) {

                Log.d("", marker.getTitle());

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ReclamoActivity.this);
                builder.setTitle("Buscar reclamos");

                final EditText input = new EditText(ReclamoActivity.this);

                input.setInputType(InputType.TYPE_CLASS_TEXT);

                input.setHint("Cantidad de km");

                /*final TextView textview = new TextView(ProyectosActivity.this);
                textview.setText("Ingrese descripción");
                builder.setCustomTitle(textview);*/

                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for(Polyline line : polylines)
                        {
                            line.remove();
                        }

                        polylines.clear();

                        //myMap.clear();

                        // Debemos buscar los reclamos cercanos y si es menor a la distancia indicada por el usuario
                        // Crear una polilinea

                        String km = input.getText().toString();

                        Location locationA = new Location("point A");

                        // Reclamo rec = (Reclamo) haspMap.get(marker);

                        Reclamo rec = (Reclamo) marker.getTag();

                        locationA.setLatitude(rec.getLatitud());
                        locationA.setLongitude(rec.getLongitud());

                        Log.d("Antes del foreach",rec.getTitulo());

                        for (Reclamo recActual : reclamos) {

                            Log.d("Reclamo",recActual.getTitulo());

                            if (!recActual.equals(rec)) {

                                Log.d("Reclamo paso if ",recActual.getTitulo());

                                Location locationB = new Location(recActual.getTitulo());

                                locationB.setLatitude(recActual.getLatitud());
                                locationB.setLongitude(recActual.getLongitud());

                                float distance = locationA.distanceTo(locationB);

                                Log.d("Distancia ",distance+"");

                                if(distance <= Float.parseFloat(km)*1000.0){

                                    Log.d("Entro al if de la poly",distance+"");

                                    // Dibujar polilineas.

                                    Polyline line = myMap.addPolyline(new PolylineOptions()
                                            .add(new LatLng(locationA.getLatitude(),locationA.getLongitude()), new LatLng(locationB.getLatitude(),locationB.getLongitude()))
                                            .width(5)
                                            .color(Color.RED));

                                    polylines.add(line);

                                }

                            }

                        }

                        Location locationB = new Location("point B");

                        /*locationB.setLatitude(latB);
                        locationB.setLongitude(lngB);

                        float distance = locationA.distanceTo(locationB);*/


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    private void iniciarMapa() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                myMap.setMyLocationEnabled(true);

            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            }
        }

        myMap.setMyLocationEnabled(true);

        //myMap.setMyLocationEnabled(true);
        // COMPLETAR
    }

    private void buscarCercanos(Integer kmCerca) {
        // COMPLETAR
        Toast.makeText(this, " Mostrar a " + kmCerca + " KMs", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Luego de completar un alta reclamo activity

        Bundle extras = data.getExtras();

        Reclamo rec = (Reclamo) extras.get("result");

        LatLng point = new LatLng(rec.getLatitud(), rec.getLongitud());

        // Agregamos el reclamo a la lista de reclamos

        reclamos.add(rec);

        // Agregamos el marcador.

        Marker m = myMap.addMarker(new MarkerOptions().position(point).title("Descripcion: " + rec.getTitulo()).snippet(point.toString()));

        // Agregamos el objeto reclamo al marcador

        m.setTag(rec);

        //haspMap.put(m, rec);
    }

    private void mostrarDialogo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        //builder.setView(R.layout.alert_distancia_busqueda);
        builder.setPositiveButton("Buscar reclamos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Dialog d = (Dialog) dialog;
                // EditText et = (EditText) d.findViewById(R.id.distanciaReclamo);
                // Log.d(":::Reclamo","Reclamos a distancia...."+et.getText().toString());
                // USAR EL DATO e invocar a buscarCercanos
                //  buscarCercanos(Integer.parseInt(et.getText().toString()));
            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_reclamo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    myMap.setMyLocationEnabled(true);

                } else {
                    Toast.makeText(this, "No permission for contacts", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }

    @Override
    public void onMapClick(LatLng point) {
        //tvLocInfo.setText(point.toString());
        myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
    }

    @Override
    public void onMapLongClick(LatLng point) {
        //tvLocInfo.setText("New marker added@" + point.toString());

        Intent i = new Intent(ReclamoActivity.this,
                AltaReclamoActivity.class);
        i.putExtra("coordenadas", point);
        startActivityForResult(i, CODIGO_RESULTADO_ALTA_RECLAMO);

        //myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));
    }

}