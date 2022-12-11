package tech.guen.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import tech.guen.dto.Data;
import tech.guen.dto.Website;

import java.io.IOException;
import java.util.Scanner;


@CommandLine.Command(name = "add")
public class AddCommand extends DataHandler implements Runnable {
    public final ObjectMapper mapper = new ObjectMapper();


    @Override
    public void run() {
        this.execute();
    }

    public void handle(Data data) {
        Scanner in = new Scanner(System.in);
        System.out.println("lets add a Website");
        Website website = this.setWebsite(null);
        data.getWebsiteList().add(website);
        try {
            this.setData(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("done, don't forget to add the path to your hosts file if you're using windows");
        System.out.println("switch? y/n");
        boolean yes;
        String s = in.nextLine();
        yes = Boolean.parseBoolean(s) || "y".equalsIgnoreCase(s);
        if (yes) {
            new SwitchCommand(website.getCode()).run();
        } else {
            System.exit(0);
        }

    }
}
