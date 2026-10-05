package com.bottomless.tests;

import com.bottomless.base.BaseTest;
import com.bottomless.pages.BottomDockPage;
import com.bottomless.pages.ThemePopoverPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ThemeSwitchingTest extends BaseTest {

    @Test(description = "REG_THM_001: Verify theme popover open and backdrop dismiss")
    public void testThemePopoverOpenAndDismiss() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        ThemePopoverPage themePage = dockPage.openThemeMenu();

        Assert.assertTrue(themePage.isThemeMenuOpen(), "Theme menu should be visible after clicking theme button");
        Assert.assertTrue(themePage.isBackdropVisible(), "Popover backdrop should be visible");

        themePage.closeViaBackdrop();
        themePage.pause(400);

        Assert.assertFalse(themePage.isThemeMenuOpen(), "Theme menu should be closed after backdrop click");
    }

    @Test(description = "REG_THM_002: Verify switching to Mist theme")
    public void testSwitchToMistTheme() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        ThemePopoverPage themePage = dockPage.openThemeMenu();

        themePage.selectMistTheme();
        themePage.pause(400);

        Assert.assertTrue(themePage.isThemeActive("mist"), "Mist theme button should have active class");
        String bodyBg = getDriver().findElement(By.tagName("body")).getCssValue("background-color");
        Assert.assertNotNull(bodyBg, "Body background color should be set");
    }

    @Test(description = "REG_THM_003: Verify switching to Tide theme")
    public void testSwitchToTideTheme() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        ThemePopoverPage themePage = dockPage.openThemeMenu();

        themePage.selectTideTheme();
        themePage.pause(400);

        Assert.assertTrue(themePage.isThemeActive("tide"), "Tide theme button should have active class");
    }

    @Test(description = "REG_THM_004: Verify switching back to Void theme")
    public void testSwitchToVoidTheme() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        ThemePopoverPage themePage = dockPage.openThemeMenu();

        // Switch to Mist first (this auto-closes popover on selection)
        themePage.selectMistTheme();
        themePage.pause(300);

        // Re-open theme menu to select Void
        themePage = dockPage.openThemeMenu();
        themePage.selectVoidTheme();
        themePage.pause(300);

        Assert.assertTrue(themePage.isThemeActive("void"), "Void theme button should have active class");
    }
}
