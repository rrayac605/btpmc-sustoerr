package mx.gob.imss.cit.pmc.sustoerr.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;

public class DateUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
	
	public static String obtenerFechaMexicoString() {
		TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
		DateFormat df = new SimpleDateFormat(SusToErrConstantes.FORMATO_FECHA_HORA);
		df.setTimeZone(tz);
		return df.format(new Date());
	}
	
	public static Date obtenerFechaMongoISO() {
		SimpleDateFormat sdf = new SimpleDateFormat(SusToErrConstantes.FORMATO_FECHA_HORA_ISO, new Locale("es", "MX"));
		SimpleDateFormat sdf1 = new SimpleDateFormat(SusToErrConstantes.FORMATO_FECHA_HORA_ISO, new Locale("es", "MX"));
		Date fecha = null;
		try {
			String fechaTexto = sdf.format(new Date());
			fecha = sdf1.parse(fechaTexto);
		} catch(Exception exc) {
			logger.error("Error al obtener fecha {}", exc);
		}
		
		return fecha;
	}
	
	public static Date obtenerFechaMexico() {
		try {
			return new SimpleDateFormat(SusToErrConstantes.FORMATO_FECHA_HORA).parse(obtenerFechaMexicoString());
		} catch(ParseException pe) {
			pe.printStackTrace();
			return null;
		}
	}
	
	public static Date fechaInico() {
		Calendar calendar = Calendar.getInstance();
		String mes = obtenerMes(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		String fecha = SusToErrConstantes.PRIMER_DIA.concat(mes).concat(String.valueOf(calendar.get(Calendar.YEAR)))
				.concat(SusToErrConstantes.HORA_INICIAL);
		TimeZone tz = TimeZone.getTimeZone(SusToErrConstantes.ISO_TIMEZONE);
		DateFormat df = new SimpleDateFormat(SusToErrConstantes.FORMATO_FECHA_HORA_ISO);
		df.setTimeZone(tz);
		try {
			Date inicio = df.parse(fecha);
			calendar.setTime(inicio);
			return calendar.getTime();
		} catch(Exception exc) {
			logger.error("Error al generar fecha inicio");
			return null;
		}
	}
	
	public static Date fechaFin() {	
		Calendar calendar = Calendar.getInstance();
		String mes = obtenerMes(String.valueOf(calendar.get(Calendar.MONTH) + 1));
		String fecha = String.valueOf(calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
				.concat(mes).concat(String.valueOf(calendar.get(Calendar.YEAR)))
				.concat(SusToErrConstantes.HORA_FINAL);
		TimeZone tz = TimeZone.getTimeZone(SusToErrConstantes.ISO_TIMEZONE);
		DateFormat df = new SimpleDateFormat(SusToErrConstantes.FORMATO_FECHA_HORA_ISO);
		df.setTimeZone(tz);
		try {
			Date fin = df.parse(fecha);
			calendar.setTime(fin);
			return calendar.getTime();
		} catch(Exception exc) {
			logger.error("Error al generar fecha fin");
			return null;
		}
	}
	
	public static String obtenerMes(String mes) {
		return mes.length() < 2 ? SusToErrConstantes.CERO.concat(mes) : mes;
	}

}
