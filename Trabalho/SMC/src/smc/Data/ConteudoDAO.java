package smc.Data;

import smc.Business.Conteudo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ConteudoDAO implements Map<String, Conteudo> {

    private Connection con;

    public int size() {
        int size = -1;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Conteudo");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                size = rs.getInt(1);
            }
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return size;
    }

    public boolean isEmpty() {
        return (this.size() == 0);
    }

    public boolean containsKey(Object key){
        boolean res = false;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Conteudo WHERE nome_Conteudo = ?");
            ps.setString(1,(String) key);
            ResultSet rs = ps.executeQuery();
            res = rs.next();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return res;
    }

    public boolean containsValue(Object value) {
        boolean res = false;

        if(value.getClass().getName().equals("smc.Business.Conteudo")){
            Conteudo c = (Conteudo) value;
            String nome = c.getNome();
            Conteudo cn = this.get(nome);
            if(cn.equals(c)){
                res=true;
            }
        }
        return res;
    }

    public Conteudo get(Object key){
        Conteudo c = new Conteudo();

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Conteudo WHERE nome_Conteudo = ?");
            ps.setString(1,(String) key);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setNome(rs.getString("nome_Conteudo"));
                c.setPath(rs.getString("path_Conteudo"));
                c.setArtista(rs.getString("artista_Conteudo"));
                c.setCategoria(rs.getString("categora_Conteudo"));

            }
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return c;
    }

    public Conteudo put(String key, Conteudo value) {
        Conteudo c;

        if(this.containsKey(key)){
            c = this.get(key);
        }
        else c = value;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Conteudo WHERE nome_Conteudo = ?");
            ps.setString(1,key);
            ps.executeUpdate();

            ps = con.prepareStatement("INSERT INTO Conteudo (nome_Conteudo, path_Conteudo, artista_Conteudo, categoria_Conteudo) VALUES (?,?,?,?)");
            ps.setString(1,key);
            ps.setString(2,value.getPath());
            ps.setString(3,value.getArtista());
            ps.setString(4,value.getCategoria());
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return c;
    }

    public Conteudo remove(Object key){
        Conteudo c = this.get((String) key);
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Conteudo WHERE nome_Conteudo = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM MyConteudo WHERE nome_MyConteudo = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM ConteudoPlaylist WHERE nome_ContPlay= ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return c;
    }

    public void putAll(Map<? extends String, ? extends Conteudo> m){
        for(Conteudo cont : m.values()) {
            put(cont.getNome(), cont);
        }

    }

    public void clear(){

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE * FROM Conteudo");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE * FROM MyConteudo");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE * FROM ConteudoPlaylist");
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
    }

    public Set<String> keySet(){
        Set<String> set = null;

        try{
            con = Connect.connect();
            set = new HashSet<>();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Conteudo");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                set.add(rs.getString("nome_Conteudo"));
            }
        }
        catch(SQLException e){
            System.out.printf(e.getMessage());
        }
        finally{
            try{
                Connect.close(con);
            }
            catch(Exception e){
                System.out.printf(e.getMessage());
            }
        }
        return set;
    }

    public Collection<Conteudo> values(){
        Set<Conteudo> set = new HashSet<>();
        Set<String> keys = new HashSet<>(this.keySet());
        for(String key : keys){
            set.add(this.get(key));
        }
        return set;
    }

    public Set<Entry<String, Conteudo>> entrySet(){
        Set<String> keys = new HashSet<>(this.keySet());

        HashMap<String, Conteudo> map = new HashMap<>();
        for(String key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
}
