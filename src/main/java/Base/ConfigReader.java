package Base;

import java.io.FileInputStream;
import java.util.Properties;


//This class is based on the signleton pattern which follows the oops principles
//Encapsulation
//Abstraction
public class ConfigReader {

    private static Properties prop;
    private static ConfigReader instance;

    private ConfigReader(){
             prop = new Properties();
                try {
                    FileInputStream input = new FileInputStream("src/main/resources/config-qa.properties");
                    prop.load(input);
                }
                catch (Exception e){
                    e.getMessage();
                }


    }

    public static ConfigReader getInstance(){
        if(instance==null){
            synchronized(ConfigReader.class){
                if(instance==null){
                    instance = new ConfigReader();
                }
            }

        }
        return instance;
    }

    public String get(String key){
        return prop.getProperty(key);
    }


}
