package ru.kpfu.itis.vaihdass.baseInterfaces;

import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;

import java.util.Map;

public interface iCommandManager {
    void executeCommand(Props props, Resp resp, DefaultResourceManager resourceManager);

    void setCommand(String commandName, Command command);

    void removeCommand(String commandName);

    Command getCommand(String commandName);

    Map<String, Command> getCommands();
}
