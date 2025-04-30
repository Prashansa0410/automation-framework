import Base.BaseTest;
import Base.ConfigReader;

public class launchTest extends BaseTest {

    public void launchHomePageTest(){

        driver.get(ConfigReader.getInstance().get("baseUrl"));
        
    }
}
