package smc.Business;

import smc.Data.AdministradorDAO;
import smc.Data.ComumDAO;
import smc.Data.ConteudoDAO;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class SMC {
    private AdministradorDAO admins;
    private ComumDAO comuns;
    private ConteudoDAO conteudo;

    String[] categorias = {"Rock", "Hip Hop", "Blues", "Clássico","Eletrónica","Fado","Funk","Grunge","Jazz","Kizomba","Pop","Reggae"} ;


    public SMC(){
        this.admins = new AdministradorDAO();
        this.comuns = new ComumDAO();
        this.conteudo = new ConteudoDAO();
    }

                        /* Iniciar sessão */

    /**
     * Verifica se existe no sistema algum Utilizador Comum
     * com o nome inserido. Caso exista, verifica se a password inserida
     * corresponde ao utilizador.
     * @param nome  nome do Utilizador
     * @param password pass do Utilizador
     * @return true or false
     */
    public boolean comumExistente(String nome, String password){
        if(!this.comuns.containsKey(nome))
            return false;
        else
            return this.comuns.get(nome).getPassword().equals(password);
    }

    /**
     * Verifica se existe no sistema algum Utilizador Admin
     * com o nome inserido. Caso exista, verifica se a password inserida
     * corresponde ao utilizador.
     * @param nome  nome do Utilizador
     * @param password pass do Utilizador
     * @return true or false
     */
    public boolean adminExistente(String nome, String password){
        if(!this.admins.containsEmail(nome))
            return false;
        else
            return this.admins.get(nome).getPassword().equals(password);
    }

    /**
     * Devolve o utilizador que iniciou sessão.
     * @param nome  nome do Utilizador
     * @param password pass do Utilizador
     * @throws UtilizadorInexistente
     * @return Utilizador
     */
    public Utilizador iniciarSessao(String nome, String password)  throws UtilizadorInexistente, PasswordInvalida{
        boolean valid = this.comumExistente(nome, password);
        boolean valid2 = this.adminExistente(nome, password);
        if(!valid && !valid2)
            throw new UtilizadorInexistente("Utilizador " + nome + " é inexistente ou a password inserida é inválida.");

        Utilizador aux;
        if(valid)
            aux = this.comuns.get(nome);
        else
            aux = this.admins.get(nome);
        return aux;
    }

                        /* Terminar sessão */
    /**
     * Termina a sessão do utilizador.
     */
    public void terminarSessao(){
        System.out.println("Até logo!");
    }

                       /* Registar utilizador */

    /**
     * Verifica se existe no sistema algum Utilizador Comum
     * com o nome inserido. Caso não exista, verifica se
     * o email inserido já foi utilizado por outro utilizador.
     * @param nome  nome do Utilizador
     * @param email email do Utilizador
     * @return true or false
     */
    public boolean validarComum(String nome, String email){
        if(this.comuns.containsKey(nome))
            return false;
        if(this.comuns.containsEmail(email))
            return false;
        return true;
    }

    /**
     * Verifica se existe no sistema algum Utilizador Admin
     * com o nome inserido. Caso não exista, verifica se
     * o email inserido já foi utilizado por outro utilizador.
     * @param nome  nome do Utilizador
     * @param email email do Utilizador
     * @return true or false
     */
    public boolean validarAdministrador(String nome, String email){
        if(this.admins.containsKey(nome))
            return false;
        if(this.admins.containsEmail(email))
            return false;
        return true;
    }

    /**
     * Valida os dados para o registo de um novo utilizador.
     * @param nome  nome do Utilizador
     * @param email email do Utilizador
     * @param pass pass do Utilizador
     * @throws UtilizadorInvalido
     * @return true or false
     */
    public boolean validar(String nome, String email, String pass) throws UtilizadorInvalido{
        boolean valid = validarComum(nome, email);
        boolean valid2 = validarAdministrador(nome, email);
        if(!valid && !valid2)
            throw new UtilizadorInvalido("Utilizador já existente com os dados inseridos.");
        return true;
    }

    /**
     * Regista um novo utilizador comum.
     * @param nome  nome do Utilizador
     * @param email email do Utilizador
     * @param pass pass do Utilizador
     */
    public void registarComum(String nome, String email, String pass){
        Comum c = new Comum(nome, pass, email, new HashMap<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>());
        this.comuns.put(nome, c);
    }

    /**
     * Regista um novo utilizador admin.
     * @param nome  nome do Utilizador
     * @param email email do Utilizador
     * @param pass pass do Utilizador
     */
    public void registarAdmin(String nome, String email, String pass){
        Administrador a = new Administrador(nome, pass, email);
        this.admins.put(nome, a);
    }

                    /* Upload de Conteúdo */

    /**
     * Valida os files do conteúdo inserido.
     * @param conteudo  lista dos nomes dos files de conteúdo inseridos
     * @return true or false
     */
    public boolean validarConteudo(List<String> conteudo){
        for(String x : conteudo)
            if ((!x.endsWith(".wav")) && !(x.endsWith(".mp4")))
                return false;
        return true;
    }

    /**
     * Verifica se já existe conteúdo igual ao que o utilizador inseriu.
     * @param conteudo  lista dos nomes dos conteúdos inseridos
     * @return lista dos nomes dos conteúdos iguais
     */
    public List<String> conteudoIgual(List<String> conteudo){
        List<String> l = new ArrayList<>();
        for(String x : conteudo)
            if(this.conteudo.containsKey(x))
                l.add(x);
        return l;
    }

    /**
     * Disponibiliza o conteúdo inserido na biblioteca do utilizador.
     * @param x  lista de nomes dos conteúdos
     * @param n nome do Utilizador
     */
    public void showUtilizador(List<String> x, String n){
        Map<String, String> bibliotecaU = this.comuns.get(n).getMyConteudo();
        for(String c : x)
            bibliotecaU.put(c, this.conteudo.get(c).getCategoria());
        this.comuns.get(n).setMyConteudo(bibliotecaU);
    }

    /**
     * Adiciona utilizadores à lista de Pot Amigos do utilizador
     * @param x  lista de nomes dos conteúdos
     * @param n nome do Utilizador
     */
    public void addPotenciais(List<String> x, String n){
        List<String> lista = this.comuns.get(n).getPotAmigos() ;
        for(String utilizador : this.comuns.keySet()) {
            for (String conteudo_utilizador : this.comuns.get(utilizador).getMyConteudo().keySet()) {
                if (x.contains(conteudo_utilizador)) {
                    lista.add(utilizador);
                    break;
                }
            }
        }
       this.comuns.get(n).setPotAmigos(lista);
    }

    /**
     * Elimina o conteúdo repetido.
     * @param repetida  lista dos nomes dos files de conteúdo repetido
     * @param geral lista dos nomes dos conteúdos inseridos
     * @return lista atualiza dos nomes dos conteúdos
     */
    public List<String> elimina(List<String> repetida, List<String> geral){
        List<String> l = new ArrayList<>();
        for(String from_geral: geral)
            for(String from_repetida : repetida)
                if(!(from_repetida.equals(from_geral)) && !(l.contains(from_geral))) {
                    l.add(from_geral);
                    break;
                }
        return l;
    }

    /**
     * Carrega conteúdo para o sistema
     * @param c  lista dos nomes dos conteúdo a carregar
     */
    public void carrega(List<String> c){
        for(String x : c){
            Conteudo ct = new Conteudo(x, "path", "Artista", categorias[new Random().nextInt(categorias.length)]);
            this.conteudo.put(x, ct);
        }
    }

    public List<String> copyString(List<String> conteudo){
        List<String> conteudo_a_carregar = new ArrayList<>();
        for(String x : conteudo)
            conteudo_a_carregar.add(x);
        return conteudo_a_carregar;
    }
    /**
     * Dá upload de conteúdo
     * @param conteudo  lista dos nomes dos conteúdo a dar upload
     * @param utilizador    que dá o upload
     * @throws ConteudoInvalido
     */
    public void upload(List<String> conteudo, String utilizador) throws ConteudoInvalido{
        boolean valid = validarConteudo(conteudo);
        if(!valid)
            throw new ConteudoInvalido("O conteudo + " + conteudo + " tem ficheiro(s) com formato inválido.");
        List<String> existe = conteudoIgual(conteudo);
        List<String> conteudo_a_carregar = copyString(conteudo);
        int tamanho = existe.size();
        if(tamanho != 0)
            conteudo_a_carregar = elimina(existe, conteudo);
        carrega(conteudo_a_carregar);
        showUtilizador(conteudo, utilizador);
        addPotenciais(conteudo, utilizador);

    }

                /* Alterar Categoria de Conteúdo */

    /*
    public boolean validarExistencia(){
        if(this.conteudo.size() == 0)
            return false;
        return true;
    }


    public List<String> existeConteudo() throws ConteudoInexistente{
        boolean valid = validarExistencia();
        if(!valid)
            throw  new ConteudoInexistente("Não existe conteúdo na biblioteca do sistema.");
        Set<String> x =this.conteudo.keySet();
        return new ArrayList<>(x);
    }*/
    /**
     * Altera a categoria de determinado conteúdo.
     * @param comum que altera categoria
     * @param nome  do conteudo
     * @param categoria nova categoria
     */
    public void alterarConteudo(String comum, String nome, String categoria){
        this.comuns.alterarCategoria(comum, nome, categoria);
    }

                    /* Reproduzir Conteúdo */

    /**
     * Verifica o tamanho do map de conteudo do sistema.
     * @return true or false
     */
    public boolean validarExistencia(){
        if(this.conteudo.size() == 0)
            return false;
        return true;
    }

    /**
     * Verifica se existe conteúdo no sistema.
     * @throws ConteudoInexistente
     * @return lista com os nomes de todos os conteudos da biblioteca
     */
    public List<String> existeConteudo() throws ConteudoInexistente{
        boolean valid = validarExistencia();
        if(!valid)
            throw  new ConteudoInexistente("Não existe conteúdo na biblioteca do sistema.");
        Set<String> x =this.conteudo.keySet();
        return new ArrayList<>(x);
    }

    /**
     * Dá play de conteúdo.
     * @param path  path para o conteúdo
     */
    public void play(String path){
        MediaPlayer m = new MediaPlayer(path);
    }

    /**
     * Reproduz conteudo
     * @param c nome do conteudo
     */
    public void reproduzirConteudo(String c){
        play(c);
    }

    /**
     * Devolve um map com as playlists do utilizador.
     * @param n nome do utilizador
     * @return map com as playlists do utilizador
     */
    public Map<String, Playlist> validarExistenciaPlaylist(String n){
        Map<String, Playlist> x = this.comuns.get(n).getPlaylists();
        if(x.size() == 0)
            return null;
        return x;
    }

    /**
     * Verifica se o utilizador tem playlsits.
     * @param n nome do utilizador
     * @throws PlaylistsInexistentes
     * @return map das playlists do utilizador
     */
    public Map<String, Playlist> existePlaylists(String n) throws PlaylistsInexistentes{
        Map<String, Playlist> lista = validarExistenciaPlaylist(n);
        if(lista == null)
            throw  new PlaylistsInexistentes("Você não tem playlists.");
        return lista;
    }

    /**
     * Play de uma playlist.
     * @param playlist a dar play
     */
    public void playPlaylist(List<String> playlist){
        for(String s : playlist)
            play(s);
    }

    /**
     * Reproduz uma playlist.
     * @param p nome da playlist
     * @param n nome do utilizador
     * @param shuffle opção de shuffle
     */
    public void reproduzirPlaylist(String p, String n,boolean shuffle){
        List<String> aux = this.comuns.getPlaylistEspecifica(n, p);
        if(shuffle)
            Collections.shuffle(aux);
        playPlaylist(aux);
    }

    /**
     * Reproduz conteúdo aleatório.
     */
    public void aleatorio(){
        Set<String> x = this.conteudo.keySet();
        List<String> aux = new ArrayList<>(x);
        Collections.shuffle(aux);
        playPlaylist(aux);
    }

                        /* Convidado */

    /**
     * Metodo que permite o acesso de um convidado.
     */
    public void connectSystem(){
        System.out.println("Bem vindo!");
    }

                    /* Potenciais Amigos */

    /**
     * Devolve a lista de potenciais amigos do utilzador.
     * @param c nome do utilizador
     * @return lista de potenciais amigos
     */
    public List<String> potenciaisAmigos(String c){
        return this.comuns.get(c).getPotAmigos();
    }

                     /* Download Conteúdo */

    /**
     * Devolve o conteudo da biblioteca do utilizador.
     * @param n nome do Utilizador
     * @return biblioteca do utilizador
     */
    public List<String> bibliotecaAux(String n){
        Set<String> x = this.comuns.get(n).getMyConteudo().keySet();
        return new ArrayList<>(x);
    }

    /**
     * Invoca o metodo que devolve o conteudo da biblioteca do utilizador.
     * @param n nome do Utilizador
     * @return biblioteca do utilizador
     */
    public List<String> biblioteca(String n){
        return bibliotecaAux(n);
    }

    /**
     * Faz download de conteúdo.
     * @param conteudo a dar download
     */
    public void download(String conteudo){
      //  down(conteudo);
    }

                    /* Reproduzir conteúdo como convidado */


   /* public boolean validarExistencia(){
        if(this.conteudo.size() == 0)
            return false;
        return true;
    }



    public List<String> existeConteudo() throws ConteudoInexistente{
        boolean valid = validarExistencia();
        if(!valid)
            throw  new ConteudoInexistente("Não existe conteúdo na biblioteca do sistema.");
        Set<String> x = this.conteudo.keySet();
        return new ArrayList<>(x);
    }


   public void play(String path){
        MediaPlayer m = new MediaPlayer(path);
    }

      public void reproduzirConteudo(String c){
        play(c);
    } */

                    /* Editar Conta */

    /**
     * Metodo auxiliar que devolve um array com os dados do utilizador
     * @param utilizador nome do Utilizador
     * @param v booleano true caso utilizador seja comum, falso casjo seja admin
     * @return array
     */
    public String[] buscarDadosAux(String utilizador, boolean v){
        Utilizador x;
        if(v)
            x = this.comuns.get(utilizador);
        else
            x = this.admins.get(utilizador);
        String pass = x.getPassword();
        String email = x.getEmail();
        return new String[]{utilizador, email, pass};
    }

    /**
     * Devolve um array com os dados do utilizador
     * @param utilizador nome do Utilizador
     * @return array
     */
    public String[] buscarDados(String utilizador){
        boolean validC = this.comuns.containsKey(utilizador);
        return buscarDadosAux(utilizador, validC);
    }

    /**
     * Altera o nome de um utilizador Comum
     * @param atual nome do Utilizador
     * @param novo nome do Utilizador
     */
    public void alterarNomeComum(String atual, String novo){
        Comum x;
        x = this.comuns.get(atual);
        x.setNome(novo);
        this.comuns.put(novo, x);
    }

    /**
     * Altera o nome de um utilizador Admin
     * @param atual nome do Utilizador
     * @param novo nome do Utilizador
     */
    public void alterarNomeAdmin(String atual, String novo) {
        Administrador x;
        x = this.admins.get(atual);
        x.setNome(novo);
        this.admins.put(novo, x);
    }

    /**
     * Altera a pass de um utilizador Comum
     * @param atual pass do Utilizador
     * @param nova pass do Utilizador
     */
    public void alterarPassComum(String atual, String nova){
        Comum x;
        x = this.comuns.get(atual);
        x.setPassword(nova);
        this.comuns.put(nova, x);
    }

    /**
     * Altera a pass de um utilizador Admin
     * @param atual pass do Utilizador
     * @param nova pass do Utilizador
     */
    public void alterarPassAdmin(String atual, String nova){
        Administrador x;
        x = this.admins.get(atual);
        x.setPassword(nova);
        this.admins.put(nova, x);
    }

    /**
     * Altera a conta de um utilizador
     * @param nome_atual pass do Utilizador
     * @param novo_nome pass do Utilizador
     * @param option tipo de alteração
     * @throws UtilizadorInvalido
     */
    public void alterarConta(String nome_atual,String novo_nome, int option) throws UtilizadorInvalido{
        boolean validC = this.comuns.containsKey(nome_atual);
        if(option == 0){
            boolean validC_novo = this.comuns.containsKey(novo_nome);
            boolean validA_novo = this.admins.containsKey(novo_nome);
            if(validC_novo || validA_novo)
                throw new UtilizadorInvalido("Utilizador já existente com os dados inseridos.");
            if(validC)
                alterarNomeComum(nome_atual, novo_nome);
            else
                alterarNomeAdmin(nome_atual, novo_nome);
        }
        else{
            if(validC)
                alterarPassComum(nome_atual, novo_nome);
            else
                alterarPassAdmin(nome_atual, novo_nome);
        }
    }

                        /* Eliminar Utilizador */

    /**
     * Metodo que elimina um utilizador.
     * @param nome  do Utilizador
     * @throws UtilizadorInexistente
     */
    public void eliminarUtilizador(String nome) throws UtilizadorInexistente{
        boolean valid = this.comuns.containsKey(nome);
        boolean valid2 = this.admins.containsKey(nome);
        if(!valid && !valid2)
            throw new UtilizadorInexistente("O utilizador inserido não existe.");
        if(valid)
            this.comuns.remove(nome);
        else
            this.admins.remove(nome);
    }

                    /* Remover conteúdo */
/*

    public List<String> bibliotecaAux(String n){
        Set<String> x = this.comuns.get(n).getMyConteudo().keySet();
        return new ArrayList<>(x);
    }

    public List<String> biblioteca(String n){
        return bibliotecaAux(n);
    }
*/
    /**
     * Metodo que remove determinado conteúdo.
     * @param conteudo  a remover
     */
    public void removerConteudo(String conteudo){
        this.conteudo.remove(conteudo);
    }

                    /* Enviar Pedido */
    /**
     * Metodo que adiciona um pedido à lista de pedidos pendentes de um utilizador
     * @param nome  do Utilizador
     * @param amigo a adicionar
     */
    public void send(String nome, String amigo){
        List<String> lista = this.comuns.get(amigo).getPedidosAmi();
        lista.add(nome);
        this.comuns.get(amigo).setPedidosAmi(lista);
    }

    /**
     * Metodo que envia pedido de amizade a um utilizador.
     * @param nome  do Utilizador
     * @param amigo a adicionar
     * @throws UtilizadorInexistente
     */
    public void enviarPedido(String nome, String amigo) throws  UtilizadorInexistente{
        boolean valid = this.comuns.containsKey(amigo);
        if(!valid)
            throw new UtilizadorInexistente("O utilizador inserido não existe.");
        send(nome, amigo);
    }

                        /* Responder Pedido */

    /**
     * Devolve a lista de pedidos de um utilizador.
     * @param n nome do Utilizador
     * @return lista de potenciais amigos
     */
    public List<String> pendentesAux(String n){
        Set<String> x = this.comuns.get(n).getPedidosAmi().keySet();
        return new ArrayList<>(x);
    }

    /**
     * Invoca o metodo que devolve a lista de pedidos de um utilizador.
     * @param n nome do Utilizador
     * @return lista de potenciais amigos
     */
    public List<String> pendentes(String n){
        return pendentesAux(n);
    }

    /**
     * Metodo que remove um pedido da lista de pedidos pendentes de um utilizador
     * @param nome  do Utilizador
     * @param amigo a remover dos pendentes
     */
    public void removePedido(String nome, String amigo){
        List<String> lista = this.comuns.get(nome).getPedidosAmi();
        lista.remove(amigo);
        this.comuns.get(nome).setPedidosAmi(lista);
    }

    /**
     * Metodo que adiciona um user à lista de amigos de um utilizador
     * @param nome  do Utilizador
     * @param amigo a adicionar
     */
    public void add_buddy(String nome, String amigo){
        List<String> lista = this.comuns.get(nome).getAmigos();
        List<String> lista_amigo = this.comuns.get(amigo).getAmigos();
        lista.add(amigo);
        lista_amigo.add(nome);
        this.comuns.get(nome).setAmigos(lista);
        this.comuns.get(amigo).setAmigos(lista_amigo);
    }

    /**
     * Metodo que responde a um pedido de amigo pendente
     * @param nome  do Utilizador
     * @param amigo a adicionar
     */
    public void responderPedido(String nome, int resposta, String amigo){
        removePedido(nome, amigo);
        if(resposta == 1)
            add_buddy(nome, amigo);
    }

                    /* Criar playlist */

    /**
     * Metodo auxiliar que verifica se existe uma playlist com o nome dado
     * @param utilizador    nome do Utilizador
     * @param playlist  a verificar
     */
    public boolean checkNameAux(String utilizador, String playlist){
        Map<String, Playlist> x = this.comuns.get(utilizador).getPlaylists();
        return x.containsKey(playlist);
    }

    /**
     * Metodo que verifica se existe uma playlist com o nome dado
     * @param utilizador    nome do Utilizador
     * @param playlist  a verificar
     */
    public void checkName(String utilizador, String playlist) throws PlaylistsInexistentes{
        boolean valid = checkNameAux(utilizador, playlist);
        if(!valid)
            throw new PlaylistsInexistentes("Já contém uma playlist com esse nome");
    }

    /**
     * Cria uma playlist aleatória.
     * @param utilizador    nome do Utilizador
     * @param playlist  a criar
     */
    public void criar_Play_aleatoria(String utilizador, String playlist){
        Map<String, String> map = this.comuns.get(utilizador).getMyConteudo();
        List<String> nomes = new ArrayList<>(map.keySet());
        Collections.shuffle(nomes);

        Map<String, String> y = new HashMap<>();
        for(String a : nomes){
            String categoria = map.get(a);
            y.put(a, categoria);
            if(y.size() == 20)
                break;
        }

        Playlist p = new Playlist(y);
        Map<String, Playlist> playlists = this.comuns.get(utilizador).getPlaylists();
        playlists.put(playlist, p);
    }

    /**
     * Invoca o metodo que cria uma playlist aleatória.
     * @param utilizador    nome do Utilizador
     * @param playlist  a criar
     */
    public void playlistAleatoria(String utilizador, String playlist){
        criar_Play_aleatoria(utilizador, playlist);
    }

    /**
     * Cria uma playlist.
     * @param utilizador    nome do Utilizador
     * @param playlist  a verificar
     * @return lista de conteudo do utilziador com base no genero
     */
    public void criar_Playist(String utilizador, String playlist){
        Playlist p = new Playlist();
        Map<String, Playlist> playlists = this.comuns.get(utilizador).getPlaylists();
        playlists.put(playlist, p);
        this.comuns.get(utilizador).setPlaylists(playlists);
    }

    /**
     * Devolve a lista de conteudo de um utilizador com base no artista
     * @param u    nome do Utilizador
     * @param g    genero
     * @return lista de conteudo do utilziador com base no genero
     */
    public List<String> byGenero(String u, String g){
        Map<String, String> map_conteudo = this.comuns.get(u).getMyConteudo();
        List<String> nomes = new ArrayList<>(map_conteudo.keySet());

        List<String> y = new ArrayList<>();
        for(String a : nomes){
            String categoria = map_conteudo.get(a);
            if(g.equals(categoria))
                y.add(a);
        }
        return y;
    }

    /**
     * Invoca o metodo que cria uma playlist com base num género.
     * @param utilizador    nome do Utilizador
     * @param playlist  a verificar
     * @param genero    selecionado para a criação da playlist
     * @return lista de conteudo do utilziador com base no genero
     */
    public List<String> playlistGenero(String utilizador, String playlist, String genero){
        criar_Playist(utilizador, playlist);
        List<String> conteudo_do_artista = byArtista(utilizador, genero);
        return  conteudo_do_artista;
    }

    /**
     * Adiciona conteudo a uma playlist.
     * @param u     nome do Utilizador
     * @param p     a adicionar conteudo
     * @param c     a adicionar à playlist
     * @param g     da playlist
     */
    public void addConteudoAux(String u, String p, List<String> c, String g){
        Map<String,Playlist> playlists = this.comuns.get(u).getPlaylists();
        Map<String, String> aux = playlists.get(p).getMyConteudo();
        for(String a : c)
            aux.put(a, g);
        playlists.put(p, new Playlist(aux));
        this.comuns.get(u).setPlaylists(playlists);
    }

    /**
     * Invoca o metodo que adiciona conteudo a uma playlist.
     * @param utilizador        nome do Utilizador
     * @param playlist      a adicionar conteudo
     * @param conteudo      a adicionar à playlist
     * @param genero        da playlist
     */
    public void addConteudo(String utilizador,String playlist, List<String> conteudo, String genero){
        addConteudoAux(utilizador, playlist, conteudo, genero);
    }

    /**
     * Devolve a lista de conteudo de um utilizador com base no artista
     * @param u    nome do Utilizador
     * @param a    artista
     * @return lista de conteudo do utilziador com base no artista
     */
    public List<String> byArtista(String u, String a){
        List<String> x = new ArrayList<>();
        Set<String>  set_conteudo_u = this.comuns.get(u).getMyConteudo().keySet();
        List<String> list_conteudo_u = new ArrayList<>(set_conteudo_u);
        for(String c : this.conteudo.keySet())
            if(list_conteudo_u.contains(c) && this.conteudo.get(c).getArtista().equals(a))
                x.add(c);
        return x;
    }

    /**
     * Invoca o metodo que cria uma playlist com base num artista.
     * @param utilizador    nome do Utilizador
     * @param playlist  a verificar
     * @param artista    selecionado para a criação da playlist
     * @return lista de conteudo do utilziador com base no artista
     */
    public List<String> playlistArtista(String utilizador, String playlist, String artista){
        criar_Playist(utilizador, playlist);
        List<String> conteudo_do_artista = byArtista(utilizador, artista);
        return  conteudo_do_artista;
    }

    /**
     * Adiciona conteudo a uma playlist.
     * @param u     nome do Utilizador
     * @param p     a adicionar conteudo
     * @param c     a adicionar à playlist
     */
    public void adConteudoAux(String u, String p, List<String> c){
        Map<String,Playlist> playlists = this.comuns.get(u).getPlaylists();
        Map<String, String> aux = playlists.get(p).getMyConteudo();
        Map<String, String> x = this.comuns.get(u).getMyConteudo();
        for(String a : c){
            aux.put(a, x.get(a));
        }
        playlists.put(p, new Playlist(aux));
        this.comuns.get(u).setPlaylists(playlists);
    }

    /**
     * Invoca o metodo que adiciona conteudo a uma playlist.
     * @param utilizador        nome do Utilizador
     * @param playlist      a adicionar conteudo
     * @param conteudo      a adicionar à playlist
     */
    public void adConteudo(String utilizador,String playlist, List<String> conteudo, String artista){
        adConteudoAux(utilizador, playlist, conteudo);
    }




}
