package cz.upce.nnpia.skills.Selenium.login

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.chrome.ChromeOptions




class LoginPageTest {
    private lateinit var webDriver: WebDriver
    private lateinit var wait: WebDriverWait

    companion object {
        const val baseUrl = "https://nnpia-skillsap-fe.herokuapp.com"
    }

    @BeforeEach
    internal fun setUp() {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
        options.addArguments("--no-sandbox")
        options.addArguments("--disable-dev-shm-usage")
        options.addArguments("--headless")
        webDriver = ChromeDriver(options)
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
        email.sendKeys("st55409@upce.cz")

        val password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']")))
        password.clear()
        password.sendKeys("275822dd")

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