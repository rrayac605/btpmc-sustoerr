package mx.gob.imss.cit.pmc.sustoerr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class BatchSusToErrApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchSusToErrApplication.class, args);
    }

}
