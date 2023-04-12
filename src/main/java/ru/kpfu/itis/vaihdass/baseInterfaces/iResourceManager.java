package ru.kpfu.itis.vaihdass.baseInterfaces;

import ru.kpfu.itis.vaihdass.exceptions.IncorrectDirectoryException;

import java.nio.file.Path;

public interface iResourceManager {
    String getCurrentDirectoryView();

    Path getCurrentDirectory();

    void setCurrentDirectory(String newCurrentDirectory) throws IncorrectDirectoryException;

    boolean validateDirPathGivenCurrent(String directory);

    boolean validateFilePathGivenCurrent(String filePath);

    boolean validateAnyPathGivenCurrent(String directory);

    boolean checkFormatAnyPathGivenCurrent(String directory);

    String getUserName();

    Path getHomeDirectory();
}
