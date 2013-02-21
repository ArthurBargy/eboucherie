package com.boucheriebenz.eboucherie.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

public class JdbcConnector {
    private static final Logger logger = Logger.getLogger(JdbcConnector.class);
    private static Properties props = new Properties();

    // champs assurant l'unicit� de la connexion et du chargement du driver
    private static Connection refUniqueConnection = null;
    private static boolean isDriverRegistered = false;

    static {
        try {
            props.load(JdbcConnector.class.getClassLoader()
                    .getResourceAsStream("db.properties"));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    /**
     * renvoie une connexion unique vers une base de donn�e.
     */
    public static Connection getConnection() throws Exception {
        // on regarde si la connexion existe d�j�
        if (refUniqueConnection == null) {
            // chargement du driver de la BDD si besoin
            if (!isDriverRegistered) {
                // chargement du driver
                DriverManager.registerDriver((Driver) (Class
                        .forName(props.getProperty("driver")).newInstance()));
                isDriverRegistered = true;
            }
            // cr�ation de la connection
            refUniqueConnection = DriverManager.getConnection(
                    props.getProperty("url"), props.getProperty("user"),
                    props.getProperty("password"));
            logger.debug("Connexion r�ussie");
        }
        // on renvoie la connexion
        return refUniqueConnection;
    }

    /**
     * ferme la connexion en cours.
     */
    public static void closeConnection() throws Exception {
        // fermeture de la connexion
        refUniqueConnection.close();
        // suppression de la r�f�rence vers la connexion close
        refUniqueConnection = null;
    }

    // On teste la connexion
    public static void main(String[] args) {
        try {
            Connection connexion = JdbcConnector.getConnection();
            logger.info("Connexion r�ussie");
        } catch (Exception e) {
            // on renvoie une connection nulle en cas d'erreurs de connexion
            logger.error("Erreur de connexion � la base : " + e.getMessage());
        }
    }

}
