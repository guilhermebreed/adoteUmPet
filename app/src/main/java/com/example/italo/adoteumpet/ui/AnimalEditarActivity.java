package com.example.italo.adoteumpet.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.italo.adoteumpet.R;
import com.example.italo.adoteumpet.data.model.Animal;
import com.example.italo.adoteumpet.data.model.AnimalApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalEditarActivity extends AppCompatActivity {
    private EditText editNomeAnimal;
    private EditText editDescricaoAnimal;
    private EditText editIdadeAnimal;

    private Button editModificarAnimal;
    private Button editExcluirAnimal;

    private ControladorAnimal controladorAnimal = new ControladorAnimal();
    private AnimalApi animal = new AnimalApi();
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_editar);

        extras  = getIntent().getExtras();
        int i = extras.getInt("idModificar");
        animal.modificarAnimal(controladorAnimal.animais.get(i));

        editNomeAnimal = (EditText) findViewById(R.id.edit_nome_animal);
        editDescricaoAnimal = (EditText) findViewById(R.id.edit_descricao_animal);
        editIdadeAnimal = (EditText) findViewById(R.id.edit_idade_animal);

        editNomeAnimal.setText(animal.getNomeAnimal());
        editIdadeAnimal.setText(animal.getIdade().toString());
        editDescricaoAnimal.setText(animal.getDescricao());

        editModificarAnimal = (Button) findViewById(R.id.edit_btnModificar);
        editExcluirAnimal = (Button) findViewById(R.id.edit_btnExcluir);

        editModificarAnimal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String nome = editNomeAnimal.getText().toString();
                String descricao = editDescricaoAnimal.getText().toString();
                int idade;
                idade = Integer.parseInt(editIdadeAnimal.getText().toString());
                AnimalApi animal = new AnimalApi(nome,idade,descricao);


                controladorAnimal.animais.get(extras.getInt("idModificar")).modificarAnimal(animal);

                Intent in = new Intent();
                setResult(2,in);//Here I am Setting the Requestcode 1, you can put according to your requirement
                finish();
            }
        });

        editExcluirAnimal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String API = "http://192.168.1.6:3000/api/";
                AnimalApi animalExcluir = controladorAnimal.animais.get(extras.getInt("idModificar"));
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                IAnimalApi service = retrofit.create(IAnimalApi.class);

                Call<AnimalApi> excluir = service.deleteAnimal(animalExcluir.getId());

                excluir.enqueue(new Callback<AnimalApi>() {
                    @Override
                    public void onResponse(Call<AnimalApi> call, Response<AnimalApi> response) {
                        Intent in = new Intent();
                        setResult(3,in);//Here I am Setting the Requestcode 1, you can put according to your requirement
                        finish();
                    }

                    @Override
                    public void onFailure(Call<AnimalApi> call, Throwable t) {
                        Intent in = new Intent();
                        setResult(3,in);//Here I am Setting the Requestcode 1, you can put according to your requirement
                        finish();
                    }
                });

                controladorAnimal.animais.remove(extras.getInt("idModificar"));
                Intent in = new Intent();
                setResult(3,in);//Here I am Setting the Requestcode 1, you can put according to your requirement
                finish();
            }
        });
    }
}
