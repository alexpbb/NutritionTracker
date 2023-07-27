// ThemeManager.java
public class ThemeManager {
    public enum ThemeMode {
        LIGHT, DARK
    }

    private static ThemeMode currentMode = ThemeMode.LIGHT;

    public static ThemeMode getCurrentMode() {
        return currentMode;
    }

    public static void toggleMode() {
        currentMode = (currentMode == ThemeMode.LIGHT) ? ThemeMode.DARK : ThemeMode.LIGHT;
    }
}
