package ru.kpfu.itis.vaihdass.commands;

import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.defaultImplementation.DefaultResourceManager;

public interface Command {
    void execute(Props props, Resp resp, DefaultResourceManager resourceManager);
    String getDescription();
}
