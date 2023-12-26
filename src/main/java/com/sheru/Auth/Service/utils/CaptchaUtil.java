package com.sheru.Auth.Service.utils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.SquigglesBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class CaptchaUtil {
    public static Captcha createCaptcha(Integer height, Integer width) {
        return new Captcha.Builder(width, height)
                .addBackground(new SquigglesBackgroundProducer())
                .addText(new DefaultTextProducer(), new DefaultWordRenderer())
                .addNoise(new CurvedLineNoiseProducer())
                .build();
    }

    public static String encodeCaptcha(Captcha captcha) {
        String image = null;
        try {
            ByteArrayOutputStream bos= new ByteArrayOutputStream();
            ImageIO.write(captcha.getImage(),"jpg", bos);
            byte[] byteArray= Base64.getEncoder().encode(bos.toByteArray());
            image = new String(byteArray);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return image;
    }
}
