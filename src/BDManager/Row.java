/*
IMPORTANT:
Class Row is just an abstraction of a Queue of the database,
and there is no need to modify it.
*/
package BDManager;

import java.util.ArrayList;

/**
 *
 * @author Juan Camilo Guarín P @ Otherwise Studios
 */
public class Row {
    
    public int columns;
    ArrayList<String> values;
    
    public Row(ArrayList<String> values){
        this.values = values;
        columns = values.size();
    }
    
    /**
     *
     * @param num
     * @return Returns a String, with the value of
     * a column in a table.
     */
    public String getValueAt(int num){
        return values.get(num);
    }
    
    @Override
    public String toString(){
        return values.toString();
    }
    
}
