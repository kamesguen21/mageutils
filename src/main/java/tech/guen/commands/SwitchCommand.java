package tech.guen.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import tech.guen.dto.Data;
import tech.guen.dto.Website;

import java.io.IOException;
import java.util.Optional;


@CommandLine.Command(name = "switch")
public class SwitchCommand extends DataHandler implements Runnable {
    public final ObjectMapper mapper = new ObjectMapper();
    @CommandLine.Parameters
    private String website;

    public SwitchCommand(String website) {
        this.website = website;
    }

    public SwitchCommand() {
    }

    @Override
    public void run() {
        this.execute();
    }

    public void handle(Data data) {
        if (data.getWebsiteList().isEmpty()) {
            this.handleEmpty(data);
            return;
        }
        Optional<Website> any = data.getWebsiteList().stream().filter(website -> website.getCode().equals(this.website) || website.getName().equals(this.website)).findAny();
        if (any.isEmpty()) {
            this.handleEmpty(data);
            return;
        }
        Website website = any.get();
        try {
            //update-alternatives --set php $(update-alternatives --list php | grep php8.1)
            System.out.println(mapper.writeValueAsString(website));
            runCommand("ls");
            runCommand("cp", "/home/mageutils/" + website.getCode(), "/etc/nginx/sites-enabled/magento");
            runCommand("sudo", "update-alternatives", "--set", "php", "$(update-alternatives --list php | grep php" + website.getPhpVersion() + ")");
            runCommand("sudo", "service", "nginx", "restart");
            runCommand("sudo", "php" + website.getPhpVersion() + "-fpm", "restart");
            //   runCommand("sudo changecomposer",website.getServerName());
            System.out.println("almost done");
            System.out.println("Please run");
            System.out.println("sudo composer self-update --" + website.getComposerVersion());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    void runCommand(String... command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        try {

            System.out.println(String.join(" ", pb.command()));
            Process p = pb.start();
            int exitStatus = p.waitFor();
            System.out.println(exitStatus);
        } catch (InterruptedException | IOException x) {
            x.printStackTrace();
        }
    }


}
