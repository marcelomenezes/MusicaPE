package com.parse.starter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilConfigArtistaActivity extends AppCompatActivity {

    private Button botaoTrocarImagem;

    private Toolbar toolbar;
    private String artistaNome;

    private String cidadeNome;
    private String introducao;

    private EditText cidadeNomeEditText;
    private EditText introducaoEditText;

    private Button botaoSalvarAlteracao;
    private String cidadeNomeAlterada;
    private String introducaoAlterada;

    private String imagemConfigUrl;
    private ImageView imagemConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_config_artista);

        botaoTrocarImagem = (Button) findViewById(R.id.botao_trocar_imagem);
        imagemConfig = (ImageView) findViewById(R.id.imagem_perfil_config);
        cidadeNomeEditText = (EditText) findViewById(R.id.text_config_cidade_perfil);
        introducaoEditText = (EditText) findViewById(R.id.text_config_introducao_perfil);
        botaoSalvarAlteracao = (Button) findViewById(R.id.botao_salvar_alteracao);


        botaoTrocarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });


        //Recupera dados da intent ArtistaFragment
        Intent intent = getIntent();
        //imagemPerfil = intent.get
        artistaNome = intent.getStringExtra("nomeArtista");
        cidadeNome = intent.getStringExtra("cidade");
        introducao = intent.getStringExtra("introducao");
        imagemConfigUrl = intent.getStringExtra("imagem");



        //configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_perfil_config_artista);
        toolbar.setTitle(artistaNome);
        toolbar.setTitleTextColor(R.color.preta);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);



        //associando o textview aos valores passados pelo intent
        cidadeNomeEditText.setText(cidadeNome);
        introducaoEditText.setText(introducao);


        botaoSalvarAlteracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cidadeNomeAlterada = cidadeNomeEditText.getText().toString();
                introducaoAlterada = introducaoEditText.getText().toString();

                ParseObject usuarioLogado = ParseUser.getCurrentUser();
                usuarioLogado.put("cidade", cidadeNomeAlterada);
                usuarioLogado.put("introducao", introducaoAlterada);

                usuarioLogado.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){ //sucesso
                            Toast.makeText(getApplicationContext(), "Sua alteração foi salva!", Toast.LENGTH_LONG).show();
                            /*
                            //atualizar a lista de novos eventos adicionados
                            TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
                            EventoFragment eventoFragmentoNovo = (EventoFragment) adapterNovo.getFragment(1);
                            eventoFragmentoNovo.atualizaEventos();
                               */
                        }else {//erro
                            Toast.makeText(getApplicationContext(), "Erro ao alterar dados - Tente Novamente!",
                                    Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

        Picasso.with(this)
                .load(imagemConfigUrl)
                .fit()
                .into(imagemConfig);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("onActivityResult", "onActivityResult");

        //Testar processor de retorno dos dados
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){


            //recuperar local do recurso
            Uri localImagemSelecionada = data.getData();

            //recupera imagem do local selecionado
            try {
                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);

                //comprimir no formato PNG
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagem.compress(Bitmap.CompressFormat.PNG, 5, stream);

                //Cria um array de bytes da imagem
                byte[] byteArray = stream.toByteArray();

                //Criar um arquivo com formato próprio do parse
                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
                String nomeImagem = dateFormat.format( new Date());
                ParseFile arquivoParse = new ParseFile( nomeImagem + "imagem.png", byteArray);

                //Monta o objeto para salvar no parse

                ParseObject usuarioAtual = ParseUser.getCurrentUser();
                usuarioAtual.put("imagem", arquivoParse);
                /*
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("imagem", arquivoParse);
                */
                //salvar os dados
                usuarioAtual.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e == null){ //sucesso
                            Toast.makeText(getApplicationContext(), "Sua imagem foi postada!", Toast.LENGTH_LONG).show();
                            /*
                            //atualizar a lista de novos eventos adicionados
                            TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
                            EventoFragment eventoFragmentoNovo = (EventoFragment) adapterNovo.getFragment(1);
                            eventoFragmentoNovo.atualizaEventos();
                               */
                        }else {//erro
                            Toast.makeText(getApplicationContext(), "Erro ao postar sua imagem - Tente Novamente!",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
