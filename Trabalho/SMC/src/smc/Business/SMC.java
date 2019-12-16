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

    public SMC(){
        this.admins = new AdministradorDAO();
        this.comuns = new ComumDAO();
        this.conteudo = new ConteudoDAO();
    }

    /* Iniciar sessão */

    public boolean utilizadorExistente(String n){
        return (this.comuns.containsKey(n) || this.admins.containsKey(n));
    }

    public boolean validarSessao(String n, String p){
        return (this.admins.get(n).getPassword().equals(p) || this.comuns.get(n).getPassword().equals(p));
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
        Comum c = new Comum(nome, pass, email, new HashMap<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>());
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

    public void showUtilizador(List<Conteudo> x, String n){
        for(Conteudo c : x){
            this.comuns.adicionarConteudo(c, n);
        }
    }

    public void addPotenciais(List<Conteudo> x, String n){
        for(Utilizador another : this.comuns.getComuns())
            for(Conteudo from_antoher : another.getBiblioteca())
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

    public void upload(List<Conteudo> conteudo, String n) throws ConteudoInvalido{
        boolean valid = validarConteudo(conteudo);
        if(!valid)
            throw new ConteudoInvalido("O conteudo + " + conteudo + " tem ficheiro(s) com formato inválido.");
        List<Conteudo> existe = conteudoIgual(conteudo);
        if(existe.size() != 0){
            showUtilizador(existe, n);
            addPotenciais(existe, n);
            existe = elimina(existe, conteudo);
        }
        geraCategorias(conteudo);
        carrega(conteudo);
        showUtilizador(conteudo, n);
        addPotenciais(conteudo, n);
    }

    /* Alterar Categoria de Conteúdo */

    public void alterarCatConteudo(String comum, String nome, String categoria){
        this.comuns.alterarCategoria(comum, nome, categoria);
    }

    /* Reproduzir Conteúdo */

    public boolean validarExistencia(){
        if(this.conteudo.size() == 0)
            return false;
        return true;
    }
    public boolean existeConteudo() throws ConteudoInexistente{
        boolean valid = validarExistencia();
        if(!valid)
            throw  new ConteudoInexistente("Não existe conteúdo na biblioteca do sistema.");
        return valid;
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

    public boolean validarExistenciaPlaylist(String n){
        if(this.comuns.get(n).getPlaylists().size() == 0)
            return false;
        return true;
    }

    public Map<String, Playlist> existePlaylists(String n) throws PlaylistsInexistentes{
        boolean valid = validarExistenciaPlaylist(n);
        if(!valid)
            throw  new PlaylistsInexistentes("Você não tem playlists.");
        return this.comuns.get(n).getPlaylists();
    }

    public void playPlaylist(List<String> playlist){
        for(String s : playlist)
            play(s);
    }

    public void reproduzirPlaylist(String p, String n,boolean shuffle){
        List<String> aux = this.comuns.getPlaylistEspecifica(n, p);
        if(shuffle)
            Collections.shuffle(aux);
        playPlaylist(aux);
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

    public Set<String> biblioteca(String n){
        return this.comuns.get(n).getMyConteudo().keySet();
    }

    public void download(String conteudo){
        down(conteudo);
    }

}
