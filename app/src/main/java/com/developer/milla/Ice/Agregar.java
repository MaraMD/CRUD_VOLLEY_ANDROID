package com.developer.milla.Ice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.developer.milla.guerrero.R;

import java.util.HashMap;
import java.util.Map;

public class Agregar extends AppCompatActivity {

    EditText txtName,txtEmail,txtContact, txtAge,pass,distrit;
    Button btn_insert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__data_);

        txtName     = findViewById(R.id.edtName);
        txtEmail    = findViewById(R.id.edtEmail);
        txtAge = findViewById(R.id.edtAddress);
        btn_insert = findViewById(R.id.btnInsert);
        pass = findViewById(R.id.edtPassword);


        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });
    }

    private void insertData() {

        final String nombre = txtName.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();

        final String age = txtAge.getText().toString().trim();
        final String password = pass.getText().toString().trim();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando...");

        if(nombre.isEmpty()){


            txtName.setError("complete los campos");
            return;
        }
        else if(email.isEmpty()){

            txtEmail.setError("complete los campos");
            return;
        }
        else if(age.isEmpty()){
            txtAge.setError("complete los campos");
            return;
        }

        else{
                progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.71:4000/register",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Agregar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("name",nombre);
                    params.put("email",email);
                    params.put("password",password);
                    params.put("age",age);




                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(Agregar.this);
            requestQueue.add(request);



        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void limpiar(){
        Intent intent=new Intent(Agregar.this,MainActivity.class);
        startActivity(intent);
        txtName.setText("");
        txtContact.setText("");
        txtEmail.setText("");
        txtAge.setText("");
    }
}
