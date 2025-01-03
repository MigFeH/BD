package bbdd.jdbc1;
import java.sql.*;
import java.util.Scanner;

public class Program {
	
	private static final String USERNAME = "uo287577";
	private static final String PASSWORD = "miguelin_2003";
	private static final String CONNECTION_URL = "jdbc:oracle:thin:@156.35.94.98:1521:DESA19";
	
	public static void main(String[] args) {
		try
		{
			exercise1_1();
			exercise1_2();
			exercise2();
			exercise3();
			exercise4();
			exercise5_1();
			exercise5_2();
			exercise5_3();
			exercise6_1();
			exercise6_2();
			exercise7_1();
			exercise7_2();
			exercise8();
		}catch(SQLException e)
		{
			System.err.println("SQL Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
		1.	Crear un metodo en Java que muestre por pantalla los resultados de las consultas 21 y 32 de la 
		Practica SQL2. 
		1.1. (21) Obtener el nombre y el apellido de los clientes que han adquirido un coche en un concesionario 
		de Madrid, el cual dispone de coches del modelo gti.
	 */
	public static void exercise1_1() throws SQLException {
		System.out.println("#### Exercise 1_1 ####");
		Connection con = getConnection();
		
		String query = "SELECT c.nombre, c.apellido "
				+ "FROM Clientes c, Ventas v, Concesionarios con "
				+ "WHERE c.dni = v.dni AND v.cifc = con.cifc AND con.ciudadc = 'Madrid' "
				+ "AND con.cifc in (SELECT d.cifc "
								+ "FROM Distribucion d, Coches ch "
								+ "WHERE d.codCoche = ch.codCoche "
								+ "AND ch.modelo = 'gti')";
		Statement st_query = con.createStatement();
		st_query.executeQuery(query.toString());
		
		st_query.close();
		con.close();
	}
	
	/* 
		1.2. (32) Obtener un listado de los concesionarios cuyo promedio de coches supera a la cantidad 
		promedio de todos los concesionarios. 
	*/
	public static void exercise1_2() throws SQLException {
		System.out.println("#### Exercise 1_2 ####");
		Connection con = getConnection();
		
		String query = "SELECT c.cifc, c.nombrec "
				+ "FROM Distribucion d, Concesionarios c "
				+ "WHERE d.cifc = c.cifc "
				+ "GROUP BY c.cifc "
				+ "HAVING avg(d.cantidad) >= (SELECT avg(cantidad) FROM Distribucion) ";
		Statement st_query = con.createStatement();
		st_query.executeQuery(query.toString());
		
		st_query.close();
		con.close();
	}
	
	/*
		2. Crear un metodo en Java que muestre por pantalla el resultado de la consulta 6 de la Practica SQL2 
		de forma el color de la busqueda sea introducido por el usuario.
			
			(6) Obtener el nombre de las marcas de las que se han vendido coches de un color introducido por 
			el usuario.
	*/
	public static void exercise2() throws SQLException {
		System.out.println("#### Exercise 2 ####");
		Connection con = getConnection();
		
		String query = "SELECT DISTINCT m.nombrem "
				+ "FROM Marcas m, Marco mar, Coches ch, Ventas v "
				+ "WHERE m.cifm = mar.cifm AND mar.codCoche = ch.codCoche AND ch.codCoche = v.codCoche "
				+ "AND v.color = ?";
		PreparedStatement ps_query = con.prepareStatement(query.toString());
		System.out.println("Por favor, introduzca el nombre de un color: ");
		String color = ReadString();
		ps_query.setString(1, color);
		ps_query.executeQuery();
		
		ps_query.close();
		con.close();
	}
	
	/*
		3.	Crear un metodo en Java para ejecutar la consulta 27 de la Practica SQL2 de forma que los limites 
		la cantidad de coches sean introducidos por el usuario.
		
			(27) Obtener el cifc de los concesionarios que disponen de una cantidad de coches comprendida 
			entre dos cantidades introducidas por el usuario, ambas inclusive.

	*/
	public static void exercise3() throws SQLException {
		System.out.println("#### Exercise 3 ####");
		Connection con = getConnection();
		
		String query = "SELECT cifc "
				+ "FROM Distribucion "
				+ "GROUP BY cifc "
				+ "HAVING sum(cantidad) BETWEEN ? AND ?";
		PreparedStatement ps_query = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca un valor minimo de coches disponibles: ");
		int minimo = ReadInt();
		System.out.println("Por favor, introduzca un valor maximo de coches disponibles: ");
		int maximo = ReadInt();
		
		ps_query.setInt(1, minimo);
		ps_query.setInt(2, maximo);
		ps_query.executeQuery();
		
		ps_query.close();
		con.close();
	}
	
	/*
		4.	Crear un metodo en Java para ejecutar la consulta 24 de la Practica SQL2 de forma que tanto la 
		ciudad del concesionario como el color sean introducidos por el usuario. 
			
			(24) Obtener los nombres de los clientes que no han comprado coches de un color introducido por 
			el usuario en concesionarios de una ciudad introducida por el usuario.

	*/
	public static void exercise4() throws SQLException {
		System.out.println("#### Exercise 4 ####");
		Connection con = getConnection();
		
		String query = "SELECT nombre "
				+ "FROM Clientes "
				+ "WHERE dni not in (SELECT v.dni "
									+ "FROM Ventas v, Concesionarios c "
									+ "WHERE v.color = ? AND v.cifc = c.cifc AND c.ciudadc = ?)";
		PreparedStatement ps_query = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el nombre de un color: ");
		String color = ReadString();
		ps_query.setString(1, color);
		
		System.out.println("Por favor, introduzca el nombre de una ciudad: ");
		String ciudad = ReadString();
		ps_query.setString(2, ciudad);
		
		ps_query.executeQuery();
		
		ps_query.close();
		con.close();
	}
	
	/*
		5.	Crear un metodo en Java que haciendo uso de la instruccion SQL adecuada: 
		5.1. Introduzca datos en la tabla coches cuyos datos son introducidos por el usuario.

	*/
	public static void exercise5_1() throws SQLException {
		System.out.println("#### Exercise 5_1 ####");
		Connection con = getConnection();
		
		String query = "INSERT INTO Coches VALUES(?,?,?)";
		PreparedStatement ps_query = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un coche: ");
		int codCoche = ReadInt();
		ps_query.setInt(1, codCoche);
		
		System.out.println("Por favor, introduzca el nombre de un coche: ");
		String nombrech = ReadString();
		ps_query.setString(2, nombrech);
		
		System.out.println("Por favor, introduzca el modelo de un coche: ");
		String modelo = ReadString();
		ps_query.setString(3, modelo);
		
		if(ps_query.executeUpdate() == 1)
		{
			System.out.println("Datos insertados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no insertados.");
		}
		
		ps_query.close();
		con.close();
		
	}
	
	/*
		5.2. Borre un determinado coche cuyo codigo es introducido por el usuario. 
	*/
	public static void exercise5_2() throws SQLException {
		System.out.println("#### Exercise 5_2 ####");
		Connection con = getConnection();
		
		String query = "DELETE FROM Coches WHERE codcoche = ?";
		PreparedStatement ps_query = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo del coche a borrar: ");
		int codigo = ReadInt();
		ps_query.setInt(1, codigo);
		
		if(ps_query.executeUpdate() == 1)
		{
			System.out.println("Datos eliminados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no eliminados.");
		}
		
		ps_query.close();
		con.close();
	}
	
	/*	 
		5.3. Actualice el nombre y el modelo para un determinado coche cuyo codigo es introducido por el usuario.
	*/
	public static void exercise5_3() throws SQLException {		
		System.out.println("#### Exercise 5_3 ####");
		Connection con = getConnection();
		
		String query = "UPDATE Coches SET nombrech = ? AND modelo = ? WHERE codCoche = ?";
		PreparedStatement ps_query = con.prepareStatement(query.toString());
		
		System.out.println("Por favor, introduzca el codigo del coche a actualizar: ");
		int codigo = ReadInt();
		ps_query.setInt(3, codigo);
		
		System.out.println("Por favor, introduzca el nuevo nombre para el coche: ");
		String nombre = ReadString();
		ps_query.setString(1, nombre);
		
		System.out.println("Por favor, introduzca el nuevo modelo para el coche: ");
		String modelo = ReadString();
		ps_query.setString(2, modelo);
		
		if(ps_query.executeUpdate() == 1)
		{
			System.out.println("Datos actualizados correctamente.");
		}
		else
		{
			System.out.println("Ha ocurrido un error, datos no actualizados.");
		}
		
		ps_query.close();
		con.close();
	}
	
	/*
		6. Invocar la funcion y el procedimiento del ejercicio 10 de la practica PL1 desde una aplicacion Java.
		
			(10) Realizar un procedimiento y una funcion que dado un codigo de concesionario devuelve el 
			numero ventas que se han realizado en el mismo.
			
		6.1. Funcion
	*/
	public static void exercise6_1() throws SQLException {		
		System.out.println("#### Exercise 6_1 ####");
		Connection con = getConnection();
		
		String query = "{? = call FUNCTION1_NUMERO_DE_VENTAS(?)}";
		CallableStatement cs_query = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un concesionario: ");
		String codigo = ReadString();
		cs_query.setString(2, codigo);
		cs_query.registerOutParameter(1, Types.INTEGER, 0);
		
		cs_query.execute();
		System.out.println("Numero de ventas: " + cs_query.getInt(1));
		
		cs_query.close();
		con.close();
	}
	
	/*	
		6.2. Procedimiento
	*/
	public static void exercise6_2() throws SQLException {		
		System.out.println("#### Exercise 6_2 ####");
		Connection con = getConnection();
		
		String query = "{call PROCEDURE_10(?,?)}";
		CallableStatement cs_query = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el codigo de un concesionario: ");
		String codigo = ReadString();
		cs_query.setString(1, codigo);
		cs_query.registerOutParameter(2, Types.INTEGER, 0);
		
		cs_query.execute();
		System.out.println("Numero de ventas: " + cs_query.getInt(2));
		
		cs_query.close();
		con.close();
	}
	
	/*
		7. Invocar la funcion y el procedimiento del ejercicio 11 de la Practica PL1 desde una aplicacion Java.
		 
			(11) Realizar un procedimiento y una funcion que dada una ciudad que se le pasa como parametro 
			devuelve el numero de clientes de dicha ciudad.
		
		7.1. Funcion

	*/
	public static void exercise7_1() throws SQLException {		
		System.out.println("#### Exercise 7_1 ####");
		Connection con = getConnection();
		
		String query = "{? = call FUNCTION11(?)}";
		CallableStatement cs_query = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el nombre de una ciudad: ");
		String ciudad = ReadString();
		cs_query.setString(2, ciudad);
		cs_query.registerOutParameter(1, Types.INTEGER, 0);
		
		cs_query.execute();
		System.out.println("Numero de clientes: " + cs_query.getInt(1));
		
		cs_query.close();
		con.close();
	}
	
	/*
		7.2. Procedimiento
	*/
	public static void exercise7_2() throws SQLException {		
		System.out.println("#### Exercise 7_2 ####");
		Connection con = getConnection();
		
		String query = "{call PROCEDURE11(?,?)}";
		CallableStatement cs_query = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el nombre de una ciudad: ");
		String ciudad = ReadString();
		cs_query.setString(1, ciudad);
		cs_query.registerOutParameter(2, Types.INTEGER, 0);
		
		cs_query.execute();
		System.out.println("Numero de clientes: " + cs_query.getInt(2));
		
		cs_query.close();
		con.close();
	}
	
    /*
     	8. Crear un metodo en Java que imprima por pantalla los coches que han sido adquiridos por cada cliente.
     	Ademas, debera imprimirse para cada cliente el numero de coches que ha comprado y el numero de
     	concesionarios en los que ha comprado. Aquellos clientes que no han adquirido ningun coche no
		deben aparecer en el listado.
		
		- Cliente: nombre1 apellido1 numcoches1 numconc1
			---> Coche: codcoche1 nombrech1 modelo1 color1
			---> Coche: codcoche2 nombrech2 modelo2 color2
			---> . . .
		- Cliente: nombre2 apellido2 numcoches2 numconc2
			---> Coche: codcoche1 nombrech1 modelo1 color1
			---> Coche: codcoche2 nombrech2 modelo2 color2
			---> . . .
		- . . .
    */
	public static void exercise8() throws SQLException {		
		System.out.println("#### Exercise 8 ####");	
		Connection con = getConnection();
		
		String query_clientes = "SELECT v.dni, c.nombre, c.apellido, count(v.codCoche) as numCoches, count(DISTINCT v.cifc) as numconc "
				+ "FROM Clientes c, Ventas v "
				+ "WHERE c.dni = v.dni "
				+ "GROUP BY v.dni";
		Statement st_clientes = con.createStatement();
		ResultSet rs_cliente = st_clientes.executeQuery(query_clientes.toString());
		
		String query_coches = "SELECT c.codCoche, c.nombrech, c.modelo, v.color "
				+ "FROM Ventas v, Coches c "
				+ "WHERE v.dni = ? AND v.codCoche = c.codCoche";
		PreparedStatement ps_coches = con.prepareStatement(query_coches.toString());
		
		while(rs_cliente.next())
		{
			System.out.println("Cliente: " + rs_cliente.getString("nombre") + " " + rs_cliente.getString("apellido") + " " + rs_cliente.getInt("numCoches") + " " + rs_cliente.getInt("numconc"));
			ps_coches.setString(1, rs_cliente.getString("dni"));
			ResultSet rs_coche = ps_coches.executeQuery();
			
			while(rs_coche.next())
			{
				System.out.println("\t ---> Coche: " + rs_coche.getString("codCoche") + " " + rs_coche.getString("nombrech")+ " " + rs_coche.getString("modelo")+ " " + rs_coche.getString("color"));
			}
			rs_coche.close();
		}
		rs_cliente.close();
		
		ps_coches.close();
		st_clientes.close();
		con.close();
	}
	
	// funcion
	public static void EJERCICIO2_EXAMEN_JUNIO_2022() throws SQLException
	{
		Connection con = getConnection();
		
		String query = "{? = call INFO_SPONSOR(?,?)}"; // el ? antes del igual es el return, y el tercer ? es el parámetro out
		CallableStatement cs = con.prepareCall(query.toString());
		
		System.out.println("Por favor, introduzca el nombre de un ciclista: ");
		String nombre = ReadString();
		cs.setString(2, nombre);
		cs.registerOutParameter(1, Types.INTEGER, 1); // lo que retorna (es la cantidad media que recibe en subvenciones)
		cs.registerOutParameter(3, Types.VARCHAR); // lo que devuelve (parámetro out) (es el nombre del equipo al que pertenece el ciclista)
		
		cs.execute();
		System.out.println("Nombre del ciclista: " + nombre + " - cantidad media que recibe en subvenciones: " + cs.getInt(1) + " - equipo al que pertenece: " + cs.getString(3));
		
		cs.close();
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
