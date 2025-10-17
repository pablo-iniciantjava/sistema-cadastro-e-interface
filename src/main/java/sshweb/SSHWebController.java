package sshweb;

import org.example.projetojava.config.SshProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sshservice.SSHService;
import sshserverconfig.ServerConfig;

import java.util.List;

@Controller
@RequestMapping("/sshservice/web")
public class SSHWebController {

    private final SSHService sshService = new SSHService();
    private final SshProperties sshProperties;

    public SSHWebController(SshProperties sshProperties) {
        this.sshProperties = sshProperties;
    }

    private List<String> getScriptsPermitidos() {
        return sshProperties.getScripts();
    }

    private ServerConfig buildServerConfig(String host, Integer port, String user, String password) {
        ServerConfig server = new ServerConfig();
        server.setNome("ServidorDinAmico");
        server.setHost(host);
        server.setPort(port != null ? port : 22);
        server.setUser(user);
        server.setPassword(password);
        server.setScriptsPermitidos(getScriptsPermitidos());
        return server;
    }

    // 🔹 Redireciona a raiz (/) para /sshservice/web
    @GetMapping("/")
    public String redirecionarRaiz() {
        return "redirect:/sshservice/web";
    }

    // 🔹 Página inicial - Painel principal
    @GetMapping
    public String mostrarPaginaInicial(Model model) {
        System.out.println("GET /sshservice/web chamado!");
        model.addAttribute("scripts", getScriptsPermitidos());
        return "index"; // renderiza src/main/resources/templates/index.html
    }

    // 🔹 Página de configuração SSH
    @GetMapping("/config")
    public String mostrarConfigPage(Model model) {
        System.out.println("GET /sshservice/web/config chamado!");
        return "ssh_config"; // renderiza src/main/resources/templates/ssh_config.html
    }

    // 🔹 Página de execução de comandos
    @GetMapping("/exec")
    public String mostrarExecPage(Model model) {
        System.out.println("GET /sshservice/web/exec chamado!");
        model.addAttribute("scripts", getScriptsPermitidos());
        return "ssh_exec"; // renderiza src/main/resources/templates/ssh_exec.html
    }

    // 🔹 Endpoint para executar script real via SSH
    @PostMapping(value = "/executar", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String executarScript(
            @RequestParam String host,
            @RequestParam Integer port,
            @RequestParam String user,
            @RequestParam String password,
            @RequestParam String script
    ) {
        List<String> scriptsPermitidos = getScriptsPermitidos();
        if (!scriptsPermitidos.contains(script)) {
            return "❌ Script não permitido!";
        }

        ServerConfig cfg = buildServerConfig(host, port, user, password);
        if (cfg.getHost() == null || cfg.getHost().isBlank() || cfg.getUser() == null || cfg.getUser().isBlank() || cfg.getPassword() == null || cfg.getPassword().isBlank()) {
            return "❌ Parâmetros inválidos: host, user e password são obrigatórios.";
        }
        return sshService.executarScript(cfg, script);
    }

    // 🔹 Endpoint para testar conexão sem executar script
    @PostMapping(value = "/testar", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String testarConexao(
            @RequestParam String host,
            @RequestParam Integer port,
            @RequestParam String user,
            @RequestParam String password
    ) {
        ServerConfig cfg = buildServerConfig(host, port, user, password);
        if (cfg.getHost() == null || cfg.getHost().isBlank() || cfg.getUser() == null || cfg.getUser().isBlank() || cfg.getPassword() == null || cfg.getPassword().isBlank()) {
            return "❌ Parâmetros inválidos: host, user e password são obrigatórios.";
        }
        return sshService.testarConexao(cfg);
    }
}