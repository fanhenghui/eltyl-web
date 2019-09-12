package com.eltyl.util.validate.code.image;

import com.eltyl.util.validate.code.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
@Data
public class ImageCode extends ValidateCode {
    private static final long serialVersionUID = -3157935668300472492L;

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
        super(code, expireTime);
        this.image = image;
    }
}
