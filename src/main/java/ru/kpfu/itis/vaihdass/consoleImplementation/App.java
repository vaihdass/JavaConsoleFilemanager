package ru.kpfu.itis.vaihdass.consoleImplementation;

import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultController;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultModel;
import ru.kpfu.itis.vaihdass.baseInterfaces.iApp;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultCommandManager;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;
import ru.kpfu.itis.vaihdass.commands.implementations.*;

public class App implements iApp {
    private ConsoleInputOutput consoleInputOutput;

    public static void main(String[] args) {
        App app = new App();
        app.init();
        app.start();
    }

    @Override
    public void init() {
        consoleInputOutput = new ConsoleInputOutput(System.in);
        DefaultCommandManager commandManager = new DefaultCommandManager();

        /* Configure commands */
        commandManager.setCommand("help", new HelpCommand());
        commandManager.setCommand("cd", new ChangeDirectoryCommand());
        commandManager.setCommand("cat", new ReadFileCommand());
        commandManager.setCommand("ls", new AboutDirectoryCommand());
        commandManager.setCommand("cp", new CopyFileCommand());
        commandManager.setCommand("rm", new RemovePathCommand());
        commandManager.setCommand("exit", new ExitCommand());

        DefaultResourceManager resourceManager = new DefaultResourceManager(System.getProperty("user.home"), System.getProperty("user.name"));
        DefaultModel model = new DefaultModel(commandManager, resourceManager);
        consoleInputOutput.setController(new DefaultController(consoleInputOutput, consoleInputOutput, model));
    }

    @Override
    public void start() {
        consoleInputOutput.requestCommands();
    }
}
