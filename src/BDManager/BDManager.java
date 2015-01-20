package BDManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Camilo Guarín P @ Otherwise Studios
 */
public class BDManager {

    public final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String dbname;
    private Connection connection;
    private String username, password;
    static final String mysqlUrl = "jdbc:mysql:///";
    private Statement statement;

    /**
     *
     * @param dbname The name of the database
     * @param username The username of the database
     * @param password The password of the database
     * @throws SQLException
     */
    public BDManager(String dbname, String username, String password) throws SQLException {
        this.dbname = dbname;
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @param query Ex: "select * from <table_name>"
     * @return ArrayList<Row>, with the content of the query.
     * An example of the returned, would be [[John Guarin],[Daniela Mera]]
     * Where the first column of the first person (John) would be the name and 
     * the second column would be the lastname
     * @throws ClassNotFoundException
     * @throws SQLException
     * 
     */
    public ArrayList<Row> query(String query) throws ClassNotFoundException, SQLException {
        Statement statement;
        ResultSet resultSet;
        ArrayList<Row> arreglo = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        ResultSetMetaData metaData = resultSet.getMetaData();

//        System.out.println(metaData.getColumnCount());
        while (resultSet.next()) {
            ArrayList<String> arr = new ArrayList<>();

            for (int i = 0; i < metaData.getColumnCount(); i++) {
                arr.add(resultSet.getString(i + 1));
            }
            arreglo.add(new Row(arr));
        }

        try {
            statement.close();
            connection.close();
        } catch (SQLException sqlException) {
            System.out.println("Database error");
            System.exit(1);
        }
        return arreglo;
    }

    /**
     *
     * @param update May be an insert, an update or a delete. Ej: "insert into <table> (name_col1) values('val1')"
     * @return Number of rows affected
     * @throws ClassNotFoundException
     * @throws SQLException
     * Use this method for everything that has to do with Inserts, deletes or updates
     */
    public int update(String update) throws ClassNotFoundException, SQLException {
        Statement statement;
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(update);

        try {
            statement.close();
            connection.close();
        } catch (SQLException sqlException) {
            System.out.println("Database error");
            System.exit(1);
        }
        return rowsAffected;
    }

    /**
     *
     * @param query Ej: insert into <table> (column_name) values (?)
     * @param blobRoute The global route for the blob you are inserting. Example: C://Users/User/Documents/file.png
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void updateBlob(String query, String blobRoute) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Statement statement;
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();

        PreparedStatement stmt = connection.prepareStatement(query);
        File imagen = new File(blobRoute);
        FileInputStream fis = new FileInputStream(imagen);
        stmt.setBinaryStream(1, fis, (int) imagen.length());
        stmt.execute();

        try {
            statement.close();
            connection.close();
        } catch (SQLException sqlException) {
            System.out.println("Database error");
            System.exit(1);
        }
    }

    /**
     *
     * @param query Ex: select * from <table_with_image> where id = xxxx
     * KEEP AN EYE: This will only return one image. If you need to return several images
     * then call this method several times.
     * @param columnName The name of the column that has the image you want to get.
     * @return An object of the class ImageIcon for you put it in an IFrame or something similar.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ImageIcon getImage(String query, String columnName) throws ClassNotFoundException, SQLException {
        Statement statement;
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        //Tratamiento para el campo BLOB
        Blob blob = null;
        while (rs.next()) {
            blob = rs.getBlob(columnName);
        }

        //Tratamiento como ImageIcon
        long length = blob.length();
        byte[] bArray = blob.getBytes(1, (int) length);
        ImageIcon icon = new ImageIcon(bArray);

        try {
            statement.close();
            connection.close();
        } catch (SQLException sqlException) {
            System.out.println("Database error");
            System.exit(1);
        }

        return icon;
    }
}
