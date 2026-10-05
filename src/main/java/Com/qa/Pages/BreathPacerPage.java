package Com.qa.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BreathPacerPage extends BasePage {

    private final By breathWidget = By.cssSelector(".breath-pacer-widget");
    private final By breathLabel = By.cssSelector(".breath-pacer-widget .breath-label");
    private final By breathCaption = By.cssSelector(".breath-pacer-widget .breath-caption");
    private final By breathCore = By.cssSelector(".breath-pacer-widget .breath-ring-core");

    public BreathPacerPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPacerActive() {
        return isDisplayed(breathWidget) && !hasClass(breathWidget, "hidden");
    }

    public String getBreathLabelText() {
        return getText(breathLabel);
    }

    public String getCaptionText() {
        return getText(breathCaption);
    }

    public boolean isCoreRingPresent() {
        return isDisplayed(breathCore);
    }
}
