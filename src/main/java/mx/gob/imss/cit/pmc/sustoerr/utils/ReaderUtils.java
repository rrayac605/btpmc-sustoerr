package mx.gob.imss.cit.pmc.sustoerr.utils;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.CountOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.dto.MovimientoDTO;
import mx.gob.imss.cit.pmc.sustoerr.enums.CamposAseguradoEnum;
import mx.gob.imss.cit.pmc.sustoerr.enums.CamposAuditoriasEnum;
import mx.gob.imss.cit.pmc.sustoerr.enums.CamposMovimientoEnum;

public class ReaderUtils {
	
	public static String consultaJSONSusceptibles() {
		return "{ '$and' : [" +
			   "{ 'aseguradoDTO.cveEstadoRegistro' : " + SusToErrConstantes.CVE_SUSCEPTIBLE + " }," +
			   "{ 'bitacoraErroresDTO' : { '$exists' : true } }," +
			   "{ 'isPending' : { '$exists' : false } }" +
			   "]}";
	}
	
	public static TypedAggregation<MovimientoDTO> contadorSusceptibles(){
		MatchOperation matchOperation = Aggregation.match(new Criteria().andOperator(
				Criteria.where(CamposAuditoriasEnum.FEC_ALTA.getDesc()).gte(DateUtils.fechaInico()),
				Criteria.where(CamposAuditoriasEnum.FEC_ALTA.getDesc()).lte(DateUtils.fechaFin()),
				Criteria.where(CamposAseguradoEnum.ESTADO_REGISTRO.getDesc()).is(SusToErrConstantes.CVE_ERRONEO),
				Criteria.where(CamposMovimientoEnum.ERRORES_DTO.getDesc()).exists(Boolean.TRUE),
				Criteria.where(CamposAuditoriasEnum.DESCRIPCION.getDesc()).is(SusToErrConstantes.DESCRIPCION)
				));
		
		CountOperation count = Aggregation.count().as("count");
		
		return Aggregation.newAggregation(MovimientoDTO.class, matchOperation, count);
	}
	
	public static String consultaJSONSusceptiblesOtras() {
		return "{ '$and' : [" +
			   "{ 'aseguradoDTO.cveEstadoRegistro' : " + SusToErrConstantes.CVE_SUSCEPTIBLE_OTRAS + " }," +
			   "{ 'bitacoraErroresDTO' : { '$exists' : true } }," +
			   "{ 'isPending' : { '$exists' : false } }" +
			   "]}";
	}
	
	public static TypedAggregation<MovimientoDTO> contadorSusceptiblesOtras() {
		MatchOperation matchOperation = Aggregation.match(new Criteria().andOperator(
				Criteria.where(CamposAuditoriasEnum.FEC_ALTA.getDesc()).gte(DateUtils.fechaInico()),
				Criteria.where(CamposAuditoriasEnum.FEC_ALTA.getDesc()).lte(DateUtils.fechaFin()),
				Criteria.where(CamposAseguradoEnum.ESTADO_REGISTRO.getDesc()).is(SusToErrConstantes.CVE_ERRONEO_OTRAS),
				Criteria.where(CamposMovimientoEnum.ERRORES_DTO.getDesc()).exists(Boolean.TRUE),
				Criteria.where(CamposAuditoriasEnum.DESCRIPCION.getDesc()).is(SusToErrConstantes.DESCRIPCION)
				));
		
		CountOperation count = Aggregation.count().as("count");
		
		return Aggregation.newAggregation(MovimientoDTO.class, matchOperation, count);
	}
	
	/*** Conteos totales ***/
	public static TypedAggregation<MovimientoDTO> contadorTotalSusceptibles() {
		MatchOperation matchOperation = Aggregation.match(new Criteria().andOperator(
				Criteria.where(CamposAseguradoEnum.ESTADO_REGISTRO.getDesc()).is(SusToErrConstantes.CVE_SUSCEPTIBLE),
				new Criteria().orOperator(
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).exists(Boolean.FALSE),
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).is(Boolean.FALSE))
				));
		
		CountOperation count = Aggregation.count().as("count");
		
		return Aggregation.newAggregation(MovimientoDTO.class, matchOperation, count);
	}
	
	public static TypedAggregation<MovimientoDTO> contadorTotalSusOtras() {
		MatchOperation matchOperation = Aggregation.match(new Criteria().andOperator(
				Criteria.where(CamposAseguradoEnum.ESTADO_REGISTRO.getDesc()).is(SusToErrConstantes.CVE_SUSCEPTIBLE_OTRAS),
				new Criteria().orOperator(
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).exists(Boolean.FALSE),
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).is(Boolean.FALSE))
				));
		
		CountOperation count = Aggregation.count().as("count");
		
		return Aggregation.newAggregation(MovimientoDTO.class, matchOperation, count);
	}
	
	public static TypedAggregation<MovimientoDTO> contadorTotalErroneos() {
		MatchOperation matchOperation = Aggregation.match(new Criteria().andOperator(
				Criteria.where(CamposAseguradoEnum.ESTADO_REGISTRO.getDesc()).is(SusToErrConstantes.CVE_ERRONEO),
				new Criteria().orOperator(
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).exists(Boolean.FALSE),
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).is(Boolean.FALSE))
				));
		
		CountOperation count = Aggregation.count().as("count");
		
		return Aggregation.newAggregation(MovimientoDTO.class, matchOperation, count);
	}
	
	public static TypedAggregation<MovimientoDTO> contadorTotalErrOtras() {
		MatchOperation matchOperation = Aggregation.match(new Criteria().andOperator(
				Criteria.where(CamposAseguradoEnum.ESTADO_REGISTRO.getDesc()).is(SusToErrConstantes.CVE_ERRONEO_OTRAS),
				new Criteria().orOperator(
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).exists(Boolean.FALSE),
						Criteria.where(CamposMovimientoEnum.IS_PENDING.getDesc()).is(Boolean.FALSE))
				));
		
		CountOperation count = Aggregation.count().as("count");
		
		return Aggregation.newAggregation(MovimientoDTO.class, matchOperation, count);
	}

}
