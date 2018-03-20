package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilEventoActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String nomeEvento;
    private String detalhesEvento;
    private String enderecoEvento;
    private String imagemEventoUrl;
    private String deDataEvento;

    private TextView nomeEventoText;
    private TextView detalhesEventoText;
    private TextView enderecoEventoText;
    private ImageView imagemEvento;
    private  TextView deDataEventoText;

    private Date date;
    private String deDataEventoFormatada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_evento);

        //Recupera valores enviados do EventoFragment
        Intent intent = getIntent();
        nomeEvento = intent.getStringExtra("nomeEvento");
        detalhesEvento = intent.getStringExtra("detalhesEvento");
        enderecoEvento = intent.getStringExtra("enderecoEvento");
        imagemEventoUrl = intent.getStringExtra("imagem");
        deDataEvento = intent.getStringExtra("deDataEvento");



        /*
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try{
            date = dateFormat.parse(deDataEvento);
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }

        try {
            deDataEvento = dateFormat.format(date);
        } catch (android.net.ParseException e){
            e.printStackTrace();
        }
         */
        //configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_perfil_evento);
        toolbar.setTitle(nomeEvento);
        toolbar.setTitleTextColor(R.color.preta);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //setSupportActionBar(toolbar);

        //Passar os valores pra o TextView
        nomeEventoText = (TextView) findViewById(R.id.text_nome_evento);
        detalhesEventoText = (TextView) findViewById(R.id.text_detalhes_evento);
        enderecoEventoText = (TextView) findViewById(R.id.text_endereco_evento);
        imagemEvento = (ImageView) findViewById(R.id.imagem_evento);
        deDataEventoText = (TextView) findViewById(R.id.text_de_data_evento_view);


        //Associar Textview aos valores passados pelo itent
        nomeEventoText.setText(nomeEvento);
        detalhesEventoText.setText(detalhesEvento);
        enderecoEventoText.setText(enderecoEvento);
        deDataEventoText.setText(deDataEvento);

        Picasso.with(this)
                .load(imagemEventoUrl)
                .fit()
                .into(imagemEvento);

    }

}
