import java.util.HashMap;
import java.util.Map;

public class Playlist {
    private Map<String, String> myConteudo;

    public Playlist(){
        this.myConteudo = new HashMap<>();
    }

    public Playlist(Map<String, String> mm){
        this.setMyConteudo(mm);
    }

    public Playlist(Playlist p){
        this.myConteudo = p.getMyConteudo();
    }

    public Map<String, String> getMyConteudo(){
        Map<String, String> aux = new HashMap<>();
        for(String k : this.myConteudo.keySet()){
            aux.put(k, this.myConteudo.get(k));
        }
        return aux;
    }

    public void setMyConteudo(Map<String, String> mm) {
        this.myConteudo = new HashMap<>();
        for(String k : mm.keySet()){
            this.myConteudo.put(k, mm.get(k));
        }
    }

    public Playlist clone(){
        return new Playlist(this);
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.myConteudo.hashCode();
        return hash;
    }

    public boolean equals(Object o) {
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else {
            Playlist p = (Playlist) o;
            return (p.getMyConteudo().equals(this.getMyConteudo()));
        }
    }
}
