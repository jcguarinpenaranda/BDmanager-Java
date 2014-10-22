package BDManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Camilo Guarín Peñaranda
 * Otherwise Studios
 * 2014
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // TODO code application logic here
        try {
            BDManager bdmanager = new BDManager("practica1", "root", "root");
            
            //CONSULTAS :
            ArrayList<Row> personas = bdmanager.consulta("select * from personal");
            System.out.println(personas.toString());
            //para tener un valor en específico:
            System.out.println(personas.get(0).getValueAt(0));
            //el primer get(0) me devuelve la persona 1
            //el segundo get me devuelve el valor de la columna 1 de la tabla,
            //para el caso específico de mi base de datos, el nombre
            
            //INSERTS O UPDATES O DELETE
//           String query = JOptionPane.showInputDialog("Ingresa la consulta");          
//           int rows = bdmanager.update(query);
            
            //MANEJO DE BLOBS:
            //insertar un blob en una fila ya creada
//            bdmanager.updateBlob("insert into blobs (imagen) values (?)", "U:\\puestadesol.jpg"); 
            //obtener una imagen de una fila ya creada
//            ImageIcon sol = bdmanager.getImage("select * from blobs where id = 1", "imagen");
            
        } catch (Exception e) {
            System.out.println("Error: "+e.toString());
        }

    }

}
