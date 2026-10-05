package com.bottomless.tests;

import com.bottomless.base.BaseTest;
import com.bottomless.pages.BottomDockPage;
import com.bottomless.pages.HudPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test(description = "SMK_001: Verify application title and meta description tag")
    public void testApplicationTitleAndMeta() {
        String expectedTitle = "Bottomless — An Infinite Descent";
        String actualTitle = getDriver().getTitle();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title mismatch");

        WebElement metaDescription = getDriver().findElement(By.cssSelector("meta[name='description']"));
        String content = metaDescription.getAttribute("content");
        Assert.assertNotNull(content, "Meta description should not be null");
        Assert.assertTrue(content.contains("stress-relief and mindfulness application"),
                "Meta description should describe the application purpose");
    }

    @Test(description = "SMK_002: Verify core rendering elements and UI panels")
    public void testCoreContainersPresence() {
        WebElement canvas = getDriver().findElement(By.id("particle-canvas"));
        Assert.assertTrue(canvas.isDisplayed(), "Particle canvas should be displayed");

        WebElement worldContainer = getDriver().findElement(By.id("world-container"));
        Assert.assertNotNull(worldContainer, "World container should exist in DOM");

        HudPage hudPage = new HudPage(getDriver());
        Assert.assertTrue(hudPage.isHudDisplayed(), "Top HUD panel should be visible");

        BottomDockPage dockPage = new BottomDockPage(getDriver());
        Assert.assertTrue(dockPage.isDockDisplayed(), "Bottom navigation dock should be visible");
    }
}
