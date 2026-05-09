# 🧪 Selenium Java Automation Portfolio

![CI](https://github.com/YOUR_USERNAME/selenium-java-portfolio/actions/workflows/selenium-ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-11-orange?logo=java)
![Selenium](https://img.shields.io/badge/Selenium-4.x-green?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.x-red)
![Maven](https://img.shields.io/badge/Maven-3.x-blue?logo=apachemaven)

Enterprise-grade automation framework built with **Selenium 4**, **Java 11**, **Maven**, and **TestNG**. Demonstrates Page Object Model, Data Driven Testing with Excel, Cross-Browser Testing, and rich Extent HTML Reports.

---

## 📁 Project Structure

```
selenium-java-portfolio/
├── src/
│   ├── main/java/com/qaportfolio/
│   │   ├── pages/              ← Page Object Model
│   │   │   ├── LoginPage.java
│   │   │   ├── InventoryPage.java
│   │   │   └── CheckoutPage.java
│   │   ├── utils/              ← Utilities
│   │   │   ├── DriverManager.java      (cross-browser WebDriver)
│   │   │   ├── ConfigReader.java       (reads config.properties)
│   │   │   ├── ExcelReader.java        (DDT from Excel)
│   │   │   ├── ExtentReportManager.java
│   │   │   └── ScreenshotUtil.java
│   │   └── listeners/
│   │       └── TestListener.java       (TestNG listener)
│   └── test/
│       ├── java/com/qaportfolio/
│       │   ├── base/BaseTest.java      (setup/teardown)
│       │   └── tests/
│       │       ├── LoginTest.java
│       │       ├── InventoryTest.java
│       │       └── CheckoutTest.java
│       └── resources/
│           ├── config.properties
│           ├── log4j2.xml
│           └── testdata/
│               └── LoginData.xlsx      (DDT test data)
├── .github/workflows/selenium-ci.yml
├── testng.xml                          (full suite - 3 browsers)
├── testng-ci.xml                       (CI suite - headless chrome)
├── pom.xml
└── README.md
```

---

## ✅ Framework Features

| Feature | Implementation |
|---|---|
| **Page Object Model** | Separate page classes with `@FindBy` + `PageFactory` |
| **Cross Browser Testing** | Chrome, Firefox, Edge via `testng.xml` parameters |
| **Data Driven Testing** | Excel (Apache POI) + TestNG `@DataProvider` |
| **Extent Reports** | Rich HTML report with screenshots on failure |
| **Screenshots on Failure** | Auto-captured and attached to report |
| **Parallel Execution** | TestNG parallel threads across browsers |
| **Logging** | Log4j2 — console + file |
| **WebDriverManager** | Auto-manages browser drivers (no manual download) |
| **CI/CD** | GitHub Actions — auto-runs on every push |

---

## 🧪 Test Cases

### Login Tests (7 cases)
- Valid login → redirects to inventory
- Invalid password, empty username, empty password
- Locked out user
- Login page loads correctly
- **Data Driven** — multiple users from Excel

### Inventory Tests (6 cases)
- 6 products displayed
- Sort A-Z, Z-A, Price Low-High
- Add 1 and 2 items to cart

### Checkout Tests (3 cases)
- Full end-to-end purchase flow
- Checkout fails without first name
- Checkout fails without postal code

---

## 🚀 Getting Started

### Prerequisites
- Java 11+
- Maven 3.6+
- Chrome / Firefox / Edge installed

### Run Tests

```bash
# Clone
git clone https://github.com/YOUR_USERNAME/selenium-java-portfolio.git
cd selenium-java-portfolio

# Run all tests (Chrome + Firefox + Edge in parallel)
mvn test

# Run only Chrome
mvn test -Dbrowser=chrome

# Run headless
mvn test -Dbrowser=chrome -Dheadless=true
```

### View Report
After running, open the generated HTML file in `reports/` folder.

---

## 📊 Test Data (DDT)

`LoginData.xlsx` has 3 columns:

| username | password | expectedResult |
|---|---|---|
| standard_user | secret_sauce | pass |
| locked_out_user | secret_sauce | fail |
| invalid_user | wrong_pass | fail |

---

## ⚙️ Cross Browser Execution

`testng.xml` runs tests in **parallel** across Chrome, Firefox, and Edge:

```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 👤 Aparna Bhat

Aparna Bhat
- LinkedIn: https://www.linkedin.com/in/aparna-bhat-48374b262/

