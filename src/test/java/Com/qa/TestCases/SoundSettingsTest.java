package Com.qa.TestCases;

import Com.qa.Base.BaseTest;
import Com.qa.Pages.BottomDockPage;
import Com.qa.Pages.SoundPopoverPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SoundSettingsTest extends BaseTest {

    @Test(description = "REG_SND_001: Verify Sound menu popover open and initial unmuted state after trigger")
    public void testSoundMenuOpenAndInitialMuteState() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        SoundPopoverPage soundPage = dockPage.openSoundMenu();

        Assert.assertTrue(soundPage.isSoundMenuOpen(), "Sound menu should be open");
        // Clicking dock sound button auto-unmutes audio and opens popover
        Assert.assertEquals(soundPage.getMuteButtonText(), "Mute Audio",
                "Triggering sound opens popover and initiates audio, showing 'Mute Audio'");
        Assert.assertTrue(soundPage.isSoundIconPlayingDisplayed(),
                "Bottom dock should show playing audio wave icon");
    }

    @Test(description = "REG_SND_002: Verify audio mute and unmute toggling")
    public void testAudioUnmuteAndMuteToggle() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        SoundPopoverPage soundPage = dockPage.openSoundMenu();

        // Mute audio
        soundPage.clickToggleMute();
        soundPage.pause(400);

        Assert.assertEquals(soundPage.getMuteButtonText(), "Unmute Audio",
                "Button text should update to 'Unmute Audio'");
        Assert.assertTrue(soundPage.isSoundIconMutedDisplayed(),
                "Bottom dock should display muted speaker icon");

        // Unmute audio
        soundPage.clickToggleMute();
        soundPage.pause(400);

        Assert.assertEquals(soundPage.getMuteButtonText(), "Mute Audio",
                "Button text should revert to 'Mute Audio'");
        Assert.assertTrue(soundPage.isSoundIconPlayingDisplayed(),
                "Bottom dock should show playing audio icon");
    }

    @Test(description = "REG_SND_003: Verify sound presets switching")
    public void testSoundPresetsSwitching() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        SoundPopoverPage soundPage = dockPage.openSoundMenu();

        Assert.assertTrue(soundPage.isPresetActive("harmonic108"),
                "Harmonic 108Hz should be the active preset initially");

        // Select Singing Bowl
        soundPage.selectPreset("singingBowl");
        soundPage.pause(200);
        Assert.assertTrue(soundPage.isPresetActive("singingBowl"),
                "Singing Bowl preset should be marked active");
        Assert.assertFalse(soundPage.isPresetActive("harmonic108"),
                "Harmonic 108Hz preset should no longer be active");

        // Select Deep Abyss
        soundPage.selectPreset("deepAbyss");
        soundPage.pause(200);
        Assert.assertTrue(soundPage.isPresetActive("deepAbyss"),
                "Deep Abyss preset should be marked active");
    }

    @Test(description = "REG_SND_004: Verify volume slider adjustment")
    public void testVolumeSliderAdjustment() {
        BottomDockPage dockPage = new BottomDockPage(getDriver());
        SoundPopoverPage soundPage = dockPage.openSoundMenu();

        soundPage.setVolume(0.85);
        soundPage.pause(200);

        double actualVolume = soundPage.getVolumeValue();
        Assert.assertEquals(actualVolume, 0.85, 0.05, "Volume slider value should be updated to 0.85");
    }
}
