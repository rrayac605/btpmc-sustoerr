package mx.gob.imss.cit.pmc.sustoerr.processor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.dto.AseguradoDTO;
import mx.gob.imss.cit.pmc.sustoerr.dto.AuditoriaDTO;
import mx.gob.imss.cit.pmc.sustoerr.dto.MovimientoDTO;
import mx.gob.imss.cit.pmc.sustoerr.utils.DateUtils;

@Component
@StepScope
public class SusceptiblesProcessor implements ItemProcessor<MovimientoDTO, MovimientoDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(SusceptiblesProcessor.class);
	
	@Autowired
	MongoOperations mongoOperations;
	
	@Override
	public MovimientoDTO process(MovimientoDTO movimientoDTO) {
		
		AseguradoDTO asegurado = movimientoDTO.getAseguradoDTO();
		List<AuditoriaDTO> auditorias = movimientoDTO.getAuditorias();
		
		logger.info("Comienza la actualización del NSS {}", asegurado.getNumNss());
		
		asegurado.setCveEstadoRegistro(SusToErrConstantes.CVE_ERRONEO);
		asegurado.setDesEstadoRegistro(SusToErrConstantes.DES_ERRONEO);
		asegurado.setFecActualizacion(DateUtils.obtenerFechaMongoISO());
		
		AuditoriaDTO auditoria = auditorias.get(1);
		
		if(auditoria.getFecBaja() == null) {
			auditoria.setFecBaja(DateUtils.obtenerFechaMongoISO());
		}
		
		AuditoriaDTO auditoriaSusToErr = new AuditoriaDTO();
		auditoriaSusToErr.setFecAlta(DateUtils.obtenerFechaMongoISO());
		auditoriaSusToErr.setNomUsuario(SusToErrConstantes.USUARIO);
		auditoriaSusToErr.setDesAccionRegistro(SusToErrConstantes.ACCION);
		auditoriaSusToErr.setDesSituacionRegistro(SusToErrConstantes.DES_SITUACION_REGISTRO_PENDIENTE);
		auditoriaSusToErr.setOrdenEjecucion(SusToErrConstantes.ORDEN_EJECUCION_1);
		auditoriaSusToErr.setDescripcion(SusToErrConstantes.DESCRIPCION);
		auditoriaSusToErr.setFecBaja(DateUtils.obtenerFechaMongoISO());
		auditorias.add(auditoriaSusToErr);
		
		AuditoriaDTO auditoriasSusToErr = new AuditoriaDTO();
		auditoriasSusToErr.setFecAlta(DateUtils.obtenerFechaMongoISO());
		auditoriasSusToErr.setNomUsuario(SusToErrConstantes.USUARIO);
		auditoriasSusToErr.setDesAccionRegistro(SusToErrConstantes.ACCION);
		auditoriasSusToErr.setDesSituacionRegistro(SusToErrConstantes.DES_SITUACION_REGISTRO_APROBADO);
		auditoriasSusToErr.setOrdenEjecucion(SusToErrConstantes.ORDEN_EJECUCION_2);
		auditoriasSusToErr.setDescripcion(SusToErrConstantes.DESCRIPCION);
		auditorias.add(auditoriasSusToErr);
				
		mongoOperations.save(movimientoDTO);
		
		logger.info("Concluye la actualización del NSS {}", asegurado.getNumNss());
		
		return movimientoDTO;
		
	}

}
