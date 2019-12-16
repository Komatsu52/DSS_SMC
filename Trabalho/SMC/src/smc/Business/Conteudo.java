package smc.Business;

import java.time.Duration;

public class Conteudo {
    private String nome;
    private String path;
    private String artista;
    private String categoria;

    public Conteudo(){
        this.nome = "";
        this.path = "";
        this.artista = "";
        this.categoria = "";
    }

    public Conteudo(String n, String p, String a, String c){
        this.setNome(n);
        this.setPath(p);
        this.setArtista(a);
        this.setCategoria(c);
    }

    public Conteudo(Conteudo c){
        this.nome = c.getNome();
        this.path = c.getPath();
        this.artista = c.getArtista();
        this.categoria = c.getCategoria();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String p) {
        this.path = p;
    }

    public String getArtista() {
        return this.artista;
    }

    public void setArtista(String a) {
        this.artista = a;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String c) {
        this.categoria = c;
    }

    public Conteudo clone(){
        return new Conteudo(this);
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.getNome().hashCode();
        hash = 31 * hash + this.getPath().hashCode();
        hash = 31 * hash + this.getArtista().hashCode();
        hash = 31 * hash + this.getCategoria().hashCode();
        return hash;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;
        else {
            Conteudo c = (Conteudo) o;
            return (c.getNome().equals(this.getNome())
                    && c.getPath().equals(this.getPath())
                    && c.getArtista().equals(this.getArtista())
                    && c.getCategoria().equals(this.getCategoria()));
        }
    }

    public String toString(){
        return "Nome: " + this.getNome()
                + ";\nArtista: " + this.getArtista()
                + ";\nCategoria: " + this.getCategoria()
                + ";\n";
    }
}