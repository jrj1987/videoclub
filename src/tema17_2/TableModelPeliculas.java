/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema17_2;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

public final class TableModelPeliculas extends AbstractTableModel {

    private static final String[] columnNames = {"Titulo", "Año", "Puntuación", "Sinopsis"};
    private LinkedList<Pelicula> list;
    
     public TableModelPeliculas() {
        try {
            list = new LinkedList<Pelicula>();
            cargarPeliculas();
        } catch (SQLException ex) {
            Logger.getLogger(TableModelPeliculas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TableModelPeliculas(LinkedList<Pelicula> lista) {
        this.list=lista;
        // Notifica a la vista que el contenido ha cambiado para que se refresque.
        fireTableDataChanged();
    }
    
     public void cargarPeliculas() throws SQLException {
         
         LinkedList peliculas = ConexionMySQL.getInstance().obtenerPeliculas();
         
        // Borra el contenido anterior y añade el nuevo.
        list.clear();
        list.addAll(peliculas);

        // Notifica a la vista que el contenido ha cambiado para que se refresque.
        fireTableDataChanged();
    }
     
     public void insertar(String titulo, int año, int puntuacion, String sinopsis) throws SQLException {
          
        ConexionMySQL.getInstance().insertarPelicula(titulo, año, puntuacion, sinopsis);
        cargarPeliculas();
    }

    public Pelicula getValueAt(int rowIndex) {
        return list.get(rowIndex);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return list.get(rowIndex).getTitulo();
            case 1:
                return list.get(rowIndex).getAño();
            case 2:
                return list.get(rowIndex).getPuntuacion();
            case 3:
                return list.get(rowIndex).getSinopsis();
        }
        return null;
    }

}
