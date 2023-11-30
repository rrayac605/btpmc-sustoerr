package mx.gob.imss.cit.pmc.sustoerr.schedule;

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.utils.DateUtils;

@Component
public class BatchSusToErrSchedule {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchSusToErrSchedule.class);
	
	@Autowired
	@Qualifier("jobLauncherSchedule")
	private SimpleJobLauncher jobLauncherSchedule;
	
	@Autowired
	@Qualifier("pmcSusToErr")
	private Job pmcSusToErr;
	
	@Bean
	public SimpleJobLauncher jobLauncherSchedule(JobRepository jobRepository) {
		SimpleJobLauncher launcher = new SimpleJobLauncher();
		launcher.setJobRepository(jobRepository);
		return launcher;
	}
	
	@Scheduled(cron = "${cron.expression.susToErr}")
	public void iniciaBatch() {
		
		logger.info("Se inicia la ejecución del proceso");
				
		try {
			JobParameters params = new JobParametersBuilder()
					.addDate(SusToErrConstantes.FECHA_EJECUCION, Objects.requireNonNull(DateUtils.obtenerFechaMexico()))
					.toJobParameters();
			JobExecution execution = jobLauncherSchedule.run(pmcSusToErr, params);
			logger.info(MessageFormat.format("La ejecucion fue exitosa, fecha de inicio: {0}, fecha de fin: {1}",
					execution.getStartTime(), execution.getEndTime()));			
		} catch(Exception exc) {
			logger.info("Error al ejecutar {}", exc);
		}
		
	}

}
