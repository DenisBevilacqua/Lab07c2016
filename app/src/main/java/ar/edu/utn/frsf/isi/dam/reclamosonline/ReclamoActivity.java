package ar.edu.utn.frsf.isi.dam.reclamosonline;

        import android.app.Activity;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v4.app.ActivityCompat;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import java.util.ArrayList;
        import java.util.List;

        import ar.edu.utn.frsf.isi.dam.R;
        import ar.edu.utn.frsf.isi.dam.reclamosonline.model.Reclamo;

public class ReclamoActivity extends AppCompatActivity implements OnMapReadyCallback{
    private GoogleMap myMap;
    private static final Integer CODIGO_RESULTADO_ALTA_RECLAMO = 999;

    private List<Reclamo> reclamos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //reclamos = new ArrayList<>();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap=googleMap;
        iniciarMapa();
    }

    private void iniciarMapa(){
        // COMPLETAR
    }

    private void buscarCercanos(Integer kmCerca) {
        // COMPLETAR
        Toast.makeText(this," Mostrar a "+kmCerca+ " KMs",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // COMPLETAR

    }

    private void mostrarDialogo(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //builder.setView(R.layout.alert_distancia_busqueda);
        builder.setPositiveButton("Buscar reclamos", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Dialog d = (Dialog)dialog;
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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
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

}