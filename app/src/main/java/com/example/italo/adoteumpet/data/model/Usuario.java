package com.example.italo.adoteumpet.data.model;

/**
 * Created by Alexandre on 15/10/2016.
 */
public class Usuario {

    private String user;
    private String senha;
    private String contato;

    public Usuario(String user, String senha, String contato){
        this.user = user;
        this.senha = senha;
        this.contato = contato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
