package ru.kpfu.itis.vaihdass.defaultImplementation;

import ru.kpfu.itis.vaihdass.baseInterfaces.Input;
import ru.kpfu.itis.vaihdass.baseInterfaces.Output;
import ru.kpfu.itis.vaihdass.baseInterfaces.iController;
import ru.kpfu.itis.vaihdass.dataStructs.Props;
import ru.kpfu.itis.vaihdass.dataStructs.Req;
import ru.kpfu.itis.vaihdass.dataStructs.Resp;
import ru.kpfu.itis.vaihdass.exceptions.InputEmptyCommandException;
import ru.kpfu.itis.vaihdass.exceptions.OutputEmptyViewException;
import ru.kpfu.itis.vaihdass.helpers.ArgsParser;

public class DefaultController implements iController {
    private Input input;
    private Output output;
    private DefaultModel model;

    public DefaultController(Input input, Output output, DefaultModel model) {
        if (input == null || output == null || model == null)
            throw new IllegalArgumentException("Input, output and model can't be null.");

        this.input = input;
        this.output = output;
        this.model = model;
    }

    @Override
    public void update(Req req) {
        if (req == null) return;
        Props props = null;
        try {
            props = ArgsParser.parseInput(req);
        } catch (InputEmptyCommandException e) {
            Resp resp = new Resp();
            setDefaultView(resp);
            return;
        }

        Resp resp = new Resp();
        resp.setOutputText("");
        model.updateModel(props, resp);
        try {
            output.setView(resp);
        } catch (OutputEmptyViewException ignored) {}

        setDefaultView(resp);
    }

    private void setDefaultView(Resp resp) {
        resp.setOutputText(model.getDefaultView());
        resp.setRequireLinebreakAfter(false);
        output.setView(resp);
    }
}
