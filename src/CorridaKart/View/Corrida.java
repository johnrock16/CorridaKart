package CorridaKart.View;

import CorridaKart.DAO.CorridaDAO;

/**
 *
 * @author User
 */
public class Corrida {
    
    public static void main(String[] args){
        
        CorridaDAO dao=new CorridaDAO();
        dao.decodifica();
        dao.posicaoGeral();
        dao.menorTempo();
            
    }
}
