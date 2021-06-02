package cz.upce.nnpia.skills.Selenium.login

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("test")
class LoginPage {
    private lateinit var webDriver: WebDriver
    private lateinit var wait: WebDriverWait

    companion object {
        const val baseUrl = "http://localhost:4200"
    }

    @BeforeEach
    internal fun setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/debian/Downloads/chromedriver_linux64/chromedriver")
        webDriver = ChromeDriver()
        webDriver.manage().window().maximize()
        wait = WebDriverWait(webDriver, 10)
    }

    @AfterEach
    internal fun tearDown() {
        webDriver.quit()
    }

    @Test
    internal fun shouldSuccessfullyLogin() {
        webDriver.get("$baseUrl/skills")
        var loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='login']")))
        loginButton.click()

        val email = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='email']")))
        email.clear()
        email.sendKeys("user1")

        val password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']")))
        password.clear()
        password.sendKeys("275822df")

        loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='login']")))
        loginButton.submit()

        wait.until(ExpectedConditions.urlContains("$baseUrl/skills"))

        Assertions.assertEquals("$baseUrl/skills", webDriver.currentUrl)
    }

    @Test
    internal fun shouldNotSuccessfullyLogin() {
        webDriver.get("$baseUrl/skills")
        var loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='login']")))
        loginButton.click()

        val email = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='email']")))
        email.clear()
        email.sendKeys("failed")

        val password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']")))
        password.clear()
        password.sendKeys("failed")

        loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='login']")))
        loginButton.submit()

        wait.until(ExpectedConditions.urlContains("$baseUrl/login"))

        Assertions.assertEquals("$baseUrl/login", webDriver.currentUrl)

    }
}