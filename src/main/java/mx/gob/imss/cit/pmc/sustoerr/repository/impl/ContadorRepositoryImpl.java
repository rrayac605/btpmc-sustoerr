package mx.gob.imss.cit.pmc.sustoerr.repository.impl;

import mx.gob.imss.cit.pmc.sustoerr.dto.CountDTO;
import mx.gob.imss.cit.pmc.sustoerr.repository.ContadorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.stereotype.Repository;

@Repository
public class ContadorRepositoryImpl implements ContadorRepository {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public long count(TypedAggregation<?> aggregation) {
        CountDTO countDTO = mongoOperations.aggregate(aggregation, CountDTO.class).getUniqueMappedResult();
        return countDTO != null && countDTO.getCount() != null ? countDTO.getCount() : 0L;
    }
}
