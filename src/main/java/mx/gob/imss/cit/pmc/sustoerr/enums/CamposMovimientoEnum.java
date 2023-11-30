package mx.gob.imss.cit.pmc.sustoerr.enums;

import lombok.Getter;

@Getter
public enum CamposMovimientoEnum {
	
	_ID("_id"),
	ID_ARCHIVO("identificadorArchivo"),
	ORIGEN_ARCHIVO("cveOrigenArchivo"),
	ERRORES_DTO("bitacoraErroresDTO"),
	IS_PENDING("isPending");
	
	private final String desc;
	
	CamposMovimientoEnum(String desc){
		this.desc = desc;
	}

}
