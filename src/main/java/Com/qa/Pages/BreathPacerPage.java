package Com.qa.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BreathPacerPage extends BasePage {

    @FindBy(css = ".breath-pacer-widget")
    private WebElement breathWidget;

    @FindBy(css = ".breath-pacer-widget .breath-label")
    private WebElement breathLabel;

    @FindBy(css = ".breath-pacer-widget .breath-caption")
    private WebElement breathCaption;

    @FindBy(css = ".breath-pacer-widget .breath-ring-core")
    private WebElement breathCore;

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
