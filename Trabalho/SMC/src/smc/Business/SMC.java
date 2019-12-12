package smc.Business;

import smc.Data.AdministradorDAO;
import smc.Data.ComumDAO;
import smc.Data.ConteudoDAO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class SMC {
    private AdministradorDAO admins;
    private ComumDAO comuns;
    private ConteudoDAO conteudo;

    /* Iniciar sessão */

    public boolean utilizadorExistente(String n){
        if(this.comuns.containsKey(n) || this.admins.containsKey(n))
            return true;
        return false;
    }

    public boolean validarSessao(String n, String p){
        if(this.admins.get(n).getPassword() == p || this.comuns.get(n).getPassword() == p)
            return true;
        return false;
    }

    public Utilizador iniciarSessao(String n, String p)  throws UtilizadorInexistente, PasswordInvalida{
        boolean valid = this.utilizadorExistente(n);
        if(!valid)
            throw new UtilizadorInexistente("O utilizador " + n + " é inexistente.");
        valid = this.validarSessao(n, p);
        if(!valid)
            throw new PasswordInvalida("A password está é inválida");

        Utilizador aux;
        if(this.comuns.containsKey(n))
            aux = this.comuns.get(n);
        else
            aux = this.admins.get(n);
        return aux;
    }

    /* Terminar sessão */

    public void terminarSessao(){
        System.out.println("Até logo!");
    }

    /* Registar utilizador */

    public boolean validarAux(String n, String e){
        if(this.admins.containsKey(n) || this.comuns.containsKey(n))
            return false;
        if(this.admins.containsEmail(e) || this.comuns.containsEmail(n))
            return false;
        return true;
    }

    public boolean validar(String n, String e) throws UtilizadorInvalido{
        boolean valid = validarAux(n, e);
        if(!valid)
            throw new UtilizadorInvalido("Utilizador inválido(mail ou nome existente).");
        return true;
    }

    public void registarComum(String nome, String email, String pass){
        Comum c = new Comum(nome, pass, email, new HashMap<>(), new HashMap<>(), this.conteudo, this.comuns, new ArrayList<>(), new ArrayList<>());
        this.comuns.put(nome, c);
    }

    public void registarAdmin(String nome, String email, String pass){
        Administrador a = new Administrador(nome, pass, email);
        this.admins.put(nome, a);
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
        List<Conteudo> l = new ArrayList<>();
        for(Conteudo x : c){
            if(this.conteudo.containsKey(x.getNome()))
                l.add(x.clone());
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

    public void upload(List<Conteudo> conteudo, Comum c) throws ConteudoInvalido{
        boolean valid = validarConteudo(conteudo);
        if(!valid)
            throw new ConteudoInvalido("O conteudo + " + conteudo + " tem ficheiro(s) com formato inválido.");
        List<Conteudo> existe = conteudoIgual(conteudo);
        if(existe.size() != 0){
            showUtilizador(existe, c);
            addPotenciais(existe, c);
            existe = elimina(existe, conteudo);
        }
        geraCategorias(conteudo);
        carrega(conteudo);
        showUtilizador(conteudo, c);
        addPotenciais(conteudo, c);
    }

    /* Alterar Categoria de Conteúdo */

    public void alterarCategoria(String nome, Comum c, String categoria){
        this.comuns.alterarCategoria(nome, c, categoria);
    }

    public void alterarConteudo(String nome, Comum c, String categoria){
        alterarCategoria(nome, c, categoria);
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

    public void playPlaylist(List<String> playlist){
        for(String s : playlist)
            play(s);
    }

    public void reproduzirPlaylist(String p, Utilizador u,boolean shuffle){
        List<Conteudo> playlist = this.comuns.get(u).getPlaylists().get(p);
        if(shuffle)
            Collections.shuffle(playlist);
        playPlaylist(playlist);
    }
    public void aleatorio(){
        Set<String> x = this.conteudo.keySet();
        List<String> aux = (List<String>) x;
        Collections.shuffle(aux);
        playPlaylist(aux);
    }

    /* Convidado */

    public void connectSystem(){
        System.out.println("Bem vindo!");
    }

    /* Potenciais Amigos */

    public List<String> potenciaisAmigos(Comum c){
        return c.getPotAmigos();
    }

    /* Download Conteúdo */

    public Set<String> biblioteca(Comum c){
        return this.comuns.get(c).getMyMusicas().keySet();
    }

    public void download(String conteudo){
        down(conteudo);
    }
}
