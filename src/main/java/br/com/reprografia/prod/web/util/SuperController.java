package br.com.reprografia.prod.web.util;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.exception.OAuthException;
import br.com.reprografia.prod.model.entity.ResultMessage;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Superclasse de todo Controlador do sistema.
 */

@ControllerAdvice
public abstract class SuperController {

    protected Logger log = Logger.getLogger(this.getClass());

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd/MM/yyyy"), true));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(OAuthException.class)
    @ResponseBody
    public ResultMessage appOauthException(HttpServletRequest req, Exception ex) {
        System.err.println("Request: " + req.getRequestURL() + " -> Lancou: " + ex);
        return new ResultMessage(OAuthException.class.getSimpleName(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public ResultMessage appBusinessException(HttpServletRequest req, BusinessException ex) {
        System.err.println("Request: " + req.getRequestURL() + " -> Lancou: " + ex);
        return new ResultMessage(BusinessException.class.getSimpleName(), ex.getCodigoException(), ex.getMessage());
    }
}