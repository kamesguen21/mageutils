package tech.guen.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import tech.guen.dto.Data;
import tech.guen.dto.Website;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class DataHandler {
    public final ObjectMapper mapper = new ObjectMapper();
    String NGNIX_CONFIG = "            upstream fastcgi_backend {\n" + "                server unix:/run/php/php${phpVersion}-fpm.sock;\n" + "            }\n" + "            server {\n" + "                listen 80;\n" + "                listen [::]:80;\n" + "                server_name ${serverName};\n" + "                index index.php;\n" + "                set $MAGE_ROOT ${path}/;\n" + "                set $MAGE_MODE developer;\n" + "                access_log /var/log/nginx/acc-access.log;\n" + "                error_log /var/log/nginx/err-error.log;\n" + "                client_max_body_size 100M;\n" + "                include ${path}/nginx.conf.sample;\n" + "            }";
    private Data data;

    public Data getData() throws IOException {
        return mapper.readValue(new File("/home/mageutils/data.json"), Data.class);
    }

    public void setData(Data data) throws IOException {
        Path of = Path.of("/home/mageutils");
        if (Files.notExists(of)) {
            Files.createDirectories(of);
            String file = "#!/bin/bash\nsudo composer self-update --$1\n";
            FileUtils.writeStringToFile(new File("/usr/bin/changecomposer"), file, StandardCharsets.UTF_8);
        }
        mapper.writeValue(new File("/home/mageutils/data.json"), data);
        this.data = data;
    }

    abstract void handle(Data data);

    public void execute() {
        if (this.data != null) {
            this.handle(data);
            return;
        }
        if (new File("/home/mageutils/data.json").exists()) {
            try {
                this.data = this.getData();
                this.handle(data);
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.data = new Data();
        try {
            this.setData(this.data);
            this.handle(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Website setWebsite(Website websiteToEdit) {
        Scanner in = new Scanner(System.in);
        boolean done = false;
        Website website = new Website();
        if (websiteToEdit != null) {
            website.setId(websiteToEdit.getId());
        } else {
            website.setId(UUID.randomUUID());
        }
        while (!done) {
            if (website.getName() == null) {
                System.out.println("name ?");
                System.out.println("example carrefour");
                String name = in.nextLine();
                if (websiteToEdit != null && websiteToEdit.getName() != null && name.isEmpty()) {
                    name = websiteToEdit.getName();
                } else {
                    String finalName = name;
                    if (finalName == null || finalName.isEmpty()) {
                        System.out.println("cannot submit empty string");
                        continue;
                    }
                    if (data.getWebsiteList().stream().anyMatch(website1 -> website1.getName().equals(finalName) && (websiteToEdit == null || !websiteToEdit.getId().equals(website1.getId())))) {
                        System.out.println("name already exists");
                        continue;
                    }
                }
                website.setName(name);
            }
            if (website.getCode() == null) {
                System.out.println("code ?");
                System.out.println("example cr");

                String code = in.nextLine();
                if (websiteToEdit != null && websiteToEdit.getCode() != null && code.isEmpty()) {
                    code = websiteToEdit.getCode();
                } else {
                    String finalCode = code;
                    if (finalCode == null || finalCode.isEmpty()) {
                        System.out.println("cannot submit empty string");
                        continue;
                    }
                    if (data.getWebsiteList().stream().anyMatch(website1 -> website1.getCode().equals(finalCode) && (websiteToEdit == null || !websiteToEdit.getId().equals(website1.getId())))) {
                        System.out.println("code already exists");
                        continue;
                    }
                }
                website.setCode(code);
            }
            if (website.getServerName() == null) {
                System.out.println("serverName ?");
                System.out.println("example dev.carrefour.com");

                String serverName = in.nextLine();

                if (websiteToEdit != null && websiteToEdit.getServerName() != null && serverName.isEmpty()) {
                    serverName = websiteToEdit.getServerName();
                } else {
                    String finalServerName = serverName;
                    if (finalServerName == null || finalServerName.isEmpty()) {
                        System.out.println("cannot submit empty string");
                        continue;
                    }
                    if (data.getWebsiteList().stream().anyMatch(website1 -> website1.getServerName().equals(finalServerName) && (websiteToEdit == null || !websiteToEdit.getId().equals(website1.getId())))) {
                        System.out.println("serverName already exists");
                        continue;
                    }
                }
                website.setServerName(serverName);
            }
            if (website.getPath() == null) {
                System.out.println("path ?");
                System.out.println("example /var/www/magento2/carrefour-e-commerce");

                String path = in.nextLine();
                if (websiteToEdit != null && websiteToEdit.getPath() != null && path.isEmpty()) {
                    path = websiteToEdit.getPath();
                } else {
                    String finalPath = path;
                    if (finalPath == null || finalPath.isEmpty()) {
                        System.out.println("cannot submit empty string");
                        continue;
                    }
                    if (data.getWebsiteList().stream().anyMatch(website1 -> website1.getPath().equals(finalPath) && (websiteToEdit == null || !websiteToEdit.getId().equals(website1.getId())))) {
                        System.out.println("path already exists");
                        continue;
                    }
                }
                website.setPath(path);
            }
            if (website.getPhpVersion() == null) {
                System.out.println("php Version ?");
                System.out.println("example 8.2");

                String phpVersion = in.nextLine();
                if (websiteToEdit != null && websiteToEdit.getPhpVersion() != null && phpVersion.isEmpty()) {
                    phpVersion = websiteToEdit.getPhpVersion();
                }
                if (phpVersion == null || phpVersion.isEmpty()) {
                    System.out.println("cannot submit empty string");
                    continue;
                }
                website.setPhpVersion(phpVersion);
            }
            if (website.getComposerVersion() == null) {
                System.out.println("composer Version ?");
                System.out.println("example 1 or 2");

                String composerVersion = in.nextLine();
                if (websiteToEdit != null && websiteToEdit.getComposerVersion() != null && composerVersion.isEmpty()) {
                    composerVersion = websiteToEdit.getComposerVersion();
                }
                if (composerVersion == null || composerVersion.isEmpty()) {
                    System.out.println("cannot submit empty string");
                    continue;
                }
                website.setComposerVersion(composerVersion);
            }
            done = true;
        }
        try {
            String conf = NGNIX_CONFIG.replace("${path}", website.getPath()).replace("${serverName}", website.getServerName()).replace("${phpVersion}", website.getPhpVersion());
            FileUtils.writeStringToFile(new File("/home/mageutils/" + website.getCode()), conf, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return website;
    }

    public void handleEmpty(Data data) {
        Scanner in = new Scanner(System.in);
        System.out.println("no Website found, want to add? y/n");
        String s = in.nextLine();
        boolean yes = Boolean.parseBoolean(s) || "y".equalsIgnoreCase(s);
        if (!yes) {
            System.exit(1);
        }
        new AddCommand().handle(data);
    }

    static class StreamGobbler implements Runnable {
        private final InputStream inputStream;
        private final Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
        }
    }
}
