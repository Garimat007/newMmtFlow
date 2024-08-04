package mmt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FlightSearchPage {
    WebDriver driver;
    WebDriverWait wait;

    By december2024Option = By.xpath("//span[text()='December, 2024']");
    By tripDurationSlider = By.xpath("(//div[@class='rangeslider__handle'])[1]");
    By applyButton = By.xpath("//button[normalize-space()='Apply']");
    By searchButton = By.xpath("//button[text()='Search']");

    public FlightSearchPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectDecember2024() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(december2024Option)).click();
    }

    public void adjustTripDuration() {
        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(tripDurationSlider));
        new Actions(driver).dragAndDropBy(slider, 111, 0).perform();
    }

    public void applyDates() {
        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
    }

    public void initiateSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }
}
