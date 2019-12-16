package smc.Data;

import smc.Business.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AdministradorDAO implements Map<String, Administrador> {

    private Connection con;

    public int size() {
        int size = -1;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Administrador");
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Administrador WHERE nome_Administrador = ?");
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

        if(value.getClass().getName().equals("smc.Business.Administrador")){
            Administrador a = (Administrador) value;
            String nome = a.getNome();
            Administrador ad = this.get(nome);
            if(ad.equals(a)){
                res=true;
            }
        }
        return res;
    }

    public Administrador get(Object key) {
        Administrador a = new Administrador();

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Administrador WHERE nome_Administrador = ?");
            ps.setString(1,(String) key);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                a.setNome(rs.getString("nome_Administrador"));
                a.setEmail(rs.getString("email_Administrador"));
                a.setPassword(rs.getString("password_Administrador"));
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
        return a;
    }

    public Administrador put(String key, Administrador value) {
        Administrador a;

        if(this.containsKey(key)){
            a = this.get(key);
        }
        else a = value;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Administrador WHERE nome_Administrador = ?");
            ps.setString(1,key);
            ps.executeUpdate();

            ps = con.prepareStatement("INSERT INTO Administrador (nome_Administrador, email_Administrador, password_Administrador) VALUES (?,?,?)");
            ps.setString(1,key);
            ps.setString(2,value.getEmail());
            ps.setString(3,value.getPassword());
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
        return a;
    }

    public Administrador remove(Object key) {
        Administrador a = this.get((String) key);
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Administrador WHERE nome_Administrador = ?");
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
        return a;
    }

    public void putAll(Map<? extends String, ? extends Administrador> m) {
        for(Administrador admin : m.values()) {
            put(admin.getNome(), admin);
        }
    }

    public void clear(){

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE * FROM Administrador");
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Administrador");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                set.add(rs.getString("nome_Administrador"));
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

    public Collection<Administrador> values() {
        Set<Administrador> set = new HashSet<>();
        Set<String> keys = new HashSet<>(this.keySet());
        for(String key : keys){
            set.add(this.get(key));
        }
        return set;
    }

    public Set<Entry<String, Administrador>> entrySet() {
        Set<String> keys = new HashSet<>(this.keySet());

        HashMap<String, Administrador> map = new HashMap<>();
        for(String key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }

    public boolean containsEmail(String em){
        boolean res = false;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Administrador WHERE email_Administrador= ?");
            ps.setString(1,em);
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
}
