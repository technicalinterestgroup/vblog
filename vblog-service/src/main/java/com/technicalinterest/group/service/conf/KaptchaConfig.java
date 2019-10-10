package com.technicalinterest.group.service.conf;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @package: com.technicalinterest.group.service.conf
 * @className: KaptchaConfig
 * @description:
 * @author: Shuyu.Wang
 * @date: 2019-10-10 13:04
 * @since: 0.1
 **/
@Configuration
public class KaptchaConfig {
	@Bean
	public DefaultKaptcha producer(){
		Properties properties = new Properties();
		//边框
		properties.put("kaptcha.border", "no");
		//边框粗细
//		properties.put("kaptcha.border.thinkness", "0");
		//文字间隔
		properties.put("kaptcha.textproducer.char.space", "10");
		//验证码长度
		properties.put("kaptcha.textproducer.char.length","4");
		//图片高
		properties.put("kaptcha.image.height","40");
		//图片宽
		properties.put("kaptcha.image.width","140");
		//字体实颜色
		properties.put("kaptcha.textproducer.font.color","76,197,253");
		//字体大小
		properties.put("kaptcha.textproducer.font.size","30");
        //干扰实现类
//		properties.put("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
		//干扰颜色
		properties.put("kaptcha.noise.color","245,245,245");
		//图片样式
		properties.put("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.ShadowGimpy");
		Config config = new Config(properties);
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

}
