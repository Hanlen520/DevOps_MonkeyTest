package com.monkeytest;
import java.io.IOException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.AndroidKeyCode;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import com.monkeytest.log4j.*;

public class Main {
	
	/**
	 * 所有和AppiumDriver相关的操作都必须写在该函数中
	 * @param driver
	 * @throws InterruptedException 
	 */
	public void test(AppiumDriver driver) throws InterruptedException,IOException {
		    	try {
			Thread.sleep(6000);		//等待6s，待应用完全启动
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS); //设置尝试定位控件的最长时间为8s,也就是最多尝试8s
      /*
    	 * 测试逻辑编写，对卖家界面进行monkey测试
    	 */
    	/*
    	 * 登陆管理员，添加卖家账户
    	 */
    	log4j.logger.info("输入管理员账户");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/et_username").sendKeys("root");
    	log4j.logger.info("输入管理员密码");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/et_password").sendKeys("123456");
    	log4j.logger.info("选择管理员登陆");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/rbtn_root").click();
    	log4j.logger.info("点击登录");
    	Thread.sleep(2000);
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/btn_login").click();
    	Thread.sleep(2000);
    	log4j.logger.info("成功登录");
    	log4j.logger.info("跳转到添加顾客商户列表");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/tab_add_user").click();
    	log4j.logger.info("添加卖家");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/username").sendKeys("卖1");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/password1").sendKeys("123456");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/password2").sendKeys("123456");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/rbtn_seller").click();
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/btn_add_user").click();
    	log4j.logger.info("登录卖家账户");
    	
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/et_username").sendKeys("卖1");
    	log4j.logger.info("输入卖家密码");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/et_password").sendKeys("123456");
    	log4j.logger.info("选择商户登陆");
    	driver.findElementById("com.example.xiaolanyun.ldmart:id/rbtn_seller").click();
    	log4j.logger.info("点击登录");
    	Thread.sleep(2000);
    	
    	log4j.logger.info("执行monkey_shell脚本");
    	log4j.logger.info("开始monkey测试");
    	String shpath="./monkey_shell.sh";
    	Process ps =Runtime.getRuntime().exec(shpath);
    	ps.waitFor();
	}
	
	/**
	 * AppiumDriver的初始化逻辑必须写在该函数中
	 * @return
	 */
	public AppiumDriver initAppiumTest() {
		
		AppiumDriver driver=null;
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "apk");
        File app = new File(appDir, "XiaoLanYunMart-v1.1.1-debug-2019-04-10-09-58.apk");
        
        //设置自动化相关参数
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "63a7b810");
        
        //设置安卓系统版本
        capabilities.setCapability("platformVersion", "6.0.1");
        //设置apk路径
        capabilities.setCapability("app", app.getAbsolutePath()); 
        
        //设置app的主包名和主类名
        capabilities.setCapability("appPackage", "com.example.xiaolanyun.ldmart");
        capabilities.setCapability("appActivity", "com.example.xiaolanyun.mart.ui.SplashActivity");
        //设置使用unicode键盘，支持输入中文和特殊字符
        capabilities.setCapability("unicodeKeyboard","true");
        //设置用例执行完成后重置键盘
        capabilities.setCapability("resetKeyboard","true");
        //初始化，server在这里为了避免冲突并行测试的端口，一律改用4721
        try {
			driver = new AppiumDriver(new URL("http://127.0.0.1:4721/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
        return driver;
	}
	
	public void start() throws InterruptedException, IOException {
		test(initAppiumTest());
	}
	
	public static void main(String[] args) throws InterruptedException, IOException {
		Main main = new Main();
		main.start();
	}
	

}