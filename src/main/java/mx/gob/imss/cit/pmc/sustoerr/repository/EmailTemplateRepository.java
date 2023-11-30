package mx.gob.imss.cit.pmc.sustoerr.repository;

import mx.gob.imss.cit.pmc.sustoerr.dto.EmailTemplateDTO;

public interface EmailTemplateRepository {

    EmailTemplateDTO findByName(String name);

}
