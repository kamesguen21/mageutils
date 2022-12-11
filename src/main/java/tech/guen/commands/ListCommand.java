package tech.guen.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import tech.guen.dto.Data;
import tech.guen.dto.Website;


@CommandLine.Command(name = "list")
public class ListCommand extends DataHandler implements Runnable {
    public final ObjectMapper mapper = new ObjectMapper();


    @Override
    public void run() {
        this.execute();
    }

    public void handle(Data data) {
        for (Website website : data.getWebsiteList()) {
            System.out.println("Code :" + website.getCode());
            System.out.println("Name :" + website.getName());
            System.out.println("Server name :" + website.getServerName());
            System.out.println("Path :" + website.getPath());
            System.out.println("Php version :" + website.getPhpVersion());
            System.out.println("Composer version :" + website.getComposerVersion());
        }

    }
}
