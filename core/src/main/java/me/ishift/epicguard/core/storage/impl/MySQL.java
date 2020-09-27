package me.ishift.epicguard.core.storage.impl;

import me.ishift.epicguard.core.storage.StorageSystem;

import java.sql.*;
import java.util.*;

public class MySQL extends StorageSystem {
    private final String address;
    private final int port;
    private final String database;
    private final String user;
    private final String password;
    private final boolean useSSL;

    private Connection connection;

    public MySQL(String address, int port, String database, String user, String password, boolean useSSL) {
        this.address = address;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.useSSL = useSSL;
    }

    @Override
    public void load() {
        try {
            String url = "jdbc:mysql://" + this.address + ":" + this.port + "/" + this.database + "?useSSL=" + this.useSSL;
            this.connection = DriverManager.getConnection(url, this.user, this.password);

            this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_blacklist(`address` TEXT NOT NULL)");
            this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_whitelist(`address` TEXT NOT NULL)");
            this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_accountdata(`address` TEXT NOT NULL, `nickname` TEXT NOT NULL)");

            this.blacklist = getList("epicguard_blacklist", "address");
            this.whitelist = getList("epicguard_whitelist", "address");

            //TODO loading data from accountdata table
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save() {
        this.insertList("epicguard_blacklist", "address", this.blacklist);
        this.insertList("epicguard_whitelist", "address", this.whitelist);

        //TODO i am retarded
        this.accountMap.forEach((address, nicknames) -> {
            nicknames.forEach(nick -> {
                this.executeUpdate("INSERT INTO `epicguard_accountdata` (`address`, `nickname`) VALUES ('" + address + "', '" + nick + "')");
            });
        });
    }

    private ResultSet executeQuery(String query) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            return statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void executeUpdate(String query) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Collection<String> getList(String table, String column) {
        Collection<String> list = new HashSet<>();
        try {
            ResultSet result = this.executeQuery("SELECT * FROM `" + table + "`");
            if (result != null) {
                while (result.next()) {
                    list.add(result.getString(column));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public void insertList(String table, String column, Collection<String> list) {
        if (list.isEmpty()) {
            return;
        }

        StringJoiner joiner = new StringJoiner(", ");
        for (String string : list) {
            joiner.add("('" + string + "')");
        }
        this.executeUpdate("INSERT IGNORE INTO `" + table + "` (`" + column + "`) VALUES " + joiner.toString());
    }
}
