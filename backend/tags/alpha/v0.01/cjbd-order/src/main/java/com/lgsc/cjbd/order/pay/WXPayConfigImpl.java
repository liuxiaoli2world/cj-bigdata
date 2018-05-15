package com.lgsc.cjbd.order.pay;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.util.ResourceUtils;

import com.github.wxpay.sdk.WXPayConfig;

public class WXPayConfigImpl implements WXPayConfig {
	
	private static Logger log = LogManager.getLogger(WXPayConfigImpl.class);
	
	private byte[] certData;
	private static WXPayConfigImpl INSTANCE;
	
    public WXPayConfigImpl() {
        try {
        	Configuration config = new PropertiesConfiguration("application.properties");
        	// 方式1 File使用绝对路径。如："D:/file/test.txt"。
//        	File file = new File(config.getString("wxpay.certPath"));
        	// 方式2 getClassLoader()使用相对路径。如："config/test.txt"。
//        	File file = new File(getClass().getClassLoader().getResource(config.getString("wxpay.certPath")).getPath());
        	// 方式3 ResourceUtils支持"classpath:"和"file:"的地址前缀。如：相对路径"classpath:config/test.txt"，绝对路径"file:D:/file/test.txt"。
        	File file = ResourceUtils.getFile(config.getString("wxpay.certPath"));
			InputStream certStream = new FileInputStream(file);
			this.certData = new byte[(int) file.length()];
			certStream.read(this.certData);
			certStream.close();
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		} catch (Exception e) {
			log.error(e);
		}
    }
    
    public static WXPayConfigImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                }
            }
        }
        return INSTANCE;
    }

	@Override
	public String getAppID() {
		return "wx06aec927b0eb8ac4";
	}

	@Override
	public String getMchID() {
		return "1275933101";
	}

	@Override
	public String getKey() {
		return "cjcbsapimima10485761048576104857";
	}

	@Override
	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
	}

	@Override
	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	@Override
	public int getHttpReadTimeoutMs() {
		return 10000;
	}

}
