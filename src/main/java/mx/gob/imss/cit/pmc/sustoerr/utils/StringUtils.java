package mx.gob.imss.cit.pmc.sustoerr.utils;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.dto.ParametroDTO;

import java.util.Optional;

public class StringUtils {

    public static String getFromParam(Optional<ParametroDTO<String>> parameterDTO) {
        return parameterDTO.map(ParametroDTO::getDesParametro).orElse(SusToErrConstantes.VACIO);
    }
    

}
