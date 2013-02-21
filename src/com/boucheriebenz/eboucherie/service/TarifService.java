package com.boucheriebenz.eboucherie.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.Tarif;

@Service
public class TarifService {
	
	public void save(Tarif tarif) throws Exception {
        if (tarif.getId() == null) {
            insert(tarif);
        } else {
            update(tarif);
        }
    }
	
	private void insert(Tarif tarif) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Ins�rer un tarif
        String sql = "INSERT INTO tarif(tarif1, tarif2, tarif3,tarif4) " +
                "VALUES(?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, tarif.getTarif1());
        ps.setDouble(2, tarif.getTarif2());
        ps.setDouble(3, tarif.getTarif3());
        ps.setDouble(4, tarif.getTarif4());
        
        
     // R�cup�rer l'id de tarif cr�e
        int idtarif = -1;
        String sel = "SELECT MAX(Id_tarif) FROM tarif ";
        PreparedStatement ps1 = connection.prepareStatement(sel);
        ResultSet rs = ps1.executeQuery();
        rs.next();
        System.out.println(rs);
        System.out.println(rs.getInt(1));      
        
      //  ps.setDouble(5, Double.parseDouble() );
        ps.execute();
        
        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
       
	}
	
	 
	private void update(Tarif tarif) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Actualiser tarif
        String sql = "UPDATE Tarif SET tarif1=?,tarif2=?,tarif3=?,tarif4=?" +
                "WHERE id_tarif=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, tarif.getTarif1());
        ps.setDouble(2, tarif.getTarif2());
        ps.setDouble(3, tarif.getTarif3());
        ps.setDouble(4, tarif.getTarif4());
        ps.setDouble(5, tarif.getId());
        ps.execute();
           
        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }
    public List<Tarif> getTarifs() throws Exception {
        Connection connection = JdbcConnector.getConnection();
        String sql = "SELECT * "
        		+ "FROM Tarif t "
        		+ "WHERE t.id_tarif IS NOT NULL "
	            + "ORDER BY t.id_tarif";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Tarif> tarifs = new ArrayList<Tarif>();
        while (rs.next()) {
            Tarif tarif = new Tarif();
            tarif.setId(rs.getInt("id_tarif"));
            tarif.setTarif1(rs.getDouble("t.tarif1"));
            tarif.setTarif2(rs.getDouble("t.tarif2"));
            tarif.setTarif3(rs.getDouble("t.tarif3"));
            tarif.setTarif4(rs.getDouble("t.tarif4"));
            tarifs.add(tarif);
        }

        rs.close();
        JdbcConnector.closeConnection();
        return tarifs;
    }
    
    public void delete(Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);
       
     // Éliminer tarif
        String sql = "DELETE FROM Tarif WHERE id_tarif=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();

        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }

    public Tarif getById(Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        String sql = "SELECT * FROM Tarif t "            
                + "WHERE t.id_tarif=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        rs.next();

        Tarif tarif = new Tarif();
        tarif.setId(rs.getInt("id_tarif"));
        tarif.setTarif1(rs.getDouble("t.tarif1"));
        tarif.setTarif2(rs.getDouble("t.tarif2"));
        tarif.setTarif3(rs.getDouble("t.tarif3"));
        tarif.setTarif4(rs.getDouble("t.tarif4"));

        rs.close();
        JdbcConnector.closeConnection();
        return tarif;
    }

    
}
