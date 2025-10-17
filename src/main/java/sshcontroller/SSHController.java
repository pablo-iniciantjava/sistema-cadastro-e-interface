package org.example.projetojava.controller;

import org.springframework.web.bind.annotation.*;
import sshservice.SSHService;
import sshserverconfig.ServerConfig;

@RestController
@RequestMapping("/ssh")
public class SSHController {

    private final SSHService sshService = new SSHService();
    private final ServerConfig server = new ServerConfig();

    public SSHController() {
        server.setNome("ServidorTest");
        server.setHost("10.30.0.93");
        server.setUser("root");
        server.setPassword("Rsi@2020");
        server.setScriptsPermitidos(java.util.List.of(
                "/home/root/teste.sh",
                "/home/root/atualizar.sh"
        ));
    }

    @PostMapping("/executar")
    public String executar(@RequestParam String script) {
        return sshService.executarScript(server, script);
    }
}
