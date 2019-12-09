package smc.Business;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SMC {
    private HashMap<String, Utilizador> utilizadores;
    private HashMap<String, Utilizador> administradores;
    private HashMap<String, Utilizador> comuns;
    private HashMap<String, Conteudo> conteudo;
    private HashMap<String, Conteudo> musicas;
    private HashMap<String, Conteudo> videos;

    /* Iniciar sessão */

    public boolean utilizadorExistente(String nome){
        if(this.utilizadores.containsKey(nome))
            return true;
        return false;
    }

    public boolean validarSessao(String nome, String pasword){
        if(this.utilizadores.get(nome).getPassword() == password)
            return true;
        return false;
    }

    public Utilizador iniciarSessao(String nome, String password)  throws UtilizadorInexistente, PasswordInvalida{
        boolean valid = this.utilizadorExistente(nome);
        if(!valid)
            throw new UtilizadorInexistente("O utilizador " + nome + " é inexistente.");
        valid = this.validarSessao(nome, password);
        if(!valid)
            throw new PasswordInvalida("A password está é inválida");
        Utilizador aux = this.utilizadores.get(nome);
        return aux;
    }

    /* Terminar sessão */

    public void terminarSessao(){
        System.out.println("Até logo!");
    }

    /* Registar utilizador */

    public boolean validarAux(String nome, String email, String pass){
        if(this.utilizadores.containsKey(nome))
            return false;
        if(this.utilizadores.containsEmail(email))
            return false;
        return true;
    }

    public boolean validar(String nome, String email, String pass) throws UtilizadorInvalido{
        boolean valid = validar(nome, email, pass);
        if(!valid)
            throw new UtilizadorInvalido("Utilizador inválido(mail ou nome existente).");
        return true;
    }

    public void addComum(String nome, String email, String pass){
        Utilizador u = new Utilizador(nome, email, pass);
        this.comuns.put(nome, u);
    }

    public void registarComum(String nome, String email, String pass){
        Utilizador u = new Utilizador(nome, email, pass);
        this.utilizadores.put(nome, u);
        addComum(nome, email, pass);
    }

    public void addAdmin(String nome, String email, String pass){
        Utilizador u = new Utilizador(nome, email, pass);
        this.administradores.put(nome, u);
    }

    public void registarAdmin(String nome, String email, String pass){
        Utilizador u = new Utilizador(nome, email, pass);
        this.utilizadores.put(nome, u);
        addAdmin(nome, email, pass);
    }

    /* Upload de Conteúdo */

    public boolean validarConteudo(List<Conteudo> c){
        for(Conteudo x : c) {
            if ((!x.getNome().endsWith(".mp3")) && !(x.getNome().endsWith(".mp4")))
                return false;
        }
        return true;
    }

    public List<Conteudo> conteudoIgual(List<Conteudo> c){
        List<Conteudo> l = new ArrayList<Conteudo>();
        for(Conteudo x : c){
            if(this.conteudo.containsKey(x.getNome()))
                l.add(x);
        }
        return l;
    }

    public void showUtilizador(List<Conteudo> x, Utilizador u){
        for(Conteudo c : x){
            this.comuns.get(u).getBiblioteca().add(c);
            if(c.getNome().endswith(".mp3"))
                this.comuns.getVideos().add(c);
            else
                this.comuns.getMusicas().add(c);
        }
    }

    public void addPotenciais(List<Conteudo> x, Utilizador u){
        for(Utilizador another : this.comuns.getComuns())
            for(Conteudo from_antoher : aux.getBiblioteca())
                for(Conteudo from_x : x)
                    if(from_antoher.equals(from_x))
                        this.comuns.get(u).getPotenciaisAmigos().add(another);
    }

    public List<Conteudo> elimina(List<Conteudo> a, List<Conteudo> b){
        List<Conteudo> l = new ArrayList<Conteudo>();
        for(Conteudo from_b : b)
            for(Conteudo from_a : a)
                if(!(from_a.equals(b)) && !(l.contains(from_b)))
                    l.add(from_b);
        return l;
    }

    public void geraCategorias(List<Conteudo> c){
        /* Não sei como fazer este método */
    }

    public void carrega(List<Conteudo> c){
        for(Conteudo x : c){
            this.conteudo.put(x.getNome(), x);
            if(c.getNome().endswith(".mp3"))
                this.videos.put(x.getNome(),x);
            else
                this.musicas.put(x.getNome(),x);
        }
    }

    public void upload(List<Conteudo> conteudo, Utilizador u) throws ConteudoInvalido{
        boolean valid = validarConteudo(conteudo);
        if(!valid)
            throw new ConteudoInvalido("O conteudo + " + conteudo + " tem ficheiro(s) com formato inválido.");
        List<Conteudo> existe = conteudoIgual(conteudo);
        if(existe != null){
            showUtilizador(existe, u);
            addPotenciais(existe, u);
            existe = elimina(existe, conteudo);
        }
        geraCategorias(existe);
        carrega(existe);
        showUtilizador(existe, u);
        addPotenciais(existe, u);
    }

    /* Alterar Categoria de Conteúdo */

    public void alterarCategoria(Conteudo c, Utilizador u, String categoria){
        this.comuns.get(u).getBiblioteca().get(c).setCategoria(categoria);
        if(c.getNome().endswith(".mp3"))
            this.comuns.getVideos().get(c).setCategoria(categoria);
        else
            this.comuns.getMusicas().get(c).setCategoria(categoria);
    }


    public void alterarConteudo(Conteudo c, Utilizador u, String categoria){
        alterarCategoria(c, u, categoria);
    }

    /* Reproduzir Conteúdo */

    public boolean validarExistencia(){
        if(this.conteudo.size() == 0)
            return false;
        return true;
    }
    public List<Conteudo> existeConteudo() throws ConteudoInexistente{
        boolean valid = validarExistencia();
        if(!valid)
            throw  new ConteudoInexistente("Não existe conteúdo na biblioteca do sistema.");
        return this.conteudo.getBiblioteca();
    }
    public void play(String c){
        InputStream conteudo;
        try{
            conteudo = new FileInputStream(new File(c));
            AudioStream audios = new AudioStream(conteudo);
            AudioPlayer.player.start(audios);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public void reproduzirConteudo(String c){
        play(c);
    }

    public boolean validarExistenciaPlaylist(Utilizador u){
        if(this.comuns.get(u).getPlaylists().size() == 0)
            return false;
        return true;
    }

    public HashMap<String, List<Conteudo>> existePlaylists(Utilizador u) throws PlaylistsInexistentes{
        boolean valid = validarExistenciaPlaylist(u);
        if(!valid)
            throw  new PlaylistsInexistentes("Você não tem playlists.");
        return this.comuns.get(u).getPlaylists();
    }

    public void playPlaylist(List<Conteudo> playlist){
        for(Conteudo c : playlist)
            play(c.getNome());
    }

    public void reproduzirPlaylist(String p, Utilizador u,boolean shuffle){
        List<Conteudo> playlist = this.comuns.get(u).getPlaylists().get(p);
        if(shuffle)
            Collections.shuffle(playlist);
        playPlaylist(playlist);
    }
    public void aleatorio(){
        List<Conteudo> x = this.conteudo.getBiblioteca();
        Collections.shuffle(x);
        playPlaylist(x);
    }

    /* Convidado */

    public void connectSystem(){
        System.out.println("Bem vindo!");
    }

    /* Potenciais Amigos */

    public List<Utilizador> potenciaisAmigos(Utilizador u){
        return this.comuns.get(u).getPotenciaisAmigos();
    }

    /* Download Conteúdo */

    public List<Conteudo> biblioteca(Utilizador u){
        return this.comuns.get(u).getBiblioteca();
    }

    public void download(String conteudo){
        down(conteudo);
    }
}
