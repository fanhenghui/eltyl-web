package com.eltyl.util.validate.code.image;

import com.eltyl.properties.SecurityProperties;
import com.eltyl.util.VerifyCodeUtils;
import com.eltyl.util.validate.code.ValidateCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * 图片验证码生成器
 */
public class ImageCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 浏览器覆盖配置文件，覆盖默认
     * @param request
     * @return
     */
    @Override
    public ImageCode generate(ServletWebRequest request){
       /* int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width",
                securityProperties.getCode().getImage().getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height",
                securityProperties.getCode().getImage().getHeight());
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();

        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();*/


        //生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);

        // 生成图片
        BufferedImage image=null;
        try{
            int w = 110, h = 40;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image=VerifyCodeUtils.outputImage(w, h, verifyCode);
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ImageCode(image, verifyCode, securityProperties.getCode().getImage().getExpireIn());
    }
    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}
