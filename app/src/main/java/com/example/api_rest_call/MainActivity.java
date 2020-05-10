package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ListAdapter adaptador;
    ArrayList<String> autos = new ArrayList<>();
    //para tener lista de mis autos
    ArrayList<Auto> arraysAutos = new ArrayList<>();
    Button btnAgregar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Listado de Autos");

        btnAgregar= (Button) findViewById(R.id.bntAgregar);


        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autos);
        list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adaptador);
        this.getListadoVehiculos();

    }
    //por donde voy a mandar el id del auto en la posicion que yo haga clik
    protected void onListItemClick(ListView l, View v, int position, long id){
        //super.onListItemClick(l,v,position,id);
        Intent miIntent = new Intent(MainActivity.this, DetailActivity.class);
        miIntent.putExtra("id",arraysAutos.get(position).getId());
        startActivity(miIntent);
    }


    public void getListadoVehiculos(){

        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);
        Call<List<Auto>> http_call = autoService.getAutos();
        http_call.enqueue(new Callback<List<Auto>>() {
            @Override
            public void onResponse(Call<List<Auto>> call, Response<List<Auto>> response) {
                // Si el servidor responde correctamente puedo hacer uso de la respuesta esperada:
                autos.clear();

                for (Auto auto: response.body()){
                    autos.add(auto.getMarca() + " - " + auto.getModelo());
                }
                // Aviso al base adapter que cambio mi set de datos.
                // Renderizacion general de mi ListView
                ((BaseAdapter) adaptador).notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Auto>> call, Throwable t) {
                // SI el servidor o la llamada no puede ejecutarse, muestro un mensaje de eror:
                Toast.makeText(getApplicationContext(),"Hubo un error con la API", Toast.LENGTH_LONG);

            }
        });

    }


    public void onClick(View view) {
        Intent miIntent= null;
        switch (view.getId()) {
            case R.id.bntAgregar:
                miIntent = new Intent(MainActivity.this, AddActivity.class);

        }
        if (miIntent != null) {
            startActivity(miIntent);
        }
    }
}
