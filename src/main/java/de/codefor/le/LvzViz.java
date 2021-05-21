package de.codefor.le;

import java.time.ZoneId;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class LvzViz {

    public static final ZoneId EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

    public static void main(final String[] args) {
        SpringApplication.run(LvzViz.class, args);
    }

}
