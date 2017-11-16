package com.pflb.learning;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MatrixBoardTest {
    WebDriver driver; //Поле для хранения нашего драйвера
    WebDriverWait wait; //поле для хранияния нашего Explicit Wait

    public static final String USER_NAME = "user";
    public static final String PASSWORD = "user";

    public static final String ADMIN_NAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    public static final String NAME_ADMIN_FUNCTION = "admin";

    @Before //Аннотация Junit. Говорит, что метод должен запускаться каждый раз после создания экземпляра класса, перед всеми тестами
    public void setUp() {
        //Устанавливаем System Property, чтобы наше приложени смогло найти драйвер
        //Рабочий - вариант 1
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\vika\\Work\\firefoxdriver\\geckodriver.exe");
        //Рабочий - вариант 2
        System.setProperty("webdriver.gecko.driver", "./src/test/resources/geckodriver.exe");;
        //Инициализируем драйвер
        driver = new FirefoxDriver();
        //Инициализируем Implicit Wait
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //инициализируем Explicit Wait
        wait = new WebDriverWait(driver, 10);
    }


    @Test //Аннотация Junit. Говорит, что этот метод - тестовый
    @Ignore //Проигноируем выполнение теста для выполнения теста из ДЗ
    public void loginTest(){
        driver.navigate().to("http://at.pflb.ru/matrixboard2/"); //перейти по URL
        //Найдем и сохраним элементы
        WebElement loginField = driver.findElement(By.id("login-username"));
        WebElement passwordField = driver.findElement(By.id("login-password"));
        WebElement submitBtn = driver.findElement(By.id("login-button"));

        //Заполним поля текстом
        loginField.sendKeys(USER_NAME);
        passwordField.sendKeys(PASSWORD);

        //отправим форму
        submitBtn.click();

        //В данной ситуации не нужны. Добавлены в качестве примера использования
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#profile span"))); //подождать пока элемент появиться на странице
        wait.until((d) -> {return d.findElement(By.cssSelector("#profile span")).isDisplayed();}); //пример собственной реализации ExpectedCondition. Ждем пока выражение внутри лямбды не вернет нам true (но не больше таймаута)

        WebElement usernameContainer = driver.findElement(By.cssSelector("#profile span")); //найдем элемент
        Assert.assertEquals(USER_NAME, usernameContainer.getText()); //проверим, что текст в найденном элементе совпадает с ожидаемым.
    }

    @Test
    public void loginAdminTest(){
        driver.navigate().to("http://at.pflb.ru/matrixboard2/");

        WebElement loginField = driver.findElement(By.id("login-username"));
        WebElement passwordField = driver.findElement(By.id("login-password"));
        WebElement submitBtn = driver.findElement(By.id("login-button"));

        loginField.sendKeys(ADMIN_NAME);
        passwordField.sendKeys(ADMIN_PASSWORD);

        submitBtn.click();

        /**
         * Задание: убедиться, что присутствуют функции администрирования
         */
        //Ищем элемент - admin на странице сайта - думаю там скрываются эти функции
        WebElement functionAdmin = driver.findElement(By.cssSelector("#profile span"));
        Assert.assertEquals(NAME_ADMIN_FUNCTION,functionAdmin.getText());

    }

    @After //Аннотация Junit. Говорит, что метод должен запускаться каждый раз после всех тестов
    public void tearDown() {
        driver.quit();
    }
}