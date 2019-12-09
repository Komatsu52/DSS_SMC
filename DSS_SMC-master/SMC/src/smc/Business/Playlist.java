package smc.Business;

import smc.Data.ConteudoDAO;

public class Playlist {
    private ConteudoDAO conteudo;

    public Playlist(){
        this.conteudo = new ConteudoDAO();
    }

    public Playlist(ConteudoDAO c){
        this.setConteudo(c);
    }

    public Playlist(Playlist p){
        this.conteudo = p.getConteudo();
    }

    public ConteudoDAO getConteudo() {
        return this.conteudo.clone();
    }

    public void setConteudo(ConteudoDAO c) {
        this.conteudo = c.clone();
    }

    public Playlist clone(){
        return new Playlist(this);
    }

    public int hashCode() {
        int hash = 7;
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
            return (p.getConteudo().equals(this.getConteudo()));
        }
    }
}
