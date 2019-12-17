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

    public String[] getCategorias() {
        String[] aux = new String[this.categorias.length];
        for(int i = 0; i < this.categorias.length; i++)
            aux[i] = this.categorias[i];
        return aux;
    }

    public void setCategorias(String[] cat) {
        for(int i = 0; i < cat.length; i++)
            this.categorias[i] = cat[i];
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
     *
     */
    public int choose(int option){
        return option;
    }

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
        int tamanho = existe.size();
        if(tamanho != 0)
            conteudo = elimina(existe, conteudo);
        carrega(conteudo);
        showUtilizador(conteudo, utilizador);
        addPotenciais(conteudo, utilizador);

    }

                /* Alterar Categoria de Conteúdo */
    /**
     * Altera a categoria de determinado conteúdo.
     * @param comum que altera categoria
     * @param nome  do conteudo
     * @param categoria nova categoria
     */
    public void alterarCatConteudo(String comum, String nome, String categoria){
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
     * @return lista de potenciais amigos
     */
    public List<String> bibliotecaAux(String n){
        Set<String> x = this.comuns.get(n).getMyConteudo().keySet();
        return new ArrayList<>(x);
    }

    /**
     * Invoca o metodo que devolve o conteudo da biblioteca do utilizador.
     * @param n nome do Utilizador
     * @return lista de potenciais amigos
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
}