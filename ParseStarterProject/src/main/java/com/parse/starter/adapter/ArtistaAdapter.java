package com.parse.starter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcelomenezes on 19/09/17.
 */

public class ArtistaAdapter extends ArrayAdapter<ParseObject> {

    private Context context;
    private List<ParseObject> artistas;

    public ArtistaAdapter(Context c, List<ParseObject> objects) {
        super(c,0, objects);
        this.context = c;
        this.artistas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        View view = convertView;

        ViewHolder holder = null;

        /*
        Verifica se não tem view criada
        */
        if( view == null){

            //Inicializa o objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta a partir do xml
            view = inflater.inflate(R.layout.lista_artistas, parent, false);
            holder = new ViewHolder();

            //Recuperar elementos para exibição
            holder.textArtistas = (TextView) view.findViewById(R.id.text_artistas);
            holder.textCidade = (TextView) view.findViewById(R.id.text_cidade);
            //recuperar imagem para lista de artista
            holder.artistaPostagem = (ImageView) view.findViewById(R.id.icone_artista);

            //verifica se tem artistas cadastrados
            if(artistas.size() > 0) {
                //Configurar textview para exibir artistas
                ParseObject parseObject = artistas.get(position);
                holder.textArtistas.setText(parseObject.getString("nomeArtista"));
                holder.textCidade.setText(parseObject.getString("cidade"));

                Picasso.with(context)
                        .load( parseObject.getParseFile("imagem").getUrl())
                        .fit()
                        .into(holder.artistaPostagem);
        }







        }



        return view;
    }

    static class ViewHolder{
        TextView textArtistas;
        TextView textCidade;
        ImageView artistaPostagem;

    }


}
