package cz.upce.nnpia.skills.Selenium.signup

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

@SpringBootTest
@ActiveProfiles("test")
class SignupPage {

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
    internal fun shouldSuccessfullySignUp() {
        webDriver.get("$baseUrl/skills")
        var loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='signup']")))
        loginButton.click()

        val firstname = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='firstname']")))
        firstname.clear()
        firstname.sendKeys("tester2")

        val lastname = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='lastname']")))
        lastname.clear()
        lastname.sendKeys("tester2")

        val username = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='username']")))
        username.clear()
        username.sendKeys("tester12")

        val email = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='email']")))
        email.clear()
        email.sendKeys("tester12")

        val password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']")))
        password.clear()
        password.sendKeys("tester1")

        val cpPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='cpPassword']")))
        cpPassword.clear()
        cpPassword.sendKeys("tester1")

        loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='signup']")))
        loginButton.submit()

        wait.until(ExpectedConditions.urlContains("$baseUrl/login"))

        Assertions.assertEquals("$baseUrl/login", webDriver.currentUrl)

    }
}