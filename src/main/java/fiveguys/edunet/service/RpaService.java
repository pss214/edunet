package fiveguys.edunet.service;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
@Component
@EnableAsync
public class RpaService {
    @Value("${rpa.id}")
    private String id;
    @Value("${rpa.pw}")
    private String pw; 
    @Async
    public  void rpa(String filepath, String content,String subject) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            driver.get("https://accounts.kakao.com/login?continue=https%3A%2F%2Fwww.daum.net%2F");

            WebElement idInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("loginId--1")));
            WebElement pwInput = driver.findElement(By.id("password--2"));

            typeLikeHuman(idInput, "jys880919@hanmail.net");
            typeLikeHuman(pwInput, "rhddb@06");

            WebElement loginButton = driver.findElement(By.className("btn_g"));
            Thread.sleep(500 + (int)(Math.random() * 1000)); // 버튼 클릭 전 딜레이
            loginButton.click();

            Thread.sleep(3000);
            driver.get("https://cafe.daum.net/autoedunet");
            Thread.sleep(3000);

            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("down")));
            driver.switchTo().frame(iframe);

            try {
                WebElement popupLayer = driver.findElement(By.id("popupLayer"));
                if (popupLayer.isDisplayed()) {
                    WebElement closeButton = popupLayer.findElement(By.cssSelector(".btn_close"));
                    closeButton.click();
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                // 팝업 없으면 무시
            }

            WebElement writeButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("cafe_write_article_btn")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", writeButton);
            driver.switchTo().defaultContent();
            Thread.sleep(3000);

            try {
                List<WebElement> allIframes = driver.findElements(By.tagName("iframe"));
                WebElement boardSelect = null;
                for (WebElement boardFrame : allIframes) {
                    try {
                        driver.switchTo().frame(boardFrame);
                        try {
                            boardSelect = driver.findElement(By.cssSelector("div.opt_more_g.list_board_type a.link_item"));
                        } catch (Exception e1) {
                            try {
                                boardSelect = driver.findElement(By.xpath("//a[contains(text(), '게시판 선택')]"));
                            } catch (Exception e2) {
                                boardSelect = driver.findElement(By.cssSelector("a.link_item"));
                            }
                        }
                        if (boardSelect != null) break;
                    } catch (Exception e) {
                        driver.switchTo().defaultContent();
                    }
                }
                if (boardSelect != null) {
                    boardSelect.click();
                    Thread.sleep(2000);
                    WebElement ourStoryOption = driver.findElement(By.xpath("//a[contains(@class, 'link_menu') and contains(text(), '우리들의 이야기')]"));
                    ourStoryOption.click();
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                System.out.println("게시판 선택 중 오류 발생: " + e.getMessage());
            }

            driver.switchTo().defaultContent();
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            WebElement titleInput = null;
            for (WebElement currentFrame : iframes) {
                try {
                    driver.switchTo().frame(currentFrame);
                    titleInput = driver.findElement(By.cssSelector("#article-title .title__input"));
                    if (titleInput != null) break;
                } catch (Exception e) {
                    driver.switchTo().defaultContent();
                }
            }
            if (titleInput == null) {
                driver.switchTo().defaultContent();
                titleInput = driver.findElement(By.cssSelector("#article-title .title__input"));
            }

            titleInput.sendKeys("경)"+subject+ "개설~!(축");
            Thread.sleep(2000);

            WebElement editorFrame = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("keditorContainer_ifr")));
            driver.switchTo().frame(editorFrame);
            WebElement contentBody = driver.findElement(By.tagName("body"));
            contentBody.sendKeys(content);
            driver.switchTo().defaultContent();
            Thread.sleep(1000);

            boolean imageButtonClicked = clickElementInFrames(driver, wait, By.id("mceu_0-button"));
            if (imageButtonClicked) {
                System.out.println("이미지 업로드 버튼 클릭 성공");
                Thread.sleep(1000);
            } else {
                System.out.println("이미지 업로드 버튼을 찾지 못했습니다.");
            }

            boolean imageUploaded = uploadFileInFrames(driver, filepath);
            if (!imageUploaded) {
                System.out.println("이미지 업로드 input을 찾지 못했습니다.");
            }
            Thread.sleep(1000);

            try {
                WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
                WebElement popup = shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("popupLayer")));
                if (popup.isDisplayed()) {
                    WebElement closeBtn = popup.findElement(By.cssSelector(".btn_close"));
                    closeBtn.click();
                    System.out.println("이미지 업로드 후 팝업 닫음");
                    Thread.sleep(1000);
                }
            } catch (TimeoutException te) {
                System.out.println("이미지 업로드 후 팝업 없음");
            } catch (NoSuchElementException ne) {
                System.out.println("팝업 또는 닫기 버튼이 없음");
            }

            boolean clicked = clickRegisterButtonWithWait(driver, wait);
            if (!clicked) {
                System.out.println("등록 버튼을 찾지 못했습니다.");
            }
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private static void typeLikeHuman(WebElement element, String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            element.sendKeys(Character.toString(c));
            Thread.sleep(100 + (int)(Math.random() * 200));
        }
    }

    private static boolean clickElementInFrames(WebDriver driver, WebDriverWait wait, By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            return true;
        } catch (Exception ignored) {}

        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        for (WebElement frame : frames) {
            try {
                driver.switchTo().frame(frame);
                boolean clicked = clickElementInFrames(driver, wait, locator);
                driver.switchTo().parentFrame();
                if (clicked) return true;
            } catch (Exception e) {
                driver.switchTo().parentFrame();
            }
        }
        return false;
    }

    private static boolean uploadFileInFrames(WebDriver driver, String filePath) {
        try {
            WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.display='block'; arguments[0].style.visibility='visible';", fileInput
            );
            fileInput.sendKeys(filePath);
            return true;
        } catch (Exception ignored) {}

        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        for (WebElement frame : frames) {
            try {
                driver.switchTo().frame(frame);
                boolean uploaded = uploadFileInFrames(driver, filePath);
                driver.switchTo().parentFrame();
                if (uploaded) return true;
            } catch (Exception e) {
                driver.switchTo().parentFrame();
            }
        }
        return false;
    }

    private static boolean clickRegisterButtonWithWait(WebDriver driver, WebDriverWait wait) {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'btn_g') and contains(@class, 'full_type1') and text()='등록']")
            ));
            if (btn.isDisplayed() && btn.isEnabled()) {
                btn.click();
                return true;
            }
        } catch (Exception ignored) {}

        List<WebElement> frames = driver.findElements(By.tagName("iframe"));
        for (WebElement frame : frames) {
            try {
                driver.switchTo().frame(frame);
                boolean found = clickRegisterButtonWithWait(driver, new WebDriverWait(driver, Duration.ofSeconds(3)));
                driver.switchTo().parentFrame();
                if (found) return true;
            } catch (Exception e) {
                driver.switchTo().parentFrame();
            }
        }
        return false;
    }

}