package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PerfilEventoActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String nomeEvento;
    private String detalhesEvento;
    private String enderecoEvento;
    private String imagemEventoUrl;
    private String deDataEvento;
    private String nomeUser;
    private String ateDataEvento;
    private String objectId;

    private TextView nomeEventoText;
    private TextView detalhesEventoText;
    private TextView enderecoEventoText;
    private ImageView imagemEvento;
    private TextView deDataEventoText;
    private TextView ateDataEventoText;

    private Date date;

    private String deDataEventoFormatada, ateDataEventoFormatada;

    private String usuario;

    private ParseQuery<ParseObject> query;

    private ParseObject parseEvento, eventoCarregado;

    private SimpleDateFormat formato;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_evento);

        formato = new SimpleDateFormat("dd/MM/yyyy");

        //Recupera valores enviados do EventoFragment
        Intent intent = getIntent();
        nomeUser = intent.getStringExtra("nomeArtista");
        nomeEvento = intent.getStringExtra("nomeEvento");
        detalhesEvento = intent.getStringExtra("detalhesEvento");
        enderecoEvento = intent.getStringExtra("enderecoEvento");
        imagemEventoUrl = intent.getStringExtra("imagem");
        deDataEvento = intent.getStringExtra("deDataEvento");
        ateDataEvento = intent.getStringExtra("ateDataEvento");
        objectId = intent.getStringExtra("objectId");




        //String.format(deDataEvento, formato);




        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            date = new Date();
/*
        try{
            date = dateFormat.parse(deDataEvento);
            deDataEventoText.setText(dateFormat.format(date));
        } catch (java.text.ParseException e){
            e.printStackTrace();
        }
        /*
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
      /*  toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }); */
        setSupportActionBar(toolbar);

        //Passar os valores pra o TextView
        nomeEventoText = (TextView) findViewById(R.id.text_nome_evento);
        detalhesEventoText = (TextView) findViewById(R.id.text_detalhes_evento);
        enderecoEventoText = (TextView) findViewById(R.id.text_endereco_evento);
        imagemEvento = (ImageView) findViewById(R.id.imagem_evento);
        deDataEventoText = (TextView) findViewById(R.id.text_de_data_evento_view);
        ateDataEventoText = (TextView) findViewById(R.id.text_ate_data_evento_view);


        //Associar Textview aos valores passados pelo itent
        nomeEventoText.setText(nomeEvento);
        detalhesEventoText.setText(detalhesEvento);
        enderecoEventoText.setText(enderecoEvento);
        deDataEventoText.setText(deDataEvento);
        //deDataEventoText.setText(dateFormat.format(date));
        ateDataEventoText.setText(ateDataEvento);

        Picasso.with(this)
                .load(imagemEventoUrl)
                .fit()
                .into(imagemEvento);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        usuario = ParseUser.getCurrentUser().getUsername().toString();


        if( usuario.equals(nomeUser)) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_evento, menu);
        }

        return  true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId() ){
            case R.id.action_configurar_evento:
            adicionarEvento();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void adicionarEvento(){
/*
        query = ParseQuery.getQuery("Evento");
        query.whereEqualTo("objectId", objectId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {//sucesso
                    if (objects.size() > 0) {
                        for (ParseObject parseObject : objects) {
                            eventoCarregado = parseObject;
                        }
                    }
                } else {//erro

                    e.printStackTrace();
                }
            }
        });

        parseEvento = eventoCarregado;
*/
        Intent intent = new Intent(this, PerfilConfigEventoActivity.class);

/*
        intent.putExtra("nomeEvento", parseEvento.getString("nomeEvento"));
        intent.putExtra("detalhesEvento", parseEvento.getString("detalhesEvento"));
        intent.putExtra("enderecoEvento", parseEvento.getString("enderecoEvento"));
        intent.putExtra("deDataEvento", parseEvento.getString("deDataEvento"));
        intent.putExtra("ateDataEvento", parseEvento.getString("ateDataEvento"));
        intent.putExtra("imagem", parseEvento.getParseFile("imagem").getUrl());
*/
        startActivity(intent);
    }


}
