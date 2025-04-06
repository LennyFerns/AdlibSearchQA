package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass extends utilities.ExtentReportManager {

	private static final Logger log = LogManager.getLogger(BaseClass.class);
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	protected Properties p;

	public static WebDriver getDriver() {
		return driver.get();
	}

	@BeforeClass(groups = { "smoke", "regression", "datadriven" })
	@Parameters({ "os", "browser" })
	public void openApp(String os, String br) {
		log.info("Starting Test Execution - OS: {}, Browser: {}", os, br);

		try {
			loadConfig();
			
			// Set Applitools API key globally for Eyes SDK
			String eyesApiKey = p.getProperty("applitools_api_key");
			System.setProperty("APPLITOOLS_API_KEY", eyesApiKey);

			WebDriver localDriver = initializeDriver(os, br);
			driver.set(localDriver);

			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
			getDriver().manage().window().maximize();
			getDriver().get(p.getProperty("appURL"));

			log.info("Application launched at URL: {}", p.getProperty("appURL"));
		} catch (Exception e) {
			log.error("Test initialization failed: {}", e.getMessage(), e);
		}
	}

	private void loadConfig() throws IOException {
		FileReader file = new FileReader(".//src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		log.debug("Loaded config.properties successfully.");
	}

	private WebDriver initializeDriver(String os, String br) throws Exception {
		WebDriver localDriver;

		String env = p.getProperty("execution_env").toLowerCase();
		log.info("Execution Environment: {}", env);

		if (env.equals("remote")) {
			localDriver = setupRemoteDriver(os, br);
		} else if (env.equals("local")) {
			localDriver = setupLocalDriver(br);
		} else {
			throw new IllegalArgumentException("Invalid execution environment in config.properties");
		}

		return localDriver;
	}

	private WebDriver setupRemoteDriver(String os, String br) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		switch (os.toLowerCase()) {
		case "windows":
			capabilities.setPlatform(Platform.WIN11);
			break;
		case "mac":
			capabilities.setPlatform(Platform.MAC);
			break;
		default:
			throw new IllegalArgumentException("Invalid OS: " + os);
		}

		String gridURL = "http://localhost:4444/wd/hub";
		switch (br.toLowerCase()) {
		case "chrome":
			return new RemoteWebDriver(URI.create(gridURL).toURL(), new ChromeOptions().merge(capabilities));
		case "firefox":
			return new RemoteWebDriver(URI.create(gridURL).toURL(), new FirefoxOptions().merge(capabilities));
		case "edge":
			return new RemoteWebDriver(URI.create(gridURL).toURL(), new EdgeOptions().merge(capabilities));
		default:
			throw new IllegalArgumentException("Unsupported browser: " + br);
		}
	}

	private WebDriver setupLocalDriver(String br) {
		switch (br.toLowerCase()) {
		case "chrome":
			log.info("Launching Chrome Browser");
			return new ChromeDriver();
		case "firefox":
			log.info("Launching Firefox Browser");
			return new FirefoxDriver();
		case "edge":
			log.info("Launching Edge Browser");
			return new EdgeDriver();
		default:
			throw new IllegalArgumentException("Unsupported browser: " + br);
		}
	}

	@AfterClass(groups = { "smoke", "regression", "datadriven" })
	public void closeApp() {
		try {
			if (getDriver() != null) {
				log.info("Closing browser...");
				getDriver().quit();
				driver.remove();
				log.info("Browser closed and driver removed.");
			}
		} catch (Exception e) {
			log.error("Error while closing browser: {}", e.getMessage(), e);
		}
	}

	public static String captureScreen(String tname) throws IOException {
		if (getDriver() == null) {
			throw new IllegalStateException("Driver is null, cannot take screenshot.");
		}
		String timestamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String filePath = System.getProperty("user.dir") + "/screenshots/" + tname + "_" + timestamp + ".png";

		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		File dest = new File(filePath);
		Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

		log.info("Screenshot saved at: {}", filePath);
		return filePath;
	}
}
