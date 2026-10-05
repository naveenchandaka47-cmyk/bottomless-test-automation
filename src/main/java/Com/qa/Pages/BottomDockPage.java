package Com.qa.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BottomDockPage extends BasePage {

    private final By bottomControls = By.id("bottom-controls");
    private final By btnReturn = By.id("btn-return");
    private final By btnFloat = By.id("btn-float");
    private final By btnBreath = By.id("btn-breath");
    private final By btnSound = By.id("btn-sound");
    private final By btnTheme = By.id("btn-theme");
    private final By btnMilestones = By.id("btn-milestones");
    private final By milestoneCount = By.id("milestone-count");

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
