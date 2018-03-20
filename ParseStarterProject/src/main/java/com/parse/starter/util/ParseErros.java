package com.parse.starter.util;

import java.util.HashMap;

/**
 * Created by marcelomenezes on 24/07/17.
 */

public class ParseErros {

    private HashMap<Integer, String> erros;

    public ParseErros(){
        this.erros = new HashMap<>();
        this.erros.put(201, "A senha não foi preenchida!");
        this.erros.put(202, "Usuário já existe, escolha outro nome de usuário!");
    }

    public String getErro(int codErro){
        return this.erros.get(codErro);
    }
}
