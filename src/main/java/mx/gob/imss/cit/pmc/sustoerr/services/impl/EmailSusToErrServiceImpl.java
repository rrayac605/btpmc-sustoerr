package mx.gob.imss.cit.pmc.sustoerr.services.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import mx.gob.imss.cit.pmc.sustoerr.constants.SusToErrConstantes;
import mx.gob.imss.cit.pmc.sustoerr.dto.EmailTemplateDTO;
import mx.gob.imss.cit.pmc.sustoerr.dto.MovimientoDTO;
import mx.gob.imss.cit.pmc.sustoerr.repository.ContadorRepository;
import mx.gob.imss.cit.pmc.sustoerr.repository.EmailTemplateRepository;
import mx.gob.imss.cit.pmc.sustoerr.services.EmailSusToErrService;
import mx.gob.imss.cit.pmc.sustoerr.utils.ReaderUtils;
import mx.gob.imss.cit.pmc.sustoerr.utils.DateUtils;

@Service
public class EmailSusToErrServiceImpl implements EmailSusToErrService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailSusToErrServiceImpl.class);
	
	@Autowired
	private EmailTemplateRepository emailTemplateRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ContadorRepository contadorRepository;
	
	@Override
	public void enviarCorreo(String nombreTemplate) {
		try {
			EmailTemplateDTO emailTemplate = emailTemplateRepository.findByName(nombreTemplate);
			llenarTemplate(emailTemplate);
			MimeMessageHelper mimeMessageHelper = construirMimeMessageHelper(emailTemplate);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} catch(MessagingException | MailException me) {
			logger.error("Error al enviar el correo ", me);
		} catch (UnsupportedEncodingException uee) {
			logger.error("Erorr al codificar encabezado {}", uee);
		}
	}
	
	private MimeMessageHelper construirMimeMessageHelper(EmailTemplateDTO emailTemplate) throws MessagingException, UnsupportedEncodingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, Boolean.TRUE);
		mimeMessageHelper.setSubject(MimeUtility.encodeText(emailTemplate.getSubject(), "UTF-8", "Q"));
		mimeMessageHelper.setFrom(emailTemplate.getFrom());
		mimeMessageHelper.setTo(emailTemplate.getTo());
		mimeMessageHelper.setText(emailTemplate.getTemplate(), Boolean.TRUE);
		mimeMessageHelper.addInline(SusToErrConstantes.ENCABEZADO_IMG,
				new ClassPathResource(SusToErrConstantes.RUTA_IMGS.concat(SusToErrConstantes.ENCABEZADO_IMG)));
		mimeMessageHelper.addInline(SusToErrConstantes.PIE_IMG,
				new ClassPathResource(SusToErrConstantes.RUTA_IMGS.concat(SusToErrConstantes.PIE_IMG)));
		
		return mimeMessageHelper;
	}
	
	private void llenarTemplate(EmailTemplateDTO emailTemplateDTO) {
		
		TypedAggregation<MovimientoDTO> susActualizados = ReaderUtils.contadorSusceptibles();
		TypedAggregation<MovimientoDTO> susOtrasActualizados = ReaderUtils.contadorSusceptiblesOtras();
		TypedAggregation<MovimientoDTO> susceptiblesTotal = ReaderUtils.contadorTotalSusceptibles();
		TypedAggregation<MovimientoDTO> susOtrasTotal = ReaderUtils.contadorTotalSusOtras();
		TypedAggregation<MovimientoDTO> errorTotal = ReaderUtils.contadorTotalErroneos();
		TypedAggregation<MovimientoDTO> errorOtrasTotal = ReaderUtils.contadorTotalErrOtras();
		
		long susObtenidos = contadorRepository.count(susActualizados);
		long susModificados = contadorRepository.count(susActualizados);
		long susOtrasObtenidos = contadorRepository.count(susOtrasActualizados);
		long susOtrasModificados = contadorRepository.count(susOtrasActualizados);
		long totalSus = contadorRepository.count(susceptiblesTotal);
		long totalSusOtras = contadorRepository.count(susOtrasTotal);
		long totalErr = contadorRepository.count(errorTotal);
		long totalErrOtras = contadorRepository.count(errorOtrasTotal);
		
		long susAntes = totalSus + susModificados;
		long susOtrasAntes = totalSusOtras + susOtrasModificados;
		long errAntes = totalErr - susModificados;
		long errOtrasAntes = totalErrOtras - susOtrasModificados;
		
		String fechaHora = "";
		String totalesAntes = "";
		String totalesActualizados = "";
		String totalesDespues = "";
		
		long[] conteosAntes = {susAntes, susOtrasAntes, errAntes, errOtrasAntes};
		long[] conteosActualizados = {susModificados, susOtrasModificados};
		long[] conteosDespues = {totalSus, totalSusOtras, totalErr, totalErrOtras};
		
		fechaHora = fechaHora.concat("<p>").concat(DateUtils.obtenerFechaMexicoString()).concat("</p>");
		
		for(int i = 0; i < SusToErrConstantes.RIESGOS_ANTES.length; i++) {
			totalesAntes = totalesAntes.concat("<p>").concat(SusToErrConstantes.RIESGOS_ANTES[i])
					.concat(String.valueOf(conteosAntes[i])).concat("</p>");
		}
		
		for(int j = 0; j < SusToErrConstantes.RIESGOS_ACTUALIZADOS.length; j++) {
			totalesActualizados = totalesActualizados.concat("<p>").concat(SusToErrConstantes.RIESGOS_ACTUALIZADOS[j])
					.concat(String.valueOf(conteosActualizados[j])).concat("</p>");
		}
		
		for(int k = 0; k < SusToErrConstantes.RIESGOS_DESPUES.length; k++) {
			totalesDespues = totalesDespues.concat("<p>").concat(SusToErrConstantes.RIESGOS_DESPUES[k])
					.concat(String.valueOf(conteosDespues[k])).concat("</p>");
			
		}
		
		logger.info("Datos en fechaHora {}", fechaHora);
		logger.info("Conteos -> Sus {} SusOtras {}", susObtenidos, susOtrasObtenidos);
		logger.info("Datos en totales antes {}", totalesAntes);
		logger.info("Datos en totales actualizados {} ", totalesActualizados);
		logger.info("Datos en totales despues {}", totalesDespues);
		
		emailTemplateDTO.setTemplate(emailTemplateDTO.getTemplate().replace(SusToErrConstantes.FECHA_HORA, fechaHora));
		emailTemplateDTO.setTemplate(emailTemplateDTO.getTemplate().replace(SusToErrConstantes.TOTALES_ANTES, totalesAntes));
		emailTemplateDTO.setTemplate(emailTemplateDTO.getTemplate().replace(SusToErrConstantes.TOTALES_ACTUALIZADOS, totalesActualizados));
		emailTemplateDTO.setTemplate(emailTemplateDTO.getTemplate().replace(SusToErrConstantes.TOTALES_DESPUES, totalesDespues));
	}

}
