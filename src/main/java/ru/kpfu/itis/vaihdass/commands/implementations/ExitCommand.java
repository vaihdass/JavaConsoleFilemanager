package ru.kpfu.itis.vaihdass.commands.implementations;

import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;

public class ExitCommand implements Command {
    private final String ABOUT_COMMAND = "Allows to exit the file manager by terminating its process.";

    @Override
    public void execute(Props props, Resp resp, DefaultResourceManager resourceManager) {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return ABOUT_COMMAND;
    }
}
