package mx.gob.imss.cit.pmc.sustoerr.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.services.EmailSusToErrService;

@Component
@StepScope
public class CorreoSusToErrTasklet implements Tasklet {
	
	@Autowired
	private EmailSusToErrService emailSusToErrService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunk) throws Exception {
		try {
			String nombreTemplate = SusToErrConstantes.CORREO_TEMPLATE;
			emailSusToErrService.enviarCorreo(nombreTemplate);
		} catch(Exception exc) {
			exc.printStackTrace();
		}
		return RepeatStatus.FINISHED;
	}

}
