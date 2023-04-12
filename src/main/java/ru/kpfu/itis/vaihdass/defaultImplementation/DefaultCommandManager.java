package ru.kpfu.itis.vaihdass.defaultImplementation;

import ru.kpfu.itis.vaihdass.baseInterfaces.iCommandManager;
import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.util.HashMap;
import java.util.Map;

public class DefaultCommandManager implements iCommandManager {
    private Map<String, Command> commandsMap;

    public DefaultCommandManager() {
        commandsMap = new HashMap<>();
    }

    public void executeCommand(Props props, Resp resp, DefaultResourceManager resourceManager) {
        if (props == null || props.getCommand() == null) return;
        Command commandLower = getCommand(props.getCommand().toLowerCase());
        if (commandLower == null) {
            resp.setOutputText(ColoredStringBuilder.getColored("Error: Such command wasn't found.", ColoredStringBuilder.AnsiColors.ANSI_RED.bold()));
            return;
        }
        props.setCommandManager(this);
        commandLower.execute(props, resp, resourceManager);
    }

    public void setCommand(String commandName, Command command) {
        commandsMap.put(commandName, command);
    }

    public void removeCommand(String commandName) {
        commandsMap.remove(commandName);
    }

    public Command getCommand(String commandName) {
        return commandsMap.get(commandName);
    }

    public Map<String, Command> getCommands() {
        return commandsMap;
    }
}
