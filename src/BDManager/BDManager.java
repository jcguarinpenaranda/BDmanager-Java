/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Juan Camilo Guarín P.
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
     * @param dbname Nombre de la base de datos
     * @param username El username de la base de datos
     * @param password El password de la base de datos
     * @throws SQLException
     */
    public BDManager(String dbname, String username, String password) throws SQLException {
        this.dbname = dbname;
        this.username = username;
        this.password = password;
    }

    /**
     *
     * @param consulta Ej: "select * from <nombre_tabla>"
     * @return Arreglo de objetos Row, con el contenido de toda la consulta.
     * Un ejemplo de algo retornado sería [[Juan 123456],[Daniela 456789]]
     * Donde la primera columna de la primera persona (Juan) seria el nombre y 
     * la segunda un código cualquiera
     * @throws ClassNotFoundException
     * @throws SQLException
     * Utilizar este método solo para consultas. La clase devuelve ArrayList<Row>, que contiene todos los 
     * valores que retorna una consulta.
     */
    public ArrayList<Row> consulta(String consulta) throws ClassNotFoundException, SQLException {
        Statement statement;
        ResultSet resultSet;
        ArrayList<Row> arreglo = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();
        resultSet = statement.executeQuery(consulta);
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
     * @param adicionOupdateOdelete Una adición (insert) o un update (update) o
     * eliminar un dato (delete). Ej: "insert into <tabla> (nom_col1) values('val1')"
     * @return Número de columnas afectadas
     * @throws ClassNotFoundException
     * @throws SQLException
     * Utilizar este método para todo lo que tiene que ver con
     * INSERTS, UPDATES O DELETES
     */
    public int update(String adicionOupdateOdelete) throws ClassNotFoundException, SQLException {
        Statement statement;
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(adicionOupdateOdelete);

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
     * @param consulta Ej: insert into <tabla> (nombre_columna) values (?)
     * @param rutaBlob Ruta global hasta la imagen que se quiere insertar
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void updateBlob(String consulta, String rutaBlob) throws ClassNotFoundException, SQLException, FileNotFoundException {
        Statement statement;
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();

        PreparedStatement stmt = connection.prepareStatement(consulta);
        File imagen = new File(rutaBlob);
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
     * @param consulta Ej: select * from <tabla_que_contiene_imagen> where id = xxxx
     * OJO: solo retorna UN ImageIcon. Si necesita retornar varios ImageIcon,
     * llame esta función varias veces
     * @param nombreColumna El nombre de la columna que contiene la imagen a retornar
     * @return Un objeto de la clase ImageIcon para mostrar luego en un JFrame o algo parecido
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public ImageIcon getImage(String consulta, String nombreColumna) throws ClassNotFoundException, SQLException {
        Statement statement;
        Class.forName(JDBC_DRIVER);
        connection = DriverManager.getConnection(mysqlUrl + dbname, username, password);
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(consulta);
        //Tratamiento para el campo BLOB
        Blob blob = null;
        while (rs.next()) {
            blob = rs.getBlob(nombreColumna);
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
