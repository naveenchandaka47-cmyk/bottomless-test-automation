package com.bottomless.tests;

import com.bottomless.base.BaseTest;
import com.bottomless.pages.BottomDockPage;
import com.bottomless.pages.HudPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DescentControlsTest extends BaseTest {

    @Test(description = "REG_PHY_001: Verify Zen Float Mode descent activation & velocity")
    public void testZenFloatDescent() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        HudPage hudPage = new HudPage(getDriver());

        Assert.assertFalse(dockPage.isFloatActive(), "Float mode should initially be inactive");

        dockPage.toggleFloatMode();
        Assert.assertTrue(dockPage.isFloatActive(), "Float mode button should have 'active' class");

        // Allow descent for 2.5 seconds
        dockPage.pause(2500);

        double speed = hudPage.getSpeedValue();
        Assert.assertTrue(speed > 0.5, "Velocity should be positive during float descent. Actual: " + speed);

        double depth = hudPage.getDepthValue();
        Assert.assertTrue(depth > 0, "Depth should have increased from 0. Actual: " + depth);
    }

    @Test(description = "REG_PHY_002: Verify Zen Float Mode toggle deactivation")
    public void testZenFloatDeactivation() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        HudPage hudPage = new HudPage(getDriver());

        dockPage.toggleFloatMode();
        Assert.assertTrue(dockPage.isFloatActive(), "Float mode should be active");
        dockPage.pause(1000);

        dockPage.toggleFloatMode();
        Assert.assertFalse(dockPage.isFloatActive(), "Float mode should now be inactive");

        // Wait for damping to settle speed
        dockPage.pause(2000);
        double speed = hudPage.getSpeedValue();
        Assert.assertTrue(speed < 1.0, "Speed should dampen toward 0 after deactivation. Actual: " + speed);
    }

    @Test(description = "REG_PHY_003: Verify keyboard down arrow and spacebar descent")
    public void testKeyboardDescent() {
        HudPage hudPage = new HudPage(getDriver());

        // Focus body and send DOWN and SPACE
        for (int i = 0; i < 5; i++) {
            getDriver().findElement(By.tagName("body")).sendKeys(Keys.ARROW_DOWN);
            getDriver().findElement(By.tagName("body")).sendKeys(Keys.SPACE);
            hudPage.pause(100);
        }

        hudPage.pause(1000);
        double depth = hudPage.getDepthValue();
        Assert.assertTrue(depth > 0, "Depth should increase when pressing DOWN or SPACE. Actual: " + depth);
    }

    @Test(description = "REG_PHY_004: Verify Return to Surface button appearance and action")
    public void testReturnToSurface() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        HudPage hudPage = new HudPage(getDriver());

        // At surface, return button is hidden
        Assert.assertFalse(dockPage.isReturnButtonVisible(), "Return button should be hidden at surface");

        // Descend past 20m threshold using PageDown keys
        for (int i = 0; i < 12; i++) {
            getDriver().findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
            dockPage.pause(80);
        }
        dockPage.pause(3500);

        double descendedDepth = hudPage.getDepthValue();
        log.info("Current depth reached: {}m", descendedDepth);

        // Check return button is now visible (appears when depth > 20m)
        Assert.assertTrue(dockPage.isReturnButtonVisible(),
                "Return button should be visible when depth > 20m. Reached: " + descendedDepth + "m");

        // Click return and wait for physics engine glide to bring depth back to surface
        dockPage.clickReturnToSurface();

        boolean returnedToSurface = false;
        for (int i = 0; i < 20; i++) {
            dockPage.pause(500);
            if (hudPage.getDepthValue() < 5.0) {
                returnedToSurface = true;
                break;
            }
        }

        double finalDepth = hudPage.getDepthValue();
        Assert.assertTrue(returnedToSurface,
                "Depth should glide back to surface (< 5.0m). Final depth: " + finalDepth + "m");
    }
}
