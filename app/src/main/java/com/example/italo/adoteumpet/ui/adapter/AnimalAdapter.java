package com.example.italo.adoteumpet.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.italo.adoteumpet.R;
import com.example.italo.adoteumpet.data.model.Animal;
import com.example.italo.adoteumpet.data.model.AnimalApi;

import java.util.List;

/**
 * Created by Italo on 06/10/2016.
 */

public class AnimalAdapter extends ArrayAdapter<AnimalApi>{
    private Context context;
    private List<AnimalApi> animalList;

    public AnimalAdapter (Context context, List<AnimalApi> animalList){
        super(context,0,animalList);
        this.context = context;
        this.animalList = animalList;
    }

    @Override
    public View getView (int position, View view, ViewGroup parent){
        AnimalApi animal = animalList.get(position);

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item, null);
        }

        TextView txtDesc = (TextView) view.findViewById(R.id.nome_pet);
        txtDesc.setText(animal.getNomeAnimal());

        return view;
    }
}
