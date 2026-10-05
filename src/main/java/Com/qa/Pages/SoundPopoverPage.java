package Com.qa.Pages;

import Com.qa.Util.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SoundPopoverPage extends BasePage {

    @FindBy(id = "sound-menu")
    private WebElement soundMenu;

    @FindBy(css = "#sound-menu .popover-close-btn")
    private WebElement closeBtn;

    @FindBy(id = "btn-toggle-sound-mute")
    private WebElement btnToggleMute;

    @FindBy(id = "sound-vol-slider")
    private WebElement soundVolSlider;

    @FindBy(css = "button[data-preset='harmonic108']")
    private WebElement presetHarmonic108;

    @FindBy(css = "button[data-preset='singingBowl']")
    private WebElement presetSingingBowl;

    @FindBy(css = "button[data-preset='deepAbyss']")
    private WebElement presetDeepAbyss;

    @FindBy(id = "sound-icon-muted")
    private WebElement iconMuted;

    @FindBy(id = "sound-icon-playing")
    private WebElement iconPlaying;

    public SoundPopoverPage(WebDriver driver) {
        super(driver);
    }

    public boolean isSoundMenuOpen() {
        try {
            wait.until(ExpectedConditions.visibilityOf(soundMenu));
            return !hasClass(soundMenu, "hidden");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getMuteButtonText() {
        return getText(btnToggleMute);
    }

    public void clickToggleMute() {
        log.info("Clicking toggle mute button via JS dispatch to prevent dual-firing");
        WebElement btn = waitForClickability(btnToggleMute);
        Utils.clickViaJS(driver, btn);
    }

    public boolean isSoundIconMutedDisplayed() {
        return isDisplayed(iconMuted) && !hasClass(iconMuted, "hidden");
    }

    public boolean isSoundIconPlayingDisplayed() {
        return isDisplayed(iconPlaying) && !hasClass(iconPlaying, "hidden");
    }

    public void selectPreset(String preset) {
        log.info("Selecting sound preset: {}", preset);
        switch (preset) {
            case "harmonic108":
                click(presetHarmonic108);
                break;
            case "singingBowl":
                click(presetSingingBowl);
                break;
            case "deepAbyss":
                click(presetDeepAbyss);
                break;
            default:
                click(By.cssSelector("button[data-preset='" + preset + "']"));
                break;
        }
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
        log.info("Setting volume slider to {}", value);
        Utils.setSliderValue(driver, soundVolSlider, value);
    }

    public double getVolumeValue() {
        String val = soundVolSlider.getAttribute("value");
        return val != null ? Double.parseDouble(val) : 0.5;
    }

    public void close() {
        log.info("Closing sound menu popover");
        click(closeBtn);
    }
}
