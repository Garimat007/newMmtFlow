package mmt;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    By closeModalButton = By.className("commonModal__close");
    By fromCityInput = By.id("fromCity");
    By fromCityPlaceholder = By.xpath("//input[@placeholder='From']");
    By bangaloreOption = By.xpath("//span[text()='Bengaluru']");
    By toCityInput = By.id("toCity");
    By intlFlightOption = By.xpath("//*[text()='Planning an international holiday?']");
    By oneWayOption = By.xpath("//*[text()='One Way']");
    By roundTripOption = By.xpath("//*[text()='Round Trip']");
    By toCityPlaceholder = By.xpath("(//span[text()='ANYWHERE'])[1]");
    By enterCityInput = By.xpath("(//input[@placeholder='Enter City'])[2]");
    By dubaiOption = By.xpath("(//span[text()='Dubai, United Arab Emirates'])[1]");
    By datesDurationOption = By.xpath("//span[text()='Anytime , 7 Days']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void closePromotionModal() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//section[@class='modalMain tcnFooter'])[1]")));
        wait.until(ExpectedConditions.elementToBeClickable(closeModalButton)).click();
    }

    public void selectFromCity() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromCityInput)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(fromCityPlaceholder)).click();
        driver.findElement(fromCityPlaceholder).sendKeys("Bangalore");
        wait.until(ExpectedConditions.elementToBeClickable(bangaloreOption)).click();
    }

    public void selectInternationalTrip() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(toCityInput)).click();
        wait.until(ExpectedConditions.elementToBeClickable(intlFlightOption)).click();
    }

    public void selectRoundTrip() {
        driver.findElement(oneWayOption).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(roundTripOption)).click();
    }

    public void setToCity() {
        driver.findElement(toCityPlaceholder).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(enterCityInput)).click();
        driver.findElement(enterCityInput).sendKeys("Dubai");
        wait.until(ExpectedConditions.elementToBeClickable(dubaiOption)).click();
    }

    public void selectDatesAndDuration() {
        wait.until(ExpectedConditions.elementToBeClickable(datesDurationOption)).click();
    }
}
