package com.parse.starter.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.activity.PerfilArtistaActivity;
import com.parse.starter.activity.PerfilEventoActivity;
import com.parse.starter.adapter.ArtistaAdapter;
import com.parse.starter.adapter.EventoAdapter;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class EventoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private ListView listView;
    private List<ParseObject> eventos_listados, eventosFiltrados, recuperarEventos, carregarEventos;
    private ArrayAdapter<ParseObject> adapter, adaptado, retornado;
    private ParseQuery<ParseObject> query;

    private SwipeRefreshLayout swipeRefreshLayout;

    public EventoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_evento, container, false);


        //Montar Listview e adapter

        eventos_listados = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.lista_eventos);
        adapter = new EventoAdapter(getActivity(), eventos_listados);
        listView.setAdapter(adapter);

        limparBusca();

        getEventosListados();
        getEventosCarregados();

        carregarEventos = getEventosCarregados();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Recupera os dados da listview
                ParseObject parseObject = eventos_listados.get(position);


                        //= parseObject.getString("username");
                //Enviar os daos para a PerfilEventoActivity
                Intent intent = new Intent(getActivity(), PerfilEventoActivity.class);
                intent.putExtra("imagem", parseObject.getParseFile("imagem").getUrl());
                intent.putExtra("nomeEvento", parseObject.getString("nomeEvento"));
                intent.putExtra("detalhesEvento", parseObject.getString("detalhesEvento"));
                intent.putExtra("enderecoEvento", parseObject.getString("enderecoEvento"));
                intent.putExtra("nomeUsuario", parseObject.getString("nomeUsuario"));
                //intent.putExtra("username", parseObject.)
                //intent.putExtra("deDataEvento", parseObject.getString("deDataEvento"));

                intent.putExtra("deDataEvento", parseObject.getDate("deDataEvento").toString());
                intent.putExtra("objectId", parseObject.getString("objectId"));
                intent.putExtra("ateDataEvento", parseObject.getString("ateDataEvento"));



                startActivity(intent);

            }
        });


        //Swipe
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onRefresh(){
        atualizaEventos();
    }

    private void getEventosListados(){

        //Recupera imagens das postagens
        query = ParseQuery.getQuery("Evento");
        //query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        //query.orderByDescending("ateDataEvento");
        query.orderByAscending("deDataEvento");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){//sucesso
                    //verifica se já existem eventos listados
                    if(objects.size()>0){
                        eventos_listados.clear();
                        for (ParseObject parseObject : objects){
                            eventos_listados.add(parseObject);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        atualizaEventos();
                        listView.requestLayout();
                    }

                }else {//erro

                    e.printStackTrace();
                }

            }
        });


    }

    public void atualizaEventos(){
        getEventosListados();
    }

    public void limparBusca(){
            retornado = new EventoAdapter(getActivity(), eventos_listados);
            listView.setAdapter(retornado);

    }

    public List<ParseObject> getEventosCarregados(){

        query = ParseQuery.getQuery("Evento");
        query.orderByAscending("nomeEvento");
        recuperarEventos = new ArrayList<>();
        carregarEventos = new ArrayList<>();

        try{
            recuperarEventos = query.find();
            for (ParseObject parseObject : recuperarEventos){
                carregarEventos.add(parseObject);
            }

        } catch (ParseException e){
            e.printStackTrace();
        }
        return carregarEventos;
    }

    public void buscar(String s) {
        if (s == null || s.trim().equals("")) {
            eventosFiltrados = eventos_listados;
            limparBusca();
            return;
        }
        getEventosCarregados();
        List<ParseObject> eventosencontrados = new ArrayList<>(carregarEventos);

        if (eventosencontrados.size() > 0) {
            for (int i = eventosencontrados.size() - 1; i >= 0; i--) {
                //for (int i =  3; i >= 0; i--) {
                ParseObject parseObject = eventosencontrados.get(i);
                if (!parseObject.getString("nomeEvento").toString().toUpperCase().contains(s.toUpperCase())) {
                    eventosencontrados.remove(parseObject);
                }
                //adapter.notifyDataSetChanged();
            }
            eventosFiltrados = eventosencontrados;

        } else {

        }

        adaptado = new EventoAdapter(getActivity(), eventosFiltrados);
        listView.setAdapter(adaptado);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Recupera os dados da listview
                ParseObject parseObject = eventosFiltrados.get(position);

                //Enviar os daos para a PerfilEventoActivity
                Intent intent = new Intent(getActivity(), PerfilEventoActivity.class);
                intent.putExtra("imagem", parseObject.getParseFile("imagem").getUrl());
                intent.putExtra("nomeEvento", parseObject.getString("nomeEvento"));
                intent.putExtra("detalhesEvento", parseObject.getString("detalhesEvento"));
                intent.putExtra("enderecoEvento", parseObject.getString("enderecoEvento"));
                intent.putExtra("deDataEvento", parseObject.getString("deDataEvento"));
                intent.putExtra("ateDataEvento", parseObject.getString("ateDataEvento"));
                intent.putExtra("objectId", parseObject.getString("objectId"));

                startActivity(intent);

            }
        });


    }
}
