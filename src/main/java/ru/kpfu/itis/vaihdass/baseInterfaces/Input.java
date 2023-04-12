package ru.kpfu.itis.vaihdass.baseInterfaces;

import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultController;
import ru.kpfu.itis.vaihdass.dataStructs.Req;

public abstract class Input {
    private DefaultController controller;
    private String lastCommand;

    public Input() {
    }

    public Input(DefaultController controller) {
        setController(controller);
    }

    public void setController(DefaultController controller) {
        if (controller == null) throw new IllegalArgumentException("Controller can't be null.");
        this.controller = controller;
    }

    public void lastCommandChanged() {
        if (controller == null) throw new IllegalArgumentException("Controller can't be null.");
        controller.update(new Req(lastCommand, this));
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        if (lastCommand == null) throw new IllegalArgumentException("Command can't be null.");
        this.lastCommand = lastCommand;
    }
}
