import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Tests {
    WebDriver _globalDriver;
    WebDriver _globalDriverFull;
    String _email = new String();
    String _password = new String();

    @BeforeClass
    public static String generateRandomEmail() {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "example.com"};
        String[] characters = {"abcdefghijklmnopqrstuvwxyz", "0123456789"};

        Random random = new Random();
        StringBuilder email = new StringBuilder();

        // Generate username part
        int usernameLength = random.nextInt(10) + 5; // Random length between 5 to 14 characters
        for (int i = 0; i < usernameLength; i++) {
            String characterSet = characters[random.nextInt(2)]; // Selecting either alphabets or numbers
            char randomChar = characterSet.charAt(random.nextInt(characterSet.length()));
            email.append(randomChar);
        }

        // Adding '@' symbol
        email.append("@");

        // Selecting random domain
        String randomDomain = domains[random.nextInt(domains.length)];
        email.append(randomDomain);

        return email.toString();
    }

    @BeforeClass
    public static String generateRandomUserame() {
        String[] characters = {"abcdefghijklmnopqrstuvwxyz", "0123456789"};

        Random random = new Random();
        StringBuilder username = new StringBuilder();

        // Generate username part
        int usernameLength = random.nextInt(10) + 5; // Random length between 5 to 14 characters
        for (int i = 0; i < usernameLength; i++) {
            String characterSet = characters[random.nextInt(2)]; // Selecting either alphabets or numbers
            char randomChar = characterSet.charAt(random.nextInt(characterSet.length()));
            username.append(randomChar);
        }
        return username.toString();
    }

    @BeforeClass
    public void SetupUserDetails() {
        _email = generateRandomEmail();
        _password = generateRandomUserame();
    }

    @BeforeTest
    public void SetupWebDriver() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");
        _globalDriver = new ChromeDriver();
        _globalDriver.get("https://www.aruodas.lt/");
        _globalDriver.manage().window().maximize();
    }

    @BeforeTest
    public void SetupWebDriverFull() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--start-maximized");
        _globalDriverFull = new ChromeDriver();
        _globalDriverFull.get("https://www.aruodas.lt/");
        _globalDriverFull.manage().window().maximize();
    }

    @Test// registracija
    public void testTC0101() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _globalDriver.findElement(By.id("onetrust-accept-btn-handler")).click();//išjungiami cookies
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[1]/ul/li[5]/a")).click();//pasirenkamas meniu
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[5]/ul[1]/li[2]/a")).click();//pasirenkama prisijungti
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/a[2]")).click();//pasirenkama registruotis
        _globalDriver.findElement(By.id("userName")).sendKeys(_email);//suvedamas pasto adresas
        _globalDriver.findElement(By.id("password")).sendKeys(_password);//suvedamas slaptazodis
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div[1]/form/button")).click();//paspaudziama "Registruotis"
//        _globalDriver.close();
    }

    @Test// garažo paieška
    public void testTC0102() {
        _globalDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        _globalDriver.findElement(By.id("onetrust-accept-btn-handler")).click();//išjungiami cookies
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[1]/div/ul[1]/li[1]/ul/li[6]")).click();//atidaromi garažų skelbimai
        _globalDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[1]/div/ul[3]/li[1]/a[1]")).click();//atidaromas savivaldybių sąrašas
        _globalDriver.findElement(By.xpath("/html/body/div[1]/ul[1]/li[2]/a")).click();//pasirenkama Kauno savivaldybė
        _globalDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[1]/div/ul[3]/li[2]/a")).click();//išskleidžiamas mikrorajonų sąrašas
        _globalDriver.findElement(By.id("listFilterText")).sendKeys("Žaliakalnis");//įvedamas Žaliakalnis
        _globalDriver.findElement(By.xpath("/html/body/div/ul/li[14]/label")).click();//pasirenkamas Žaliakalnis
        _globalDriver.findElement(By.id("submitSelect1")).click();//išsaugojamas pasirinkimas
        _globalDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[1]/div/ul[3]/li[7]/a")).click();//išskleidžimas kainų sąrašas
        _globalDriver.findElement(By.xpath("/html/body/div/ul[3]/li[8]/label")).click();//max 20 000€
        _globalDriver.findElement(By.id("submitSelect1")).click();//išsaugojamas pasirinkimas
        _globalDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        _globalDriver.findElement(By.id("submit_search_button")).click();//pradedama paieška
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/ul[1]/li[1]/a/span")).click();//atidaromas pirmas rezultatas
        WebElement resultText = _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div/div[10]/div[1]"));//skelbimo aprašymas
        String garazoAprasymas = resultText.getText();
        garazoAprasymas.contains("garažas");
        _globalDriver.close();
    }

    @Test// skelbimo patalpinimas
    public void testTC0103() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _globalDriver.findElement(By.id("onetrust-reject-all-handler")).click();//išjungiami cookies
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[3]/div/div/a")).click();//pasirenkama įdėti skelbimą
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[2]/ul/li[2]/ul/li[2]/span")).click();//įkelti namo skelbimą
        _globalDriver.findElement(By.xpath("//*[@id=\"dealType\"]/ul/li[1]")).click();//pardavimui
        _globalDriver.findElement(By.id("regionTitle")).click();//savivaldybės pasirinkimas
        _globalDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div[3]/div[6]/div/ul/li[2]")).click();//pasirenkamas Kaunas
        _globalDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[1]/div[2]/span/input[2]")).click();//išskleidžiamos gyvenvietės
        _globalDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div[3]/div[7]/div/ul/li")).click();//pasirenkama Kauno m. gyvenvietė
        _globalDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[1]/div[3]/span/input[2]")).click();//išskleidžiami mikrorajonai
        _globalDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div[3]/div[8]/div/ul/li[28]")).click();//pasirenkamas Žaliakalnis
        _globalDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[1]/div[4]/span")).click();//išskleidžiamos gatvės
        _globalDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/div[3]/div[9]/div/ul/li[4]")).click();//pasirenkama Baranausko gatvė
        _globalDriver.findElement(By.id("regionTitle")).sendKeys("420");//įvedamas namo numeris
        _globalDriver.findElement(By.id("FAreaOverAllValue")).sendKeys("420");//įvedamas namo plotas
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[3]/div[2]/div/div/div/div[2]/div[2]")).click();//aukštų skaičius
        _globalDriver.findElement(By.id("FAreaLotValue")).sendKeys("6");//įvedamas sklypo plotas
        _globalDriver.findElement(By.id("fieldFBuildYear")).sendKeys("1969");//įvedamas namo pastatymo metai
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[5]/div/div/div[2]/div/div[1]/div[2]")).click();//namo tipas - gyvenamasis
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[6]/div[1]/div/div[2]/div/div[5]/div[2]")).click();//pastato tipas - karkasinis
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[6]/div[2]/div/div[2]/div/div[1]/div[2]")).click();//pastato įrengimas - įrengtas
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[6]/div[4]/div[1]/div/label")).click();//centrinis šildymas
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[8]/div[7]/label/a/span[2]/input")).sendKeys("C:\\Users\\Beatrice\\Desktop\\house.jpg");//įkelti nuotraukas
        _globalDriver.findElement(By.id("priceValue")).sendKeys("420000");//įvedama kaina
        _globalDriver.findElement(By.id("fieldphone")).sendKeys("61234567");//įvedamas telefono numeris
        _globalDriver.findElement(By.id("fieldemail")).sendKeys(_email);//įvedamas elektroninio pašto adresas
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[10]/div[4]/div/label/span")).click();//išjungti kontaktavimo el.paštu funkciją skelbime
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div[3]/div[1]/form/div[1]/div[10]/div[7]/div/label/span")).click();//sutikti su portalo taisyklėmis
        _globalDriver.findElement(By.id("formSubmitButton")).click();//skelbimas pateikiamas tinklapiui
        _globalDriver.close();
    }

    @Test// registracija ir atsijungimas
    public void testTC0104() {
        _globalDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        _globalDriver.findElement(By.id("onetrust-accept-btn-handler")).click();//išjungiami cookies
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[1]/ul/li[5]/a")).click();//pasirenkamas meniu
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[5]/ul[1]/li[2]/a")).click();//pasirenkama prisijungti
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div/div[4]/a[2]")).click();//pasirenkama registruotis
        _globalDriver.findElement(By.id("userName")).sendKeys(_email);//suvedamas pasto adresas
        _globalDriver.findElement(By.id("password")).sendKeys(_password);//suvedamas slaptazodis
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div[1]/form/button")).click();//paspaudziama "Registruotis"
        _globalDriver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[1]/ul/li[5]/a/span[1]")).click();//atidaromas meniu
        _globalDriver.findElement(By.xpath("/html/body/div[2]/div/div[5]/ul[1]/li[10]/a")).click();//pasirenkama atsijungti
        _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[1]/ul/li[5]/a")).click();//atidaromas meniu
        WebElement resultText = _globalDriver.findElement(By.xpath("/html/body/div[1]/div/div[5]/ul[1]/li[2]/a"));//Prisijungti mygtukas
        String mygtukoPavadinimas = resultText.getText();
        mygtukoPavadinimas.contains("Prisijungti");
        _globalDriver.close();
    }

    @Test// butų vertės skaičiuoklė
    public void testTC0105() {
        _globalDriverFull.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        _globalDriverFull.findElement(By.id("onetrust-accept-btn-handler")).click();//išjungiami cookies
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[3]/div[3]/div/ul/li[5]/a")).click();//Atidaroma "Butų vertės skaičiuoklė"
        _globalDriverFull.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        _globalDriverFull.findElement(By.id("address")).sendKeys("Aušros gatvė");
        _globalDriverFull.findElement(By.id("address")).sendKeys((Keys.DOWN));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _globalDriverFull.findElement(By.id("address")).sendKeys((Keys.ENTER));
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _globalDriverFull.findElement(By.id("address")).sendKeys((Keys.ENTER));
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        _globalDriverFull.findElement(By.id("street_number")).sendKeys("12");//Suvedamas buto namo numeris
        _globalDriverFull.findElement(By.id("area")).sendKeys("64");//Suvedamas buto namo numeris
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[2]/div[3]/span/span")).click();//Išskleidžiamas pastato tipo drop down
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[2]/div[3]/span/ul/li[1]")).click();//Pasirenkamas namo tipas
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[2]/div[4]/span/span")).click();//Išskleidžiamaskambarių skaičiaus drop down
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[2]/div[4]/span/ul/li[3]")).click();//Pasirenkamas kambarių skaičius
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[3]/div[2]/input")).sendKeys("1964");//Atidaroma "Butų vertės skaičiuoklė"
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[3]/div[3]/span/span")).click();//Išskleidžimamas įrengimas
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[3]/div[3]/span/ul/li[1]")).click();//Pasirenkamas įrengimas
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[3]/div[4]/span/span")).click();//Išskleidžiamas aukštų drop down
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[3]/div[4]/span/ul/li[5]")).click();//Pasirenkamas aukštas
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[3]/div[5]/span/span")).click();//Išskleidžiamas viso aukštų drop down
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[1]/div/div[3]/div[5]/span/ul/li[6]")).click();//Pasirenkamas namo aukštis
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[3]/div[1]/ul/li[1]/label/span")).click();//Pasirenkamas centrinis šildymas
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[3]/div[2]/ul/li[10]/label/span")).click();//Yra balkonas
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[3]/div[2]/ul/li[12]/label/span")).click();//Yra rūsys
        _globalDriverFull.findElement(By.id("email")).sendKeys(_email);//Įvedamas pašto adresas
        _globalDriverFull.findElement(By.xpath("/html/body/div[1]/div[4]/div[1]/div/form/div[4]/div[2]/button")).click();//Paspaudžiamas "Gauti būsto įvertinimą"
        _globalDriver.close();
    }





}
