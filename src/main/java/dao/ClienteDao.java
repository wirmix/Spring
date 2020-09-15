package dao;

/*Clase que gestiona a empleado y sus actividades correspondientes
 * 
 * Creado 12/09/20
 * */

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import bean.Empleado;
import bean.Pagos;

public class ClienteDao implements IUCliente {

	Connection con = null;

	
	//Método que registra entidad empleado
	public void registrarEmpleado(String nombre, String lastname, String birthday, int idGen, int idJob) {
		try {
			String sql = "{call SP_REGISTRAR(?,?,?,?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, nombre);
			cs.setString(2, lastname);
			cs.setString(3, birthday);
			cs.setInt(4, idGen);
			cs.setInt(5, idJob);
			cs.execute();
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Método que registra horas trabajadas de empleado
	public void registrarHoras(int idEmpleado, int HorasTrabajadas, String fecha) {
		try {
			String sql = "{call SP_REGISTRARHORAS(?,?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, idEmpleado);
			cs.setInt(2, HorasTrabajadas);
			cs.setString(3, fecha);
			cs.execute();
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void obtenerEmpleadosporPuesto(String puesto) {
		try {
			String sql = "{call SP_OBTENERPUESTOS(?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, puesto);
			cs.execute();
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// método que recibirá nombre y apellido
	public boolean verificarRegistro(String nombre, String lastname) {
		boolean flag = false;
		try {
			String sql = "{call SP_VERIFICARUSUARIO(?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, nombre);
			cs.setString(2, lastname);
			if (cs.execute()) {
				flag = true;
			}
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	// método que recibirá id
		public boolean verificarRegistroId(int idEmpleado) {
			boolean flag = false;
			try {
				String sql = "{call SP_VERIFICARUSUARIOID(?)}";
				CallableStatement cs = con.prepareCall(sql);
				cs.setInt(1, idEmpleado);
				if (cs.execute()) {
					flag = true;
				}
				cs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return flag;
		}

	// Método que solo recibirá fecha de cumpleaños
	public boolean verificarEdad(String birthday) {
		boolean flag = false;
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaNac = LocalDate.parse(birthday, fmt);
		LocalDate ahora = LocalDate.now();
		Period periodo = Period.between(fechaNac, ahora);
		if (periodo.getYears() > 18) {
			flag = true;
		}
		return flag;
	}

	// Método que recibirá solo id género
	public boolean verificarExistenciaGenero(int idGen) {
		boolean flag = false;
		try {
			String sql = "{call SP_VERIFICAREGISTROGEN(?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, idGen);
			if (cs.execute()) {
				flag = true;
			}
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	// Método que recibirá idJob
	public boolean verificarExistenciaJob(int idJob) {
		boolean flag = false;
		try {
			String sql = "{call SP_VERIFICAREGISTROJOB(?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, idJob);
			if (cs.execute()) {
				flag = true;
			}
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verificaHorasTrabajadas(int idUsuario) {
		boolean flag = false;
		try { // Id Usuario
			String sql = "{call SP_VERIFICAHORASTRABAJADAS(?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, idUsuario);
			if (cs.getResultSet().getFetchSize() > 20) {
				flag = true;
			}
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean verificaRegistroDobleHoras(int idEmpleado, String fecha) {
		boolean flag = false;
		try {
			String sql = "{call SP_VERIFICAREGISTROHORA(?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, idEmpleado);
			cs.setString(2, fecha);
			if (cs.execute()) {
				flag = true;
			}
			cs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	// Verifica si la fecha es mayor o menos a la actual
	public boolean verificarFecha(String fechaRegistro) throws ParseException {
		boolean flag = false;
		Date fechaactual = new Date(System.currentTimeMillis());
		String fechaInicio = fechaRegistro;
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaInicioDate = date.parse(fechaInicio);
		if (fechaInicioDate.after(fechaactual)) {
			flag = true; // Fecha de registro mayor
		}
		return flag;
	}
	
	public Empleado horasPorEmpleado(int idEmpleado, String fechaInicio, String fechaFinal) {
		Empleado emp = new Empleado();
		boolean flag = false;
		try {
			String sql = "{call SP_VERIFICARANGODEHORAS(?,?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, idEmpleado);
			cs.setString(2, fechaInicio);
			cs.setString(3, fechaFinal);
			if (cs.execute()) {
				cs.getClass();
			}
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emp;
	}
	
	public boolean verificarRegistro(String puesto) {
		boolean flag = false;
		try {
			String sql = "{call SP_VERIFICAREGISTROJOB(?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setString(1, puesto);
			if (cs.execute()) {
				flag = true;
			}
			cs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public Pagos rangoPago(int idEmpleado, String fechaInicio, String fechaFinal) {
		Pagos pagos = new Pagos();
		try {
			String sql = "{call SP_OBTENER PAGO(?,?,?)}";
			CallableStatement cs = con.prepareCall(sql);
			cs.setInt(1, idEmpleado);
			cs.setString(2, fechaInicio);
			cs.setString(3, fechaFinal);
			if (cs.execute()) {
				cs.getClass();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagos;
	}
	

}
