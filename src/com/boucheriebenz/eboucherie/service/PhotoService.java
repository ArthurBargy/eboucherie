package com.boucheriebenz.eboucherie.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.boucheriebenz.eboucherie.jdbc.JdbcConnector;
import com.boucheriebenz.eboucherie.model.FichierPhoto;
import com.boucheriebenz.eboucherie.model.Photo;

@Service
public class PhotoService {

    private static final Logger logger = Logger.getLogger(PhotoService.class);
    private static Properties props = new Properties();

    static {
        try {
            props.load(JdbcConnector.class.getClassLoader()
                    .getResourceAsStream("photos.properties"));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public List<Photo> getPhotos() throws Exception {
        Connection connection = JdbcConnector.getConnection();
        String sql = "SELECT * FROM photo ORDER BY libelle";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Photo> photos = new ArrayList<Photo>();
        while (rs.next()) {
            Photo photo = new Photo();
            photo.setId(rs.getInt("id_photo"));
            photo.setLibelle(rs.getString("libelle"));
            photo.setLien(rs.getString("lien"));
            photos.add(photo);
        }

        rs.close();
        JdbcConnector.closeConnection();
        return photos;
    }

    public void save(String entrepot, FichierPhoto fichier) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Sauvegarder la photo dans le serveur
        String filePath = entrepot + props.getProperty("dossier")
                + fichier.getFichier().getOriginalFilename();
        logger.debug("Sauvegarder: " + filePath);
        FileOutputStream outputStream = new FileOutputStream(
                new File(filePath));
        outputStream.write(fichier.getFichier().getBytes());
        outputStream.close();

        // Insérer la photo dans la BDD
        String sql = "INSERT INTO photo(libelle, lien) VALUES(?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, fichier.getLibelle());
        ps.setString(2, props.getProperty("dossier")
                + fichier.getFichier().getOriginalFilename());
        ps.execute();

        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }

    public void delete(String entrepot, Integer id) throws Exception {
        Connection connection = JdbcConnector.getConnection();
        connection.setAutoCommit(false);

        // Récupérer le lien de la photo
        String sql = "SELECT lien FROM photo WHERE id_photo=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        String lien = "";
        if (rs.next()) {
            lien = rs.getString(1);
        } else {
            throw new SQLException("Erreur d'élimination, clé primaire " +
                    "phpto existant");
        }

        // Éliminer la photo du serveur
        String filePath = entrepot + lien;
        File file = new File(filePath);
        file.delete();

        // Éliminer la photo de la BDD
        sql = "DELETE FROM photo WHERE id_photo=?";
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();

        connection.commit();
        connection.setAutoCommit(true);
        JdbcConnector.closeConnection();
    }

}
