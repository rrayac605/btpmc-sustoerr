package mx.gob.imss.cit.pmc.sustoerr.repository;

import mx.gob.imss.cit.pmc.sustoerr.dto.ParametroDTO;

import java.util.List;
import java.util.Optional;

public interface ParametroRepository {

    Optional<ParametroDTO<String>> findOneByCve(String cve);

    Optional<ParametroDTO<List<String>>> findListByCve(String cve);

}
