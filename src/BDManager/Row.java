/*
IMPORTANTE:
La clase ROW sirve como complemento para BD Manager.
Un row es la abstracción de lo que es una fila en una 
tabla de la base de datos.
No es necesario modificar nada aqui para que funcione.
*/
package BDManager;

import java.util.ArrayList;

/**
 *
 * @author Juan Camilo Guarín Peñaranda
 */
public class Row {
    
    private int columns;
    ArrayList<String> values;
    
    public Row(ArrayList<String> values){
        this.values = values;
        columns = values.size();
    }
    
    /**
     *
     * @param num
     * @return Retorna un String que es el contenido de una
     * columna de la búsqueda en una tabla
     */
    public String getValueAt(int num){
        return values.get(num);
    }
    
    @Override
    public String toString(){
        return values.toString();
    }
    
}
