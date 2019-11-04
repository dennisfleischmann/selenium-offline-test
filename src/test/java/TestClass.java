import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dennisfleischmann on 04.11.19.
 */
public class TestClass {

    @Test
    public void testFirefox() {

        WebDriver driver = setupDriver().withFireFoxDriver();
        driver.get("https://www.google.de");
        driver.close();
        driver.quit();

    }

    @Test
    public void testChrome() throws IOException {

        WebDriver driver = setupDriver().withChromiumDriver();

        driver.get("https://www.google.de");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Selenium");

        disConnectInternet(driver);
        element.submit();

        driver.close();
        driver.quit();

    }

    /**
     * Disconnects the browser from the wifi/Internet by sending configuration parameter
     * @param driver
     * @throws IOException
     */
    protected void disConnectInternet(WebDriver driver) throws IOException {
        Map map = new HashMap();
        map.put("offline", true);
        map.put("latency", 5);
        map.put("download_throughput", 500);
        map.put("upload_throughput", 1024);


        CommandExecutor executor = ((ChromeDriver)driver).getCommandExecutor();
        Response response = executor.execute(
                new Command(((ChromeDriver)driver).getSessionId(), "setNetworkConditions", ImmutableMap.of("network_conditions", ImmutableMap.copyOf(map))));

    }

    private TestClass setupDriver() {
        System.setProperty("webdriver.chrome.driver","/Users/dennisfleischmann/Dropbox/Dev/selenium/chromedriver");
        System.setProperty("webdriver.gecko.driver","/Users/dennisfleischmann/Dropbox/Dev/selenium/geckodriver");

        return this;
    }

    private WebDriver withChromiumDriver() {

        return new ChromeDriver();
    }

    /**
     * returns Chromium Driver
     * @return
     */
    private WebDriver withFireFoxDriver() {

        return new ChromeDriver();
    }
}
