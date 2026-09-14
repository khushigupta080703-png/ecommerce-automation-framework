# E-Commerce Web Automation Testing Framework

[![Run Tests](https://github.com/khushigupta080703-png/ecommerce-automation-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/khushigupta080703-png/ecommerce-automation-framework/actions/workflows/ci.yml)

**Live test report:** https://khushigupta080703-png.github.io/ecommerce-automation-framework/
*(updates automatically after every push)*

A Selenium WebDriver + TestNG + Maven automation framework built with the
Page Object Model (POM), targeting [SauceDemo](https://www.saucedemo.com/) —
a public demo e-commerce site made for practicing exactly this kind of testing.

## Tech Stack
- **Java 11**
- **Selenium WebDriver 4.21**
- **TestNG 7.10**
- **Maven** (dependency management + test execution)
- **WebDriverManager** (auto-downloads the correct browser driver binary — no manual chromedriver setup)
- **Page Object Model** design pattern
- **Git/GitHub** for version control

## Project Structure
```
ecommerce-automation-framework/
├── pom.xml
├── src/
│   ├── main/java/com/framework/
│   │   ├── base/
│   │   │   ├── BasePage.java        # PageFactory init for all page objects
│   │   │   └── BaseTest.java        # @BeforeMethod/@AfterMethod driver lifecycle
│   │   ├── pages/
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductsPage.java
│   │   │   ├── CartPage.java
│   │   │   └── CheckoutPage.java
│   │   ├── utils/
│   │   │   ├── ConfigReader.java    # reads config.properties
│   │   │   ├── DriverManager.java   # thread-safe WebDriver creation
│   │   │   ├── WaitUtils.java       # centralized explicit waits
│   │   │   └── ScreenshotUtils.java # screenshot capture
│   │   ├── listeners/
│   │   │   └── TestListener.java    # auto screenshot on failure
│   │   └── resources/
│   │       └── config.properties
│   └── test/java/com/framework/tests/
│       ├── LoginTest.java
│       ├── AddToCartTest.java
│       └── CheckoutTest.java
│   └── test/resources/
│       └── testng.xml               # smoke & regression suites
└── screenshots/                     # auto-populated on test failure
```

## Prerequisites
- Java JDK 11 or higher installed (`java -version`)
- Maven installed (`mvn -version`)
- Google Chrome installed (default browser; Firefox/Edge also supported)

## Setup
```bash
git clone <your-repo-url>
cd ecommerce-automation-framework
mvn clean install -DskipTests
```

## Running Tests

Run everything (default — every test runs exactly once):
```bash
mvn clean test
```

Run only the smoke suite:
```bash
mvn clean test -Dgroups=smoke
```

Run only the regression suite:
```bash
mvn clean test -Dgroups=regression
```

Run a specific test class:
```bash
mvn clean test -Dtest=LoginTest
```

Run headless (e.g. in CI) by setting `headless=true` in `config.properties`,
or override at runtime:
```bash
mvn clean test -Dheadless=true
```

## Configuration
All environment settings live in `src/main/resources/config.properties`:
```properties
browser=chrome
headless=false
url=https://www.saucedemo.com/
implicitWait=5
explicitWait=15
```
Change `browser` to `firefox` or `edge` to switch browsers — WebDriverManager
handles the driver binary automatically.

## Reports & Screenshots
- TestNG generates an HTML report at `test-output/index.html` after each run.
- On any test failure, `TestListener` automatically captures a screenshot to
  `/screenshots/<testName>_<timestamp>.png` for debugging.

## What's Covered
| Area | Scenarios |
|---|---|
| Login | valid login, invalid credentials, locked-out user, empty credentials |
| Product selection / Cart | add single/multiple products, cart badge count, cart item validation, remove product |
| Checkout | full happy-path checkout, required-field validation, order total display |

## Extending the Framework
1. **New page** → create a class in `pages/` extending `BasePage`, declare
   `@FindBy` locators, and expose business-readable methods (avoid exposing
   raw `WebElement`s to test classes).
2. **New test** → create a class in `tests/` extending `BaseTest`, tag
   methods with `@Test(groups = {"smoke"|"regression"})`.
3. **New suite/group** → add `<include name="...">` entries in `testng.xml`.
4. **CI integration** → this project runs cleanly with `mvn clean test` in any
   CI system (GitHub Actions, Jenkins, etc.); just install a JDK + Chrome on
   the runner first, or set `headless=true`.

## Git/GitHub Workflow
```bash
git init
git add .
git commit -m "Initial commit: e-commerce automation framework"
git branch -M main
git remote add origin <your-repo-url>
git push -u origin main
```
