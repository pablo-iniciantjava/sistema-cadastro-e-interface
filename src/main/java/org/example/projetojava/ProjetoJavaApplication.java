package org.example.projetojava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "org.example.projetojava",       // pacote base do projeto
        "org.example.projetojava.sshweb",    // controllers da interface web SSH
        "org.example.projetojava.sshservice", // serviços SSH
        "org.example.projetojava.controller"  // caso tenha outros controllers
})
public class ProjetoJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoJavaApplication.class, args);
    }
}
