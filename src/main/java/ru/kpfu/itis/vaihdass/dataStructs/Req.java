package ru.kpfu.itis.vaihdass.dataStructs;

import ru.kpfu.itis.vaihdass.baseInterfaces.Input;

public class Req {
    private final Input input;
    private final String command;

    public Req(String command, Input input) {
        if (command == null || input == null) throw new IllegalArgumentException("Command and input can't be null.");
        this.command = command;
        this.input = input;
    }

    public String getCommand() {
        return command;
    }

    public Input getInput() {
        return input;
    }
}
