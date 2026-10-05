package com.bottomless.tests;

import com.bottomless.base.BaseTest;
import com.bottomless.pages.HudPage;
import com.bottomless.pages.PhilosophyModalPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class PhilosophyModalTest extends BaseTest {

    @Test(description = "REG_RUL_001: Verify Philosophy / Rules modal display and 4 Zeroes")
    public void testPhilosophyModalRulesContent() {
        HudPage hudPage = new HudPage(getDriver());
        PhilosophyModalPage modalPage = hudPage.openPhilosophyModal();

        Assert.assertTrue(modalPage.isModalDisplayed(), "Philosophy modal should be displayed");

        List<String> ruleTitles = modalPage.getRuleTitles();
        Assert.assertEquals(ruleTitles.size(), 4, "Should display 4 core philosophy rules");

        Assert.assertTrue(ruleTitles.contains("ZERO ADS"), "Rules must contain ZERO ADS");
        Assert.assertTrue(ruleTitles.contains("ZERO PERMISSIONS"), "Rules must contain ZERO PERMISSIONS");
        Assert.assertTrue(ruleTitles.contains("ZERO SIGN-IN"), "Rules must contain ZERO SIGN-IN");
        Assert.assertTrue(ruleTitles.contains("ZERO FAIL STATES"), "Rules must contain ZERO FAIL STATES");
    }

    @Test(description = "REG_RUL_002: Verify modal dismissal via close button and ESC key")
    public void testPhilosophyModalDismissal() {
        HudPage hudPage = new HudPage(getDriver());
        PhilosophyModalPage modalPage = hudPage.openPhilosophyModal();

        Assert.assertTrue(modalPage.isModalDisplayed(), "Modal should be visible");

        // Close via button
        modalPage.closeModal();
        Assert.assertTrue(modalPage.isModalDismissed(), "Modal should be dismissed after clicking close button");

        // Open again and close via ESC key
        modalPage = hudPage.openPhilosophyModal();
        Assert.assertTrue(modalPage.isModalDisplayed(), "Modal should re-open");

        modalPage.closeModalViaEscape();
        Assert.assertTrue(modalPage.isModalDismissed(), "Modal should be dismissed after pressing ESC key");
    }
}
