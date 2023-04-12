package ru.kpfu.itis.vaihdass.commands.implementations;

import org.apache.commons.io.FileUtils;
import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.io.IOException;
import java.nio.file.Paths;

public class RemovePathCommand implements Command {
    private final String ABOUT_COMMAND = "Deletes the specified file or folder (recursively by default).\n" +
            "Arguments: <Path> can be set relative to the current directory OR absolute.";

    @Override
    public void execute(Props props, Resp resp, DefaultResourceManager resourceManager) {
        if (props == null || props.getArgs() == null || props.getArgs().length == 0 || props.getArgs()[0] == null) {
            resp.setOutputText(ColoredStringBuilder.getColored("Can't remove a file/directory. " +
                            "Specify the path to remove the file/directory with the argument:\n" +
                            "'Remove command' <Path>.\n" +
                            "Arguments: <Path> of file/directory can be set relative to the current directory OR absolute.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()));
            return;
        }

        if (!resourceManager.validateAnyPathGivenCurrent(props.getArgs()[0])) {
            resp.setOutputText(ColoredStringBuilder.getColored("The specified path is incorrect. Can't remove a file/directory.\n" +
                            "Specify the valid path to remove the file/directory like this:\n" +
                            "'Remove command' <Path>.\n" +
                            "Arguments: <Path> of file/directory can be set relative to the current directory OR absolute.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()));
            return;
        }

        try {
            if (resourceManager.getCurrentDirectory().startsWith(resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0])).normalize())) {
                resp.setOutputText(ColoredStringBuilder.getColored("The specified path is unacceptable, since it is the parent of the current. " +
                                "Can't remove a file/directory.\n" +
                                "Specify the valid path to remove the file/directory like this:\n" +
                                "'Remove command' <Path>.\n" +
                                "Arguments: <Path> of file/directory can be set relative to the current directory OR absolute.",
                        ColoredStringBuilder.AnsiColors.ANSI_YELLOW.bold()));
                return;
            }

            FileUtils.forceDelete(resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0]).normalize()).toFile());

            if (!resourceManager.getCurrentDirectory().toFile().exists()) { // HOW?!?
                resourceManager.setCurrentDirectory(resourceManager.getHomeDirectory().toString());
                if (!resourceManager.getCurrentDirectory().toFile().exists() && props.getCommandManager() != null) {
                    props.setCommand("exit");
                    props.getCommandManager().executeCommand(props, resp, resourceManager);
                }
            }
        } catch (IOException e) {
            resp.setOutputText(ColoredStringBuilder.getColored("Insufficient access problem or internal error." +
                            "Can't remove a file/directory.\n" +
                            "Specify a valid path to remove the file/directory like this:\n" +
                            "'Remove command' <Path>.\n" +
                            "Arguments: <Path> of file/directory can be set relative to the current directory OR absolute.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.bold()));
        }
    }

    @Override
    public String getDescription() {
        return ABOUT_COMMAND;
    }
}
