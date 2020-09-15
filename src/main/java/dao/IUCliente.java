package dao;

import java.text.ParseException;

import bean.Empleado;


public interface IUCliente {
	
	public void registrarEmpleado(String nombre, String apaterno, String amaterno, int idGen, int idJob);	
	
	public boolean verificarRegistro(String puesto);
	
	public Empleado horasPorEmpleado(int idEmpleado, String fechaInicio, String fechaFinal);
	
	public boolean verificarFecha(String fechaRegistro) throws ParseException;
	
	public boolean verificaRegistroDobleHoras(int idEmpleado, String fecha);
	
	public boolean verificaHorasTrabajadas(int idUsuario);
	
	public boolean verificarExistenciaJob(int idJob);
	
	public boolean verificarExistenciaGenero(int idGen);
	
	public boolean verificarEdad(String birthday);
	
	public boolean verificarRegistro(String nombre, String lastname);
	
	public void obtenerEmpleadosporPuesto(String puesto);
	
	public void registrarHoras(int idEmpleado, int HorasTrabajadas, String fecha);
	
}
