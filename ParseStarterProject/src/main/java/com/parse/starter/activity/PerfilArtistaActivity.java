package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.starter.R;
import com.squareup.picasso.Picasso;

public class PerfilArtistaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String artistaNome;

    private String cidadeNome;
    private String introducao;

    private TextView cidadeNomeText;
    private TextView introducaoText;

    private String imagemPerfilUrl;
    private ImageView imagemPerfil;

    private String linkURL;
    private TextView linkURLText;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_artista);


        //Recupera dados da intent ArtistaFragment
        Intent intent = getIntent();
        artistaNome = intent.getStringExtra("nomeArtista");
        cidadeNome = intent.getStringExtra("cidade");
        introducao = intent.getStringExtra("introducao");
        imagemPerfilUrl = intent.getStringExtra("imagem");
        linkURL = intent.getStringExtra("linksArtista");


        //configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_perfil_artista);
        toolbar.setTitle(artistaNome);
        toolbar.setTitleTextColor(R.color.preta);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);


        //configurar o perfil
        cidadeNomeText = (TextView) findViewById(R.id.text_cidade_perfil);
        introducaoText = (TextView) findViewById(R.id.text_introducao_perfil);
        imagemPerfil = (ImageView) findViewById(R.id.imagem_perfil);
        linkURLText = (TextView) findViewById(R.id.lista_rede);


        //associando o textview aos valores passados pelo intent
        cidadeNomeText.setText(cidadeNome);
        introducaoText.setText(introducao);
        linkURLText.setText(linkURL);

        linkURLText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilArtistaActivity.this, WebViewArtistaActivity.class);
                intent.putExtra("artista", artistaNome);
                //intent.putExtra("link", linkURL);
                //startActivity(intent);
                webView = (WebView) findViewById(R.id.webView);
                // initializing toolbar


                //webView.getSettings().setJavaScriptEnabled(true);
                //webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(linkURL);
                webView.setHorizontalScrollBarEnabled(false);
            }
        });

        Picasso.with(this)
                .load(imagemPerfilUrl)
                .fit()
                .into(imagemPerfil);
    }
}
