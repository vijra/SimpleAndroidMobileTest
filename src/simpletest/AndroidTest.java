package simpletest;

// library to verify if URL is malformed
import java.net.MalformedURLException;

// library for URL, and drivers
import java.net.URL;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

// library for date and property files
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

// library used to access supporting files.
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

// library used to find elements (by id, class, and xpath)
import org.openqa.selenium.By;
// library for web element
import org.openqa.selenium.WebElement;

// library to handle screen
import org.openqa.selenium.Dimension;

// libraries for desired capabilities configurations and driver wait.
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
// library for test methods
import org.junit.*;

public class AndroidTest {
  public static String _deviceName;
  public static String _email;
  public static String _passwd;
  public static String _appName;
  public static String _appPackage;
  public static String _appActivity;
  public static int _iterate;

  public static AndroidDriver _driver;
  public static WebDriverWait _wait;
  public static Properties config = new Properties();

  public static void setup() throws MalformedURLException {
    File classpathRoot = new File(System.getProperty("user.dir"));
    File appDir = new File(classpathRoot, "/support_files");
    File app = new File(appDir, _appName);

    DesiredCapabilities capabilities = new DesiredCapabilities();

    // Browser name should be empty if testing Android app, else if browser means required that name.
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");

    // This should be Android, if using iOS as it is.
    capabilities.setCapability("platformName", "Android");

    // Android Version – for real device get this from "About device", for emulator as per setting.
    capabilities.setCapability(CapabilityType.VERSION, "4.4");

    // device name – since this is an actual device name is found using ADB
    capabilities.setCapability("deviceName", _deviceName);

    // the absolute local path to the APK
    capabilities.setCapability("app", app.getAbsolutePath());

    // App package and app Activity of Android app
    // You can get these by generating manifest file using the command
    // "$HOME/Library/Android/sdk/build-tools/<required version>/aapt list -a <.apk file> > manifest.txt inside
    // app package will be near to the "Package Group 0" and
    // app activity will be near to "E: activity " use the appropriate one.
    capabilities.setCapability("appPackage", _appPackage);
    capabilities.setCapability("appActivity", _appActivity);

    // if you wish to avoid any unwanted popup messages use the below
    // capabilities.setCapability("autoAcceptAlerts", "true");

    // Initialize driver and wait objects
      try {
        _driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        _wait = new WebDriverWait(_driver,30);
        System.out.println("Android driver created successfully...");
      } catch (MalformedURLException e) {
        System.err.println("Check the configuration property file, also don't forget to start the Appium.");
        e.printStackTrace();
      }
  }

  public static void performActivityTest() throws Exception {
    long startTime = System.currentTimeMillis();
    long endTime, count = 1;

    // Authentication page
    clickElementById(Locators._btnSignIn);

    // User name and password
    sendKeysById(Locators._txtEmail, _email);
    sendKeysById(Locators._txtPasswd, _passwd);

    // Login Submit
    clickElementById(Locators._btnLoginSubmit);



    do {
        // Do activity
        // Evaluate time for first iteration
        endTime   = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        // get current date time with Date()
        Date date = new Date();
        String str_date = dateFormat.format(date);
        String time_diff = timeDiff (startTime, endTime);
        String tmp;
        if (_iterate != -1) {
          _iterate--;
          tmp = String.format("%s => Iteration remaining: %d, Elapsed time: %s", str_date, _iterate, time_diff);
        } else {
          tmp = String.format("%s => Iteration count: %d, Elapsed time: %s", str_date, count, time_diff);
          count++;
        }
        System.out.println(tmp);
    } while(_iterate > 0 || _iterate == -1);
  }

  public static void tearDown() {
    _driver.closeApp();
    _driver.removeApp(_appPackage);
    _driver.quit();
    System.out.println("Application closed and removed.");
  }

  public static void clickElementByXpath(String xpath) {
    waitForClickableElementByXpath(xpath).click();
    implicitWait();
  }

