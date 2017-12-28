package fantapianto.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;




public class HttpUtils {

//	private static WebDriver driver;
	
	private static Map<String, WebDriver> driversMap = new HashMap<String, WebDriver>();
	
	private static int downloadedPages=0;
	
	private static WebDriver loggedWebDriver;


	
	
	public static Document getHtmlPage(String url){
		Document doc = null;
		System.out.print(".");
		while (true){
			try {

				if (loggedWebDriver == null)
					initLoggedWebDriver();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				loggedWebDriver.get(url);
			    
			    String pageSource = loggedWebDriver.getPageSource();
				doc = Jsoup.parse(pageSource);
				
				break;
			}
			catch (Exception e){
				System.out.print("e");
			}
		}
		return doc;
	
	}
	


	private static void initLoggedWebDriver() {
		String url = AppConstants.LOGIN_PAGE;
		
		WebDriver champDriver = getChampDriver(url);
		
//		if (driver == null){
		champDriver.get(url);
//		champDriver.navigate().refresh();

		WebElement navBar   = champDriver.findElement(By.id("myNav"));
		WebElement loginBtnNavBar = navBar.findElement(By.className("btnlogin"));
		

		WebElement cookieInfoCloseButton = champDriver.findElement(By.className("iubenda-cs-close-btn"));
		WebDriverWait wait2 = new WebDriverWait(champDriver,2);
		wait2.until(ExpectedConditions.elementToBeClickable(cookieInfoCloseButton));
		cookieInfoCloseButton.click();
	    
		 
		 
	    WebElement id = champDriver.findElement(By.id("username"));
	    WebElement pass = champDriver.findElement(By.id("password"));
	    WebElement loginButton = champDriver.findElement(By.id("accedi"));

	    
	    loginBtnNavBar.click();
	    WebDriverWait wait = new WebDriverWait(champDriver,2);
	    wait.until(ExpectedConditions.elementToBeClickable(loginButton));
	    
	    
	    id.sendKeys(AppConstants.user);
	    pass.sendKeys(AppConstants.password);
	    loginButton.click();
	   
	    
	    loggedWebDriver = champDriver;
	}


	public static Document getHtmlPageNoLogin(String url){
		System.out.print(".");
		downloadedPages++;
//		if (downloadedPages%10 == 0){
//			System.out.println("downloaded pages : " + downloadedPages);
//		}
		Document doc = null;
		while (true){
			try {

				
				
				doc = chromeWork(url);
				
				
				
				return doc;
			}
			catch (Exception e){
				System.out.print("e");
			}
				
				
		}
		
	}
	
	

	private static void eeee(WebDriver champDriver) {
		try {
		
        
		}
		catch (Exception e){
			
		}
	}
	
	
	
	private static Document chromeWork(String url) {
		Document doc;
		WebDriver champDriver = getChampDriver(url);
//		if (driver == null){
//			driver = initDriver();
//		}
									
		
//			long startTime = System.nanoTime();
//			System.out.println("2 DOWNLOAD PAGE...");
		
		champDriver.get(url);
		champDriver.navigate().refresh();

//			long currentTime = System.nanoTime();
//			long duration = (currentTime - startTime);  //divide by 1000000 to get milliseconds.
//			System.out.println("DONE\t" + duration / 1000000);
//			System.out.println();
		
		String pageSource = champDriver.getPageSource();
//		System.out.println(pageSource);
		doc = Jsoup.parse(pageSource);
		
		return doc;
	}




	private static WebDriver getChampDriver(String url) {
		String driverName = "prova";//getChampName(url);
		WebDriver champDriver = driversMap.get(driverName);
		if (champDriver == null) {
			champDriver = initDriver();
			driversMap.put(driverName, champDriver);
		}
		return champDriver;
	}






	private static WebDriver initDriver() {
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:/driver/chromedriver.exe")).build(); 
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless", "--disable-gpu");
		options.addArguments("--allow-file-access-from-files");
		options.addArguments("--verbose");
		options.addArguments("load-extension=C:\\Users\\Menesbatto\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cfhdojbkjhnklbpkdaibdccddilifddb\\1.13.4_0");
		capabilities.setVersion("");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		
		
			long startTime = System.nanoTime();
			System.out.println("1 CARICAMENTO DRIVER...");

		WebDriver driver = new ChromeDriver(service, capabilities);
		
			long currentTime = System.nanoTime();
			long duration = (currentTime - startTime);  //divide by 1000000 to get milliseconds.
			System.out.println("DONE\t" + duration / 1000000);
			System.out.println();
			
//			WebDriverWait wait = new WebDriverWait(driver,2);
//			wait.until(ExpectedConditions.elementToBeClickable(loginButton));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			WebDriverWait wait2 = new WebDriverWait(driver,20);
//			WebElement body   = driver.findElement(By.tagName("body"));
//			body.findElement(By.id("title-main"));
//			wait2.until(ExpectedConditions.textToBePresentInElement(body, "stata installata"));
			

			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "/w");
		return driver;
	}



	private static Document phantomNotWork() {
		System.setProperty("phantomjs.binary.path", "C:/driver/phantomjs-2.1.1-windows/bin/phantomjs.exe");
		ArrayList<String> cliArgsCap = new ArrayList<String>();
	
		
		DesiredCapabilities caps =  DesiredCapabilities.phantomjs();
		cliArgsCap.add("--web-security=false");
		cliArgsCap.add("--ssl-protocol=any");
		cliArgsCap.add("--ignore-ssl-errors=true");
		cliArgsCap.add("--webdriver-loglevel=INFO");
		cliArgsCap.add("--load-images=false");

		
		caps.setJavascriptEnabled(true);
		
		caps.setCapability(CapabilityType.SUPPORTS_FINDING_BY_CSS, true);
		caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
		
		
//											long startTime = System.nanoTime();
//											System.out.println("1 CARICAMENTO DRIVER...");
		
		WebDriver driver = new PhantomJSDriver(caps);
		
		
//											long currentTime = System.nanoTime();
//											long duration = (currentTime - startTime);  //divide by 1000000 to get milliseconds.
//											System.out.println("DONE\t" + duration / 1000000);
//											System.out.println();
//											System.out.println("2 DOWNLOAD PAGE...");
		
	
		
		driver.get("http://www.oddsportal.com/soccer/italy/serie-a/results/");
		
		waitForLoad(driver);
		
		String pageSource = driver.getPageSource();
		Document doc = Jsoup.parse(pageSource);
		
//											currentTime = System.nanoTime();
//											duration = (currentTime - startTime);  //divide by 1000000 to get milliseconds.
//											System.out.println("DONE\t" + duration / 1000000);

		return doc;
	}
	
	
	
	
	
	
	public static void waitForLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
				By.tagName("body"), "Crotone"));
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.id("tournamentTable")));
		
    }




//	public static void shutdown(ChampEnum champ) {
//		WebDriver driver = getChampDriver(champ.getNextMatchesUrl());
//		driver.close();
//		String champName = getChampName(champ.getResultsUrl());
//		driversMap.remove(champName);
//	}
	
	
//	import javax.ws.rs.core.NewCookie;
//	import com.sun.jersey.api.client.ClientResponse;
//	public static Document getHtmlPageWithCookies(ClientResponse cuResponse, String url){
//		Document doc = null;
//		try {
//			Connection connect = Jsoup.connect(url);
//			doc = connect.get();
//			List<NewCookie> cookies = cuResponse.getCookies();
//			
//			for (NewCookie c : cookies) {
//				connect.cookie(c.getName(), c.getValue());
//			}
//		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return doc;
//	}
}
