package bbdd.jdbc2;
import java.sql.*;
import java.util.Scanner;

public class Program {
	
	private static final String USERNAME = "uo287577";
	private static final String PASSWORD = "miguelin_2003";
	private static final String CONNECTION_URL = "jdbc:oracle:thin:@156.35.94.98:1521:DESA19";
	
	public static void main(String[] args) {
		try
		{
			exercise2_b();
			exercise3_a();
		}catch(SQLException e)
		{
			System.err.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	/*
    2. En JAVA:
    b. Realizar un listado en el que, para los cines de una determinada localidad, se indique la recaudacion total
	obtenida en cada cine, asi como la obtenida por cada una de las peliculas en el proyectadas
    	Cine 1 - Recaudacion_total
			Codpelicula1 - Titulo1- Recaudacion_total_pelicula_1_en_cine1
			Codpelicula 2 - Titulo2 - Recaudacion_total_pelicula_2_en_cine1
		Cine 2 – Recaudacion_total
			Codpelicula1 – Titulo1 – Recaudacion_total_pelicula_1_en_cine2
			Codpelicula 2 – Titulo2 – Recaudacion_total_pelicula_2_en_cine2
			Codpelicula 3 – Titulo3 – Recaudacion_total_pelicula_3_en_cine2 
    */
	public static void exercise2_b() throws SQLException {
		System.out.println("#### Exercise 2_b ####");
		Connection con = getConnection();
		
		String query_cines = "SELECT c.codCine, sum(e.precio) as recaudacion_total "
				+ "FROM Cines c, Salas s, Entradas e "
				+ "WHERE c.codCine = s.codCine AND s.codSala = e.codSala AND c.localidad = ? "
				+ "GROUP BY c.codCine";
		PreparedStatement ps_cines = con.prepareStatement(query_cines.toString());
		
		System.out.println("Por favor, introduzca el nombre de una localidad: ");
		String localidad = ReadString(); 
		ps_cines.setString(1, localidad);
		
		ResultSet rs_cine = ps_cines.executeQuery();
		
		// peliculas proyectadas en un cine determinado
		String query_peliculas = "SELECT p.codPelicula, p.titulo, sum(e.precio) as recaudacion_peli "
				+ "FROM Salas s, Peliculas p, Entradas e "
				+ "WHERE s.codCine = ? AND s.codSala = e.codSala AND p.codPelicula = e.codPelicula "
				+ "GROUP BY p.codPelicula, p.titulo";
		PreparedStatement ps_peliculas = con.prepareStatement(query_peliculas.toString());
		
		while(rs_cine.next())
		{
			System.out.println("Cine " + rs_cine.getString("codCine") + " - Recaudacion_total: " + rs_cine.getInt("recaudacion_total"));
			
			ps_peliculas.setString(1, rs_cine.getString("codCine"));
			ResultSet rs_peli = ps_peliculas.executeQuery();
			
			while(rs_peli.next())
			{
				System.out.println("\t Codpelicula: " + rs_peli.getString("codPelicula") + " - Titulo: " + rs_peli.getString("titulo") + " - Recaudacion total de la pelicula: " + rs_peli.getInt("recaudacion_peli"));
			}
			rs_peli.close();
		}
		rs_cine.close();
		ps_peliculas.close();
		ps_cines.close();
		con.close();
	}
	
	/*
	3. En JAVA:
	a. Realizar un listado en el que se indique la siguiente informacion para cada pelicula:
		Titulo_Pelicula 1
			Cine 1
				Sala - Sesion - Numero de espectadores
				Sala - Sesion - Numero de espectadores
			Cine 2
				Sala - Sesion - Numero de espectadores
				Sala - Sesion - Numero de espectadores
	*/
	public static void exercise3_a() throws SQLException {
		System.out.println("#### Exercise 3_a ####");
		Connection con = getConnection();
		
		String query_pelis = "SELECT p.titulo, p.codPelicula "
				+ "FROM Proyectan pr, Peliculas p "
				+ "WHERE pr.codPelicula = p.codPelicula";
		Statement st_pelis = con.createStatement();
		ResultSet rs_peli = st_pelis.executeQuery(query_pelis.toString());
		
		// cines en los que se proyecta una determinada pelicula
		String query_cines = "SELECT s.codCine "
				+ "FROM Salas s, Proyectan pr "
				+ "WHERE pr.codPelicula = ? AND pr.codSala = s.codSala";
		PreparedStatement ps_cines = con.prepareStatement(query_cines.toString());
		
		// salas de un determinado cine en las que se proyecta una determinada pelicula
		String query_salas = "SELECT s.codSala, pr.sesion, sum(pr.entradasVendidas) as numero_espectadores "
				+ "FROM Salas s, Proyectan pr "
				+ "WHERE s.codCine = ? AND s.codSala = pr.codSala AND pr.codPelicula = ? "
				+ "GROUP BY s.codSala, pr.sesion";
		PreparedStatement ps_salas = con.prepareStatement(query_salas.toString());
		
		while(rs_peli.next())
		{
			System.out.println("Pelicula: " + rs_peli.getString("codPelicula") + " - Titulo: " + rs_peli.getString("titulo"));
			
			ps_cines.setString(1, rs_peli.getString("codPelicula"));
			ResultSet rs_cine = ps_cines.executeQuery();
			
			while(rs_cine.next())
			{
				System.out.println("\t Cine " + rs_cine.getString("codCine"));
				
				ps_salas.setString(1, rs_cine.getString("codCine")); // codCine
				ps_salas.setString(2, rs_peli.getString("codPelicula")); // codPelicula
				ResultSet rs_sala = ps_salas.executeQuery();
				
				while(rs_sala.next())
				{
					System.out.println("\t\t Sala " + rs_sala.getString("codSala") + " - Sesion " + rs_sala.getString("sesion") + " - Numero de espectadores " + rs_sala.getInt("numero_espectadores"));
				}
				rs_sala.close();
			}
			rs_cine.close();
		}
		rs_peli.close();
		
		ps_salas.close();
		ps_cines.close();
		st_pelis.close();
		con.close();
	}
	
	private static Connection getConnection() throws SQLException
	{
		if(DriverManager.getDriver(CONNECTION_URL) == null)
		{
			if(CONNECTION_URL.contains("oracle"))
			{
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			}
			else
			{
				DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
			}
		}
		return DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
	}
		
	@SuppressWarnings("resource")
	private static String ReadString(){
		return new Scanner(System.in).nextLine();		
	}
	
	@SuppressWarnings("resource")
	private static int ReadInt(){
		return new Scanner(System.in).nextInt();			
	}	
}
