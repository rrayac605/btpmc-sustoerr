package mx.gob.imss.cit.pmc.sustoerr.repository.impl;

import mx.gob.imss.cit.pmc.sustoerr.dto.ParametroDTO;
import mx.gob.imss.cit.pmc.sustoerr.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ParametroRepositoryImp implements ParametroRepository {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<ParametroDTO<String>> findOneByCve(String cve) {
        Query query = new Query(Criteria.where("cveIdParametro").is(cve));
        return Optional.ofNullable(mongoOperations.findOne(query, ParametroDTO.class));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<ParametroDTO<List<String>>> findListByCve(String cve) {
        Query query = new Query(Criteria.where("cveIdParametro").is(cve));
        return Optional.ofNullable(mongoOperations.findOne(query, ParametroDTO.class));
    }
}
