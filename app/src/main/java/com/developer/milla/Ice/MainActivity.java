package com.developer.milla.Ice;

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
import com.developer.milla.guerrero.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        adapter = new Adaptador(this, getUserList());
        listView.setAdapter(adapter);
        //searchView=findViewById(R.id.ser);


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
//Descomentar esto
        //retrieveData();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                return false;
//            }
//        });


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
                params.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxNywibmFtZSI6Im1hcmEiLCJhZ2UiOjEyMSwiZW1haWwiOiJtYXJhQHBydWViYS5jb20iLCJwYXNzd29yZCI6IiQyYiQxMCRCR3RNWDJ2LlE0WEdKSkFjd3RrR0kuRzBkNWlDYzVsMWgxbHBIWEdUL21LVzBpTzRGQWxteSJ9LCJpYXQiOjE2MDc1ODY1ODIsImV4cCI6MTYwNzc1OTM4Mn0.INpwMXJrEL3A1DhCiOzYLVC5P1YdMiUzUpbVro7eLc0");
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
                params.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxNywibmFtZSI6Im1hcmEiLCJhZ2UiOjEyMSwiZW1haWwiOiJtYXJhQHBydWViYS5jb20iLCJwYXNzd29yZCI6IiQyYiQxMCRCR3RNWDJ2LlE0WEdKSkFjd3RrR0kuRzBkNWlDYzVsMWgxbHBIWEdUL21LVzBpTzRGQWxteSJ9LCJpYXQiOjE2MDc1ODY1ODIsImV4cCI6MTYwNzc1OTM4Mn0.INpwMXJrEL3A1DhCiOzYLVC5P1YdMiUzUpbVro7eLc0");
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

    //id /nombre helado // Imagen del helado // Precio // sabor
    // id  /name           // email            // password// age
    public List<Gelato> getUserList() {
        List<Gelato> lista = new ArrayList<>();

        lista.add(new Gelato("1","Griego", "data:image/jpeg;base64,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", 23));
        lista.add(new Gelato("2","Patricio", "data:image/jpeg;base64,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", 23));
        lista.add(new Gelato("3","Negro", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTa8fH8-RvcjVwgaBlZZQClzP2-H0iJjcOYRA&usqp=CAU", 23));
        lista.add(new Gelato("4","Nose", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0OBQ5KmHwwXvgaaJlFKgMtfugEfxDb1wInw&usqp=CAU", 23));
        lista.add(new Gelato("5","Griego 2", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0OBQ5KmHwwXvgaaJlFKgMtfugEfxDb1wInw&usqp=CAU", 23.00));
        lista.add(new Gelato("6","Griego 3", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR0OBQ5KmHwwXvgaaJlFKgMtfugEfxDb1wInw&usqp=CAU", 23.00));

        return lista;
    }
}
