package com.vi.comun.controller;

import com.vi.comun.dominio.AudMail;
import com.vi.comun.dominio.Parametro;
import com.vi.comun.services.MailService;
import com.vi.comun.util.Log;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author jerviver21
 */
@ManagedBean(name="mailController")
@SessionScoped
public class MailController {
    
    @EJB
    MailService service;
    
    private AudMail datosMail;
    
    
    @PostConstruct
    public void init(){
        datosMail = new AudMail();
    }
    
    public void enviarMail(){
        try {
            service.enviarMail(datosMail);
            FacesUtil.addMessage(FacesUtil.INFO, " email enviado al destinatario!");
            datosMail = new AudMail();
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, " Error al enviar el mail");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    /**
     * @return the datosMail
     */
    public AudMail getDatosMail() {
        return datosMail;
    }

    /**
     * @param datosMail the datosMail to set
     */
    public void setDatosMail(AudMail datosMail) {
        this.datosMail = datosMail;
    }
    
    
}
