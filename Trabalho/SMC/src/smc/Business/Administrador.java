
public class Administrador extends Utilizador {

    public Administrador(){
        super();
    }

    public Administrador(String n, String p, String e){
        super(n, p, e);
    }

    public Administrador(Administrador a){
        super(a);
    }

    public Administrador clone() {
        return new Administrador(this);
    }

    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Administrador a = (Administrador) o;
            return (a.getEmail() == this.getEmail()
                    && a.getNome() == this.getNome()
                    && a.getPassword() == this.getPassword());
        }
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.getNome().hashCode();
        hash = 31 * hash + this.getPassword().hashCode();
        hash = 31 * hash + this.getEmail().hashCode();
        return hash;
    }

    public String toString(){
        String aux = "Nome: " + this.getNome() + ";\nPassword: " + this.getPassword() + ";\nEmail: " + this.getEmail() + ";\n";
        return aux;
    }
}
