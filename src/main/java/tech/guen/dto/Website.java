package tech.guen.dto;

import java.util.UUID;

public class Website {
    private String name;
    private UUID id;
    private String code;
    private String composerVersion;
    private String phpVersion;
    private String serverName;
    private String path;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComposerVersion() {
        return composerVersion;
    }

    public void setComposerVersion(String composerVersion) {
        this.composerVersion = composerVersion;
    }

    public String getPhpVersion() {
        return phpVersion;
    }

    public void setPhpVersion(String phpVersion) {
        this.phpVersion = phpVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
