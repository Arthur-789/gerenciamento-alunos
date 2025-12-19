package br.com.gerenciamento.acceptance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioAcceptanceTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testCadastroAluno() {
        driver.get("http://localhost:8080");

        WebElement linkCadastro = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Clique aqui para se cadastrar")));
        linkCadastro.click();

        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.id("email")));
        email.sendKeys("teste@email.com");

        WebElement usuario = driver.findElement(By.id("user"));
        usuario.sendKeys("usuarioTeste");

        WebElement senha = driver.findElement(By.id("senha"));
        senha.sendKeys("senha123");

        WebElement cadastrar = driver.findElement(By.xpath("//button[text()='Cadastrar']"));
        cadastrar.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/salvarUsuario"));
        assertEquals("http://localhost:8080/salvarUsuario", driver.getCurrentUrl());
    }
}