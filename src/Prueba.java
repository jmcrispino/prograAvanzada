import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class Prueba {

	private Connection conn;

	public Prueba() {
		conn = SQLiteConnection.getConnection();
	}

	public void listarEmpleados() {
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select	E.tipoDocumento || ' - ' || E.documento as Documento, "
				+ " E.Apellido || ', ' || E.nombre as Apyn, "
				+ " E.fechaIngreso, "
				+ " D.nroDepto || '-' || D.nombre as Depto "
				+ " from	Empleado E "
				+ "	join Departamento D "
				+ "		on E.nroDepto = D.nroDepto " + " Order by 3, 2;";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			System.out.printf("%-20s %-50s %-10s %-25s\n", "Documento",
					"Apellido y Nombre", "F.Ingreso", "Depto");
			while (rs.next()) {
				String documento = rs.getString(1);
				String empleado = rs.getString(2);
				String fechaIngreso = rs.getString(3);
				String departamento = rs.getString(4);

				System.out.printf("%-20s %-50s %-10s %-25s\n", documento,
						empleado, fechaIngreso, departamento);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void agregarEmpleado(String documento, String tipoDocumento,
			String apellido, String nombre, String cargo, String fechaIngreso,
			int nroDepto) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn
					.prepareStatement("Insert into Empleado values(?, ?, ?, ?, ?, ?, ?);");
			pstmt.setString(1, documento);
			pstmt.setString(2, tipoDocumento);
			pstmt.setString(3, apellido);
			pstmt.setString(4, nombre);
			pstmt.setString(5, cargo);
			pstmt.setString(6, fechaIngreso);
			pstmt.setInt(7, nroDepto);
			pstmt.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void actualizaEmpleadoDpto(String documento, String tipoDocumento,
			int nroDepto) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn
					.prepareStatement("Update Empleado set nroDepto = ? where documento = ? and tipoDocumento = ?;");
			pstmt.setInt(1, nroDepto);
			pstmt.setString(2, documento);
			pstmt.setString(3, tipoDocumento);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void eliminaEmpleado(String documento, String tipoDocumento) {
		PreparedStatement pstmt = null;

		try {
			pstmt = conn
					.prepareStatement("Delete from Empleado where documento = ? and tipoDocumento = ?;");
			pstmt.setString(1, documento);
			pstmt.setString(2, tipoDocumento);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void desconectar() {
		SQLiteConnection.close();
	}

	public static void main(String[] args) {

		Prueba prueba = new Prueba();
		prueba.listarEmpleados();
		System.out.println("Agrego nuevo empleado...");
		prueba.agregarEmpleado("28976455", "DNI", "Saviola", "Juan", "Analista",
				"2016-01-01", 1);
		prueba.listarEmpleados();
		System.out.println("Actualizo departamento de empleado...");
		prueba.actualizaEmpleadoDpto("27199432", "DNI", 3);
		prueba.listarEmpleados();
		System.out.println("Elimino empleado...");
		prueba.eliminaEmpleado("28976455", "DNI");
		prueba.listarEmpleados();
		prueba.desconectar();
	}
}
