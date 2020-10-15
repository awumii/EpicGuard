/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.core.storage.impl;

import me.xneox.epicguard.core.storage.StorageSystem;

import java.sql.*;
import java.util.*;

// Currently, MySQL does not store accountMap which
// is used to limit alt accounts.
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

            this.blacklist = getList("epicguard_blacklist", "address");
            this.whitelist = getList("epicguard_whitelist", "address");

            this.accountMap = new HashMap<>();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void save() {
        this.insertList("epicguard_blacklist", "address", this.blacklist);
        this.insertList("epicguard_whitelist", "address", this.whitelist);
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
            while (result.next()) {
                list.add(result.getString(column));
            }
            result.close();
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
         this.executeUpdate("TRUNCATE `" + table + "`");
        this.executeUpdate("INSERT INTO `" + table + "` (`" + column + "`) VALUES " + joiner.toString());
    }
}
