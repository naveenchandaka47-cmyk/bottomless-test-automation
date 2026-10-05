package com.bottomless.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static void unload() {
        driverThreadLocal.remove();
    }
}
