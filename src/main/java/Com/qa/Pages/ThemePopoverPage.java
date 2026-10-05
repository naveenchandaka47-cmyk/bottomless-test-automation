package Com.qa.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ThemePopoverPage extends BasePage {

    @FindBy(id = "theme-menu")
    private WebElement themeMenu;

    @FindBy(css = "#theme-menu .popover-close-btn")
    private WebElement closeBtn;

    @FindBy(id = "popover-backdrop")
    private WebElement popoverBackdrop;

    @FindBy(css = "button[data-theme='mist']")
    private WebElement btnThemeMist;

    @FindBy(css = "button[data-theme='tide']")
    private WebElement btnThemeTide;

    @FindBy(css = "button[data-theme='void']")
    private WebElement btnThemeVoid;

    public ThemePopoverPage(WebDriver driver) {
        super(driver);
    }

    public boolean isThemeMenuOpen() {
        try {
            wait.until(ExpectedConditions.visibilityOf(themeMenu));
            return !hasClass(themeMenu, "hidden");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isBackdropVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(popoverBackdrop));
            return !hasClass(popoverBackdrop, "hidden");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void selectMistTheme() {
        log.info("Selecting Mist theme");
        click(btnThemeMist);
    }

    public void selectTideTheme() {
        log.info("Selecting Tide theme");
        click(btnThemeTide);
    }

    public void selectVoidTheme() {
        log.info("Selecting Void theme");
        click(btnThemeVoid);
    }

    public boolean isThemeActive(String theme) {
        By themeSelector = By.cssSelector("button[data-theme='" + theme.toLowerCase() + "']");
        try {
            wait.until(ExpectedConditions.attributeContains(themeSelector, "class", "active"));
            return true;
        } catch (TimeoutException e) {
            return hasClass(themeSelector, "active");
        }
    }

    public void closeViaButton() {
        log.info("Closing theme popover via close button");
        click(closeBtn);
    }

    public void closeViaBackdrop() {
        log.info("Closing theme popover via backdrop tap");
        click(popoverBackdrop);
    }
}
