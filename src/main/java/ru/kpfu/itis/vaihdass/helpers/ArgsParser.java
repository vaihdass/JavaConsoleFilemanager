package ru.kpfu.itis.vaihdass.helpers;

import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Req;
import ru.kpfu.itis.vaihdass.exceptions.InputEmptyCommandException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ArgsParser {
    private static final Pattern SPLIT_PATTERN = Pattern.compile("([^'\"]\\S*|[\"'].+?[\"|'])\\s*");

    public static Props parseInput(Req req) throws InputEmptyCommandException {
        List<String> inputList = new ArrayList<String>();
        Matcher matcher = SPLIT_PATTERN.matcher(req.getCommand());
        while (matcher.find()) inputList.add(matcher.group(1));
        String[] splitInput = removeEmptyStringsAndFirstDashes(inputList);

        Props props = new Props(req.getInput());
        switch (splitInput.length) {
            case 0:
                throw new InputEmptyCommandException("Input string is empty.");
            case 1:
                props.setCommand(splitInput[0]);
                break;
            default:
                props.setCommand(splitInput[0]);
                props.setArgs(Arrays.copyOfRange(splitInput, 1, splitInput.length));
        }
        return props;
    }

    protected static String removeIfFirstDash(String text) {
        if (text.length() != 0 && text.charAt(0) == '-') return (text.length() > 1 ? text.substring(1) : "");
        return text;
    }

    protected static String removeIfInBrackets(String text) {
        if (text.length() >= 2 && ((text.charAt(0) == '\"' && text.charAt(text.length() - 1) == '\"')
                || (text.charAt(0) == '\'' && text.charAt(text.length() - 1) == '\''))) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }

    protected static String[] removeEmptyStringsAndFirstDashes(List<String> inputArray) {
        return inputArray.stream().map(ArgsParser::removeIfFirstDash)
                .map(ArgsParser::removeIfInBrackets)
                .map(String::trim)
                .filter(s -> !s.equals(""))
                .toArray(String[]::new);
    }
}