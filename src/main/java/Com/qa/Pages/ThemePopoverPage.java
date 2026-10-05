package Com.qa.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ThemePopoverPage extends BasePage {

    private final By themeMenu = By.id("theme-menu");
    private final By closeBtn = By.cssSelector("#theme-menu .popover-close-btn");
    private final By popoverBackdrop = By.id("popover-backdrop");

    private final By btnThemeMist = By.cssSelector("button[data-theme='mist']");
    private final By btnThemeTide = By.cssSelector("button[data-theme='tide']");
    private final By btnThemeVoid = By.cssSelector("button[data-theme='void']");

    public ThemePopoverPage(WebDriver driver) {
        super(driver);
    }

    public boolean isThemeMenuOpen() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(themeMenu));
            wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(themeMenu, "class", "hidden")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isBackdropVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(popoverBackdrop));
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
