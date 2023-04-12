package ru.kpfu.itis.vaihdass.dataStructs;

import ru.kpfu.itis.vaihdass.baseInterfaces.Input;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultCommandManager;

public class Props {
    private Input input;
    private String command;
    private DefaultCommandManager commandManager;
    private String[] args;

    public Props(Input input) {
        if (input == null) throw new IllegalArgumentException("Input can't be null.");
        this.input = input;
    }

    public Input getInput() {
        return input;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public DefaultCommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(DefaultCommandManager commandManager) {
        this.commandManager = commandManager;
    }
}