  public static WebElement waitForClickableElementByXpath(String xpath) {
    WebElement element = getElementByXpath(xpath);
    _wait.until(ExpectedConditions.elementToBeClickable(element));
    return element;
  }

  public static void clickElementById(String id) {
    getElementById(id).click();
    implicitWait();
  }

  public static void sendKeysById(String str_locator, String str_value) {
    getElementById(str_locator).sendKeys(str_value);
    implicitWait();
  }

  public static WebElement getElementByXpath(String xpath) {
    implicitWait();
    return _driver.findElement(By.xpath(xpath));
  }

  public static WebElement getElementById(String id) {
    implicitWait();
    return _driver.findElement(By.id(id));
  }

  public static void waitForPageLoad() {
    _driver.manage().timeouts().pageLoadTimeout(30,  TimeUnit.SECONDS);
  }

  public static void implicitWait() {
    _driver.manage().timeouts().implicitlyWait(60,  TimeUnit.SECONDS);
  }

  public static String timeDiff(long startDate, long endDate){
    // milliseconds
    long different = endDate - startDate;

    long secondsInMilli = 1000;
    long minutesInMilli = secondsInMilli * 60;
    long hoursInMilli = minutesInMilli * 60;
    long daysInMilli = hoursInMilli * 24;

    long elapsedDays = different / daysInMilli;
    different = different % daysInMilli;

    long elapsedHours = different / hoursInMilli;
    different = different % hoursInMilli;

    long elapsedMinutes = different / minutesInMilli;
    different = different % minutesInMilli;

    long elapsedSeconds = different / secondsInMilli;
    long elapsedMilliSeconds = different % secondsInMilli;

    return (
        String.format("%d days, %02d hr, %02d min, %02d sec, %03d m.sec",
            elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds, elapsedMilliSeconds));
  }

  public static void loadConfig() throws Exception {
    Reader input_config_file = null;
    try {
        String filename =  System.getProperty("user.dir") + "/support_files/perf_config.properties";
        input_config_file =new FileReader (filename);// ClassLoader.class.getResourceAsStream(filename);
        config.load(input_config_file);

        // get values from property file.
        _email = config.getProperty("email");
        _passwd = config.getProperty("passwd");
        _deviceName = config.getProperty("deviceName");
        _appName = config.getProperty("appName");
        _appPackage = config.getProperty("appPackage");
        _appActivity = config.getProperty("appActivity");
        _iterate = Integer.parseInt(config.getProperty("iterate"));

        System.out.println("app name : " + _appName);
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    finally{
      if(input_config_file !=null){
        try {
            input_config_file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
      }
    }
  }

  public static void scrollUpAndDown() {
    implicitWait();

    Dimension dimensions = _driver.manage().window().getSize();
    Double screenHeightStart = dimensions.getHeight() * 0.5;
    int scrollStart = screenHeightStart.intValue();
    Double screenHeightEnd = dimensions.getHeight() * 0.2;
    int scrollEnd = screenHeightEnd.intValue();

    // Scroll down for n times.
    int n  = 10;
    for (int i = 0; i < dimensions.getHeight() && i < n; i++) {
      _driver.swipe(0,scrollStart,0,scrollEnd,2000);
    }
    implicitWait();
    // Scroll up for m times.
    int m = 11;
    for (int i = 0; i < dimensions.getHeight() && i < m; i++) {
      _driver.swipe(0,scrollEnd,0,scrollStart,2000);
    }
    implicitWait();
  }

  public static void main(String[] args) {
    try {
      // Load all configuration details from the property file.
      loadConfig();
      // Setup all the required driver and other items.
      setup();
      // perform the Test activity.
      performActivityTest();
      // Clean up and closing app and driver.
      tearDown();
    } catch (Exception e) {
      e.printStackTrace();
      // Closing the app if any error, to start next time without getting "Session already running message"
      _driver.closeApp();
    }
  }
}