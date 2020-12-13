package com.developer.milla.Ice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class Editar extends AppCompatActivity {

    EditText edId,edName,edContact,edEmail,edAddress;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);

        edId = findViewById(R.id.ed_id);
        edName = findViewById(R.id.ed_name);
        //edContact = findViewById(R.id.ed_contact);
        edEmail = findViewById(R.id.ed_email);
        edAddress = findViewById(R.id.ed_address);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");


        edId.setText(MainActivity.users.get(position).getId());
        edName.setText(MainActivity.users.get(position).getName());
        edEmail.setText(MainActivity.users.get(position).getEmail());
        //edContact.setText(MainActivity.users.get(position).getPassword());
        edAddress.setText(MainActivity.users.get(position).getAge());
        System.out.println(MainActivity.users.get(position).getPassword());



    }

    public void btn_updateData(View view) {

        final String name = edName.getText().toString();
        final String email = edEmail.getText().toString();
        //final String contacto = edContact.getText().toString();
        final String age = edAddress.getText().toString();
        //final String id = edId.getText().toString();





        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.PUT, "http://192.168.1.71:4000/user/"+edId.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Editar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxNywibmFtZSI6Im1hcmEiLCJhZ2UiOjEyMSwiZW1haWwiOiJtYXJhQHBydWViYS5jb20iLCJwYXNzd29yZCI6IiQyYiQxMCRCR3RNWDJ2LlE0WEdKSkFjd3RrR0kuRzBkNWlDYzVsMWgxbHBIWEdUL21LVzBpTzRGQWxteSJ9LCJpYXQiOjE2MDc1ODY1ODIsImV4cCI6MTYwNzc1OTM4Mn0.INpwMXJrEL3A1DhCiOzYLVC5P1YdMiUzUpbVro7eLc0");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                //params.put("id",id);
                params.put("name",name);
                params.put("email",email);
                params.put("age",age);


                //params.put("password", MainActivity.users.get(position).getPassword());

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Editar.this);
        requestQueue.add(request);





    }
    public void limpiar(){
        Intent intent=new Intent(Editar.this,MainActivity.class);
        startActivity(intent);

    }
}
