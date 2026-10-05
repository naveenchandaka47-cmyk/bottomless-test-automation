package com.bottomless.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public final class JavaScriptUtils {

    private JavaScriptUtils() {
    }

    public static Object execute(WebDriver driver, String script, Object... args) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script, args);
    }

    public static void click(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void simulateWheelScroll(WebDriver driver, int deltaY) {
        String script = "window.dispatchEvent(new WheelEvent('wheel', { deltaY: arguments[0] }));";
        ((JavascriptExecutor) driver).executeScript(script, deltaY);
    }

    public static void setSliderValue(WebDriver driver, WebElement slider, double value) {
        String script = "arguments[0].value = arguments[1]; " +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true })); " +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));";
        ((JavascriptExecutor) driver).executeScript(script, slider, String.valueOf(value));
    }
}
