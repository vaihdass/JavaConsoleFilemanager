package ru.kpfu.itis.vaihdass.commands.implementations;

import org.apache.commons.io.FileUtils;
import ru.kpfu.itis.vaihdass.commands.Command;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AboutDirectoryCommand implements Command {
    private final String ABOUT_COMMAND = "Displays a list of directories ath and files by the specified p(by default in the current directory).\n" +
            "<Path>? can be set relative to the current directory OR absolute.";
    private final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - hh:mm");
    private final int FILESIZE_KB = 1024;
    private final int FILESIZE_MB = FILESIZE_KB * 1024;
    private final int FILESIZE_GB = FILESIZE_MB * 1024;
    private final String COLUMNS_DIVIDER = "  ";

    @Override
    public void execute(Props props, Resp resp, DefaultResourceManager resourceManager) {
        if (props == null) {
            resp.setOutputText(ColoredStringBuilder.getColored(
                    "Unexpected internal error.",
                    ColoredStringBuilder.AnsiColors.ANSI_RED.bold()));
            return;
        }

        Path directory = (props.getArgs() != null
                    && props.getArgs().length != 0
                    && props.getArgs()[0] != null
                    && resourceManager.validateDirPathGivenCurrent(props.getArgs()[0]))
                ? resourceManager.getCurrentDirectory().resolve(Paths.get(props.getArgs()[0]).normalize())
                : resourceManager.getCurrentDirectory();

        List<PathInfo> pathInfoList = Arrays.stream(Objects.requireNonNull(directory.toFile().listFiles()))
                .map(file -> {
                    try {
                        return new PathInfo(file.getName(), file.isDirectory(), getFormattedModifiedTime(file),
                                getFormattedSize(getNormalSize(file)), file.canRead(), file.canWrite(), file.canExecute());
                    } catch (IOException e) {
                        resp.setOutputText(ColoredStringBuilder.getColored(
                                "Unexpected internal error while working with the file properties of the specified directory.",
                                ColoredStringBuilder.AnsiColors.ANSI_RED.bold()));
                    }
                    return null;
                })
                .collect(Collectors.toList());

        if (!resp.getOutputText().equals("")) return;

        // Got max values length to format columns
        int maxLengthFilename = pathInfoList.stream().map(pathInfo -> pathInfo.getName().length()).max(Comparator.naturalOrder()).orElse(0);
        int maxLengthSize = pathInfoList.stream().map(pathInfo -> pathInfo.getSize().length()).max(Comparator.naturalOrder()).orElse(0);

        StringBuilder resultString = new StringBuilder(ColoredStringBuilder.getColored("\nMODE:"
                        + COLUMNS_DIVIDER
                        + fillRightToLengthWithSpace("NAME:", maxLengthFilename)
                        + COLUMNS_DIVIDER + COLUMNS_DIVIDER
                        + fillRightToLengthWithSpace("SIZE:", maxLengthSize)
                        + COLUMNS_DIVIDER + COLUMNS_DIVIDER
                        + fillRightToLengthWithSpace("LAST MODIFIED:", "dd.MM.yyyy - hh:mm:ss".length())
                        + '\n',
                ColoredStringBuilder.AnsiColors.ANSI_YELLOW.bold()));

        if (pathInfoList.size() == 0) {
            resultString.append(ColoredStringBuilder.getColored("Nothing...\n",
                    ColoredStringBuilder.AnsiColors.ANSI_PURPLE.text()));
        }
        for (PathInfo pathInfo : pathInfoList) {
            resultString
                    .append(pathInfo.getMode())
                            .append(COLUMNS_DIVIDER).append(COLUMNS_DIVIDER)
                    .append(coloredName(pathInfo, fillRightToLengthWithSpace(pathInfo.getName(), Math.max(maxLengthFilename, "NAME:".length()))))
                            .append(COLUMNS_DIVIDER).append(COLUMNS_DIVIDER)
                    .append(fillRightToLengthWithSpace(pathInfo.getSize(), Math.max(maxLengthSize, "SIZE:".length())))
                            .append(COLUMNS_DIVIDER).append(COLUMNS_DIVIDER)
                    .append(pathInfo.getLastWriteTime())
                    .append('\n');
        }

        resp.setOutputText(String.valueOf(resultString));
    }

    @Override
    public String getDescription() {
        return ABOUT_COMMAND;
    }

    private String coloredName(PathInfo pathInfo, String name) {
        if (!pathInfo.isDirectory()) return name;
        return ColoredStringBuilder.getColored(name, ColoredStringBuilder.AnsiColors.ANSI_BLUE.bold());
    }

    private String fillRightToLengthWithSpace(String text, int length) {
        if (length <= text.length()) return text;

        StringBuilder textBuilder = new StringBuilder(text);
        while (textBuilder.length() < length) {
            textBuilder.append(" ");
        }

        return textBuilder.toString();
    }

    private String getFormattedModifiedTime(File file) throws IOException {
        return dateFormat.format(Files.getLastModifiedTime(file.toPath()).toMillis());
    }

    private long getNormalSize(File file) {
        if (!file.isDirectory()) return FileUtils.sizeOf(file);
        return 0;
    }

    private String getFormattedSize(long size) {
        if (size < FILESIZE_KB) return size + "B";
        if (size < FILESIZE_MB) return String.format("%.3f", ((double) size) / FILESIZE_KB) + "KB";
        if (size < FILESIZE_GB) return String.format("%.3f", ((double) size) / FILESIZE_MB) + "MB";
        return String.format("%.3f", ((double) size) / FILESIZE_GB) + "GB";
    }

    private class PathInfo {
        private String lastWriteTime;
        private String size;
        private String name;
        private boolean readable;
        private boolean writable;
        private boolean executable;
        private boolean isDirectory;

        public PathInfo(String name, boolean isDirectory, String lastWriteTime, String size,
                        boolean readable, boolean writable, boolean executable) {
            this.lastWriteTime = lastWriteTime;
            this.size = size;
            this.name = name;
            this.isDirectory = isDirectory;
            this.readable = readable;
            this.writable = writable;
            this.executable = executable;
        }

        public String getSize() {
            return size;
        }

        public boolean isDirectory() {
            return isDirectory;
        }

        public String getLastWriteTime() {
            return lastWriteTime;
        }

        public String getName() {
            return name;
        }

        public String getMode() {
            return (readable ? "r" : "-") +
                    (writable ? 'w' : '-') +
                    (executable ? 'x' : '-');
        }
    }
}
