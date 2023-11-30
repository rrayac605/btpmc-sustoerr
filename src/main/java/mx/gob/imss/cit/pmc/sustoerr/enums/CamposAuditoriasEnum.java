package mx.gob.imss.cit.pmc.sustoerr.enums;

import lombok.Getter;

@Getter
public enum CamposAuditoriasEnum {
	
	FEC_ALTA("auditorias.fecAlta"),
	DESCRIPCION("auditorias.descripcion");
	
	private final String desc;
	
	CamposAuditoriasEnum(String desc) {
		this.desc = desc;
	}
	
}
