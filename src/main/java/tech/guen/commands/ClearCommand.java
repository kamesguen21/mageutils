package tech.guen.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import tech.guen.dto.Data;

import java.io.IOException;


@CommandLine.Command(name = "clear")
public class ClearCommand extends DataHandler implements Runnable {
    public final ObjectMapper mapper = new ObjectMapper();


    @Override
    public void run() {
        this.execute();
    }

    public void handle(Data data) {
        try {
            this.setData(new Data());
            System.out.println("cleared done empty, you happy now");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
