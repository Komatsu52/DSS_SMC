package smc.Business;

import smc.Data.ConteudoDAO;

import java.util.HashMap;
import java.util.Map;

public class Playlist {
    private Map<String, String> myMusicas;
    private ConteudoDAO conteudo;

    public Playlist(){
        this.myMusicas = new HashMap<>();
        this.conteudo = new ConteudoDAO();
    }

    public Playlist(Map<String, String> mm, ConteudoDAO c){
        this.setMyMusicas(mm);
        this.setConteudo(c);
    }

    public Playlist(Playlist p){
        this.myMusicas = p.getMyMusicas();
        this.conteudo = p.getConteudo();
    }

    public ConteudoDAO getConteudo() {
        ConteudoDAO aux = new ConteudoDAO();
        for(String k : this.conteudo.keySet()){
            aux.put(k, this.conteudo.get(k));
        }
        return aux;
    }

    public void setConteudo(ConteudoDAO c) {
        this.conteudo = new ConteudoDAO();
        for(String k : c.keySet()){
            this.conteudo.put(k, c.get(k));
        }
    }

    public Map<String, String> getMyMusicas(){
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

    public Playlist clone(){
        return new Playlist(this);
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.myMusicas.hashCode();
        hash = 31 * hash + this.conteudo.hashCode();
        return hash;
    }

    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else {
            Playlist p = (Playlist) o;
            return (p.getMyMusicas().equals(this.getMyMusicas())
                    && p.getConteudo().equals(this.getConteudo()));
        }
    }
}
