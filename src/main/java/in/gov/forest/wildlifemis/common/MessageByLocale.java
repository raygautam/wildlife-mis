package in.gov.forest.wildlifemis.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Get messagae in all supported landguage based on the locale messaage
 *
 * */

@Service
public class MessageByLocale {
    private MessageSource messageSource;

    @Autowired
    public MessageByLocale(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(String id) {
        Locale locale = LocaleContextHolder.getLocale();

        return messageSource.getMessage(id, null, id, locale);
    }

    public String getMessage(String id, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(id, args, id, locale);
    }
}

