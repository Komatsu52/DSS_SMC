package smc.Business;

import smc.Data.ComumDAO;
import smc.Data.ConteudoDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comum extends Utilizador{

    private Map<String, String> myMusicas;
    private Map<String, Playlist> playlists;
    private ConteudoDAO biblioteca;
    private ComumDAO comuns;
    private List<String> amigos;
    private List<String> potAmigos;

    public Comum(){
        super();
        this.myMusicas = new HashMap<>();
        this.playlists = new HashMap<>();
        this.biblioteca = new ConteudoDAO();
        this.comuns = new ComumDAO();
        this.amigos = new ArrayList<>();
        this.potAmigos = new ArrayList<>();
    }

    public Comum(String n, String p, String e, Map<String, String> mm, Map<String, Playlist> play, ConteudoDAO bp, ComumDAO c, List<String> a, List<String> pa){
        super(n, p, e);
        this.setMyMusicas(mm);
        this.setPlaylists(play);
        this.setBiblioteca(bp);
        this.setComuns(c);
        this.setAmigos(a);
        this.setPotAmigos(pa);
    }

    public Comum (Comum a){
        super(a);
        this.myMusicas = a.getMyMusicas();
        this.playlists = a.getPlaylists();
        this.biblioteca = a.getBiblioteca();
        this.comuns = a.getComuns();
        this.amigos = a.getAmigos();
        this.potAmigos = a.getPotAmigos();
    }

    public Map<String, String> getMyMusicas() {
        Map<String, String> aux = new HashMap<>();
        for(String k : this.myMusicas.keySet()){
            aux.put(k, this.myMusicas.get(k));
        }
        return aux;
    }

    public void setMyMusicas(Map<String, String> mm) {
        this.myMusicas = new HashMap<>();
        for(String k : mm.keySet()){
            this.myMusicas.put(k, mm.get(k));
        }
    }

    public Map<String, Playlist> getPlaylists(){
        Map<String, Playlist> aux = new HashMap<>();
        for(String s : this.playlists.keySet())
            aux.put(s, this.playlists.get(s));
        return aux;
    }

    public void setPlaylists(Map<String, Playlist> p) {
        this.playlists = new HashMap<>();
        for(String s : p.keySet())
            this.playlists.put(s, p.get(s));
    }

    public ConteudoDAO getBiblioteca() {
        return this.biblioteca;
    }

    public void setBiblioteca(ConteudoDAO biblioteca) {
        this.biblioteca = biblioteca;
    }


    public ComumDAO getComuns() {
        return this.comuns;
    }

    public void setComuns(ComumDAO comuns) {
        this.comuns = comuns;
    }

    public List<String> getAmigos() {
        List<String> aux = new ArrayList<>();
        for(String s : this.amigos){
            aux.add(s);
        }
        return aux;
    }

    public void setAmigos(List<String> a) {
        this.amigos = new ArrayList<>();
        for(String s : a){
            this.amigos.add(s);
        }
    }

    public List<String> getPotAmigos() {
        List<String> aux = new ArrayList<>();
        for(String s : this.potAmigos){
            aux.add(s);
        }
        return aux;
    }

    public void setPotAmigos(List<String> a) {
        this.potAmigos = new ArrayList<>();
        for(String s : a){
            this.potAmigos.add(s);
        }
    }

    public Comum clone() {
        return new Comum(this);
    }

    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Comum a = (Comum) o;
            return (a.getEmail().equals(this.getEmail())
                    && a.getNome().equals(this.getNome())
                    && a.getPassword().equals(this.getPassword())
                    && a.getMyMusicas().equals(this.getMyMusicas())
                    && a.getPlaylists().equals(this.getPlaylists())
                    && a.getAmigos().equals(this.getAmigos())
                    && a.getPotAmigos().equals(this.getPotAmigos()));
        }
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.getNome().hashCode();
        hash = 31 * hash + this.getPassword().hashCode();
        hash = 31 * hash + this.getEmail().hashCode();
        hash = 31 * hash + this.getMyMusicas().hashCode();
        hash = 31 * hash + this.getPlaylists().hashCode();
        hash = 31 * hash + this.getAmigos().hashCode();
        hash = 31 * hash + this.getPotAmigos().hashCode();
        return hash;
    }

    public String toString(){
        return "Nome: " + this.getNome()
                   + ";\nPassword: " + this.getPassword()
                   + ";\nEmail: " + this.getEmail()
                   + ";\n";
    }

    public void alterarCategoria(String nome, String cat){
        this.myMusicas.remove(nome);
        this.myMusicas.put(nome, cat);
    }
}
