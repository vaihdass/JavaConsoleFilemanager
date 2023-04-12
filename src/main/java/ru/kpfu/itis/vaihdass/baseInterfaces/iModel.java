package ru.kpfu.itis.vaihdass.baseInterfaces;

import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;

public interface iModel {
    void updateModel(Props props, Resp resp);
    String getDefaultView();
}
