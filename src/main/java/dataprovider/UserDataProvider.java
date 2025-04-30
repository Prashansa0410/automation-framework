package dataprovider;

import org.testng.annotations.DataProvider;
import pojos.User;
import utils.JsonUtils;

import java.util.List;

public class UserDataProvider {

    @DataProvider(name="userDataProvider")
    public Object[][] provideUserData(){
        List<User> users = JsonUtils.getDataList("src/main/resources/testdata/loginData.json",User.class);
        Object[][] data = new Object[users.size()][1];

        for(int i=0;i<users.size();i++){
            data[i][0] = users.get(i);
        }
        return data;
    }

    @DataProvider(name="IncorrectUserData")
    public Object[][] provideIncorrectUserData(){
        Object[][] data = new Object[1][2];
        data[0][0] = "IncorrectEmail@email.com";
        data[0][1] ="IncorrectPwd";
        return data;
    }
}
