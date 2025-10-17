package sshservice;

import com.jcraft.jsch.*;
import sshserverconfig.ServerConfig;

import java.io.BufferedReader;
import java.io.InputStream;
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
            // Captura stderr (extended data) também
            InputStream errStream = channel.getExtInputStream();
            BufferedReader errReader = new BufferedReader(new InputStreamReader(errStream));
            channel.connect();

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Lê stderr
            StringBuilder errOutput = new StringBuilder();
            String errLine;
            while ((errLine = errReader.readLine()) != null) {
                errOutput.append(errLine).append("\n");
            }

            // Aguarda término para capturar exit status
            while (!channel.isClosed()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {
                }
            }

            int exitStatus = channel.getExitStatus();

            channel.disconnect();
            session.disconnect();

            if (errOutput.length() > 0) {
                output.append("\n[stderr]\n").append(errOutput);
            }

            output.append("\n[exit-status] ").append(exitStatus);

        } catch (Exception e) {
            return "Erro SSH: " + e.getMessage();
        }

        return output.toString().trim();
    }

    public String testarConexao(ServerConfig server) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(server.getUser(), server.getHost(), server.getPort());
            session.setPassword(server.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(5000); // timeout 5s
            boolean connected = session.isConnected();
            session.disconnect();
            return connected ? "Conectado com sucesso" : "Falha ao conectar";
        } catch (Exception e) {
            return "Erro SSH: " + e.getMessage();
        }
    }
}