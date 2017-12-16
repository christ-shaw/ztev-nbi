package com.ztev.nbi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ServletComponentScan
@ImportResource(locations={"classpath*:/ztev-dubbo-consumer-springcontext.xml","classpath*:/ztev-dubbo-charging-consumer-springcontext.xml"})
public class NbiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NbiApplication.class, args);
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
