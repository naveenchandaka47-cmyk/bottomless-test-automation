package Com.qa.TestCases;

import Com.qa.Base.BaseTest;
import Com.qa.Pages.HudPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HudAndTelemetryTest extends BaseTest {

    @Test(description = "REG_HUD_001: Verify default telemetry values on initial load")
    public void testDefaultTelemetryValues() {
        HudPage hudPage = new HudPage(getDriver());

        Assert.assertEquals(hudPage.getDepthText(), "0", "Initial depth should be 0");
        Assert.assertEquals(hudPage.getUnitText(), "m", "Initial depth unit should be 'm'");
        Assert.assertTrue(hudPage.getZoneText().contains("The Surface"),
                "Initial zone should be 'The Surface (0 m)'");
        Assert.assertEquals(hudPage.getSpeedValue(), 0.0, 0.1, "Initial speed should be 0.0 m/s");
    }

    @Test(description = "REG_HUD_002: Verify depth unit toggle between Metric and Imperial")
    public void testDepthUnitToggle() {
        HudPage hudPage = new HudPage(getDriver());

        Assert.assertEquals(hudPage.getUnitText(), "m", "Default unit should be 'm'");

        hudPage.clickUnitToggle();
        Assert.assertEquals(hudPage.getUnitText(), "ft", "Unit should toggle to 'ft'");

        hudPage.clickUnitToggle();
        Assert.assertEquals(hudPage.getUnitText(), "m", "Unit should toggle back to 'm'");
    }
}
