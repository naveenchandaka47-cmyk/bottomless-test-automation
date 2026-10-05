package Com.qa.TestCases;

import Com.qa.Base.BaseTest;
import Com.qa.Pages.AtlasDrawerPage;
import Com.qa.Pages.BottomDockPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AtlasDrawerTest extends BaseTest {

    @Test(description = "REG_ATL_001: Verify Milestone Atlas drawer open, close, and counter")
    public void testAtlasDrawerInteraction() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());

        String initialCount = dockPage.getMilestoneCountText();
        Assert.assertTrue(initialCount.contains("0") || initialCount.contains("("),
                "Milestone counter should display counter format (e.g. (0))");

        AtlasDrawerPage atlasPage = dockPage.openAtlasDrawer();
        atlasPage.pause(300);

        Assert.assertTrue(atlasPage.isDrawerOpen(), "Atlas drawer should be open");
        Assert.assertEquals(atlasPage.getDrawerTitleText(), "Depth Atlas",
                "Drawer title should read 'Depth Atlas'");

        atlasPage.closeDrawer();
        atlasPage.pause(300);

        Assert.assertFalse(atlasPage.isDrawerOpen(), "Atlas drawer should be closed");
    }
}
