package com.parse.starter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelomenezes on 04/11/17.
 */

public class EventoAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private List<ParseObject> eventos_listados;

    public EventoAdapter(Context c, List<ParseObject> objects) {
        super(c, 0, objects);
        this.context = c;
        this.eventos_listados = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view = convertView;


        /*
        Verifica se não tem view criada
        */
        if( view == null){

            //Inicializa o objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta a partir do xml
            view = inflater.inflate(R.layout.lista_evento, parent, false);
        }

        //Recupera os elementos para exibição
        TextView textNomeEvento = (TextView) view.findViewById(R.id.text_nome_lista_evento);
        TextView textEnderecoEvento = (TextView) view.findViewById(R.id.text_endereco_lista_evento);

        ImageView imagemEvento = (ImageView) view.findViewById(R.id.imagem_lista_evento);

        //verifica se existe eventos listados
        if(eventos_listados.size() > 0){


            //Configurar TextView para exibir os eventos
            ParseObject parseObject = eventos_listados.get(position);
            textNomeEvento.setText(parseObject.getString("nomeEvento"));
            textEnderecoEvento.setText(parseObject.getString("detalhesEvento"));




            Picasso.with(context)
                    .load( parseObject.getParseFile("imagem").getUrl())
                    .fit()
                    .into(imagemEvento);
        }



        return view;
    }
}
