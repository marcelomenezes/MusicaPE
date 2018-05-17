/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
//import android.support.v7.app.ActionBarActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.ArtistaAdapter;
import com.parse.starter.adapter.TabsAdapter;
import com.parse.starter.fragments.ArtistaFragment;
import com.parse.starter.fragments.EventoFragment;
import com.parse.starter.util.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItemCompat.OnActionExpandListener {

    private Toolbar toolbarPrincipal;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    //para passar conteúdo na configuração do artista
    private ParseObject artistaConfig;

    private FragmentManager fragmentManager;
    private ArtistaFragment artistaFragment;
    private EventoFragment eventoFragment;

    private Context context;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configura o Toolbar
        toolbarPrincipal = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbarPrincipal.setLogo(R.drawable.logo);
        setSupportActionBar(toolbarPrincipal);

        //configura as abas
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager_main);

        //configura o adapter
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.cinzaEscuro));
        slidingTabLayout.setViewPager(viewPager);




        //fragmentManager = getSupportFragmentManager();
        //artistaFragment = (ArtistaFragment) fragmentManager.findFragmentById(R.id.lista_artistas);


        //define O Sliding Tab
        artistaFragment = (ArtistaFragment) tabsAdapter.getFragment(0);
        eventoFragment = (EventoFragment) tabsAdapter.getFragment(1);

/*
        //RecyclerView
        List<ParseObject> artistas;
        artistas = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lista_artistas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ArtistaAdapter(context, artistas));
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchitem = menu.findItem(R.id.action_pesquisar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.hintBusca));

        MenuItem filtro = menu.findItem(R.id.action_filtrar_ritmo);
        SearchView searchViewFiltro = (SearchView) MenuItemCompat.getActionView(filtro);
        //searchView.setOnClickListener(this);





        //SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId() ){
            case R.id.action_sair:
                //deslogar
                deslogarUsuario();
                return true;
            case R.id.action_configuracoes:
                configurarPerfil();
                return true;
            case R.id.action_adicionar_evento:
                adicionarEvento();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void adicionarEvento(){

        Intent intent = new Intent(this, PerfilConfigEventoActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario(){
        ParseUser.logOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void configurarPerfil(){
        ParseObject parseObject = ParseUser.getCurrentUser();

        Intent intent = new Intent(this, PerfilConfigArtistaActivity.class);
        intent.putExtra("imagem", parseObject.getParseFile("imagem").getUrl());
        intent.putExtra("nomeArtista", parseObject.getString("nomeArtista"));
        intent.putExtra("cidade", parseObject.getString("cidade"));
        intent.putExtra("introducao", parseObject.getString("introducao"));
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s){
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        artistaFragment.buscar(s);
        eventoFragment.buscar(s);
        return false;
    }
    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true; // para expandir a view
    }
    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        artistaFragment.limparBusca();
        eventoFragment.limparBusca();
        return true; // para voltar ao normal
    }
}
