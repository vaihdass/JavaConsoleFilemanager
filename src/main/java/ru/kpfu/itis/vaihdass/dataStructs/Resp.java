package ru.kpfu.itis.vaihdass.dataStructs;

public class Resp {
    private String outputText;
    private boolean requireLinebreakAfter = true;

    public Resp() {}

    public String getOutputText() {
        return outputText;
    }

    public void setOutputText(String outputText) {
        this.outputText = outputText;
    }

    public void setRequireLinebreakAfter(boolean state) {
        requireLinebreakAfter = state;
    }

    public boolean isRequireLinebreakAfter() {
        return requireLinebreakAfter;
    }
}
