package com.bottomless.tests;

import com.bottomless.base.BaseTest;
import com.bottomless.pages.BottomDockPage;
import com.bottomless.pages.BreathPacerPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BreathPacerTest extends BaseTest {

    @Test(description = "REG_BRT_001: Verify Breath Pacer toggle on and off")
    public void testBreathPacerToggle() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        BreathPacerPage pacerPage = new BreathPacerPage(getDriver());

        Assert.assertFalse(pacerPage.isPacerActive(), "Breath pacer should be hidden initially");

        // Toggle on
        dockPage.toggleBreathPacer();
        dockPage.pause(400);

        Assert.assertTrue(pacerPage.isPacerActive(), "Breath pacer should be visible after clicking Breathe");
        Assert.assertTrue(pacerPage.isCoreRingPresent(), "Pacer core ring should be present");
        Assert.assertTrue(pacerPage.getCaptionText().equalsIgnoreCase("5.5s Resonant Rhythm"),
                "Caption text should state 5.5s Resonant Rhythm. Actual: " + pacerPage.getCaptionText());

        // Toggle off
        dockPage.toggleBreathPacer();
        dockPage.pause(400);

        Assert.assertFalse(pacerPage.isPacerActive(), "Breath pacer should be hidden after toggling off");
    }

    @Test(description = "REG_BRT_002: Verify keyboard shortcut 'B' for Breath Pacer")
    public void testKeyboardShortcutBreathPacer() {
        BreathPacerPage pacerPage = new BreathPacerPage(getDriver());

        Assert.assertFalse(pacerPage.isPacerActive(), "Breath pacer should be hidden initially");

        // Press 'b'
        getDriver().findElement(By.tagName("body")).sendKeys("b");
        pacerPage.pause(400);

        Assert.assertTrue(pacerPage.isPacerActive(), "Breath pacer should be active after pressing 'b'");

        // Press 'b' again
        getDriver().findElement(By.tagName("body")).sendKeys("b");
        pacerPage.pause(400);

        Assert.assertFalse(pacerPage.isPacerActive(), "Breath pacer should be hidden after pressing 'b' again");
    }
}
