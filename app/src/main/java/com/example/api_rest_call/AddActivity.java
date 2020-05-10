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

public class AddActivity extends AppCompatActivity {

    EditText txtMarca, txtModelo;
    Button guardar;
    Auto miAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Agregar Auto");

        txtMarca = (EditText) findViewById(R.id.marca);
        txtModelo = (EditText) findViewById(R.id.modelo);
        guardar = (Button) findViewById(R.id.btnGuardar);


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                miAuto = new Auto(txtMarca.getText().toString(), txtModelo.getText().toString());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                AutoService autoService = retrofit.create(AutoService.class);
                Call<Void> http_call = autoService.addAuto(miAuto);

                http_call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Auto creado!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Hubo un error", Toast.LENGTH_LONG).show();
                    }
                });

            }

        });
    }

    public void cancelarOnClick(View view) {
        finish();
    }
}
