package ru.kpfu.itis.vaihdass.commands.implementations;

import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadFileCommand implements Command {
    private final String ABOUT_COMMAND = "Concatenate file to standard output.\n" +
            "Arguments: <File path> <File encoding>?";
    private final String DEFAULT_COMMAND_VIEW = ColoredStringBuilder.getColored("\"'View file command' <Path> <File encoding>?\"\n" +
            "<Path> can be set relative to the current directory OR absolute.\n" +
            "<File encoding>? can be set (OR default UTF-8).", ColoredStringBuilder.AnsiColors.ANSI_WHITE.text());

    @Override
    public void execute(Props props, Resp resp, DefaultResourceManager resourceManager) {
        if (props == null || props.getArgs() == null || props.getArgs().length < 1) {
            resp.setOutputText(ColoredStringBuilder.getColored(
                    "Can't read the file. Specify the file path with the argument (Encoding too, if required):\n",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()) + DEFAULT_COMMAND_VIEW);
            return;
        }

        if (props.getArgs()[0] == null || !resourceManager.validateFilePathGivenCurrent(props.getArgs()[0])) {
            resp.setOutputText(ColoredStringBuilder.getColored(
                    "File path isn't correct. Please, specify a current file path with:\n",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()) + DEFAULT_COMMAND_VIEW);
            return;
        }

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0]).normalize()).toFile().toPath()),
                    props.getArgs().length >= 2 && props.getArgs()[1] != null ? props.getArgs()[1] : "UTF-8");

            StringBuilder bufferString = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                bufferString.append(line).append('\n');
            }

            resp.setOutputText(bufferString.toString());
        } catch (UnsupportedEncodingException e) {
            resp.setOutputText(ColoredStringBuilder.getColored(
                    "The specified encoding is not supported. Please, try to specify another one with:\n",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()) + DEFAULT_COMMAND_VIEW);
        } catch (IOException e) {
            resp.setOutputText(ColoredStringBuilder.getColored(
                    "Could not read the contents of the file.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.text()));
        }
    }

    @Override
    public String getDescription() {
        return ABOUT_COMMAND;
    }
}
