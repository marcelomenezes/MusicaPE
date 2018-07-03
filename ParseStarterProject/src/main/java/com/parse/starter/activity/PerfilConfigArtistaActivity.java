package com.parse.starter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
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


import android.support.v7.widget.AppCompatAutoCompleteTextView;

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


    private EditText ritmosConfigText;
    private String ritmosLista;

    ChipsMultiAutoCompleteTextview mu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_config_artista);

        botaoTrocarImagem = (Button) findViewById(R.id.botao_trocar_imagem);
        imagemConfig = (ImageView) findViewById(R.id.imagem_perfil_config);
        cidadeNomeEditText = (EditText) findViewById(R.id.text_config_cidade_perfil);
        introducaoEditText = (EditText) findViewById(R.id.text_config_introducao_perfil);
        botaoSalvarAlteracao = (Button) findViewById(R.id.botao_salvar_alteracao);

        ritmosConfigText = findViewById(R.id.text_config_ritmos_artista);

        //ritmosConfigText = (ChipsMultiAutoCompleteTextview) findViewById(R.id.multiAutoCompleteTextView1);


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
        ritmosLista = intent.getStringExtra("ritmos");



        //configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_perfil_config_artista);
        toolbar.setTitle(artistaNome);
        toolbar.setTitleTextColor(R.color.preta);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);



        //associando o textview aos valores passados pelo intent
        cidadeNomeEditText.setText(cidadeNome);
        introducaoEditText.setText(introducao);
        ritmosConfigText.setText(ritmosLista);


       // mu = (ChipsMultiAutoCompleteTextview) findViewById(R.id.multiAutoCompleteTextView1);

//        String[] item = getResources().getStringArray(R.array.ritmos);

  //      Log.i("", "Country Count : " + item.length);
    //    mu.setAdapter(new ArrayAdapter(this,
      //          android.R.layout.simple_dropdown_item_1line, item));
        //mu.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        ritmosConfigText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                Spannable spannable = ritmosConfigText.getText();



                for (int i =0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        ritmosConfigText.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    }else {
                        ritmosConfigText.setBackgroundColor(Color.parseColor("#CCCCFF"));
                    }

                }
                /*
                if(s.length() != 0) {
                    //ritmosConfigText.setTextColor(Color.parseColor("#CCCCFF"));

                    ritmosConfigText.setBackgroundColor(Color.parseColor("#CCCCFF"));
                    ForegroundColorSpan fcs = new ForegroundColorSpan( Color.GREEN);
                    String s1 = s.toString();
                    int in=0; // start searching from 0 index

            // keeps on searching unless there is no more function string found
                    while ((in = s1.indexOf(ritmosLista,in)) >= 0) {
                        spannable.setSpan(
                                fcs,
                                in,
                                in + ritmosLista.length(),
                                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        // update the index for next search with length
                        in += ritmosLista.length();
                    }
                }
                */
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        botaoSalvarAlteracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cidadeNomeAlterada = cidadeNomeEditText.getText().toString();
                introducaoAlterada = introducaoEditText.getText().toString();

                ParseObject usuarioLogado = ParseUser.getCurrentUser();
                usuarioLogado.put("cidade", cidadeNomeAlterada);
                usuarioLogado.put("introducao", introducaoAlterada);
                usuarioLogado.put("ritmos", ritmosLista);

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

        atualizaImagem();



    }
    public void atualizaImagem(){
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
                            atualizaImagem();
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
