package com.developer.milla.Ice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.milla.guerrero.R;

import java.util.List;

public class Adaptador extends ArrayAdapter<Gelato> {

    Context context;
    List<Gelato> arrayListEmployee;


    public Adaptador(@NonNull Context context, List<Gelato> arrayListEmployee) {
        super(context, R.layout.custom_list_item,arrayListEmployee);

        this.context = context;
        this.arrayListEmployee = arrayListEmployee;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,null,true);

        TextView name = view.findViewById(R.id.name);
        TextView sabor = view.findViewById(R.id.sabor);
        TextView precio = view.findViewById(R.id.precio);
        ImageView heladoImage = view.findViewById(R.id.heladoImage);
        //id /nombre helado // Imagen del helado // Precio // sabor
        // id  /name           // email            // password// age

        name.setText(arrayListEmployee.get(position).getNomProducto());
        //sabor.setText(arrayListEmployee.get(position).get());
        precio.setText(String.valueOf(arrayListEmployee.get(position).getPrecio()));
        Picasso.get().load(arrayListEmployee.get(position).getDescripcion()).into(heladoImage);


        return view;
    }
}
