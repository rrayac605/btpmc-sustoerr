package mx.gob.imss.cit.pmc.sustoerr.constants;

import java.util.Map;

import org.springframework.data.domain.Sort;

import com.google.common.collect.ImmutableMap;

public class SusToErrConstantes {
	
	//Claves (aseguradoDTO.cveEstadoRegistro) a actualizar
	public static final int CVE_SUSCEPTIBLE = 4;
			
	public static final int CVE_SUSCEPTIBLE_OTRAS = 8;
		
	//Claves y descripciones (aseguradoDTO.cveEstadoRegistro, aseguradoDTO.desEstadoRegistro) a ser actualizadas
	public static final int CVE_ERRONEO = 2;
	
	public static final String DES_ERRONEO = "Erróneo";
		
	public static final int CVE_ERRONEO_OTRAS = 6;
	
	public static final String DES_ERRONEO_OTRAS = "Erróneos otras delegaciones";
		
	//Auditorias
	public static final String FORMATO_FECHA_HORA_ISO = "ddMMyyyy HH:mm:ss";
	
	public static final String USUARIO = "Sistema PMC";
		
	public static final String ACCION = "Modificación";
		
	public static final int ORDEN_EJECUCION_1 = 1;
	
	public static final String DES_SITUACION_REGISTRO_PENDIENTE = "Pendiente de Aprobar";
			
	public static final int ORDEN_EJECUCION_2 = 2;
	
	public static final String DES_SITUACION_REGISTRO_APROBADO = "Aprobado";
		
	public static final String DESCRIPCION = "Cambio de estado del registro de susceptible a erróneo por el sistema.";
	
	//Chunk size
	public static final Integer CHUNK_SIZE = 100;
	
	//Parametros
	public static final String FECHA_EJECUCION = "execution_date";
	
	public static final String PRIMER_DIA = "01";
	
	public static final String ISO_TIMEZONE = "UTC";
	
	public static final String HORA_INICIAL = " 00:00:00";
	
	public static final String HORA_FINAL = " 23:59:59";
	
	public static final String CERO = "0";
	
	public static final String VACIO = "";
	
	//Ordenamiento
	public static final Map<String, Sort.Direction> ORDENAMIENTO = ImmutableMap.of("_id", Sort.Direction.ASC);
	
	//Correo electronico
	public static final String RUTA_IMGS = "/static/images/";
	
	public static final String ENCABEZADO_IMG = "header.png";
	
	public static final String PIE_IMG = "footer.png";
	
	public static final String CORREO_TEMPLATE = "SUS_TO_ERR_TEMPLATE";
	
	public static final String FECHA_HORA = "${fechaHora}";
	
	public static final String TOTALES_ANTES = "${cifrasAntes}";
	
	public static final String TOTALES_ACTUALIZADOS = "${cifrasSusToErr}";
	
	public static final String TOTALES_DESPUES = "${cifrasDespues}";
		
	public static final String FORMATO_FECHA_HORA = "dd-MM-yyyy HH:mm";
	
	public static final String[] RIESGOS_ANTES = {"Total de registros Susceptibles de ajuste: ",
			"Total de registros Susceptibles de ajuste otras delegaciones: ", "Total de registros Err&oacute;neos: ",
			"Total de registros Err&oacute;neos otras delegaciones: "};
	
	public static final String[] RIESGOS_ACTUALIZADOS = {"Total de registros Susceptibles de ajuste con c&oacute;digo de error: ",
			"Total de registros Susceptibles de ajuste otras delegaciones con c&oacute;digo de error: "};
	
	public static final String[] RIESGOS_DESPUES = {"Total de registros Susceptibles de ajuste: ",
			"Total de registros Susceptibles de ajuste otras delegaciones: ", "Total de registros Err&oacute;neos: ",
			"Total de registros Err&oacute;neos otras delegaciones existentes: "};
	
}
