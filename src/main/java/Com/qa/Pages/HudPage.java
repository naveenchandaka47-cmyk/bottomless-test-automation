package Com.qa.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HudPage extends BasePage {

    private final By topHud = By.id("top-hud");
    private final By hudDepth = By.id("hud-depth");
    private final By hudUnitToggle = By.id("hud-unit-toggle");
    private final By hudDepthSub = By.id("hud-depth-sub");
    private final By hudZone = By.id("hud-zone");
    private final By hudSpeedVal = By.id("hud-speed-val");
    private final By btnPhilosophy = By.id("btn-philosophy");

    public HudPage(WebDriver driver) {
        super(driver);
    }

    public boolean isHudDisplayed() {
        return isDisplayed(topHud);
    }

    public String getDepthText() {
        return getText(hudDepth);
    }

    public double getDepthValue() {
        try {
            return Double.parseDouble(getDepthText());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public String getUnitText() {
        return getText(hudUnitToggle);
    }

    public void clickUnitToggle() {
        log.info("Clicking depth unit toggle button");
        click(hudUnitToggle);
    }

    public String getZoneText() {
        return getText(hudZone);
    }

    public double getSpeedValue() {
        try {
            return Double.parseDouble(getText(hudSpeedVal));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public PhilosophyModalPage openPhilosophyModal() {
        log.info("Opening Philosophy / Rules modal via HUD button");
        click(btnPhilosophy);
        return new PhilosophyModalPage(driver);
    }
}
