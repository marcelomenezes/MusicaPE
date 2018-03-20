package com.parse.starter.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.parse.starter.R;
import com.parse.starter.fragments.ArtistaFragment;
import com.parse.starter.fragments.EventoFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marcelomenezes on 24/08/17.
 */

public class TabsAdapter extends FragmentStatePagerAdapter {

    private Context context;
    //private String[] abas = new String[]{"Artista", "Evento"};
    private int[] icones = new int[]{R.drawable.ic_recent_actors, R.drawable.ic_action_calendar_month };

    private int tamanhoIcone;

    //private HashMap<Integer, Fragment> fragmentosUtilizados;
    private ArrayList<Fragment> fragmentosUtilizados;

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        context = c;
        double escala = this.context.getResources().getDisplayMetrics().density;
        tamanhoIcone = (int) (36 * escala);
        //this.fragmentosUtilizados = new HashMap<>();
        this.fragmentosUtilizados = new ArrayList<>();
        ArtistaFragment af = new ArtistaFragment();
        EventoFragment ef = new EventoFragment();
        fragmentosUtilizados.add(af);
        fragmentosUtilizados.add(ef);
    }

    @Override
    public Fragment getItem(int position) {
    /*
        Fragment fragment = null;

        switch (position){
            case 0 :
                fragment = new ArtistaFragment();
                fragmentosUtilizados.put(position, fragment);
                break;

            case 1 :
                fragment = new EventoFragment();
                fragmentosUtilizados.put(position, fragment);
                break;
        }
        return fragment;
        */
        return fragmentosUtilizados.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        super.destroyItem(container,position,object);
        fragmentosUtilizados.remove(position);
    }

    public Fragment getFragment(Integer indice){
        return fragmentosUtilizados.get(indice);
    }

    @Override
    public CharSequence getPageTitle(int position){

        //Recuperar o icone de acordo com a posicão
        Drawable drawable = ContextCompat.getDrawable(this.context, icones[position]);
        drawable.setBounds(0,0, tamanhoIcone, tamanhoIcone);

        //Permite colocar uma imagem dentro de um texto
        ImageSpan imageSpan = new ImageSpan(drawable);

        //Classes utilizadas para retornar CharSequence
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );

        return spannableString;

    }

    @Override
    public int getCount() {
        return icones.length;
    }
}
