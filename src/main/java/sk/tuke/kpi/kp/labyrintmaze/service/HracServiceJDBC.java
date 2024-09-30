package sk.tuke.kpi.kp.labyrintmaze.service;

import sk.tuke.kpi.kp.labyrintmaze.entity.Hrac;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class HracServiceJDBC implements HracService{
    public static final String JDBC_URL = "jdbc:postgresql://localhost/gamestudio";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "9zsY?Egs";
    public static final String INSERT_STATEMENT = "INSERT INTO hrac(name, heslo, registeredOn)VALUES (?,?,?)";
    private static final String SELECT_STATEMENT = "SELECT h.heslo FROM hrac h WHERE h.name = ?";
    private static final String SELECT_STATEMENT_HRAC = "SELECT FROM hrac WHERE name = ?";


    @Override
    public String getHeslo(String name) throws RatingException {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(SELECT_STATEMENT)) {
            statement.setString(1, name);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    String password = rs.getString("heslo");
                    return password;
                }
                return null;
            }
        } catch (SQLException e) {
            throw new HracException(e);
        }
    }

    @Override
    public void addHrac(Hrac hrac) {
        try(var connection= DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            var statement=connection.prepareStatement(INSERT_STATEMENT)
        ){
            statement.setString(1, hrac.getName());
            statement.setString(2, hrac.getHeslo());
            statement.setTimestamp(3, new Timestamp(hrac.getRegisteredOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new HracException(e);
        }
    }

    @Override
    public Hrac getHrac(String name) {
        try (var connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             var statement = connection.prepareStatement(SELECT_STATEMENT_HRAC)) {
            statement.setString(1, name);
            try (var rs = statement.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("ident");
                    String password = rs.getString("heslo");
                    Date registeredOn = rs.getDate("registeredOn");
                    return new Hrac(name, password, registeredOn);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new HracException(e);
        }
    }
}
