package com.developer.milla.guerrero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalleslActivity extends AppCompatActivity {

    TextView tvid,tvname,tvemail,tvcontact,tvaddress;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //Initializing Views
        tvid = findViewById(R.id.txtid);
        tvname = findViewById(R.id.txtname);
        tvemail = findViewById(R.id.txtemail);
        //tvcontact = findViewById(R.id.txcontact);
        tvaddress = findViewById(R.id.txtaddress);

        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");

        tvid.setText("ID: "+MainActivity.users.get(position).getId());
        tvname.setText("Nombre: "+MainActivity.users.get(position).getName());
        tvemail.setText("Email: "+MainActivity.users.get(position).getEmail());
        //tvcontact.setText("Contacto: "+MainActivity.users.get(position).getPassword());
        tvaddress.setText("Edad: "+MainActivity.users.get(position).getAge());





    }
}
