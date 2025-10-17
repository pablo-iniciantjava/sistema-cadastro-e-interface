package sshcontroller;

import org.example.projetojava.config.SshProperties;
import org.springframework.web.bind.annotation.*;
import sshservice.SSHService;
import sshserverconfig.ServerConfig;

@RestController
@RequestMapping("/ssh")
public class SSHController {

    private final SSHService sshService = new SSHService();
    private final SshProperties sshProperties;

    public SSHController(SshProperties sshProperties) {
        this.sshProperties = sshProperties;
    }

    private ServerConfig buildServerConfig(String host, Integer port, String user, String password) {
        ServerConfig server = new ServerConfig();
        server.setNome("ServidorReal");
        server.setHost(host);
        server.setPort(port != null ? port : 22);
        server.setUser(user);
        server.setPassword(password);
        server.setScriptsPermitidos(sshProperties.getScripts());
        return server;
    }

    @PostMapping("/executar")
    public String executar(
            @RequestParam String host,
            @RequestParam Integer port,
            @RequestParam String user,
            @RequestParam String password,
            @RequestParam String script
    ) {
        ServerConfig cfg = buildServerConfig(host, port, user, password);
        if (cfg.getHost() == null || cfg.getHost().isBlank() || cfg.getUser() == null || cfg.getUser().isBlank() || cfg.getPassword() == null || cfg.getPassword().isBlank()) {
            return "❌ Parâmetros inválidos: host, user e password são obrigatórios.";
        }
        return sshService.executarScript(cfg, script);
    }
}
