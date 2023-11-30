package mx.gob.imss.cit.pmc.sustoerr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.dto.MovimientoDTO;
import mx.gob.imss.cit.pmc.sustoerr.processor.SusceptiblesOtrasProcessor;
import mx.gob.imss.cit.pmc.sustoerr.processor.SusceptiblesProcessor;
import mx.gob.imss.cit.pmc.sustoerr.tasklet.CorreoSusToErrTasklet;
import mx.gob.imss.cit.pmc.sustoerr.utils.ReaderUtils;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = { "mx.gob.imss.cit.pmc.sustoerr" })
public class BatchSusToErrConfiguration extends DefaultBatchConfigurer {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchSusToErrConfiguration.class);
	
	@Autowired
	private JobBuilderFactory jbf;
	
	@Autowired
	private StepBuilderFactory sbf;
	
	@Autowired
	private MongoOperations mongoOperations;
	
	@Autowired
	private SusceptiblesProcessor susceptiblesProcessor;
	
	@Autowired
	private SusceptiblesOtrasProcessor susceptiblesOtrasProcessor;
		
	@Autowired
	private CorreoSusToErrTasklet correoSusToErrTasklet;
	
	@Bean(name = "pmcSusToErr")
	public Job susToErr() {
		return jbf.get("pmcSusToErr")
				.incrementer(new RunIdIncrementer())
				.start(pasoActualizaErroneos())
				.next(pasoActualizaErroneosOtras())
				.next(pasoEnviaCorreo())
				.build();
	}
	
	@Bean
	public Step pasoActualizaErroneos() {
		return sbf.get("pasoActualizaErroneos")
				.<MovimientoDTO, MovimientoDTO>chunk(SusToErrConstantes.CHUNK_SIZE)
				.reader(consultarMovimientosSusceptibles())
				.processor(susceptiblesProcessor)
				.writer(susceptiblesWriter())
				.build();
	}
	
	@Bean
	public Step pasoActualizaErroneosOtras() {
		return sbf.get("pasoActualizaErroneosOtras")
				.<MovimientoDTO, MovimientoDTO>chunk(SusToErrConstantes.CHUNK_SIZE)
				.reader(consultarMovimientosSusceptiblesOtras())
				.processor(susceptiblesOtrasProcessor)
				.writer(susceptiblesWriter())
				.build();
	}
	
	@Bean
	public Step pasoEnviaCorreo() {
		return sbf.get("pasoEnviaCorreo")
				.tasklet(correoSusToErrTasklet)
				.build();
	}
		
	@Bean
	@StepScope
	public MongoItemReader<MovimientoDTO> consultarMovimientosSusceptibles() {
		logger.info("<---------------------------------------------------------------------->");
		logger.info("Consulta susceptibles {}", ReaderUtils.consultaJSONSusceptibles());
		logger.info("<---------------------------------------------------------------------->");
		return new MongoItemReaderBuilder<MovimientoDTO>()
				.name("consultarMovimientosSusceptibles")
				.template(mongoOperations)
				.targetType(MovimientoDTO.class)
				.sorts(SusToErrConstantes.ORDENAMIENTO)
				.jsonQuery(ReaderUtils.consultaJSONSusceptibles())
				.build();
	}
	
	@Bean
	@StepScope
	public MongoItemReader<MovimientoDTO> consultarMovimientosSusceptiblesOtras() {
		logger.info("<---------------------------------------------------------------------------------------------->");
		logger.info("Consulta susceptibles otras delegaciones {}", ReaderUtils.consultaJSONSusceptiblesOtras());
		logger.info("<---------------------------------------------------------------------------------------------->");
		return new MongoItemReaderBuilder<MovimientoDTO>()
				.name("consultarMovimientosSusceptiblesOtras")
				.template(mongoOperations)
				.targetType(MovimientoDTO.class)
				.sorts(SusToErrConstantes.ORDENAMIENTO)
				.jsonQuery(ReaderUtils.consultaJSONSusceptiblesOtras())
				.build();
	}
	
	@Bean
	MongoItemWriter<MovimientoDTO> susceptiblesWriter() {
		return new MongoItemWriterBuilder<MovimientoDTO>()
				.template(mongoOperations)
				.collection("MCT_MOVIMIENTO")
				.build();
	}

}
