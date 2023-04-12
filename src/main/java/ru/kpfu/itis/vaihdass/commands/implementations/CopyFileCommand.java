package ru.kpfu.itis.vaihdass.commands.implementations;

import org.apache.commons.io.FileUtils;
import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.io.IOException;
import java.nio.file.Paths;

public class CopyFileCommand implements Command {
    private final String ABOUT_COMMAND = "Copies a file or folder from the first specified path to the second (recursively by default).\n" +
            "Arguments: <Path 1> and <Path 2> can be set relative to the current directory OR absolute.";

    @Override
    public void execute(Props props, Resp resp, DefaultResourceManager resourceManager) {
        if (props == null || props.getArgs() == null || props.getArgs().length < 2
                || props.getArgs()[0] == null || props.getArgs()[1] == null) {
            resp.setOutputText(ColoredStringBuilder.getColored("Can't copy a file/directory. " +
                            "Specify two paths to copy the file/directory with the argument:\n" +
                            "'Copy command' <Path 1> <Path 2>.\n" +
                            "Arguments: <Path 1|2> of file/directory can be set relative to the current directory OR absolute.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()));
            return;
        }

        boolean a1 = resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0])).normalize().toFile().isFile();
        boolean a2 = resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0])).normalize().toFile().isFile();

        if (!resourceManager.validateAnyPathGivenCurrent(props.getArgs()[0])
                || !resourceManager.checkFormatAnyPathGivenCurrent(props.getArgs()[1])
                || (resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0])).normalize().toFile().isFile() != resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[1])).normalize().toFile().isFile())
        ) {
            System.out.println(!resourceManager.validateAnyPathGivenCurrent(props.getArgs()[0]) + " " + !resourceManager.validateAnyPathGivenCurrent(props.getArgs()[1]));
            System.out.println(resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0])).normalize() + " " + resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[1])).normalize());
                    resp.setOutputText(ColoredStringBuilder.getColored("Specified paths is incorrect. Can't copy a file/directory.\n" +
                            "Specify valid paths to copy the file/directory with the argument:\n" +
                            "'Copy command' <Path 1> <Path 2>.\n" +
                            "Arguments: <Path 1|2> of file/directory can be set relative to the current directory OR absolute.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()));
            return;
        }

        try {
            if (resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0]).normalize()).toFile().isFile()) {
                FileUtils.copyFile(resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0]).normalize()).toFile(),
                        resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[1]).normalize()).toFile());
            } else {
                FileUtils.copyDirectory(resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0]).normalize()).toFile(),
                        resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[1]).normalize()).toFile());
            }
        } catch (IOException e) {
            resp.setOutputText(ColoredStringBuilder.getColored("Insufficient access problem or internal error." +
                            "Can't copy a file/directory.\n" +
                            "Specify valid paths to copy the file/directory with the argument:\n" +
                            "'Copy command' <Path 1> <Path 2>.\n" +
                            "Arguments: <Path 1|2> of file/directory can be set relative to the current directory OR absolute.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.bold()));
        }
    }

    @Override
    public String getDescription() {
        return ABOUT_COMMAND;
    }
}
