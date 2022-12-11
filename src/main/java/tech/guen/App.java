package tech.guen;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import tech.guen.commands.*;

import static picocli.CommandLine.Command;


/**
 * Hello world!
 */
@Command(subcommands = {
        SwitchCommand.class,
        AddCommand.class,
        EditCommand.class,
        ClearCommand.class,
        ListCommand.class,

})
public class App implements Runnable {
    public final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        CommandLine.run(new App(), args);
    }

    @Override
    public void run() {
        System.out.println("Hi, this a command line utility to manage you magento projects in ubuntu");

    }
/*    @Override
    public void run() {
        boolean yes;
        System.out.println("Hi, this a command line utility to manage you magento projects in ubuntu, continue? y/n");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        yes = Boolean.parseBoolean(s) || "y".equalsIgnoreCase(s);
        if(!yes){
            System.exit(0);
        }
        System.out.println(" 1 : switch apps");
        System.out.println(" 2 : create new app");
        System.out.println(" 3 : restart nginx & php");
    }*/
}
