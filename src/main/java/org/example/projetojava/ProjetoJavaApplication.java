package org.example.projetojava;

import org.example.projetojava.config.SshProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {
        "org.example.projetojava",       // pacote base do projeto
        "sshweb",                        // controllers da interface web SSH
        "sshservice",                    // serviços SSH
        "sshcontroller",                 // controllers REST SSH
        "org.example.projetojava.conexweb"    // HomeController
})
@EnableConfigurationProperties(SshProperties.class)
public class ProjetoJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoJavaApplication.class, args);
    }
}
