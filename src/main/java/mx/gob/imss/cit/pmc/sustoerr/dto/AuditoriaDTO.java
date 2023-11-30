package mx.gob.imss.cit.pmc.sustoerr.dto;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("MCT_AUDITORIA")
public class AuditoriaDTO extends FechasAuditoriaDTO {
		
	@Getter
	@Setter
	private String nomUsuario;
	
	@Getter
	@Setter
	private String numFolioMovOriginal;
	
	@Getter
	@Setter
	private String desObservacionesAprobador;
	
	@Getter
	@Setter
	private String desObservacionesSol;
	
	@Getter
	@Setter
	private Integer cveIdAccionRegistro;
	
	@Getter
	@Setter
	private Integer cveEstadoRegistro;
	
	@Getter
	@Setter
	private String desAccionRegistro;
	
	@Getter
	@Setter
	private String desEstadoRegistro;
	
	@Getter
	@Setter
	private String desCambio;
		
	@Getter
	@Setter
	private String desSituacionRegistro;
	
	@Getter
	@Setter
	private String cveSituacionRegistro;
	
	@Getter
	@Setter
	private Integer ordenEjecucion;
	
	@Getter
	@Setter
	private String descripcion;

}
