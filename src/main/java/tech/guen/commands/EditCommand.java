package tech.guen.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import tech.guen.dto.Data;
import tech.guen.dto.Website;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;


@CommandLine.Command(name = "edit")
public class EditCommand extends DataHandler implements Runnable {
    public final ObjectMapper mapper = new ObjectMapper();
    @CommandLine.Parameters
    private String websiteCode;

    @Override
    public void run() {
        this.execute();
    }

    public void handle(Data data) {
        Scanner in = new Scanner(System.in);
        System.out.println("lets edit a Website");
        Optional<Website> any = data.getWebsiteList().stream().filter(website1 -> website1.getCode().equals(websiteCode)).findAny();
        if (any.isEmpty()) {
            this.handleEmpty(data);
            return;
        }
        Website website1 = this.setWebsite(any.get());
        data.setWebsiteList(data.getWebsiteList().stream().peek(website -> {
            if (website.getCode().equals(websiteCode)) {
                website.setPath(website1.getPath());
                website.setCode(website1.getCode());
                website.setServerName(website1.getServerName());
                website.setName(website1.getName());
                website.setPhpVersion(website1.getPhpVersion());
                website.setId(website1.getId());
                website.setComposerVersion(website1.getComposerVersion());
            }
        }).collect(Collectors.toList()));
        try {
            this.setData(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
