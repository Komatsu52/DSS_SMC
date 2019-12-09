package smc.Business;

import smc.Data.ConteudoDAO;
import smc.Data.UtilizadorDAO;

import java.util.HashMap;
import java.util.Map;

public class Comum extends Utilizador{

    private Map<String, String> myCategorias;
    private Playlist playlist;
    private ConteudoDAO biblioPessoal;
    private UtilizadorDAO amigos;
    private UtilizadorDAO potAmigos;

    public Comum(){
        super();
        this.myCategorias = new HashMap<>();
        this.playlist = new Playlist();
        this.biblioPessoal = new ConteudoDAO();
        this.amigos = new UtilizadorDAO();
        this.potAmigos = new UtilizadorDAO();
    }

    public Comum(String n, String p, String e, Map<String, String> mc, Playlist play, ConteudoDAO bp, UtilizadorDAO a, UtilizadorDAO pa){
        super(n, p, e);
        this.setMyCategorias(mc);
        this.setPlaylist(play);
        this.setBiblioPessoal(bp);
        this.setAmigos(a);
        this.setPotAmigos(pa);
    }

    public Comum (Comum a){
        super(a);
        this.myCategorias = a.getMyCategorias();
        this.playlist = a.getPlaylist();
        this.biblioPessoal = a.getBiblioPessoal();
        this.amigos = a.getAmigos();
        this.potAmigos = a.getPotAmigos();
    }

    public Map<String, String> getMyCategorias() {
        Map<String, String> aux = new HashMap<String, String>();
        for(String k : this.myCategorias.keySet()){
            aux.put(k, this.myCategorias.get(k));
        }
        return aux;
    }

    public void setMyCategorias(Map<String, String> myC) {
        this.myCategorias = new HashMap<String, String>();
        for(String k : myC.keySet()){
            this.myCategorias.put(k, myC.get(k));
        }
    }

    public Playlist getPlaylist(){
        return this.playlist.clone();
    }

    public void setPlaylist(Playlist p) {
        this.playlist = p.clone();
    }

    public ConteudoDAO getBiblioPessoal() {
        return this.biblioPessoal.clone();
    }

    public void setBiblioPessoal(ConteudoDAO bP) {
        this.biblioPessoal = bP.clone();
    }

    public UtilizadorDAO getAmigos() {
        return this.amigos.clone();
    }

    public void setAmigos(UtilizadorDAO a) {
        this.amigos = a.clone();
    }

    public UtilizadorDAO getPotAmigos() {
        return this.potAmigos.clone();
    }

    public void setPotAmigos(UtilizadorDAO pA) {
        this.potAmigos = pA.clone();
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
            return (a.getEmail() == this.getEmail()
                    && a.getNome() == this.getNome()
                    && a.getPassword() == this.getPassword()
                    && a.getMyCategorias().equals(this.getMyCategorias())
                    && a.getPlaylist().equals(this.getPlaylist())
                    && a.getBiblioPessoal().equals(this.getBiblioPessoal())
                    && a.getAmigos().equals(this.getAmigos())
                    && a.getPotAmigos().equals(this.getPotAmigos()));
        }
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.getNome().hashCode();
        hash = 31 * hash + this.getPassword().hashCode();
        hash = 31 * hash + this.getEmail().hashCode();
        hash = 31 * hash + this.getMyCategorias().hashCode();
        hash = 31 * hash + this.getPlaylist().hashCode();
        hash = 31 * hash + this.getBiblioPessoal().hashCode();
        hash = 31 * hash + this.getAmigos().hashCode();
        hash = 31 * hash + this.getPotAmigos().hashCode();
        return hash;
    }

    public String toString(){
        String aux = "Nome: " + this.getNome()
                   + ";\nPassword: " + this.getPassword()
                   + ";\nEmail: " + this.getEmail()
                   + ";\n";
        return aux;
    }
}
