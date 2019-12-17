package smc.Business;

import java.awt.BorderLayout;
import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author BUDDHIMA
 */

public class MediaPlayer extends javax.swing.JPanel {

    public MediaPlayer(String path){
        ProcessBuilder pb = new ProcessBuilder("vlc", path); //primeiro argumento tem de ser alterado consoante o OS.
        try{
            Process start = pb.start();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    /*public static void main(String[] args) {
        MediaPlayer m = new MediaPlayer("src/a.wav");
    }*/

}

