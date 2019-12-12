package smc.Business;

import java.time.Duration;

public class Conteudo {
    private String nome;
    private byte[] codigo;
    private Duration duracao;
    private String categoria;

    public Conteudo(){
        this.nome = "";
        this.codigo = new byte[1024];
        this.duracao = Duration.ZERO;
        this.categoria = "";
    }

    public Conteudo(String n, byte[] cod, Duration d, String c){
        this.setNome(n);
        this.setCodigo(cod);
        this.setDuracao(d);
        this.setCategoria(c);
    }

    public Conteudo(Conteudo c){
        this.nome = c.getNome();
        this.codigo = c.getCodigo();
        this.duracao = c.getDuracao();
        this.categoria = c.getCategoria();
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public byte[] getCodigo() {
        byte[] aux = new byte[this.codigo.length];
        for(int i = 0; i < this.codigo.length; i++)
            aux[i] = this.codigo[i];
        return aux;
    }

    public void setCodigo(byte[] cod) {
        this.codigo = new byte[cod.length];
        for(int i = 0; i < this.codigo.length; i++)
            this.codigo[i] = cod[i];
    }

    public Duration getDuracao() {
        return this.duracao;
    }

    public void setDuracao(Duration d) {
        this.duracao = d;
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
}
