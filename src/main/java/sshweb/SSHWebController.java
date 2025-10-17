package org.example.projetojava.sshweb;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/sshservice/web")
public class SSHWebController {

    private final List<String> scriptsPermitidos = List.of(
            "/home/root/teste.sh",
            "/home/root/atualizar.sh"
    );

    // 🔹 Redireciona a raiz (/) para /sshservice/web
    @GetMapping("/")
    public String redirecionarRaiz() {
        return "redirect:/sshservice/web";
    }

    // 🔹 Página inicial - Painel principal
    @GetMapping
    public String mostrarPaginaInicial(Model model) {
        System.out.println("GET /sshservice/web chamado!");
        model.addAttribute("scripts", scriptsPermitidos);
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
        model.addAttribute("scripts", scriptsPermitidos);
        return "ssh_exec"; // renderiza src/main/resources/templates/ssh_exec.html
    }

    // 🔹 Endpoint para simular execução de script
    @PostMapping(value = "/executar", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String executarScript(@RequestParam String script) {
        if (!scriptsPermitidos.contains(script)) {
            return "❌ Script não permitido!";
        }

        try {
            Thread.sleep(400 + new Random().nextInt(1200)); // simula tempo de execução
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("✅ Executado: ").append(script).append("\n");
        sb.append("Timestamp: ").append(Instant.now()).append("\n\n");
        sb.append("[INFO] Iniciando execução...\n");
        sb.append("[OK] Passo 1 concluído\n");
        sb.append("[OK] Passo 2 concluído\n");
        sb.append("[INFO] Execução finalizada com sucesso.\n");

        return sb.toString();
    }
}