package com.eltyl.util.validate.code.image;

import com.eltyl.util.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor  extends AbstractValidateCodeProcessor<ImageCode> {
    /**
     * 发送图形验证码，将其写到相应中
     * @param request
     * @param validateCode
     * @throws Exception
     */
    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
