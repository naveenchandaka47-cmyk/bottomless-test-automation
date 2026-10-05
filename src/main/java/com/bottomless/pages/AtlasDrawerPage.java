package com.bottomless.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AtlasDrawerPage extends BasePage {

    private final By drawer = By.id("milestone-drawer");
    private final By closeBtn = By.id("btn-close-drawer");
    private final By milestoneList = By.id("milestone-list");
    private final By drawerTitle = By.cssSelector("#milestone-drawer .drawer-title");

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
