package tema17_2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.TimeZone;

/**
 * Clase para la conexión con una base de datos MySQL
 *
 */
public class ConexionMySQL {

    // Base de datos a la que nos conectamos
    private final String BD;
    // Usuario de la base de datos
    private final String USUARIO;
    // Contraseña del usuario de la base de datos
    private final String PASS;
    // Objeto donde se almacenará nuestra conexión
    private Connection connection;
    // Indica que está en localhost
    private final String HOST;
    // Indica que está en el siguiente puerto
    private final String PORT;
    // Zona horaria
    private TimeZone zonahoraria;

    /**
    * Patrón de diseño Singleton
    * Su intención consiste en garantizar que una clase solo tenga una instancia y proporcionar un punto de acceso global a ella.
    * 
    */
    private static ConexionMySQL instance;  // Solo habrá una conexión en toda la aplicación
    
     /**
     * Constructor de la clase
     *
     * @param host Servidor donde se encuentra la BD
     * @param usuario Usuario de la base de datos
     * @param pass Contraseña del usuario
     * @param bd Base de datos a la que nos conectamos
     */
    private ConexionMySQL(String host, String port, String usuario, String pass, String bd) {
        HOST = host;
        PORT = port;
        USUARIO = usuario;
        PASS = pass;
        BD = bd;
        connection = null;
    }

    public static void crearConexion(String host, String port, String usuario, String pass, String bd) throws SQLException {
        if (instance != null) {
            try {
                instance.desconectar();
            } catch (SQLException e) {
                throw new SQLException("Error al cerrar conexión previa con MySQL: " + e.getMessage());
            }
        }

        instance = new ConexionMySQL(host, port, usuario, pass, bd);
    }

    public static ConexionMySQL getInstance() throws SQLException {
        if (instance == null) {
            throw new SQLException("Debe crear una conexión antes de acceder a la instancia.");
        }

        return instance;
    }

    /**
     * Comprueba que el driver de MySQL esté correctamente integrado
     *
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    private void registrarDriver() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al conectar con MySQL: " + e.getMessage());
        }
    }

    /**
     * Conecta con la base de datos
     *
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    public void conectar() throws SQLException {
        if (connection == null || connection.isClosed()) {
            registrarDriver();
            // Obtengo la zona horaria
            Calendar now = Calendar.getInstance();
            zonahoraria = now.getTimeZone();
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + BD + "?user="
                    + USUARIO + "&password=" + PASS + "&useLegacyDatetimeCode=false&serverTimezone="
                    + zonahoraria.getID());
                    }
    }

    /**
     * Cierra la conexión con la base de datos
     *
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    public void desconectar() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Ejecuta una consulta SELECT
     *
     * @param consulta Consulta SELECT a ejecutar
     * @return Resultado de la consulta
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    public ResultSet ejecutarSelect(String consulta) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery(consulta);

        return rset;
    }

    /**
     * Ejecuta una consulta INSERT, DELETE o UPDATE
     *
     * @param consulta Consulta INSERT, DELETE o UPDATE a ejecutar
     * @return Cantidad de filas afectadas
     * @throws SQLException Se lanzará cuando haya un fallo con la base de datos
     */
    public int ejecutarInsertDeleteUpdate(String consulta) throws SQLException {
        Statement stmt = connection.createStatement();
        int fila = stmt.executeUpdate(consulta);

        return fila;
    }
    
    /***
     * COMENTAR
     */
    public LinkedList<Pelicula> obtenerPeliculas() {
         
        LinkedList<Pelicula> peliculas = new LinkedList<>();

        try {
  
            ResultSet rset = ConexionMySQL.getInstance().ejecutarSelect("SELECT * FROM peliculas");
            
            while(rset.next()){
                String titulo = rset.getString("titulo");
                int anyo = rset.getInt("año");
                int puntuacion = rset.getInt("puntuacion");
                String sinopsis = rset.getString("sinopsis");
                Pelicula p = new Pelicula(titulo, anyo, puntuacion, sinopsis);
                peliculas.add(p);
            }
         }
         catch(SQLException e){
             
         }

        return peliculas;
    }
    
     /***
     * COMENTAR
     */
     public int insertarPelicula(String titulo, int año, int puntuacion, String sinopsis) throws SQLException {
        Statement stmt = connection.createStatement();
        String consulta = "INSERT INTO peliculas (id, titulo, año, puntuacion, sinopsis) VALUES (NULL, '"+titulo+"', '"+año+"', '"+puntuacion+"', '"+sinopsis+"');";
        int fila = stmt.executeUpdate(consulta);

        return fila;
    }
}
