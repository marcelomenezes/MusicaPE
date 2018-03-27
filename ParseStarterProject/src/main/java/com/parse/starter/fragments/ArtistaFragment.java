package com.parse.starter.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.activity.PerfilArtistaActivity;
import com.parse.starter.adapter.ArtistaAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistaFragment extends Fragment{


    private ListView listView;
    private List<ParseUser> recuperarArtistas;
    private List<ParseObject> artistas,  carregado, resultado, carregarArtistas, artistasFiltrados;
    private ArrayAdapter<ParseObject> adapter, adaptado, retornado;
    private ParseQuery<ParseUser> query;


    String[] arrayRitmo = new String[]{"baião", "brega-romântico", "brega-pop", "brega-funk", "ciranda",
                                        "coco", "cavalo-marinho", "forró", "frevo", "maracatu", "caboclinho",
                                        "xaxado", "manguebeat", "rap", "sertanejo", "rock", "samba",
                                        "pop"};




    public ArtistaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artista, container, false);


        //Montar ListView e adapter
        artistas = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.lista_artistas);
        adapter = new ArtistaAdapter(getActivity(), artistas);
        listView.setAdapter(adapter);



        //Montar Perfil do artista
        getArtistas();
        getArtistasCarregados();

       carregarArtistas =  getArtistasCarregados();
        limparBusca();

        /*
        Adicionar click para artista e abrir o perfil respectivo
        */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recupera os dados da listview para serem passados.
                ParseObject parseObject = artistas.get(position);

                //enviar dados para o PerfilArtista
                Intent intent = new Intent(getActivity(), PerfilArtistaActivity.class);
                intent.putExtra("imagem", parseObject.getParseFile("imagem").getUrl());
                intent.putExtra("nomeArtista", parseObject.getString("nomeArtista"));
                intent.putExtra("cidade", parseObject.getString("cidade"));
                intent.putExtra("introducao", parseObject.getString("introducao"));
                intent.putExtra("linksArtista", parseObject.getString("linksArtista"));

                startActivity(intent);
            }
        });


        return view;
    }

    public void getArtistas(){

        //Query para selecionar todos artistas cadastrados, com exceção do usuário logado
        query = ParseUser.getQuery();
        //query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.whereNotEqualTo("flagArtista", "NO");
        query.orderByAscending("nomeArtista");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {

                if(e == null){//sucesso

                    //verifica se existem artistas listados
                    if(objects.size() > 0) {

                        artistas.clear();
                        for (ParseUser parseUser : objects){
                            artistas.add(parseUser);
                        }
                        adapter.notifyDataSetChanged();
                        //retornado.notifyDataSetChanged();
                       // atualizaArtistas();
                    }
                }else{//erro

                    e.printStackTrace();
                }
            }
        });

    }

    public void atualizaArtistas(){getArtistas();}

    public List<ParseObject> getArtistasCarregados(){

        query = ParseUser.getQuery();
        recuperarArtistas = new ArrayList<>();
        carregarArtistas = new ArrayList<>();

        try{
            recuperarArtistas = query.find();
            for (ParseUser parseUser : recuperarArtistas){
                carregarArtistas.add(parseUser);
            }

        } catch (ParseException e){
            e.printStackTrace();
        }
        return carregarArtistas;
    }

    public void limparBusca(){
        //Montar ListView e adapter
        retornado = new ArtistaAdapter(getActivity(), artistas);
        listView.setAdapter(retornado);
        //atualizaArtistas();

    }

    public void buscar(String s) {
        if (s == null || s.trim().equals("")) {
            //getArtistasCarregados();
            //List<ParseObject> artistasencontrados = new ArrayList<>(carregarArtistas);
            artistasFiltrados = artistas;
            limparBusca();
            return;
        } else {
            getArtistasCarregados();
            List<ParseObject> artistasencontrados = new ArrayList<>(carregarArtistas);

            if (artistasencontrados.size() > 0) {
                for (int i = artistasencontrados.size() - 1; i >= 0; i--) {
                    //for (int i =  3; i >= 0; i--) {
                    ParseObject parseObject = artistasencontrados.get(i);
                    if (!parseObject.getString("nomeArtista").toString().toUpperCase().contains(s.toUpperCase())) {
                        artistasencontrados.remove(parseObject);
                    }
                }
                artistasFiltrados = artistasencontrados;

            } else {

            }

            adaptado = new ArtistaAdapter(getActivity(), artistasFiltrados);
            listView.setAdapter(adaptado);
        }
         /*
        Adicionar click para artista e abrir o perfil respectivo
        */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Recupera os dados da listview para serem passados.
                ParseObject parseObject = artistasFiltrados.get(position);

                //enviar dados para o PerfilArtista
                Intent intent = new Intent(getActivity(), PerfilArtistaActivity.class);
                intent.putExtra("imagem", parseObject.getParseFile("imagem").getUrl());
                intent.putExtra("nomeArtista", parseObject.getString("nomeArtista"));
                intent.putExtra("cidade", parseObject.getString("cidade"));
                intent.putExtra("introducao", parseObject.getString("introducao"));


                startActivity(intent);
            }
        });

    }
}
