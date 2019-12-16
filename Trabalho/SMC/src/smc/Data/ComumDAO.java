package smc.Data;

import smc.Business.Comum;
import smc.Business.Conteudo;
import smc.Business.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ComumDAO implements Map<String, Comum> {

    private Connection con;

    public int size(){
        int size = -1;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM Comum");
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
        return this.size()==0;
    }

    public boolean containsKey(Object key){
        boolean res = false;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Comum WHERE nome_Comum = ?");
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

        if(value.getClass().getName().equals("smc.Business.Comum")){
            Comum c = (Comum)value;
            String user = c.getNome();
            Comum co = this.get(user);
            if(co.equals(c)){
                res=true;
            }
        }
        return res;
    }

    public Comum get(Object key){
        Comum c = new Comum();

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Comum WHERE nome_Comum = ?");
            ps.setString(1,(String) key);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                c.setNome(rs.getString("nome_Comum"));
                c.setEmail(rs.getString("email_Comum"));
                c.setPassword(rs.getString("password_Comum"));

                Map<String, String> mus = new HashMap<>();
                ps = con.prepareStatement("SELECT * FROM MyConteudo WHERE Comum_nome_Comum = ?");
                ps.setString(1, (String) key);
                rs = ps.executeQuery();
                while(rs.next()){
                    mus.put(rs.getString("nome_MyConteudo"), rs.getString("categoria_MyConteudo"));
                }
                c.setMyConteudo(mus);

                Map<String, Playlist> play = new HashMap<>();
                PreparedStatement ps2;
                ResultSet rs2;
                ps = con.prepareStatement("SELECT * FROM Playlist WHERE Comum_nome_Comum = ?");
                ps.setString(1, (String) key);
                rs = ps.executeQuery();
                while (rs.next()){
                    Map<String, String> aux = new HashMap<>();
                    Playlist auxplay = new Playlist();
                    ps2 = con.prepareStatement("SELECT * FROM ConteudoPlaylist WHERE Playlist_Comum_nome_Comum = ? AND Playlist_nome_Playlist = ?");
                    ps2.setString(1, (String) key);
                    ps2.setString(2, rs.getString("nome_Playlist"));
                    rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        aux.put(rs2.getString("nome_ContPlay"), rs2.getString("categoria_ContPlay"));
                    }
                    auxplay.setMyConteudo(aux);
                    play.put(rs.getString("nome_Playlist"), auxplay);
                }
                c.setPlaylists(play);

                List<String> ami = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM Amigo WHERE Comum_nome_Comum = ?");
                ps.setString(1,(String) key);
                rs = ps.executeQuery();
                while(rs.next()){
                    ami.add(rs.getString("nome_Amigo"));
                }
                c.setAmigos(ami);

                List<String> potami = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM PotAmigo WHERE Comum_nome_Comum = ?");
                ps.setString(1,(String) key);
                rs = ps.executeQuery();
                while(rs.next()){
                    potami.add(rs.getString("nome_PotAmigo"));
                }
                c.setPotAmigos(potami);
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

    public Comum put(String key, Comum value){
        Comum c;

        if(this.containsKey(key)){
            c = this.get(key);
        }
        else c = value;
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Comum WHERE nome_Comum = ?");
            ps.setString(1,key);
            ps.executeUpdate();

            ps = con.prepareStatement("INSERT INTO Comum (nome_Comum,email_Comum,password_Comum) VALUES (?,?,?)");
            ps.setString(1,key);
            ps.setString(2,value.getEmail());
            ps.setString(3,value.getPassword());
            ps.executeUpdate();

            Map<String, String> mus = value.getMyConteudo();
            if(mus.keySet().size()!=0){
                for(String m : mus.keySet()){
                    ps = con.prepareStatement("INSERT INTO MyConteudo (Comum_nome_Comum,nome_MyConteudo,categoria_MyConteudo) VALUES (?,?,?)");
                    ps.setString(1, key);
                    ps.setString(2, m);
                    ps.setString(3, mus.get(m));
                    ps.executeUpdate();
                }
            }

            Map<String, Playlist> play = value.getPlaylists();
            if(play.keySet().size()!=0){
                for(String p : play.keySet()){
                    ps = con.prepareStatement("INSERT INTO Playlist (Comum_nome_Comum,nome_Playlist) VALUES (?,?)");
                    ps.setString(1, key);
                    ps.setString(2, p);
                    ps.executeUpdate();

                    mus = play.get(p).getMyConteudo();
                    if(mus.keySet().size()!=0){
                        for (String m : mus.keySet()) {
                            ps = con.prepareStatement("INSERT INTO ConteudoPlaylist (Playlist_Comum_nome_Comum,Playlist_nome_Playlist,nome_ContPlay,categoria_ContPlay) VALUES (?,?,?,?)");
                            ps.setString(1, key);
                            ps.setString(2, p);
                            ps.setString(3, m);
                            ps.setString(4, mus.get(m));
                            ps.executeUpdate();
                        }
                    }
                }
            }

            List<String> ami = value.getAmigos();
            if(ami.size()!=0){
                for(String a : ami){
                    ps = con.prepareStatement("INSERT INTO Amigo (Comum_nome_Comum,nome_Amigo) VALUES (?,?)");
                    ps.setString(1,key);
                    ps.setString(2,a);
                    ps.executeUpdate();
                }
            }

            ami = value.getPotAmigos();
            if(ami.size()!=0){
                for(String a : ami){
                    ps = con.prepareStatement("INSERT INTO PotAmigo (Comum_nome_Comum,nome_PotAmigo) VALUES (?,?)");
                    ps.setString(1,key);
                    ps.setString(2,a);
                    ps.executeUpdate();
                }
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

    public Comum remove(Object key){
        Comum c = this.get((String) key);
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM Comum WHERE nome_Comum = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM MyConteudo WHERE Comum_nome_Comum = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM Playlist WHERE Comum_nome_Comum = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM ConteudoPlaylist WHERE Playlist_Comum_nome_Comum = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM Amigo WHERE Comum_nome_Comum = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM Amigo WHERE nome_Amigo = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM PotAmigo WHERE Comum_nome_Comum = ?");
            ps.setString(1, (String) key);
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE FROM PotAmigo WHERE nome_PotAmigo = ?");
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

    public void putAll(Map<? extends String, ? extends Comum> m){
        for(Comum com : m.values()) {
            put(com.getNome(), com);
        }
    }

    public void clear(){

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE * FROM Comum");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE * FROM MyConteudo");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE * FROM Playlist");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE * FROM ConteudoPlaylist");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE * FROM Amigo");
            ps.executeUpdate();
            ps = con.prepareStatement("DELETE * FROM PotAmigo");
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
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Comum");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                set.add(rs.getString("nome_Comum"));
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

    public Collection<Comum> values(){
        Set<Comum> set = new HashSet<>();
        Set<String> keys = new HashSet<>(this.keySet());
        for(String key : keys){
            set.add(this.get(key));
        }
        return set;
    }

    public Set<Entry<String, Comum>> entrySet(){
        Set<String> keys = new HashSet<>(this.keySet());

        HashMap<String, Comum> map = new HashMap<>();
        for(String key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }

    public boolean containsEmail(String em){
        boolean res = false;

        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Comum WHERE email_Comum = ?");
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

    public void adicionarConteudo(Conteudo c, String n){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("INSERT INTO MyConteudo (Comum_nome_Comum,nome_MyConteudo,categoria_MyConteudo) VALUES (?,?,?)");
            ps.setString(1, n);
            ps.setString(2, c.getNome());
            ps.setString(3, c.getCategoria());
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

    public void alterarCategoria(String comum, String nome, String categoria){
        try{
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("DELETE FROM MyConteudo WHERE Comum_nome_Comum = ? AND nome_MyConteudo = ?");
            ps.setString(1, comum);
            ps.setString(2, nome);
            ps.executeUpdate();
            ps = con.prepareStatement("INSERT INTO MyConteudo (Comum_nome_Comum,nome_MyConteudo,categoria_MyConteudo) VALUES (?,?,?)");
            ps.setString(1, comum);
            ps.setString(2, nome);
            ps.setString(3, categoria);
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

    public List<String> getPlaylistEspecifica(String nomeC, String nomeP){
        List<String> aux = new ArrayList<>();
        try {
            con = Connect.connect();
            PreparedStatement ps = con.prepareStatement("SELECT FROM ConteudoPlaylist WHERE Playlist_Comum_nome_Comum = ? AND Playlist_nome_Playlist = ?");
            ps.setString(1, nomeC);
            ps.setString(2, nomeP);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                aux.add(rs.getString("nome_ContPlay"));
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
        return aux;
    }
}
