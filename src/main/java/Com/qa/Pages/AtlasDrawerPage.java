package Com.qa.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AtlasDrawerPage extends BasePage {

    @FindBy(id = "milestone-drawer")
    private WebElement drawer;

    @FindBy(id = "btn-close-drawer")
    private WebElement closeBtn;

    @FindBy(id = "milestone-list")
    private WebElement milestoneList;

    @FindBy(css = "#milestone-drawer .drawer-title")
    private WebElement drawerTitle;

    public AtlasDrawerPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDrawerOpen() {
        return isDisplayed(drawer) && !hasClass(drawer, "hidden");
    }

    public String getDrawerTitleText() {
        return getText(drawerTitle);
    }

    public void closeDrawer() {
        log.info("Closing Atlas drawer");
        click(closeBtn);
    }
}
