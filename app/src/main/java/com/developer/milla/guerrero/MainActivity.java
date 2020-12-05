package com.developer.milla.guerrero;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Adaptador adapter;
    public static ArrayList<Users> users = new ArrayList<>();


    Users usuarios;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.myListView);
        adapter = new Adaptador(this, users);
        listView.setAdapter(adapter);
        searchView=findViewById(R.id.ser);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"Mostrar datos","Editar datos","elimando con exito"};
                builder.setTitle(users.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        switch (i){

                            case 0:

                                startActivity(new Intent(getApplicationContext(), DetalleslActivity.class)
                                .putExtra("position",position));

                                break;

                            case 1:
                                startActivity(new Intent(getApplicationContext(), Editar.class)
                                .putExtra("position",position));

                                break;

                            case 2:

                                deleteData(users.get(position).getId());

                                break;


                        }



                    }
                });


                builder.create().show();


            }
        });

        retrieveData();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


    }

    private void deleteData(final String id) {

        StringRequest request = new StringRequest(Request.Method.DELETE, "http://192.168.1.71:4000/user/"+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(MainActivity.this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "datos no eliminado", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJuYW1lIjoibWFyYSIsImFnZSI6MTgsImVtYWlsIjoibWFyYUBwcnVlYmEuY29tIiwicGFzc3dvcmQiOiIkMmIkMTAkUEg1NDJhS3hkSE1weHdQSzNhMlpudS9sc1UxNFFYazZ6NWdVWW5STzBpT0g5Y3hqQ1ZsRHUifSwiaWF0IjoxNjA2ODg3ODQ4LCJleHAiOjE2MDcwNjA2NDh9.9B5r4Hp8nFM7PYyVDhJuQxfGOx75b_GuOCIg1s6IKQQ");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    public void retrieveData(){

        StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.1.71:4000/user/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        users.clear();
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            Boolean sucess = jsonObject.getBoolean("ok");
                            JSONArray jsonArray = jsonObject.getJSONArray("users");

                            if(sucess){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String password = object.getString("password");
                                    String age = object.getString("age");

                                    usuarios = new Users(id,name,email,password,age);
                                    users.add(usuarios);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJuYW1lIjoibWFyYSIsImFnZSI6MTgsImVtYWlsIjoibWFyYUBwcnVlYmEuY29tIiwicGFzc3dvcmQiOiIkMmIkMTAkUEg1NDJhS3hkSE1weHdQSzNhMlpudS9sc1UxNFFYazZ6NWdVWW5STzBpT0g5Y3hqQ1ZsRHUifSwiaWF0IjoxNjA2ODg3ODQ4LCJleHAiOjE2MDcwNjA2NDh9.9B5r4Hp8nFM7PYyVDhJuQxfGOx75b_GuOCIg1s6IKQQ");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void btn_add_activity(View view) {
        startActivity(new Intent(getApplicationContext(), Agregar.class));

    }
    public  void  buscador(String chartext){
        chartext=chartext.toLowerCase(Locale.getDefault());
        adapter.clear();
    }
}
