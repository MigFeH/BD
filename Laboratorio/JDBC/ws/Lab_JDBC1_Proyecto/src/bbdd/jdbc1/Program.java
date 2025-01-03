package bbdd.jdbc1;
import java.sql.*;
import java.util.Scanner;

import oracle.jdbc.proxy.annotation.Pre;

public class Program {
	
	//ORACLE
	private static String USERNAME = "uo287577";
	private static String PASSWORD = "miguelin_2003";
	private static String CONNECTION_STRING = "jdbc:oracle:thin:@156.35.94.98:1521:DESA19";
	
	public static void main(String[] args) {
		try
		{
			//exercise1_1();
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
			exercise8();
		}catch (SQLException e)
		{
			System.err.println("SQL Exception " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/*
	 * public static void ejemplo() { //Ejemplos para leer por teclado
	 * System.out.println("Leer un entero por teclado"); int entero = ReadInt();
	 * System.out.println("Leer una cadena por teclado"); String cadena =
	 * ReadString(); }
	 */
	

	/*
		1.	Crear un metodo en Java que muestre por pantalla los resultados de las consultas 21 y 32 de la Practica SQL2. 
		1.1. (21) Obtener el nombre y el apellido de los clientes que han adquirido un coche en un concesionario de Madrid, el cual dispone de coches del modelo gti.
	 */
	public static void exercise1_1() throws SQLException{
		System.out.println("###### EXERCISE 1 ######");
		Connection con = getConnection();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT nombre, apellido ");
		query.append(" FROM clientes ");
		query.append("  WHERE dni IN (");
		query.append("   SELECT DISTINCT V.dni ");
		query.append("    FROM ventas V, coches CH, concesionarios CO, distribucion D ");
		query.append("     WHERE V.cifc=CO.cifc ");
		query.append("     AND CO.ciudadc='madrid' ");
		query.append("     AND CO.cifc=D.cifc ");
		query.append("     AND D.codcoche = CH.codcoche ");
		query.append("     AND CH.modelo = 'gti' ");
		query.append(" )");
		
		
		System.out.println(query.toString());
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query.toString());
		showResults(rs);
	}
	
	/* 
		1.2. (32) Obtener un listado de los concesionarios cuyo promedio de coches supera a la cantidad promedio de todos los concesionarios. 
	*/
	public static void exercise1_2() throws SQLException{
		System.out.println("###### EXERCISE 1_2 ######");
		Connection con = getConnection();
		
		String query = "SELECT cifc,nombrec,ciudadc "
				+ "FROM concesionarios "
				+ "WHERE cifc IN( "
				+ "SELECT cifc "
				+ "FROM distribucion "
				+ "GROUP BY cifc "
				+ "HAVING SUM(cantidad)> (SELECT AVG(total) FROM(SELECT SUM(cantidad)total FROM distribucion GROUP BY cifc)))";
		
		System.out.println(query.toString());
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query.toString());
		showResults(rs);
	}
	
	/*
		2. Crear un metodo en Java que muestre por pantalla el resultado de la consulta 6 de la Practica SQL2 de forma el color de la busqueda sea introducido por el usuario.
			(6) Obtener el nombre de las marcas de las que se han vendido coches de un color introducido por el usuario.
	*/
	public static void exercise2() throws SQLException{
		System.out.println("###### EXERCISE 2 ######");
		Connection con = getConnection();
		
		String query = "SELECT DISTINCT M.nombrem "
				+ "FROM marcas M, marco R, ventas V "
				+ "WHERE M.cifm=R.cifm AND R.codcoche=V.codcoche AND V.color=?";
				
		PreparedStatement pst = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el color: ");
		String color = ReadString();
		
		pst.setString(1, color);
		ResultSet rs = pst.executeQuery();
		showResults(rs);
		
		rs.close();
		pst.clearBatch();
		con.close();
	}
	
	/*
		3.	Crear un metodo en Java para ejecutar la consulta 27 de la Practica SQL2 de forma que los limites la cantidad de coches sean introducidos por el usuario. 
			(27) Obtener el cifc de los concesionarios que disponen de una cantidad de coches comprendida entre dos cantidades introducidas por el usuario, ambas inclusive.
	*/
	public static void exercise3() throws SQLException{
		System.out.println("###### EXERCISE 3 ######");
		Connection con = getConnection();
		
		String query = "SELECT cifc, sum(cantidad) AS total "
				+ "FROM distribucion "
				+ "GROUP BY cifc "
				+ "HAVING sum(cantidad)>=? AND sum(cantidad)<=?";
				
		PreparedStatement pst = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el mínimo de coches: ");
		int minimo = ReadInt();
		
		System.out.println("Por favor, introduzca el máximo de coches: ");
		int maximo = ReadInt();
		
		
		pst.setInt(1, minimo);
		pst.setInt(2, maximo);
		
		
		ResultSet rs = pst.executeQuery();
		showResults(rs);
		
		rs.close();
		pst.clearBatch();
		con.close();
	}
	
	/*
		4.	Crear un metodo en Java para ejecutar la consulta 24 de la Practica SQL2 de forma que tanto la ciudad del concesionario como el color sean introducidos por el usuario. 
			(24) Obtener los nombres de los clientes que no han comprado coches de un color introducido por el usuario en concesionarios de una ciudad introducida por el usuario.
	*/
	public static void exercise4() throws SQLException{
		System.out.println("###### EXERCISE 4 ######");
		Connection con = getConnection();
		
		String query = "SELECT DISTINCT c.nombre "
				+ "FROM Clientes c, Ventas v "
				+ "WHERE c.dni = v.dni "
				+ "AND v.dni NOT IN (SELECT ve.dni "
				+ "					FROM Ventas ve, Concesionarios con "
				+ "					WHERE ve.cifc = con.cifc"
				+ "					AND con.ciudadc = ?"
				+ "					AND ve.color = ?)";
		
		PreparedStatement pst = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca la ciudad del concesionario: ");
		String ciudad = ReadString();
		
		System.out.println("Por favor introduzca el color del coche: ");
		String color = ReadString();
		
		pst.setString(1, ciudad);
		pst.setString(2, color);
		
		ResultSet rs = pst.executeQuery();
		showResults(rs);
		
		rs.close();
		pst.clearBatch();
		con.close();
	}
	
	/*
		5.	Crear un metodo en Java que haciendo uso de la instruccion SQL adecuada: 
		5.1. Introduzca datos en la tabla coches cuyos datos son introducidos por el usuario.
	*/
	public static void exercise5_1() throws SQLException{
		System.out.println("###### EXERCISE 5_1 ######");
		Connection con = getConnection();
		
		String query = "INSERT INTO coches (codcoche,nombrech,modelo) VALUES (?,?,?)";
		PreparedStatement pst = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo del coche: ");
		int codcoche = ReadInt();
		System.out.println("Por favor, introduzca el nombre del coche: ");
		String nombrech = ReadString();
		System.out.println("Por favor, introduzca el modelo del coche: ");
		String modelo = ReadString();
		
		pst.setInt(1, codcoche);
		pst.setString(2, nombrech);
		pst.setString(3, modelo);
		
		if(pst.executeUpdate() == 1)
		{
			System.out.println("Datos insertados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no insertados.");
		}
		
		
		
		pst.close();
		con.close();
	}
	
	/*
		5.2. Borre un determinado coche cuyo codigo es introducido por el usuario. 
	*/
	public static void exercise5_2() throws SQLException{
		System.out.println("###### EXERCISE 5_2 ######");
		Connection con = getConnection();
		
		String query = "DELETE FROM Coches WHERE codcoche = ?";
		PreparedStatement pst = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo del coche: ");
		int codcoche = ReadInt(); 
		
		pst.setInt(1, codcoche);
		
		if(pst.executeUpdate() == 1)
		{
			System.out.println("Coche eliminado correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, coche no eliminado.");
		}
		
		
		pst.close();
		con.close();
	}
	
	/*	 
		5.3. Actualice el nombre y el modelo para un determinado coche cuyo codigo es introducido por el usuario.
	*/
	public static void exercise5_3() throws SQLException{		
		System.out.println("###### EXERCISE 5_3 ######");
		Connection con = getConnection();
		
		String query = "UPDATE Coches SET nombrech = ? AND modelo = ? WHERE codcoche = ?";
		PreparedStatement pst = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el nombre del coche: ");
		String nombrech = ReadString();
		System.out.println("Por favor, introduzca el modelo del coche: ");
		String modelo = ReadString();
		System.out.println("Por favor, introduzca el codigo del coche: ");
		int codcoche = ReadInt();
		
		pst.setString(1, nombrech);
		pst.setString(2, modelo);
		pst.setInt(3, codcoche);
		
		if(pst.executeUpdate() == 1)
		{
			System.out.println("Datos actualizados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no actualizados.");
		}
		
		pst.close();
		con.close();
	}
	
	/*
		6. Invocar la funcion y el procedimiento del ejercicio 10 de la practica PL1 desde una aplicacion Java. 
			(10) Realizar un procedimiento y una funcion que dado un codigo de concesionario devuelve el numero ventas que se han realizado en el mismo.
		6.1. Funcion
	*/
	public static void exercise6_1() throws SQLException{
		System.out.println("###### EXERCISE 6_1 ######");
		Connection con = getConnection();
		
		StringBuilder query = new StringBuilder();
		query.append("{? = call FUNCTION1_NUMERO_DE_VENTAS(?)}");
		
		System.out.println("Por favor, introduzca el código del concesionario: ");
		String cifc = ReadString();
		
		CallableStatement cst = con.prepareCall(query.toString());
		cst.registerOutParameter(1, Types.INTEGER, 0); // registro del parámetro de salida de la funcion
		cst.setString(2, cifc); // parametro de la funcion
		
		cst.execute();
		System.out.println("Ventas: " + cst.getInt(1)); // devuelve el primer parámetro de salida de la funcion
		
		cst.close();
		con.close();
	}
	
	/*	
		6.2. Procedimiento
	*/
	public static void exercise6_2() throws SQLException{		
		System.out.println("###### EXERCISE 6_2 ######");
		Connection con = getConnection();
		
		StringBuilder query = new StringBuilder();
		query.append("{call PROCEDURE_10(?,?)}");
		
		System.out.println("Por favor, introduzca el código del concesionario: ");
		String cifc = ReadString();
		
		CallableStatement cst = con.prepareCall(query.toString());
		cst.setString(1, cifc); // parametro del procedimiento
		cst.registerOutParameter(2, Types.INTEGER, 0); // registro del parámetro de salida del procedimiento
		
		cst.execute();
		System.out.println("Ventas: " + cst.getInt(2)); // devuelve el primer parámetro de salida del procedimiento
		
		cst.close();
		con.close();
	}
	
	/*
		7. Invocar la funcion y el procedimiento del ejercicio 11 de la Practica PL1 desde una aplicacion Java. 
			(11) Realizar un procedimiento y una funcion que dada una ciudad que se le pasa como parametro devuelve el numero de clientes de dicha ciudad.
		7.1. Funcion
	*/
	public static void exercise7_1() throws SQLException{		
		System.out.println("###### EXERCISE 7_1 ######");
		Connection con = getConnection();
		
		StringBuilder query = new StringBuilder();
		query.append("{? = call FUNCTION11(?)}");
		
		System.out.println("Por favor, introduzca la ciudad: ");
		String ciudad = ReadString();
		
		CallableStatement cst = con.prepareCall(query.toString());
		cst.registerOutParameter(1, Types.INTEGER, 0); // registro del parámetro de salida de la funcion
		cst.setString(2, ciudad); // parametro de la funcion
		
		cst.execute();
		System.out.println("Numero de clientes: " + cst.getInt(1)); // devuelve el primer parámetro de salida de la funcion
		
		cst.close();
		con.close();
	}	
	
	/*
		7.2. Procedimiento
	*/
	public static void exercise7_2() throws SQLException{		
		System.out.println("###### EXERCISE 7_2 ######");
		Connection con = getConnection();
		
		StringBuilder query = new StringBuilder();
		query.append("{call PROCEDURE11(?,?)}");
		
		System.out.println("Por favor, introduzca la ciudad: ");
		String ciudad = ReadString();
		
		CallableStatement cst = con.prepareCall(query.toString());
		cst.registerOutParameter(2, Types.INTEGER, 0); // registro del parámetro de salida de la funcion
		cst.setString(1, ciudad); // parametro de la funcion
		
		cst.execute();
		System.out.println("Numero de clientes: " + cst.getInt(2)); // devuelve el primer parámetro de salida del procedimiento
		
		cst.close();
		con.close();
	}
	
    /*
     	8. Crear un metodo en Java que imprima por pantalla los coches que han sido adquiridos por cada cliente.
     	Ademas, debera imprimirse para cada cliente el numero de coches que ha comprado y el numero de
     	concesionarios en los que ha comprado. Aquellos clientes que no han adquirido ningun coche no
		deben aparecer en el listado.
    */
	public static void exercise8() throws SQLException{		
		System.out.println("###### EXERCISE 8 ######");
		Connection con = getConnection();
		
		// seleccionar distintos dni, nombres, y apellidos que hayan comprado un coche
		String queryClientes = "SELECT c.dni, c.nombre, c.apellido "
				+ "FROM Ventas v, Clientes c "
				+ "WHERE v.dni = c.dni";
		Statement st = con.createStatement();
		ResultSet rsclientes = st.executeQuery(queryClientes.toString());
		
		
		// contamos las ventas de un cliente
		String queryCount = "SELECT count(*) as numcoches, count(DISTINCT cifc) as numConc "
				+ "FROM Ventas "
				+ "WHERE dni = ?";
		PreparedStatement pstcount = con.prepareStatement(queryCount.toString());
		
		
		// tomamos los datos del coche del cliente en especifico
		String queryCoches = "SELECT CH.codcoche as codch, CH.nombrech as nomch, CH.modelo as mod, V.color as col "
				+ "FROM Coches CH, ventas V "
				+ "WHERE CH.codcoche = V.codcoche AND V.dni = ?";
		PreparedStatement pstcoches = con.prepareStatement(queryCoches.toString());
		
		while(rsclientes.next())
		{
			pstcount.setString(1, rsclientes.getString("dni"));
			ResultSet rs = pstcount.executeQuery();
			rs.next();
			
			System.out.println("- Cliente: "+rsclientes.getString("nombre")+" "+rsclientes.getString("apellido")+" "+rs.getInt("numcoches")+" "+rs.getInt("numConc"));
			
			pstcoches.setString(1, rsclientes.getString("dni"));
			rs = pstcoches.executeQuery();		
			
			while(rs.next())
			{
			  System.out.println("---> Coche: "+rs.getString("codch")+" "+rs.getString("nomch")+" "+rs.getString("mod")+" "+rs.getString("col"));
			}
			rs.close();
		}
		
		pstcount.close();
		pstcoches.close();
		rsclientes.close();	
		st.close();
		con.close();
		
		
	}
	
	
	// --- DATABASE UTILS ---
	private static Connection getConnection() throws SQLException {
		if(DriverManager.getDriver(CONNECTION_STRING) == null)
		{
			if(CONNECTION_STRING.contains("oracle"))
			{
				DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			}
			else
			{
				DriverManager.registerDriver(new org.hsqldb.jdbc.JDBCDriver());
			}
		}
		return DriverManager.getConnection(CONNECTION_STRING, USERNAME,PASSWORD);
		
	}
	
	private static void showResults(ResultSet rs) throws SQLException
	{
		int columnCount = rs.getMetaData().getColumnCount();
		StringBuilder headers = new StringBuilder();
		
		for(int i = 1; i < columnCount; i++)
		{
			headers.append(rs.getMetaData().getColumnName(i) + "\t");
		}
		headers.append(rs.getMetaData().getColumnName(columnCount));
		
		System.out.println(headers.toString());
		StringBuilder result = null;
		
		while (rs.next())
		{
			result = new StringBuilder();
			for(int i = 1; i < columnCount; i++)
			{
				result.append(rs.getObject(i) + "\t");
			}
			result.append(rs.getObject(columnCount));
			System.out.println(result.toString());
			
		}
		
		if(result == null)
		{
			System.out.println("No se encontraron datos.");
		}
		
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
