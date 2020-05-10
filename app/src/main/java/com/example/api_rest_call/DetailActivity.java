package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    EditText txtMarca, txtModelo;
    Button editar,eliminar,volver;
    String idAuto, marca, modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("Detalle Auto");

        txtMarca = (EditText) findViewById(R.id.marca);
        txtModelo  = (EditText) findViewById(R.id.modelo);
        editar = (Button) findViewById(R.id.btnEditar);
        eliminar =(Button) findViewById(R.id.btnEliminar);
        volver = (Button) findViewById(R.id.btnVolver);

        //recibo el ID de mi Auto
        Bundle miBundle = getIntent().getExtras();
        idAuto = miBundle.getString("id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final AutoService autoService = retrofit.create(AutoService.class);
        Call<Auto> http_call = autoService.getAuto(idAuto);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Auto miAuto = response.body();
                //setteo valores del auto
                txtMarca.setText(miAuto.getMarca());
                txtModelo.setText(miAuto.getModelo());
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);


            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<Void> http_call = autoService.deleteAuto(idAuto);
                http_call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Auto eliminado!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marca = txtMarca.getText().toString();
                modelo = txtModelo.getText().toString();

                Call<Void> http_call = autoService.saveAuto(idAuto,marca,modelo);
                Log.i("DATOS", idAuto+"-"+marca+"-"+modelo);
                http_call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Auto editado!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable e) {
                        Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_LONG).show();                    }
                });
            }

        });

    }

    public void cancelarOnClick(View view) {
        finish();
    }
}
