package ru.kpfu.itis.vaihdass.helpers;

public abstract class ColoredStringBuilder {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    public static String getColored(String text, String ansiColor) {
        return AnsiColors.reset() + (ansiColor != null ? ansiColor : "") + text + AnsiColors.reset();
    }

    public enum AnsiColors {
        ANSI_RED(RED, RED_BACKGROUND, RED_BOLD, RED_UNDERLINED),
        ANSI_BLACK(BLACK, BLACK_BACKGROUND, BLACK_BOLD, BLACK_UNDERLINED),
        ANSI_GREEN(GREEN, GREEN_BACKGROUND, GREEN_BOLD, GREEN_UNDERLINED),
        ANSI_YELLOW(YELLOW, YELLOW_BACKGROUND, YELLOW_BOLD, YELLOW_UNDERLINED),
        ANSI_BLUE(BLUE, BLUE_BACKGROUND, BLUE_BOLD, BLUE_UNDERLINED),
        ANSI_PURPLE(PURPLE, PURPLE_BACKGROUND, PURPLE_BOLD, PURPLE_UNDERLINED),
        ANSI_CYAN(CYAN, CYAN_BACKGROUND, CYAN_BOLD, CYAN_UNDERLINED),
        ANSI_WHITE(WHITE, WHITE_BACKGROUND, WHITE_BOLD, WHITE_UNDERLINED);

        private final String textColor;
        private final String backgroundColor;
        private final String boldColor;
        private final String underlinedColor;

        AnsiColors(String textColor, String backgroundColor, String boldColor, String underlinedColor) {
            this.textColor = textColor;
            this.backgroundColor = backgroundColor;
            this.boldColor = boldColor;
            this.underlinedColor = underlinedColor;
        }

        public String text() {
            return textColor;

        }

        public String background() {
            return backgroundColor;
        }

        public String bold() {
            return boldColor;
        }

        public String underlined() {
            return underlinedColor;
        }

        public static String reset() {
            return RESET;
        }
    }
}
