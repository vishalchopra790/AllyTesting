package AllyTest;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;


import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;

public class Amazon {
    @Rule
    public TestName testName = new TestName();
    WebDriver driver;

    private static final URL scriptUrl = Amazon.class.getResource("/axe.min.js");

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://www.amazon.in");
    }

    @Test
    public void allyTest() {
        JSONObject responseJson = new AXE.Builder(driver, scriptUrl).analyze();
        JSONArray violations = responseJson.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("no errors");
        } else {
            AXE.writeResults("allyTest", responseJson);
            Assert.assertTrue(false, AXE.report(violations));
        }

    }


    @Test
    public void testAccesblityWithSkipFrames() {
        JSONObject responseJson = new AXE.Builder(driver, scriptUrl).skipFrames().analyze();
        JSONArray violations = responseJson.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("no errors");
        } else {
            AXE.writeResults("allyTest", responseJson);
            Assert.assertTrue(false, AXE.report(violations));
        }

    }

    @Test
    public void testAccessibility() {

        JSONObject responseJSON = new AXE.Builder(driver, scriptUrl).analyze();

        JSONArray violations = responseJSON.getJSONArray("violations");

        if (violations.length() == 0) {
            Assert.assertTrue(true, "No violations found");
        } else {
            AXE.writeResults(testName.getMethodName(), responseJSON);
            Assert.assertTrue(false, AXE.report(violations));
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
