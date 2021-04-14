package com.example.facturacioncarpintero.Utils;

public class ButtonDelayUtils {

    // =======================================
    // Constants
    // =======================================
    private static long lastClickTime = 0;
    // =======================================
    // Fields
    // =======================================

    // =======================================
    // Constructors
    // =======================================

    // =======================================
    // Setters/Getters
    // =======================================

    // =======================================
    // Methods from SuperClass/Interfaces
    // =======================================

    // =======================================
    // Methods
    // =======================================
    public static boolean isFastDoubleClick()
    {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    // =======================================
    // Inner Classes/Interfaces
    // =======================================
}