package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.Empleado;
import bean.Horario;
import dao.ClienteDao;

@Controller
public class PersonController {
	
	ClienteDao cl = new ClienteDao();
	Empleado c = new Empleado();
	
	@RequestMapping(value="agregarempleado", method = RequestMethod.POST)
	public @ResponseBody HttpStatus PostService(@RequestParam String nombre, @RequestParam String lastname, 
			@RequestParam String birthday, @RequestParam int idGen, @RequestParam int idJob){
		System.out.println(nombre);
		if (cl.verificarEdad(birthday) == true && cl.verificarExistenciaGenero(idGen)) {
			if (cl.verificarExistenciaJob(idJob)) {
				cl.registrarEmpleado(nombre, lastname, birthday, idGen, idJob);
			}
		}		
		return HttpStatus.OK;
	}
	
	
	@RequestMapping(value="agregarhoras", method = RequestMethod.POST)
	public @ResponseBody HttpStatus horasTrabajo(@RequestParam int idEmpleado, @RequestParam int horas, @RequestParam String fecha){
		
		if(cl.verificaHorasTrabajadas(idEmpleado) == true && cl.verificaRegistroDobleHoras(idEmpleado, fecha)) {
			cl.registrarHoras(idEmpleado, horas, fecha);
		}
		
		return HttpStatus.OK;
	}
	
	
	@RequestMapping(value="verificausuariotrabajo", method = RequestMethod.POST)
	public @ResponseBody HttpStatus usuarioTrabajo(@RequestParam String puesto) {
		if(cl.verificarRegistro(puesto)) {
			cl.obtenerEmpleadosporPuesto(puesto);
		}
		return HttpStatus.OK;
	}
	
	@RequestMapping(value="horasdetrabajoporempleado",method = RequestMethod.POST)
	public @ResponseBody HttpStatus usuarioHoras(@RequestBody Horario hor, @RequestParam String fechaInicio, @RequestParam String fechaFinal) throws ParseException {
		if(cl.verificarFecha(hor.getFecha())) {
			cl.horasPorEmpleado(hor.getIdEmpleado(), fechaInicio, fechaFinal);
		}		
		return HttpStatus.OK;
	}
	
	
	@RequestMapping(value="pagoporhoras", method = RequestMethod.POST)
	public @ResponseBody HttpStatus pagoHor(@RequestParam int idEmpleado, @RequestParam String fechaInicio, @RequestParam String fechaFinal ) throws ParseException {
		if (cl.verificarRegistroId(idEmpleado) == true && cl.verificarFecha(fechaFinal) == true) {
			cl.rangoPago(idEmpleado, fechaInicio, fechaFinal);
		}
		return HttpStatus.OK;
	}
	
	
	
	
	
	

}
