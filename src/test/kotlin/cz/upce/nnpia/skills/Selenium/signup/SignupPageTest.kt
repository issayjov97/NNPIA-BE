package cz.upce.nnpia.skills.Selenium.signup

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.io.File

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class SignupPageTest {

    private lateinit var webDriver: WebDriver
    private lateinit var wait: WebDriverWait

    companion object {
        const val baseUrl = "https://nnpia-skillsap-fe.herokuapp.com"
    }

    @BeforeEach
    internal fun setUp() {
        WebDriverManager.chromedriver().setup()
        val options = ChromeOptions()
         options.setHeadless(true)
        val circleCIChromedriverPath = "/usr/local/bin/chromedriver"
        if (File(circleCIChromedriverPath).exists()) {
            System.setProperty("webdriver.chrome.driver", circleCIChromedriverPath);
        }
        webDriver = ChromeDriver(options)
        wait = WebDriverWait(webDriver, 50)
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
        firstname.sendKeys("tester")

        val lastname = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='lastname']")))
        lastname.clear()
        lastname.sendKeys("tester")

        val username = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='username']")))
        username.clear()
        username.sendKeys("tester")

        val email = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='email']")))
        email.clear()
        email.sendKeys("tester@tester.cz")

        val password = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='password']")))
        password.clear()
        password.sendKeys("tester1234")

        val cpPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='cpPassword']")))
        cpPassword.clear()
        cpPassword.sendKeys("tester1234")

        loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='signup']")))
        loginButton.submit()

        wait.until(ExpectedConditions.alertIsPresent())

        webDriver.switchTo().alert().accept();


        wait.until(ExpectedConditions.urlContains("$baseUrl/login"))


        Assertions.assertEquals("$baseUrl/login", webDriver.currentUrl)

    }
}
