package smc.Business;

public abstract class Utilizador {

    private String nome;
    private String password;
    private String email;

    public Utilizador(){
        this.nome = "";
        this.password = "";
        this.email = "";
    }

    public Utilizador(String n, String p, String e){
        this.setNome(n);
        this.setPassword(p);
        this.setEmail(e);
    }

    public Utilizador(Utilizador d){
        this.nome = d.getNome();
        this.password = d.getPassword();
        this.email = d.getEmail();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public abstract Utilizador clone();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public abstract String toString();
}
