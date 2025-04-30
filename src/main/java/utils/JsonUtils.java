package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;

public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    //Generic methd to read json and return a list of POJOS

    public static <T> List<T> getDataList(String filePath, Class<T> clazz){
        try{
            return mapper.readValue(new File(filePath),mapper.getTypeFactory().constructCollectionType(List.class,clazz));
        }
        catch(Exception e){
            throw new RuntimeException("Failed to load json from "+filePath, e);
        }
    }
}
