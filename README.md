# BottomLess — Selenium WebDriver Regression Test Automation Framework

An enterprise-grade, thread-safe, Page Object Model (POM) test automation framework built for regression and smoke testing of the **Bottomless** web application ([https://naveenchandaka47-cmyk.github.io/bottomless/](https://naveenchandaka47-cmyk.github.io/bottomless/)).

---

## 🚀 Tech Stack & Tooling

- **Language:** Java 21 (LTS)
- **Automation Tool:** Selenium WebDriver 4.29.0
- **Test Framework:** TestNG 7.11.0
- **Design Pattern:** Page Object Model (POM) + Factory Pattern
- **Reporting Engine:** ExtentReports 5 (Dark theme, rich HTML, Base64 screenshot capture on failure)
- **Logging:** Log4j2
- **Build & Dependency Management:** Apache Maven 3.9+
- **Continuous Integration:** GitHub Actions (`regression-tests.yml`)

---

## 📋 Test Cases Specification & Sheets

Comprehensive test cases, steps, preconditions, test data, and expected results are tracked in two formats:
- **Markdown Specification:** [`TEST_CASES.md`](./TEST_CASES.md)
- **Excel / Jira / TestRail Import:** [`test-cases.csv`](./test-cases.csv)

### Covered Test Categories:
1. **Smoke & Sanity:** Page title, meta attributes, HTML5 Canvas, Parallax world containers.
2. **HUD & Telemetry:** Default surface metrics, metric $\leftrightarrow$ imperial ($\text{m} \leftrightarrow \text{ft}$) toggling, dynamic kilometer scaling.
3. **Physics & Descent:** Zen Float mode auto-descent, keyboard controls (<kbd>↓</kbd>, <kbd>Space</kbd>), Return-to-Surface glide.
4. **Theme Palettes:** Void (OLED Black) $\leftrightarrow$ Mist (Warm Daylight) $\leftrightarrow$ Tide (Oceanic Seafoam).
5. **Sound Engine:** 108Hz Pythagorean overtone drone, Singing Bowl, Deep Abyss presets, mute/unmute state synchronization, volume slider.
6. **Mindful Companions:** 5.5s Resonant Breath Pacer widget and keyboard shortcut (<kbd>B</kbd>).
7. **Rules & Philosophy:** Four Zeroes manifesto modal verification ("ZERO ADS", "ZERO PERMISSIONS", "ZERO SIGN-IN", "ZERO FAIL STATES").
8. **Milestone Atlas:** Discovered landmarks drawer, count badges, and closing behavior.

---

## 📁 Repository Structure (Eclipse / Maven Layout)

```
BottomLess-Selenium-Automation/
├── .github/
│   └── workflows/
│       └── regression-tests.yml      # CI/CD workflow running headless tests on push/PR
├── .gitignore
├── pom.xml                           # Maven dependencies & surefire plugin configuration
├── testng-regression.xml             # Full regression suite runner (21 test cases)
├── testng-smoke.xml                  # Quick smoke/sanity suite runner
├── TEST_CASES.md                     # Markdown test cases sheet
├── test-cases.csv                    # CSV test cases sheet for Excel/Jira
├── README.md                         # Documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Com.qa.Base/
│   │   │   │   ├── BaseTest.java             # Browser setup, navigation, and teardown
│   │   │   │   └── ParallelBaseTest.java     # ThreadLocal driver for parallel execution
│   │   │   ├── Com.qa.Config/
│   │   │   │   └── Config.properties         # App URL, browser, headless flag, timeouts
│   │   │   ├── Com.qa.Pages/
│   │   │   │   ├── BasePage.java             # Common explicit waits & interaction methods
│   │   │   │   ├── HudPage.java              # Depth, unit toggle, speed indicator, rules button
│   │   │   │   ├── BottomDockPage.java       # Float, Breathe, Sound, Theme, Atlas buttons
│   │   │   │   ├── ThemePopoverPage.java     # Void, Mist, Tide switching & verification
│   │   │   │   ├── SoundPopoverPage.java     # Mute/unmute, volume slider, sound presets
│   │   │   │   ├── BreathPacerPage.java      # Concentric ring breath companion
│   │   │   │   ├── PhilosophyModalPage.java  # 4 Radical Zeroes rules cards verification
│   │   │   │   └── AtlasDrawerPage.java      # Milestone discoveries and atlas drawer
│   │   │   └── Com.qa.Util/
│   │   │       └── Utils.java                # Unified utilities (Config, Waits, JS, Screenshots)
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   ├── Com.qa.TestCases/
│       │   │   ├── SmokeTest.java
│       │   │   ├── HudAndTelemetryTest.java
│       │   │   ├── DescentControlsTest.java
│       │   │   ├── ThemeSwitchingTest.java
│       │   │   ├── SoundSettingsTest.java
│       │   │   ├── BreathPacerTest.java
│       │   │   ├── PhilosophyModalTest.java
│       │   │   └── AtlasDrawerTest.java
│       │   └── utils/
│       │       ├── ExtentManager.java        # ExtentSparkReporter configuration
│       │       └── ExtentReportHelper.java   # TestNG ITestListener + screenshot capture
│       └── resources/
│           ├── config.properties
│           └── log4j2.xml
```

---

## ⚡ Quick Start & Execution

### Prerequisites
- **Java JDK 21+** (`java -version`)
- **Apache Maven 3.9+** (`mvn -version`)
- **Google Chrome / Firefox / Edge**

### Run Full Regression Suite (Headless)
```bash
mvn clean test -Dheadless=true -DsuiteXmlFile=testng-regression.xml
```

### Run Full Regression Suite (With Browser UI)
```bash
mvn clean test -Dheadless=false -DsuiteXmlFile=testng-regression.xml
```

### Run Smoke Test Suite Only
```bash
mvn clean test -Dheadless=true -DsuiteXmlFile=testng-smoke.xml
```

### Run Against Local Dev Server
```bash
mvn clean test -Dheadless=true -Durl=http://localhost:5173
```

### Run on Different Browsers
```bash
mvn clean test -Dheadless=true -Dbrowser=firefox
mvn clean test -Dheadless=true -Dbrowser=edge
```

---

## 📊 ExtentReports & Artifacts

After execution, interactive HTML reports are generated at:
```
reports/Bottomless_Regression_Report_[timestamp].html
```
- Includes execution timestamps, pass/fail status, test step details.
- Automatically captures and embeds **Base64 screenshots** on test failure.
- Execution logs are saved to `logs/automation.log`.

---

## 🔄 GitHub Actions CI/CD

The workflow in `.github/workflows/regression-tests.yml` automatically triggers on every push and pull request to the `main` branch. It executes the regression suite headlessly in an Ubuntu runner and uploads the Extent report as an artifact downloadable from GitHub Actions.
