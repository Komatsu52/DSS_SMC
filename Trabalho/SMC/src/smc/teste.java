package smc;

import smc.Business.Comum;
import smc.Business.SMC;
import smc.Data.ComumDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class teste {
    public static void main(String args[]) {
        Map<String, Comum> map = new ComumDAO();
        Comum aux = new Comum("johny", "merdas@gmail.com", "ola", new HashMap<>(), new HashMap<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        map.put(aux.getNome(), aux);
        System.out.println(map.get(aux.getNome()).toString());
        System.out.println(map.size());
        System.out.println(map.isEmpty());
        System.out.println(map.keySet());
        System.out.println(map.containsKey(aux.getNome()));
        map.clear();
        System.out.println(map.size());
    }
}
