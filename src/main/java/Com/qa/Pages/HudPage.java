package Com.qa.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HudPage extends BasePage {

    @FindBy(id = "top-hud")
    private WebElement topHud;

    @FindBy(id = "hud-depth")
    private WebElement hudDepth;

    @FindBy(id = "hud-unit-toggle")
    private WebElement hudUnitToggle;

    @FindBy(id = "hud-depth-sub")
    private WebElement hudDepthSub;

    @FindBy(id = "hud-zone")
    private WebElement hudZone;

    @FindBy(id = "hud-speed-val")
    private WebElement hudSpeedVal;

    @FindBy(id = "btn-philosophy")
    private WebElement btnPhilosophy;

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
