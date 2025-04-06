🚀 AdlibSearchQA Automation Framework
This is a robust Selenium TestNG-based test automation framework for validating the Search functionality of the nopCommerce demo application. It includes functional, data-driven, and visual UI validation tests integrated with Applitools, ExtentReports, and Excel data sources.


✅ Features Included
Selenium WebDriver

TestNG for test structure & execution

Page Object Model (POM)

Data-driven testing via DataProvider and Excel

ExcelUtility for reading test data

ExtentReports for HTML reports with screenshots

Applitools Eyes for visual UI validation

RetryAnalyzer to re-run failed test cases

Cross-browser execution support

Logs, Screenshots, and Reports are generated automatically



AdlibSearchQA
├── src/test/java
│   ├── pageObjects          // All Page Object classes
│   ├── testBase             // BaseClass for setup/teardown
│   ├── testCases            // TC01, TC07, TC08, TC09
│   ├── utilities            // DataProviders, ExcelUtility, RetryAnalyzer, ExtentReportManager
├── testData                 // search_testdata.xlsx
├── reports                  // ExtentReport HTML files
├── screenshots              // Captured screenshots
├── testng.xml               // TestNG suite config
├── pom.xml                  // Maven dependencies
└── run.sh                   // Shell script to trigger test execution




📦 Prerequisites
Ensure the following are installed on your machine:

Java 8 or higher

Maven 3.6+

Eclipse or IntelliJ IDEA

Chrome / Firefox / Edge browsers

Internet connection (for Applitools Eyes)


📚 Dependencies Used (from pom.xml)
selenium-java

testng

extentreports

apache-poi for Excel

log4j

commons-email

applitools-eyes-selenium-java


<dependency>
  <groupId>com.applitools</groupId>
  <artifactId>eyes-selenium-java5</artifactId>
  <version>5.79.0</version>
</dependency>


🔧 Configuration (config.properties)
Located at src/test/resources/config.properties:
execution_env=local
appURL=https://demo.nopcommerce.com/search
applitools_api_key=YOUR_API_KEY_HERE


🧪 Test Case Overview
✅ TC01_LaunchApplication
Launches the application

Verifies base title or URL

🔎 TC07_Advance_Search
Enters a keyword on homepage

Clicks "Search"

Verifies page header and navigation

🧾 TC08_Advance_Search_Page
Uses DataProvider and search_testdata.xlsx

Executes advanced search with different keyword/category/manufacturer combos

Asserts expected result text or product presence

🖼️ TC09_UIValidation
Performs Visual UI Testing using Applitools

Captures visual snapshot of the search page

Also logs screenshots in ExtentReports


📄 How to Run the Tests
💻 From Eclipse
Right-click on testng.xml → Run As → TestNG Suite

🛠️ Using Maven
mvn clean install


or with update flag:
mvn clean install -U



📜 Using Shell Script
sh run.sh



📊 View Reports
Navigate to /reports/

Open the latest Test-Report-yyyy.mm.dd.hh.mm.ss.html in browser
