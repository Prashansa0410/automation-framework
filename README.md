# Automation Practice Framework

A robust and modular automation testing framework built using **Selenium**, **TestNG**, **REST Assured**, and **ExtentReports**. This project supports both UI and API testing and includes utilities for logging, reporting, and data-driven test execution.

## 🔧 Tech Stack

- **Java 17**
- **Selenium 4**
- **TestNG 7**
- **REST Assured 5**
- **Spring Boot 3**
- **Maven**
- **ExtentReports**
- **Logback + SLF4J for logging**
- **PDFBox** for PDF validation
- **PostgreSQL** driver (for DB interaction, if needed)

## 📁 Project Structure

├── src
│ ├── main
│ │ ├── java
│ │ │ └── base, listeners, utils, config, ...
│ ├── test
│ │ ├── java
│ │ │ └── tests
├── tests/
│ └── LoginTest.java
├── testng.xml
├── pom.xml
└── README.md


## 🚀 Getting Started

### Prerequisites

- JDK 17
- Maven 3.6+
- ChromeDriver or other browser drivers (managed via WebDriverManager)

Run testng.xml directly
Right-click on testng.xml → Run

Features
UI Testing with Selenium WebDriver - Done

API Testing with Rest-Assured - Inprogress

Centralized test reporting using ExtentReports 

Logging with SLF4J and Logback

Screenshot capture on failure

Custom listeners for report lifecycle

Reports
Test reports will be generated in the /reports directory after execution.

🛠️ Maintainer
Prashansa
[GitHub](https://github.com/Prashansa0410)

📄 License
This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.

