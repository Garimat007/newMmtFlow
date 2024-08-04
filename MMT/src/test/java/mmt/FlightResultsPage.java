package mmt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class FlightResultsPage {
    WebDriver driver;
    WebDriverWait wait;

    By tripFareCalDates = By.xpath("//ul[@class='tripFareCalDates']");
    By selectedDateElement = By.xpath("//li[@class='selected']");
    By flightListings = By.xpath("(//div[@class='roundTripFlightsSection'])[1]");

    public FlightResultsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<WebElement> getTripPriceList() {
        WebElement dates = wait.until(ExpectedConditions.visibilityOfElementLocated(tripFareCalDates));
        return dates.findElements(By.cssSelector("div.tripFareCalList p.calPrice"));
    }

    public List<WebElement> getTripDateList() {
        WebElement dates = wait.until(ExpectedConditions.visibilityOfElementLocated(tripFareCalDates));
        return dates.findElements(By.cssSelector("div.tripFareCalList p.appendBottom3.boldFont.darkText.font14"));
    }

    public void selectDate() {
        wait.until(ExpectedConditions.elementToBeClickable(selectedDateElement)).click();
    }

    public boolean areFlightsAvailable() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(flightListings)).isDisplayed();
    }
}
