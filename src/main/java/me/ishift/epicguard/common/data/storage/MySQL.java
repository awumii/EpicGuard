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
import me.ishift.epicguard.common.data.DataStorage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.StringJoiner;

public class MySQL extends DataStorage {
    private HikariDataSource dataSource;

    @Override
    public void load() {
        this.initConnection();
        this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_blacklist(`address` TEXT NOT NULL);");
        this.executeUpdate("CREATE TABLE IF NOT EXISTS epicguard_whitelist(`address` TEXT NOT NULL);");
        this.executeUpdate("SELECT * FROM `epicguard_whitelist`");
        this.executeUpdate("SELECT * FROM `epicguard_blacklist`");
    }

    @Override
    public void save() {
        this.executeUpdate("INSERT INTO `epicguard_whitelist` (`address`) VALUES " + this.fromList(this.getWhitelist()));
        this.executeUpdate("INSERT INTO `epicguard_blacklist` (`address`) VALUES " + this.fromList(this.getBlacklist()));
        this.executeUpdate("SELECT * FROM `epicguard_whitelist`");
        this.executeUpdate("SELECT * FROM `epicguard_blacklist`");
    }

    private void initConnection() {
        final String mysqlPassword = config.getOrSetDefault("mysql.user.password", "password");
        final String mysqlUser = config.getOrSetDefault("mysql.user.username", "admin");
        final String mysqlHost = config.getOrSetDefault("mysql.connection.host", "127.0.0.1");
        final int mysqlPort = config.getOrSetDefault("mysql.connection.port", 3306);
        final String mysqlDatabase = config.getOrSetDefault("mysql.connection.database", "your-database");
        final boolean mysqlSSL = config.getOrSetDefault("mysql.connection.use-ssl", true);
        final int poolSize = config.getOrSetDefault("mysql.settings.pool-size", 5);
        final int connectionTimeout = config.getOrSetDefault("mysql.settings.connection-timeout", 30000);

        final HikariConfig config = new HikariConfig();
        config.addDataSourceProperty("dataSourceClassName", "com.mysql.jdbc.Driver");
        config.setMaximumPoolSize(poolSize);
        config.setConnectionTimeout(connectionTimeout);
        config.setJdbcUrl("jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDatabase + "?useSSL=" + mysqlSSL);
        config.setUsername(mysqlUser);
        config.setPassword(mysqlPassword);
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        config.addDataSourceProperty("useServerPrepStmts", true);

        this.dataSource = new HikariDataSource(config);
    }

    private ResultSet executeQuery(String query) {
        try {
            System.out.println("Executing query '" + query + "'.");
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

    private String fromList(Collection<String> list) {
        final StringJoiner joiner = new StringJoiner(", ");
        for (String address : this.getWhitelist()) {
            joiner.add("('" + address + "')");
        }
        return joiner.toString();
    }
}
