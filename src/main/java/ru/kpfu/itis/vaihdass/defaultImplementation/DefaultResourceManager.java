package ru.kpfu.itis.vaihdass.defaultImplementation;

import ru.kpfu.itis.vaihdass.baseInterfaces.iResourceManager;
import ru.kpfu.itis.vaihdass.exceptions.IncorrectDirectoryException;
import ru.kpfu.itis.vaihdass.helpers.ColoredStringBuilder;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultResourceManager implements iResourceManager {
    private String userName;
    private Path homeDirectory;
    private Path currentDirectory; // Current absolute path

    public DefaultResourceManager(String currentDirectory, String userName) throws IncorrectDirectoryException {
        setCurrentDirectory(currentDirectory);
        homeDirectory = getCurrentDirectory();
        if (userName == null) throw new IllegalArgumentException("User's name can't be null.");
        this.userName = userName;
    }

    public String getCurrentDirectoryView() {
        String resultString = ColoredStringBuilder.getColored(userName + ": ",
                ColoredStringBuilder.AnsiColors.ANSI_GREEN.text());
        if (currentDirectory.startsWith(homeDirectory)) {
            resultString += ColoredStringBuilder.getColored("~>" + homeDirectory.relativize(currentDirectory).toString() + "$ ",
                    ColoredStringBuilder.AnsiColors.ANSI_PURPLE.text());
        } else {
            resultString += ColoredStringBuilder.getColored(currentDirectory.toString() + "$ ", ColoredStringBuilder.AnsiColors.ANSI_YELLOW.text());
        }
        return resultString;
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    public void setCurrentDirectory(String newCurrentDirectory) throws IncorrectDirectoryException {
        if (newCurrentDirectory == null) throw new IllegalArgumentException("Directory can't be null.");
        if (!validateDirPathGivenCurrent(newCurrentDirectory)) throw new IncorrectDirectoryException("Directory has an incorrect format.");

        Path newCurrentDirectoryPath = Paths.get(newCurrentDirectory).normalize();
        if (currentDirectory == null) {
            currentDirectory = newCurrentDirectoryPath;
        } else {
            currentDirectory = currentDirectory.resolve(newCurrentDirectoryPath);
        }
    }

    public boolean validateDirPathGivenCurrent(String directory) {
        return validateAnyPathGivenCurrent(directory) &&
                (currentDirectory == null
                        ? Paths.get(directory).normalize().toFile().isDirectory()
                        : currentDirectory.resolve(Paths.get(directory).normalize()).toFile().isDirectory());
    }

    public boolean validateFilePathGivenCurrent(String filePath) {
        return validateAnyPathGivenCurrent(filePath)
                && currentDirectory != null
                && currentDirectory.resolve(Paths.get(filePath).normalize()).toFile().isFile();
    }

    public boolean validateAnyPathGivenCurrent(String directory) {
        Path tempPath;
        try {
            tempPath = Paths.get(directory).normalize();
        } catch (InvalidPathException e) {
            return false;
        }
        if (!tempPath.isAbsolute()) return (currentDirectory != null
                && currentDirectory.resolve(tempPath).toFile().exists());
        return tempPath.toFile().exists();
    }

    public boolean checkFormatAnyPathGivenCurrent(String directory) {
        Path tempPath;
        try {
            tempPath = Paths.get(directory).normalize();
        } catch (InvalidPathException e) {
            return false;
        }
        return true;
    }

    public String getUserName() {
        return userName;
    }

    public Path getHomeDirectory() {
        return homeDirectory;
    }
}
