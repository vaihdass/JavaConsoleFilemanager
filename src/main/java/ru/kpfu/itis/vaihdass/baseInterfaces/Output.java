package ru.kpfu.itis.vaihdass.baseInterfaces;

import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.exceptions.OutputEmptyViewException;

public interface Output {
    void setView(Resp resp) throws OutputEmptyViewException;
}
