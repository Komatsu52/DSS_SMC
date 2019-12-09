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
}
