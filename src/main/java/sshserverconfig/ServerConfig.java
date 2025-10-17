package sshserverconfig;

import java.util.List;

public class ServerConfig {
    private String nome;
    private String host;
    private int port = 22;
    private String user;
    private String password;
    private List<String> scriptsPermitidos; // Caminhos dos scripts que podem ser executados

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<String> getScriptsPermitidos() { return scriptsPermitidos; }
    public void setScriptsPermitidos(List<String> scriptsPermitidos) { this.scriptsPermitidos = scriptsPermitidos; }
}
