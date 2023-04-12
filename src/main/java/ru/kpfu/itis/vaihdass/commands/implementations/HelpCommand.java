package ru.kpfu.itis.vaihdass.commands.implementations;

import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.util.Map;

public class HelpCommand implements Command {
    private final String ABOUT_COMMAND = "Displays a list of all available commands with a description.";

    @Override
    public void execute(Props props, Resp resp, DefaultResourceManager resourceManager) {
        if (props == null || props.getCommandManager() == null) {
            resp.setOutputText(
                    ColoredStringBuilder.getColored("There is no necessary access to the descriptions of commands.",
                            ColoredStringBuilder.AnsiColors.ANSI_RED.bold()));
            return;
        }

        StringBuilder resultString = new StringBuilder(ColoredStringBuilder.getColored("\n>>> HELP: <<<\n",
                ColoredStringBuilder.AnsiColors.ANSI_RED.text()));
        for (Map.Entry<String, Command> commandEntry : props.getCommandManager().getCommands().entrySet()) {
            resultString
                    .append(ColoredStringBuilder.getColored(commandEntry.getKey().toUpperCase(), ColoredStringBuilder.AnsiColors.ANSI_YELLOW.bold()))
                    .append(ColoredStringBuilder.getColored(" -> ", ColoredStringBuilder.AnsiColors.ANSI_PURPLE.bold()))
                    .append(ColoredStringBuilder.getColored(commandEntry.getValue() == null ? "" : commandEntry.getValue().getDescription(),
                            ColoredStringBuilder.AnsiColors.ANSI_CYAN.text())).append('\n');
        }
        resp.setOutputText(resultString.toString());
    }

    @Override
    public String getDescription() {
        return ABOUT_COMMAND;
    }
}
