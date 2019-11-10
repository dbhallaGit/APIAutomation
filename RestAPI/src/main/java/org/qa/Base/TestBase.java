package org.qa.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	public Properties prop;
	public TestBase(){
		prop=new Properties();
		try {
			FileInputStream PropertyFile=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/org/qa/config/config.properties");
			prop.load(PropertyFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

}
