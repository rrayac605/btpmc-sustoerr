package mx.gob.imss.cit.pmc.sustoerr.repository;

import org.springframework.data.mongodb.core.aggregation.TypedAggregation;

public interface ContadorRepository {

    long count(TypedAggregation<?> aggregation);

}
