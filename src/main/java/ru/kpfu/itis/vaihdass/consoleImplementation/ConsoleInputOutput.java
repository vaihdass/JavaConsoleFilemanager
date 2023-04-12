package ru.kpfu.itis.vaihdass.consoleImplementation;

import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultController;
import ru.kpfu.itis.vaihdass.baseInterfaces.Input;
import ru.kpfu.itis.vaihdass.baseInterfaces.Output;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.exceptions.OutputEmptyViewException;

import java.io.InputStream;
import java.util.Scanner;

public class ConsoleInputOutput extends Input implements Output {
    private Scanner scanner;

    public ConsoleInputOutput(InputStream input) {
        super();
        scanner = new Scanner(input);
    }

    public ConsoleInputOutput(DefaultController controller, InputStream input) {
        super(controller);
        scanner = new Scanner(input);
    }

    public void requestCommands() {
        String command;
        setLastCommand(""); // For the first "userName: ~$" view
        lastCommandChanged();
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if (command != null) {
                setLastCommand(command);
                lastCommandChanged();
            }
        }
    }

    public String requestNewCommand() {
        String command;
        try {
            if (scanner.hasNextLine()) {
                command = scanner.nextLine();
                if (command != null) {
                    setLastCommand(command);
                    return command;
                }
            }
        } catch (IllegalStateException e) {
            return null;
        }
        return null;
    }

    @Override
    public void setView(Resp resp) throws OutputEmptyViewException {
        if (resp == null || resp.getOutputText().equals("")) throw new OutputEmptyViewException("Response can't be null.");
        if (!resp.isRequireLinebreakAfter()) {
            System.out.print(resp.getOutputText());
        } else {
            System.out.println(resp.getOutputText());
        }
    }
}
