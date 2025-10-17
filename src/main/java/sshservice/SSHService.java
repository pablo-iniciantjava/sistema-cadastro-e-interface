package sshservice;

import com.jcraft.jsch.*;
import sshserverconfig.ServerConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SSHService {

    public String executarScript(ServerConfig server, String scriptPath) {
        StringBuilder output = new StringBuilder();

        // Validação: só executa scripts permitidos
        if (!server.getScriptsPermitidos().contains(scriptPath)) {
            return "Erro: script não permitido!";
        }

        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(server.getUser(), server.getHost(), server.getPort());
            session.setPassword(server.getPassword());
            session.setConfig("StrictHostKeyChecking", "no"); // Ignora verificação de host
            session.connect();

            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(scriptPath);
            channel.setInputStream(null);

            BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            channel.connect();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            channel.disconnect();
            session.disconnect();

        } catch (Exception e) {
            return "Erro SSH: " + e.getMessage();
        }

        return output.toString();
    }
}
