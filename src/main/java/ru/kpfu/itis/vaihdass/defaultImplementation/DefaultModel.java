package ru.kpfu.itis.vaihdass.defaultImplementation;

import ru.kpfu.itis.vaihdass.baseInterfaces.iModel;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;

public class DefaultModel implements iModel {
    private DefaultCommandManager commandManager;
    private DefaultResourceManager resourceManager;

    public DefaultModel(DefaultCommandManager commandManager, DefaultResourceManager resourceManager) {
        if (commandManager == null || resourceManager == null)
            throw new IllegalArgumentException("Command and resource managers can't be null.");

        this.commandManager = commandManager;
        this.resourceManager = resourceManager;
    }
    @Override
    public void updateModel(Props props, Resp resp) {
        commandManager.executeCommand(props, resp, resourceManager);
    }

    @Override
    public String getDefaultView() {
        return resourceManager.getCurrentDirectoryView();
    }
}
