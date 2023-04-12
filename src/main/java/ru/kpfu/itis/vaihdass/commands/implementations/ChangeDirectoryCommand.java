package ru.kpfu.itis.vaihdass.commands.implementations;

import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.exceptions.IncorrectDirectoryException;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeDirectoryCommand implements Command {
    private final String ABOUT_COMMAND = "Change the current directory.\n" +
            "Arguments: <Path> (can be set relative to the current directory OR absolute).";

    private final String DEFAULT_COMMAND_VIEW = ColoredStringBuilder.getColored("\"'Change directory command' <Path>\"\n" +
            "<Path> can be set relative to the current directory OR absolute.", ColoredStringBuilder.AnsiColors.ANSI_WHITE.text());

    @Override
    public void execute(Props props, Resp resp, DefaultResourceManager resourceManager) {
        if (props.getArgs() == null || props.getArgs().length < 1) {
            resp.setOutputText(ColoredStringBuilder.getColored("Can't change the directory. Specify the path to change the directory with the argument:\n",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()) + DEFAULT_COMMAND_VIEW);
            return;
        }

        Path newDirectory = null;
        try {
            newDirectory = Paths.get(props.getArgs()[0]);
        } catch (InvalidPathException e) {
            resp.setOutputText(ColoredStringBuilder.getColored("The specified path is not correct. Specify the path to change the directory using the argument like this:\n",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()) + DEFAULT_COMMAND_VIEW);
            return;
        }

        try {
            resourceManager.setCurrentDirectory(resourceManager.getCurrentDirectory().resolve(newDirectory.normalize()).toString());
        } catch (IncorrectDirectoryException e) {
            resp.setOutputText(ColoredStringBuilder.getColored("The resulting path does not exist or has incorrect format. Set an existing path like this:\n",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()) + DEFAULT_COMMAND_VIEW);
        }
    }

    @Override
    public String getDescription() {
        return ABOUT_COMMAND;
    }
}
