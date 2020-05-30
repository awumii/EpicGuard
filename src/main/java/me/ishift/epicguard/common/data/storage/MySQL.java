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

package me.ishift.epicguard.common.data.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.leonhard.storage.Yaml;
import me.ishift.epicguard.common.data.DataStorage;
import me.ishift.epicguard.common.data.StorageManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MySQL extends DataStorage {
    private final StorageManager manager;
    private HikariDataSource dataSource;

    public MySQL(StorageManager manager) {
        this.manager = manager;
    }

    @Override
    public void load() {
        this.initConnection();
        this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_blacklist(`address` TEXT NOT NULL);");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_whitelist(`address` TEXT NOT NULL);");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_reconnectdata(`address` TEXT NOT NULL);");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_pingdata(`address` TEXT NOT NULL);");
        this.setBlacklist(this.getList("epicguard_blacklist", "address"));
        this.setWhitelist(this.getList("epicguard_whitelist", "address"));
        this.setRejoinData(this.getList("epicguard_reconnectdata", "address"));
        this.setPingData(this.getList("epicguard_pingdata", "address"));
    }

    @Override
    public void save() {
        this.insertList("epicguard_blacklist", "address", this.getBlacklist());
        this.insertList("epicguard_whitelist", "address", this.getWhitelist());
        this.insertList("epicguard_reconnectdata", "address", this.getRejoinData());
        this.insertList("epicguard_pingdata", "address", this.getPingData());
    }

    private void initConnection() {
        final HikariConfig config = new HikariConfig();
        config.addDataSourceProperty("dataSourceClassName", "com.mysql.jdbc.Driver");
        config.setMaximumPoolSize(this.manager.getMysqlPoolSize());
        config.setConnectionTimeout(this.manager.getMysqlTimeout());
        config.setJdbcUrl("jdbc:mysql://" + this.manager.getMysqlHost() + ":" + this.manager.getMysqlPort() + "/" + this.manager.getMysqlDatabase() + "?useSSL=" + this.manager.isMysqlSSL());
        config.setUsername(this.manager.getMysqlUser());
        config.setPassword(this.manager.getMysqlPassword());
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);

        this.dataSource = new HikariDataSource(config);
    }

    public List<String> getList(String table, String column) {
        final List<String> list = new ArrayList<>();
        try {
            final ResultSet result = this.executeQuery("SELECT * FROM `" + table + "`");
            while(result.next()) {
                list.add(result.getString(column));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    private ResultSet executeQuery(String query) {
        try {
            final Connection connection = this.dataSource.getConnection();
            final Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void executeUpdate(String query) {
        try {
            final Connection connection = this.dataSource.getConnection();
            final Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertList(String table, String column, Collection<String> list) {
        this.executeUpdate("INSERT INTO `" + table + "` (`" + column + "`) VALUES " + this.fromList(list));
    }

    private String fromList(Collection<String> list) {
        final StringJoiner joiner = new StringJoiner(", ");
        for (String string : list) {
            joiner.add("('" + string + "')");
        }
        return joiner.toString();
    }
}
