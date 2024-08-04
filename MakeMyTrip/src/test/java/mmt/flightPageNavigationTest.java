package mmt;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class flightPageNavigationTest {

    public static double calculateMedian(List<Integer> priceList) {
        Collections.sort(priceList);
        int n = priceList.size();
        if (n % 2 == 0) {
            return (priceList.get(n / 2 - 1) + priceList.get(n / 2)) / 2.0;
        } else {
            return priceList.get(n / 2);
        }
    }

    public static void main(String[] args) {
        WebDriver driver = null;
        try {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(5));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

            driver.get("https://www.makemytrip.com/flights/");

            HomePage homePage = new HomePage(driver);
            FlightSearchPage flightSearchPage = new FlightSearchPage(driver);
            FlightResultsPage flightResultsPage = new FlightResultsPage(driver);

            homePage.closePromotionModal();
            homePage.selectFromCity();
            homePage.selectInternationalTrip();
            homePage.selectRoundTrip();
            homePage.setToCity();
            homePage.selectDatesAndDuration();

            flightSearchPage.selectDecember2024();
            flightSearchPage.adjustTripDuration();
            flightSearchPage.applyDates();
            flightSearchPage.initiateSearch();

            List<WebElement> tripPriceList = flightResultsPage.getTripPriceList();
            List<WebElement> tripDateList = flightResultsPage.getTripDateList();

            List<Integer> priceList = new ArrayList<>();
            List<String> dateList = new ArrayList<>();

            if (tripPriceList.size() == tripDateList.size()) {
                for (int i = 0; i < tripPriceList.size(); i++) {
                    WebElement priceElement = tripPriceList.get(i);
                    WebElement dateElement = tripDateList.get(i);

                    String priceText = priceElement.getText().replace("₹", "").replace(",", "").trim();
                    if (!priceText.equals("-") && !priceText.isEmpty()) {
                        int price = Integer.parseInt(priceText);
                        priceList.add(price);
                        dateList.add(dateElement.getText().trim());
                    }
                }
            } else {
                System.out.println("Mismatched list sizes. Prices List: " + tripPriceList.size() + ", Dates List: " + tripDateList.size());
            }
            System.out.println("DatesList: " +dateList);
            System.out.println("Date List Size: " + tripDateList.size());
            System.out.println("No. of days on which price is available: " + priceList.size());
            System.out.println("PriceList: " + priceList);

            if (!priceList.isEmpty()) {
                double medianPrice = calculateMedian(priceList);
                List<String> lowerThanMedianDates = new ArrayList<>();
                for (int i = 0; i < priceList.size(); i++) {
                    if (priceList.get(i) < medianPrice) {
                        lowerThanMedianDates.add(dateList.get(i));
                    }
                }

                System.out.println("Median Price: " + medianPrice);
                System.out.println("Dates with prices lower than the median price: ");
                for (String date : lowerThanMedianDates) {
                    System.out.println(date);
                }
            } else {
                System.out.println("No prices available to calculate median.");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            List<String> weekendDates = new ArrayList<>();
            String lowestPriceDate = null;
            int lowestPrice = Integer.MAX_VALUE;

            try {
                for (int i = 0; i < dateList.size(); i++) {
                    String date = dateList.get(i) + " Dec 2024";
                    int price = priceList.get(i);

                    try {
                        LocalDate parsedDate = LocalDate.parse(date, formatter);

                        if (parsedDate.getDayOfWeek() == DayOfWeek.SATURDAY || parsedDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                            weekendDates.add(date);
                        }

                        if (price < lowestPrice) {
                            lowestPrice = price;
                            lowestPriceDate = date;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Date parsing error: " + e.getMessage());
                    }
                }

                System.out.println("Weekend dates with prices lower than the median price:");
                for (String weekendDate : weekendDates) {
                    System.out.println(weekendDate);
                }

                String selectedDate = null;

                if (!weekendDates.isEmpty()) {
                    selectedDate = weekendDates.get(0);
                    System.out.println("Selected Weekend Date: " + selectedDate);
                } else if (lowestPriceDate != null) {
                    selectedDate = lowestPriceDate;
                    System.out.println("Selected Date with Lowest Price: " + selectedDate);
                } else {
                    System.out.println("No dates available to select.");
                }

                if (selectedDate != null) {
                    flightResultsPage.selectDate();

                    if (flightResultsPage.areFlightsAvailable()) {
                        System.out.println("Flights are available for the selected date: " + selectedDate);
                    } else {
                        System.out.println("No flights available for the selected date: " + selectedDate);
                    }
                } else {
                    System.out.println("No date selected to check flight availability.");
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
