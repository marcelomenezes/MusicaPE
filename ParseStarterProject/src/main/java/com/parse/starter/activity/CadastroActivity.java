package com.parse.starter.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {

    private EditText cadastroSenha;
    private EditText cadastroEmail;
    private EditText cadastroUsuario;
    private EditText cadastroNome;

    private Button botaoCadastrar;
    private TextView comConta;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        cadastroSenha = (EditText) findViewById(R.id.cadastroSenhaId);
        cadastroEmail = (EditText) findViewById(R.id.cadastroEmailId);
        cadastroUsuario = (EditText) findViewById(R.id.cadastroUsuarioId);
        cadastroNome = (EditText) findViewById(R.id.cadastroNomeId);

        comConta = (TextView) findViewById(R.id.semContaId);
        botaoCadastrar = (Button) findViewById(R.id.botaoCadastrarId);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();

            }
        });

        comConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirLoginUsuario();
            }
        });
    }

    private void cadastrarUsuario() {


        //colocar imagem padrão
        Bitmap imagem = BitmapFactory.decodeResource(getResources(), R.drawable.ic_play_circle_filled);

        //comprimir no formato PNG
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.PNG, 5, stream);

        //Cria um array de bytes da imagem
        byte[] byteArray = stream.toByteArray();

        //Criar um arquivo com formato próprio do parse
        ParseFile arquivoParse = new ParseFile("imagem.png", byteArray);
        try {
            arquivoParse.save();
        }catch (ParseException e){
            e.printStackTrace();
        }

        //criar objeto usuario
        ParseUser usuario = new ParseUser();
        usuario.setUsername(cadastroUsuario.getText().toString());
        usuario.setEmail(cadastroEmail.getText().toString());
        usuario.setPassword(cadastroSenha.getText().toString());
        usuario.put("nomeArtista", cadastroNome.getText().toString());
        usuario.put("imagem", arquivoParse);

        //checar se é artista ou não
        CheckBox checkBox = (CheckBox) findViewById(R.id.item_check);
        if(checkBox.isChecked())
        {
             usuario.put("flagArtista", "YES");


        }
        else
        {
            usuario.put("flagArtista", "NO");
        }




                //salvar dados do usuario
                usuario.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(CadastroActivity.this, "Cadastro feito com sucesso", Toast.LENGTH_SHORT).show();

                            abrirLoginUsuario();
                        } else {
                            ParseErros parseErros = new ParseErros();
                            String erro = parseErros.getErro(e.getCode());
                            //Toast.makeText(CadastroActivity.this, "Erro no cadstro", Toast.LENGTH_SHORT).show();
                            Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //usuario.put("imagem", arquivoParse);

    }


    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();;
    }
}
