package mx.gob.imss.cit.pmc.sustoerr.dto;

import lombok.Getter;
import lombok.Setter;

public class CountDTO {

    @Getter
    private Long count;
    
    @Setter
    @Getter
    private Long countSus;
    
    @Setter
    @Getter
    private Long countSusOtras;

}
