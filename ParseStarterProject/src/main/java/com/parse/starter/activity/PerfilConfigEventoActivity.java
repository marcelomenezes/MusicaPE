package com.parse.starter.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.TabsAdapter;
import com.parse.starter.fragments.EventoFragment;
import com.parse.starter.activity.MainActivity;
import com.parse.starter.util.SlidingTabLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PerfilConfigEventoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button botaoEscolherImagem;
    private Button botaoSalvarEvento;

    private EditText nomeEventoEditText;
    private EditText detalhesEventoEditText;
    private EditText enderecoEventoEditText;


    private ImageView imagemEventoConfig;

    private Toolbar toolbar;

    private String nomeEvento;
    private String detalhesEvento;
    private String enderecoEvento;

    private String deDataEvento;
    private String ateDataEvento;

    private ParseObject imagemPassada;

    private Date dataEvento = new Date();

    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private ListView listView;
    private ListAdapter listAdapter;

    private EditText deDataEventoSalvaText;
    private EditText ateDataEventoSalvaText;
    private DatePickerDialog deDataEventoDialog;
    private DatePickerDialog ateDataEventoDialog;
    private SimpleDateFormat dataFormato;

    private String objectId;

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    //valoes do intent
    private String nomeEventoIntent;
    private String detalhesEventoIntent;
    private String enderecoEventoIntent;
    private String deDataEventoIntent;
    private String ateDataEventoIntent;
    private String imagemEventoIntent;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_config_evento);

        dataFormato = new SimpleDateFormat("dd-mm-yyyy");
        deDataEventoSalvaText = (EditText) findViewById(R.id.text_de_data_evento);
        ateDataEventoSalvaText = (EditText) findViewById(R.id.text_ate_data_evento);

        botaoEscolherImagem = (Button) findViewById(R.id.botao_escolher_imagem_evento);
        botaoSalvarEvento = (Button) findViewById(R.id.botao_salvar_evento);

        nomeEventoEditText = (EditText) findViewById(R.id.text_config_nome_evento);
        detalhesEventoEditText = (EditText) findViewById(R.id.text_config_introducao_evento);
        enderecoEventoEditText = (EditText) findViewById(R.id.endereco_config_evento);

        imagemEventoConfig = (ImageView) findViewById(R.id.imagem_evento_config);

        listView = (ListView) findViewById(R.id.lista_eventos);

        //configura as abas

        viewPager = (ViewPager) findViewById(R.id.view_pager_main);


        //configura o adapter
        //configurar adapter para atualização de lista do fragment

        //recuperar valor do itent PerfilEventoActivity
        Intent intent = getIntent();
        nomeEventoIntent = intent.getStringExtra("nomeEvento");
        detalhesEventoIntent = intent.getStringExtra("detalhesEvento");
        enderecoEventoIntent = intent.getStringExtra("enderecoEvento");
        deDataEventoIntent = intent.getStringExtra("deDataEvento");
        ateDataEventoIntent = intent.getStringExtra("ateDataEvento");
        imagemEventoIntent = intent.getStringExtra("imagem");

        //setar valores passados por um evento já criado
        nomeEventoEditText.setText(nomeEventoIntent);
        detalhesEventoEditText.setText(detalhesEventoIntent);
        enderecoEventoEditText.setText(enderecoEventoIntent);
        deDataEventoSalvaText.setText(deDataEventoIntent);
        ateDataEventoSalvaText.setText(ateDataEventoIntent);



        setData();

        Picasso.with(this)
                .load(imagemEventoIntent)
                .fit()
                .into(imagemEventoConfig);




        botaoEscolherImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        //configurar toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_perfil_config_evento);
        toolbar.setTitle("Salvar Evento");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        //associando o textview aos valores passados pelo intent
        //nomeEventoEditText.setText(nomeEvento);
        //detalhesEventoEditText.setText(detalhesEvento);


    }

    private void setData(){
        deDataEventoSalvaText.setOnClickListener(this);
        ateDataEventoSalvaText.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        deDataEventoDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                deDataEventoSalvaText.setText(dataFormato.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        ateDataEventoDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ateDataEventoSalvaText.setText(dataFormato.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if(view == deDataEventoSalvaText) {
            deDataEventoDialog.show();
        } else if(view == ateDataEventoSalvaText) {
            ateDataEventoDialog.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("onActivityResult", "onActivityResult");

        //Testar processor de retorno dos dados
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){


            //recuperar local do recurso
            Uri localImagemSelecionada = data.getData();
            Toast.makeText(getApplicationContext(), "Imagem do evento foi selecionada.", Toast.LENGTH_LONG).show();
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
                final String nomeImagem = dateFormat.format( new Date());
                final ParseFile arquivoParse = new ParseFile( nomeImagem + "imagem.png", byteArray);


                //Monta o objeto para salvar no parse


                /*
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("imagem", arquivoParse);
                */

                botaoSalvarEvento.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Monta o objeto para salvar no parse
                        ParseObject parseEvento = new ParseObject("Evento");
                        parseEvento.put("imagem", arquivoParse);

                        nomeEvento = nomeEventoEditText.getText().toString();
                        detalhesEvento = detalhesEventoEditText.getText().toString();
                        enderecoEvento = enderecoEventoEditText.getText().toString();
                        deDataEvento = deDataEventoSalvaText.getText().toString();
                        ateDataEvento = ateDataEventoSalvaText.getText().toString();


                        try {
                            dataEvento = dataFormato.parse(deDataEvento);
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                        //Date finalDate = dataFormato.format(deDataEvento);
                        //Criar objeto modelo evento
                        parseEvento.put("username", ParseUser.getCurrentUser());
                        parseEvento.put("nomeEvento", nomeEvento);
                        parseEvento.put("detalhesEvento", detalhesEvento);
                        parseEvento.put("enderecoEvento", enderecoEvento);
                        parseEvento.put("deDataEvento", dataEvento);
                        parseEvento.put("ateDataEvento", ateDataEvento);


                        //salvar os dados
                        parseEvento.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) { //sucesso
                                    Toast.makeText(getApplicationContext(), "Seu evento foi postado.", Toast.LENGTH_LONG).show();
                                    /*
                                    //atualizar a lista de novos eventos adicionados
                                    TabsAdapter adapterNovo = (TabsAdapter) listView.getAdapter();
                                    EventoFragment eventoFragmentoNovo = (EventoFragment) adapterNovo.getFragment(1);
                                    eventoFragmentoNovo.atualizaEventos();
*/
                                    finish();


                                } else {//erro
                                    Toast.makeText(getApplicationContext(), "Erro ao postar evento - Tente Novamente!",
                                            Toast.LENGTH_LONG).show();
                                }

                            }
                        });
                    }
                });

            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
