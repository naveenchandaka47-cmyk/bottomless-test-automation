package Com.qa.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BottomDockPage extends BasePage {

    @FindBy(id = "bottom-controls")
    private WebElement bottomControls;

    @FindBy(id = "btn-return")
    private WebElement btnReturn;

    @FindBy(id = "btn-float")
    private WebElement btnFloat;

    @FindBy(id = "btn-breath")
    private WebElement btnBreath;

    @FindBy(id = "btn-sound")
    private WebElement btnSound;

    @FindBy(id = "btn-theme")
    private WebElement btnTheme;

    @FindBy(id = "btn-milestones")
    private WebElement btnMilestones;

    @FindBy(id = "milestone-count")
    private WebElement milestoneCount;

    public BottomDockPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDockDisplayed() {
        return isDisplayed(bottomControls);
    }

    public void toggleFloatMode() {
        log.info("Toggling Zen Float mode");
        click(btnFloat);
    }

    public boolean isFloatActive() {
        return hasClass(btnFloat, "active");
    }

    public void toggleBreathPacer() {
        log.info("Toggling Breath Companion");
        click(btnBreath);
    }

    public SoundPopoverPage openSoundMenu() {
        log.info("Opening Sound Controls menu");
        click(btnSound);
        return new SoundPopoverPage(driver);
    }

    public ThemePopoverPage openThemeMenu() {
        log.info("Opening Theme Palette menu");
        click(btnTheme);
        return new ThemePopoverPage(driver);
    }

    public AtlasDrawerPage openAtlasDrawer() {
        log.info("Opening Milestone Atlas drawer");
        click(btnMilestones);
        return new AtlasDrawerPage(driver);
    }

    public String getMilestoneCountText() {
        return getText(milestoneCount);
    }

    public boolean isReturnButtonVisible() {
        return isDisplayed(btnReturn) && !hasClass(btnReturn, "hidden");
    }

    public void clickReturnToSurface() {
        log.info("Clicking Return to Surface button");
        click(btnReturn);
    }
}
