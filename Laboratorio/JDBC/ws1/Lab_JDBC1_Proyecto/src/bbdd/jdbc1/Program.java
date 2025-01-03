package bbdd.jdbc1;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.*;

public class Program {
	private static String CONNECTION_URL = "jdbc:oracle:thin:@156.35.94.98:1521:DESA19";
	private static String USER = "UO287577";
	private static String PASSWORD = "miguelin_2003";
	
	public static void main(String[] args) {
		try
		{
			exercise1_1();
			//exercise1_2();
			//exercise2();
			//exercise3();
			//exercise4();
			//exercise5_1();
			//exercise5_2();
			//exercise5_3();
			//exercise6_1();
			//exercise6_2();
			//exercise7_1();
			//exercise7_2();
			//exercise8();
		}catch(SQLException e)
		{
			System.err.println("SQL Exception " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
		1.	Crear un metodo en Java que muestre por pantalla los resultados de las consultas
		 	21 y 32 de la Practica SQL2.
		 	
		1.1. (21) Obtener el nombre y el apellido de los clientes que han adquirido un coche 
		en un concesionario de Madrid, el cual dispone de coches del modelo gti.
	 */
	public static void exercise1_1() throws SQLException {
		System.out.println("###### EXERCISE 1_1 ######");
		Connection con = getConnection();
		Statement st = con.createStatement();
		
		String query = "SELECT c.nombre, c.apellido "
				+ "FROM Ventas v, Clientes c, Coches ch, Concesionarios co, Distribucion d "
				+ "WHERE v.dni = c.dni "
				+ "AND v.cifc = co.cifc "
				+ "AND co.ciudadc = 'Madrid' "
				+ "AND d.codcoche = ch.codcoche "
				+ "AND d.cifc = co.cifc "
				+ "AND ch.modelo = 'gti' ";
		st.executeQuery(query.toString());
		
		st.close();
		con.close();
	}
	
	/* 
		1.2. (32) Obtener un listado de los concesionarios cuyo promedio de coches supera a 
		la cantidad promedio de todos los concesionarios. 
	*/
	public static void exercise1_2() throws SQLException {
		System.out.println("###### EXERCISE 1_2 ######");
		Connection con = getConnection();
		Statement st = con.createStatement();
		
		String query = "SELECT d.cifc "
				+ "FROM Distribucion d "
				+ "GROUP BY d.cifc, d.codcoche "
				+ "HAVING avg(d.cantidad) > (SELECT avg(cantidad) FROM Distribucion GROUP BY cifc, codcoche)";
		st.executeQuery(query.toString());
		
		st.close();
		con.close();
	}
	
	/*
		2. Crear un metodo en Java que muestre por pantalla el resultado de la consulta 6 de 
		la Practica SQL2 de forma el color de la busqueda sea introducido por el usuario.
			
			(6) Obtener el nombre de las marcas de las que se han vendido coches de un color 
			introducido por el usuario.
	*/
	public static void exercise2() throws SQLException {
		System.out.println("###### EXERCISE 2 ######");
		Connection con = getConnection();
		
		String query = "SELECT m.nombrem "
				+ "FROM Marcas m, Marco ma, Coches ch, Ventas v "
				+ "WHERE v.codcoche = ch.codcoche "
				+ "AND v.color = ? "
				+ "AND ma.codcoche = ch.codcoche "
				+ "AND ma.cifm = m.cifm";
		PreparedStatement ps = con.prepareStatement(query.toString());
		System.out.println("Por favor, introduzca el nombre de un color: ");
		String color = ReadString();
		ps.setString(1, color);
		ps.executeQuery();
		
		ps.close();
		con.close();
	}
	
	/*
		3.	Crear un metodo en Java para ejecutar la consulta 27 de la Practica SQL2 de forma 
		que los limites de la cantidad de coches sean introducidos por el usuario. 
			
			(27) Obtener el cifc de los concesionarios que disponen de una cantidad de coches 
			comprendida entre dos cantidades introducidas por el usuario, ambas inclusive.

	*/
	public static void exercise3() throws SQLException {
		System.out.println("###### EXERCISE 3 ######");
		Connection con = getConnection();
		
		String query = "SELECT d.cifc "
				+ "FROM Distribucion d "
				+ "GROUP BY d.cifc, d.codcoche "
				+ "HAVING sum(d.cantidad) BETWEEN ? AND ?";
		PreparedStatement ps = con.prepareStatement(query.toString());
		System.out.println("Por favor, introduzca un valor minimo de coches disponibles para un concesionario: ");
		int minimo = ReadInt();
		ps.setInt(1, minimo);
		System.out.println("Por favor, introduzca un valor maximo de coches disponibles para un concesionario: ");
		int maximo = ReadInt();
		ps.setInt(2, maximo);
		ps.executeQuery();
		
		ps.close();
		con.close();
	}
	
	/*
		4.	Crear un metodo en Java para ejecutar la consulta 24 de la Practica SQL2 de 
		forma que tanto la ciudad del concesionario como el color sean introducidos por el 
		usuario. 
			
			(24) Obtener los nombres de los clientes que no han comprado coches de un color 
			introducido por el usuario en concesionarios de una ciudad introducida por el 
			usuario.

	*/
	public static void exercise4() throws SQLException {
		System.out.println("###### EXERCISE 4 ######");
		Connection con = getConnection();
		
		String query = "SELECT c.nombre "
				+ "FROM Clientes c, Ventas v "
				+ "WHERE c.dni = v.dni "
				+ "AND v.dni not in (SELECT v1.dni "
								+ "FROM Ventas v1, Concesionarios con "
								+ "WHERE v1.color = ? "
								+ "AND v1.cifc = con.cifc "
								+ "AND con.ciudadc = ?)";
		PreparedStatement ps = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el nombre de un color: ");
		String color = ReadString();
		ps.setString(1, color);
		
		System.out.println("Por favor, introduzca el nombre de una ciudad: ");
		String ciudad = ReadString();
		ps.setString(2, ciudad);
		
		ps.executeQuery();
		
		ps.close();
		con.close();
	}
	
	/*
		5.	Crear un metodo en Java que haciendo uso de la instruccion SQL adecuada: 
		5.1. Introduzca datos en la tabla coches cuyos datos son introducidos por el usuario.

	*/
	public static void exercise5_1() throws SQLException {
		System.out.println("###### EXERCISE 5_1 ######");
		Connection con = getConnection();
		
		String query = "INSERT INTO Coches VALUES(?,?,?)";
		PreparedStatement ps = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un coche: ");
		int codigo = ReadInt();
		ps.setInt(1, codigo);
		
		System.out.println("Por favor, introduzca el nombre de un coche: ");
		String nombre = ReadString();
		ps.setString(2, nombre);
		
		System.out.println("Por favor, introduzca el modelo de un coche: ");
		String modelo = ReadString();
		ps.setString(3, modelo);
		
		if(ps.executeUpdate() == 1)
		{
			System.out.println("Datos insertados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no insertados.");
		}
		
		ps.close();
		con.close();
	}
	
	/*
		5.2. Borre un determinado coche cuyo codigo es introducido por el usuario. 
	*/
	public static void exercise5_2() throws SQLException {
		System.out.println("###### EXERCISE 5_2 ######");
		Connection con = getConnection();
		
		String query = "DELETE FROM Coches WHERE codcoche = ?";
		PreparedStatement ps = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un coche: ");
		int codigo = ReadInt();
		ps.setInt(1, codigo);
		
		if(ps.executeUpdate() == 1)
		{
			System.out.println("Datos eliminados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no eliminados.");
		}
		
		ps.close();
		con.close();
	}
	
	/*	 
		5.3. Actualice el nombre y el modelo para un determinado coche cuyo codigo es 
		introducido por el usuario.
	*/
	public static void exercise5_3() throws SQLException {		
		System.out.println("###### EXERCISE 5_3 ######");
		Connection con = getConnection();
		
		String query = "UPDATE Coches SET nombrech = ? AND modelo = ? WHERE codcoche = ?";
		PreparedStatement ps = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un coche: ");
		int codigo = ReadInt();
		ps.setInt(3, codigo);
		
		System.out.println("Por favor, introduzca nuevo nombre del coche: ");
		String nombre = ReadString();
		ps.setString(1, nombre);
		
		System.out.println("Por favor, introduzca nuevo modelo del coche: ");
		String modelo = ReadString();
		ps.setString(2, modelo);
		
		if(ps.executeUpdate() == 1)
		{
			System.out.println("Datos actualizados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no actualizados.");
		}
		
		ps.close();
		con.close();
	}
	
	/*
		6. Invocar la funcion y el procedimiento del ejercicio 10 de la practica PL1 desde 
		una aplicacion Java. 
			
			(10) Realizar un procedimiento y una funcion que dado un codigo de concesionario 
			devuelve el numero ventas que se han realizado en el mismo.
		6.1. Funcion
	*/
	public static void exercise6_1() throws SQLException {		
		System.out.println("###### EXERCISE 6_1 ######");
		Connection con = getConnection();
		
		String query = "{? = call FUNCTION1_NUMERO_DE_VENTAS(?)}";
		CallableStatement cs = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un concesionario: ");
		String codigo = ReadString();
		cs.setString(2, codigo);
		cs.registerOutParameter(1, Types.INTEGER, 0);
		
		cs.execute();
		
		System.out.println("Numero de ventas: " + cs.getInt(1));
		
		cs.close();
		con.close();
	}
	
	/*	
		6.2. Procedimiento
	*/
	public static void exercise6_2() throws SQLException {		
		System.out.println("###### EXERCISE 6_2 ######");
		Connection con = getConnection();
		
		String query = "{call PROCEDURE_10(?,?)}";
		CallableStatement cs = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un concesionario: ");
		String codigo = ReadString();
		cs.setString(1, codigo);
		cs.registerOutParameter(2, Types.INTEGER, 0);
		
		cs.execute();
		
		System.out.println("Numero de ventas: " + cs.getInt(2));
		
		cs.close();
		con.close();
	}
	
	/*
		7. Invocar la funcion y el procedimiento del ejercicio 11 de la Practica PL1 desde 
		una aplicacion Java. 
			
			(11) Realizar un procedimiento y una funcion que dada una ciudad que se le 
			pasa como parametro devuelve el numero de clientes de dicha ciudad.
		7.1. Funcion

	*/
	public static void exercise7_1() throws SQLException {		
		System.out.println("###### EXERCISE 7_1 ######");
		Connection con = getConnection();
		
		String query = "{? = call FUNCTION11(?)}";
		CallableStatement cs = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el nombre de una ciudad: ");
		String ciudad = ReadString();
		cs.setString(2, ciudad);
		cs.registerOutParameter(1, Types.INTEGER, 0);
		
		cs.execute();
		
		System.out.println("Numero de clientes: " + cs.getInt(1));
		
		cs.close();
		con.close();
	}	
	
	/*
		7.2. Procedimiento
	*/
	public static void exercise7_2() throws SQLException {
		System.out.println("###### EXERCISE 7_2 ######");
		Connection con = getConnection();
		
		String query = "{call PROCEDURE11(?,?)}";
		CallableStatement cs = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el nombre de una ciudad: ");
		String ciudad = ReadString();
		cs.setString(1, ciudad);
		cs.registerOutParameter(2, Types.INTEGER, 0);
		
		cs.execute();
		
		System.out.println("Numero de clientes: " + cs.getInt(2));
		
		cs.close();
		con.close();
	}
	
    /*
     	8. Crear un metodo en Java que imprima por pantalla los coches que han sido 
     	adquiridos por cada cliente.
     	
     	Ademas, debera imprimirse para cada cliente el numero de coches que ha comprado y 
     	el numero de concesionarios en los que ha comprado. Aquellos clientes que no han 
     	adquirido ningun coche no deben aparecer en el listado.
    */
	public static void exercise8() throws SQLException {
		System.out.println("###### EXERCISE 8 ######");
		Connection con = getConnection();
		
		// clientes que han comprado
		String query_clientes = "SELECT c.nombre, c.apellido, count(v.codcoche) as numcoches, count(DISTINCT v.cifc) as numconc "
				+ "FROM Clientes c, Ventas v "
				+ "WHERE c.dni = v.dni ";
		Statement st_clientes = con.createStatement();
		ResultSet rs_clientes = st_clientes.executeQuery(query_clientes.toString());
		
		
		// coches del cliente
	}
	
	private static Connection getConnection() throws SQLException
	{
		if(DriverManager.getDriver(CONNECTION_URL) == null)
		{
			if(CONNECTION_URL.contains("oracle"))
			{
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			}else
			{
				DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
			}
		}
		return DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
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
