package mx.gob.imss.cit.pmc.sustoerr.controller;

import java.text.MessageFormat;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.utils.DateUtils;

@RestController
@RequestMapping("/btpmcSusToErr")
public class BatchSusToErrControllerImpl {
	
	Logger logger = LoggerFactory.getLogger("BatchSusToErrControllerImpl");
	
	@Autowired
	@Qualifier("jobLauncherController")
	private SimpleJobLauncher jobLauncherController;
	
	@Autowired
	@Qualifier("pmcSusToErr")
	private Job pmcSusToErr;
	
	@RequestMapping("/health/ready")
    @ResponseStatus(HttpStatus.OK)
    public void ready() {
    	// Indica que el ms esta listo para recibir peticiones
    }

    @RequestMapping("/health/live")
    @ResponseStatus(HttpStatus.OK)
    public void live() {
    	//Indica que el ms esta vivo
    }
	
	@Bean
	public SimpleJobLauncher jobLauncherController(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}
	
	@PostMapping(value = "/ejecutar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> execute() {
		
		logger.info("Se inicia la ejecución del proceso");
		
		ResponseEntity<?> response = null;
		
		try {
			JobParameters params = new JobParametersBuilder()
					.addDate(SusToErrConstantes.FECHA_EJECUCION, Objects.requireNonNull(DateUtils.obtenerFechaMexico()))
					.toJobParameters();
			JobExecution execution = jobLauncherController.run(pmcSusToErr, params);
			response = new ResponseEntity<>("Proceso de actualización de susceptibles a erróneos ejecutado correctamente",
					HttpStatus.OK);
			logger.info(MessageFormat.format("La ejecucion fue exitosa, fecha de inicio: {0}, fecha de fin: {1}",
					execution.getStartTime(), execution.getEndTime()));			
		} catch(Exception exc) {
			logger.info("Error al ejecutar {}", exc);
			response = new ResponseEntity<>("Error al ejecutar el batch", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
		
	}

}
