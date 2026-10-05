package com.bottomless.pages;

import com.bottomless.utils.JavaScriptUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SoundPopoverPage extends BasePage {

    private final By soundMenu = By.id("sound-menu");
    private final By closeBtn = By.cssSelector("#sound-menu .popover-close-btn");
    private final By btnToggleMute = By.id("btn-toggle-sound-mute");
    private final By soundVolSlider = By.id("sound-vol-slider");

    private final By presetHarmonic108 = By.cssSelector("button[data-preset='harmonic108']");
    private final By presetSingingBowl = By.cssSelector("button[data-preset='singingBowl']");
    private final By presetDeepAbyss = By.cssSelector("button[data-preset='deepAbyss']");

    private final By iconMuted = By.id("sound-icon-muted");
    private final By iconPlaying = By.id("sound-icon-playing");

    public SoundPopoverPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSoundMenuOpen() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(soundMenu));
            wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(soundMenu, "class", "hidden")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getMuteButtonText() {
        return getText(btnToggleMute);
    }

    public void clickToggleMute() {
        log.info("Clicking toggle mute/unmute button (dispatched via JS to avoid dual pointerup+click firing)");
        WebElement btn = waitForClickability(btnToggleMute);
        JavaScriptUtils.click(driver, btn);
    }

    public boolean isSoundIconMutedDisplayed() {
        return isDisplayed(iconMuted) && !hasClass(iconMuted, "hidden");
    }

    public boolean isSoundIconPlayingDisplayed() {
        return isDisplayed(iconPlaying) && !hasClass(iconPlaying, "hidden");
    }

    public void selectPreset(String preset) {
        log.info("Selecting sound preset: {}", preset);
        By locator = By.cssSelector("button[data-preset='" + preset + "']");
        click(locator);
    }

    public boolean isPresetActive(String preset) {
        By locator = By.cssSelector("button[data-preset='" + preset + "']");
        try {
            wait.until(ExpectedConditions.attributeContains(locator, "class", "active"));
            return true;
        } catch (TimeoutException e) {
            return hasClass(locator, "active");
        }
    }

    public void setVolume(double value) {
        log.info("Setting sound volume slider to {}", value);
        WebElement slider = driver.findElement(soundVolSlider);
        JavaScriptUtils.setSliderValue(driver, slider, value);
    }

    public double getVolumeValue() {
        WebElement slider = driver.findElement(soundVolSlider);
        String val = slider.getAttribute("value");
        return val != null ? Double.parseDouble(val) : 0.5;
    }

    public void close() {
        log.info("Closing sound menu popover");
        click(closeBtn);
    }
}
