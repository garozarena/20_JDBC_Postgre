package curso.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.Scanner;

public class Consultar {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		
		/**CREATE STATEMENT**/
		Class.forName("org.postgresql.Driver");
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/biblioteca", "postgres", "123");
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM libros");
		Scanner in = new Scanner(System.in);
		
		System.out.println("CREATE STATMENT");
		System.out.println("________________");
		while(rs.next()) {
			System.out.println(rs.getString("titulo"));
			System.out.println("Autor: "+rs.getString("autor"));
			System.out.println("Fecha publicacion: "+rs.getDate("fecha"));
			System.out.println(rs.getFloat("precio")+"€");
			System.out.println("________________");
		}
		//EJECUTAR INSERT
		//int insertar = st.executeUpdate("INSERT INTO libros (id,titulo, autor, fecha, precio) values (1,'Romeo y Julieta', 'Shakespeare', '1597-4-2', 19.90)");
		//System.out.println("Filas insertadas: "+insertar);
		
		
		
		
		/**PREPARE STATEMENT**/
		System.out.println("********************");
		System.out.println("PREPARE STATEMENT");
		System.out.println("Escribe una ID para un libro concreto");
		int id = in.nextInt();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM libros WHERE id = ?");
		pstmt.setInt(1, id);
		ResultSet rs2 = pstmt.executeQuery();
		
		System.out.println("________________");
		while(rs2.next()) {
			System.out.println("Titulo: "+rs2.getString("titulo"));
			System.out.println("Autor: "+rs2.getString("autor"));
			System.out.println("Fecha publicacion: "+rs2.getDate("fecha"));
			System.out.println("Precio: "+rs2.getFloat("precio")+"€");
		}
		System.out.println("________________");
		
		
		
		
		/**CALLABLE STATEMENT**/
		CallableStatement cstmt = conn.prepareCall("{call listaLibrosPorAutor(?)}");
		cstmt.setString(1, "Cervantes");
		ResultSet rs3 = cstmt.executeQuery();
		
		System.out.println("********************");
		System.out.println("CALLABLE STATMENT");
		System.out.println("________________");
		while(rs3.next()) {
			System.out.println("Titulo: "+rs3.getString("titulo"));
			System.out.println("Autor: "+rs3.getString("autor"));
		}
	}

}
