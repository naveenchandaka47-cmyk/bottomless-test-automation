package Com.qa.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class PhilosophyModalPage extends BasePage {

    @FindBy(id = "philosophy-modal")
    private WebElement modal;

    @FindBy(id = "btn-close-philosophy")
    private WebElement closeBtn;

    @FindBy(css = "#philosophy-modal .rule-item h3")
    private List<WebElement> ruleHeadings;

    public PhilosophyModalPage(WebDriver driver) {
        super(driver);
    }

    public boolean isModalDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(modal));
            return !hasClass(modal, "hidden");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isModalDismissed() {
        try {
            wait.until(ExpectedConditions.attributeContains(modal, "class", "hidden"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<String> getRuleTitles() {
        List<String> titles = new ArrayList<>();
        for (WebElement el : ruleHeadings) {
            titles.add(el.getText().trim());
        }
        return titles;
    }

    public void closeModal() {
        log.info("Closing Philosophy modal via close button");
        click(closeBtn);
    }

    public void closeModalViaEscape() {
        log.info("Closing Philosophy modal via ESC key");
        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
    }
}
