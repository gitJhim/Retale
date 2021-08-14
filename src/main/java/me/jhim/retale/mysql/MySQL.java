package me.jhim.retale.mysql;

import me.jhim.retale.Retale;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    Retale retale;

    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    private Connection connection;

    public MySQL(Retale retale) {
        this.retale = retale;
        ConfigurationSection config = retale.getConfig();
        this.host = config.getString("mysql.host");
        this.port = config.getString("mysql.port");
        this.database = config.getString("mysql.database");
        this.username = config.getString("mysql.username");
        this.password = config.getString("mysql.password");
    }

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
        }
    }

    public void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
