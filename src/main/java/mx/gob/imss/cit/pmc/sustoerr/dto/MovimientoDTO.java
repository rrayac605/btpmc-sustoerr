package mx.gob.imss.cit.pmc.sustoerr.dto;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document("MCT_MOVIMIENTO")
public class MovimientoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public MovimientoDTO () {}
	
	@Getter
	@Setter
	@Id
	private ObjectId objectId;
	
	@Getter
	@Setter
	private ObjectId identificadorArchivo;
	
	@Getter
	@Setter
	private String cveOrigenArchivo;
	
	@Getter
	@Setter
	private AseguradoDTO aseguradoDTO;
	
	@Getter
	@Setter
	private IncapacidadDTO incapacidadDTO;
	
	@Getter
	@Setter
	private PatronDTO patronDTO;
	
	@Getter
	@Setter
	private List<BitacoraErroresDTO> bitacoraErroresDTO;
	
	@Getter
	@Setter
	private List<AuditoriaDTO> auditorias;

}
