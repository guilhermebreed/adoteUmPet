package com.example.italo.adoteumpet.ui.view;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.italo.adoteumpet.R;
import com.example.italo.adoteumpet.data.model.Animal;
import com.example.italo.adoteumpet.data.model.AnimalApi;
import com.example.italo.adoteumpet.ui.controller.ControladorAnimal;
import com.example.italo.adoteumpet.ui.controller.PessoaController;
import com.example.italo.adoteumpet.ui.interfaces.api.IAnimalApi;
import com.squareup.picasso.Picasso;

//import retrofit.Callback;
//import retrofit.RestAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Italo on 06/10/2016.
 */

public class AnimalActivity extends AppCompatActivity{

    private EditText cadNomeAnimal;
    private EditText cadDescricaoAnimal;
    private EditText cadIdadeAnimal;
    private EditText cadRacaAnimal;
    private Button cadIncluirAnimal;
    private Button uploadImagem;
    private ImageView iv;
    private ControladorAnimal controladorAnimal = new ControladorAnimal();
    private String textoRaca;
    private int ra;
    private String textoTipoAnimal;
    private int tip;
    private String caminhoFoto = "";

    public static final int  IMAGEM_INTERNA = 12;

    private PessoaController pessoaController = new PessoaController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_cadastro);


        cadNomeAnimal = (EditText) findViewById(R.id.cad_nome_animal);
        cadDescricaoAnimal = (EditText) findViewById(R.id.cad_descricao_animal);
        cadIdadeAnimal = (EditText) findViewById(R.id.cad_idade_animal);
        cadRacaAnimal = (EditText) findViewById(R.id.cad_raca_animal);
        cadIncluirAnimal = (Button) findViewById(R.id.cad_btnIncluir);
        uploadImagem = (Button) findViewById(R.id.uploadimg);
        iv = (ImageView) findViewById(R.id.img);

        /*//Spinner Raça
        // Cria um ArraAdapter usando um array de string e um layout padrão de spinner
        Spinner spnRaca = (Spinner)
                findViewById(R.id.spnRaca);
        ArrayAdapter adapterRaca =
                ArrayAdapter.createFromResource(this, R.array.Raca, android.R.layout.simple_spinner_item);
        //alterar a fonte de dados(adapter) do Spinner
        spnRaca.setAdapter(adapterRaca);

        textoRaca = spnRaca.getSelectedItem().toString();
        ra = spnRaca.getSelectedItemPosition();


        //Spinner TipoAnimal
        // Cria um ArraAdapter usando um array de string e um layout padrão de spinner
        Spinner spnTipoAnimal = (Spinner)
                findViewById(R.id.spnTipoAnimal);
        ArrayAdapter adapterTipoAnimal =
                ArrayAdapter.createFromResource(this, R.array.TipoAnimal, android.R.layout.simple_spinner_item);
        //alterar a fonte de dados(adapter) do Spinner
        spnTipoAnimal.setAdapter(adapterTipoAnimal);

        textoTipoAnimal = spnTipoAnimal.getSelectedItem().toString();
        tip = spnRaca.getSelectedItemPosition();*/

        uploadImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putString("imgAtual","");

                Intent intent = new Intent(AnimalActivity.this, UploadFoto.class);
                intent.putExtras(extras);

                startActivityForResult(intent,1);
            }
        });

        cadIncluirAnimal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Rest Adapte
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IAnimalApi.API_LOCATION)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                IAnimalApi service = retrofit.create(IAnimalApi.class);

                    AnimalApi animal = new AnimalApi();
                    animal.setNomeAnimal(cadNomeAnimal.getText().toString());
                    animal.setDescricao(cadDescricaoAnimal.getText().toString());
                    animal.setIdade(Integer.parseInt(cadIdadeAnimal.getText().toString()));
                    animal.setRaca(cadRacaAnimal.getText().toString());
                    animal.setIdPessoa(pessoaController.getPessoaLogada().getIdPessoa());
                    animal.setContato(pessoaController.getPessoaLogada().getContato());
                    animal.setFoto(caminhoFoto);

                    Call<AnimalApi> animalPost = service.postAnimal(animal);
                    animalPost.enqueue(new Callback<AnimalApi>() {
                        @Override
                        public void onResponse(Call<AnimalApi> call, Response<AnimalApi> response) {
                            int statusCode = response.code();

                            AnimalApi animalapi = response.body();
                            Log.d("Salvar animal", "animal salvo" + statusCode);

                            Intent in = new Intent();
                            setResult(1,in);//Here I am Setting the Requestcode 1, you can put according to your requirement
                            finish();
                        }

                        @Override
                        public void onFailure(Call<AnimalApi> call, Throwable t) {
                            Log.d("Salvar animal", "Erro"+ t.getMessage());
                        }
                    });

            }
        });
    }

    protected void onActivityResult(int codigo, int resultado, Intent intent){
        if(resultado == 1){
            Toast.makeText(this,"Foto enviada!!!",Toast.LENGTH_SHORT);
            Bundle extra = intent.getExtras();
            caminhoFoto = extra.getString("imgAtual");
            Log.e("Teste Imagem",caminhoFoto);
            //iv.setImageResource(ControladorAnimal.converterImagemCerta(caminhoFoto));
            Log.e("Teste Imagem",caminhoFoto);
        }
    }

    public void uploadImagem(){
        UploadFoto upload = new UploadFoto();
        upload.upload();

    }

    public void pegarFoto(){
        UploadFoto upload = new UploadFoto();
        upload.getFoto();
    }

 /*   public void pegarImg(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGEM_INTERNA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == IMAGEM_INTERNA){
            if(resultCode == RESULT_OK){
                Uri imagemSelecionada = intent.getData();

                String[] colunas = {MediaStore.Images.Media.DATA}; // Coluna _data //Recursos nativos do AS para acessar imagem

                Cursor cursor = getContentResolver().query(imagemSelecionada, colunas, null, null, null); //Outras aplicações acessam os recursos de sua aplicação através da Interface Content
                cursor.moveToFirst();

                int indexColuna = cursor.getColumnIndex(colunas [0]);
                String pathImg   = cursor.getString(indexColuna);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
                //Picasso.with(getApplicationContext()).load(pathImg).into(iv);
                iv.setImageBitmap(bitmap);

            }else{
                Log.i("", "Deu merda");
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    */

    public boolean verificarCampos(){
        int resultado = 0; // resultado os valores são 1 para cadNomeAnimal, 2 para cadIdadeAnimal, e 5 para cadDescricaoAnimal
        if(cadNomeAnimal.getText().toString().equals("") || cadNomeAnimal.getText() == null){
            resultado +=1;
        }
        if(cadIdadeAnimal.getText().toString().equals("") || cadNomeAnimal.getText() == null){
            resultado += 2;
        }
        if(cadDescricaoAnimal.getText().toString().equals("") || cadDescricaoAnimal.getText() == null){
            resultado +=5;
        }

        if(resultado > 0){
            destacarCampos(resultado);
            return false;
        }
        return true;
    }

    public void destacarCampos(int valor){
        cadDescricaoAnimal.getBackground().setColorFilter(Color.parseColor("#DDDDDD"), PorterDuff.Mode.LIGHTEN);
        cadIdadeAnimal.getBackground().setColorFilter(Color.parseColor("#DDDDDD"), PorterDuff.Mode.LIGHTEN);
        cadNomeAnimal.getBackground().setColorFilter(Color.parseColor("#DDDDDD"), PorterDuff.Mode.LIGHTEN);
        if (valor >= 5){
            cadDescricaoAnimal.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.LIGHTEN);
            valor -= 5;
        }
        if (valor >= 2){
            cadIdadeAnimal.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.LIGHTEN);
            valor -= 2;
        }
        if(valor >= 1){
            cadNomeAnimal.getBackground().setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.LIGHTEN);
            valor -= 1;
        }
    }
}
