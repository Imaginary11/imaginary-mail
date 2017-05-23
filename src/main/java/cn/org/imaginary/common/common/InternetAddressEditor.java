package cn.org.imaginary.common.common;

import cn.org.imaginary.common.util.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.beans.PropertyEditorSupport;

/**
 * Author    : imaginary
 * Date      : 2017/5/23 16 35
 * Version   : V1.0
 * Desc      :
 */
public class InternetAddressEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                setValue(new InternetAddress(text));
            }
            catch (AddressException ex) {
                throw new IllegalArgumentException("Could not parse mail address: " + ex.getMessage());
            }
        }
        else {
            setValue(null);
        }
    }

    @Override
    public String getAsText() {
        InternetAddress value = (InternetAddress) getValue();
        return (value != null ? value.toUnicodeString() : "");
    }

}

