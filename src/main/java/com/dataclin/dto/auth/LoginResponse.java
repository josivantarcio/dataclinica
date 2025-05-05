package com.dataclin.dto.auth;

import com.dataclin.model.Usuario.TipoUsuario;

public class LoginResponse {
    private String token;
    private String nome;
    private String email;
    private TipoUsuario tipo;

    public LoginResponse(String token, String nome, String email, TipoUsuario tipo) {
        this.token = token;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }
} 