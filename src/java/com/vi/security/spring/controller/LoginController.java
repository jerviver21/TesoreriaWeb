package com.vi.security.spring.controller;

import com.vi.comun.controller.GeneralController;
import com.vi.util.FacesUtil;
import com.vi.util.SpringUtils;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.WebAttributes;

/**
 * @author Jerson Viveros
 */

@ManagedBean(name="loginController")
@RequestScoped
//public class LoginController implements PhaseListener {
public class LoginController{
    private String mensaje;
    private String urlLicencia = null;
    private String link;
    
    
    public LoginController(){
        Object ex = FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(WebAttributes.AUTHENTICATION_EXCEPTION);
        String usrname = (String)FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get(WebAttributes.LAST_USERNAME);
        if(ex != null){
            if(ex instanceof BadCredentialsException){
                GeneralController generalControler = (GeneralController)FacesUtil.getManagedBean("#{generalController}");
                BadCredentialsException bce = (BadCredentialsException)ex;
                //Queda pendiente la internacionalización del spring security, porque es un recamello, 
                //mensaje = bde.getMessage();
                if(generalControler.getLocale().equals("es")){
                   mensaje="Usuario o Clave invalidos";
                }else{
                   mensaje = "Bad Credentials";
                }
            }else if(ex instanceof DisabledException){
                mensaje =  "El usuario se encuentra inactivo";
                urlLicencia = "/registro/activacion.xhtml";
                link = "Activar"; 
            }else{
                Exception exc = (Exception) ex;
                mensaje =  "Error al tratar de autenticar "+ex.toString();
                exc.printStackTrace();
            }
        }
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().remove(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public String doLogin() throws ServletException, IOException{
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        ServletRequest request = (ServletRequest)context.getRequest();
        
        System.out.println("--> "+request.getParameter("j_password")+" - "+SpringUtils.getPasswordEncoder().encodePassword(request.getParameter("j_password"), null));

        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward((ServletRequest) context.getRequest(),
				(ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }


    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the urlLicencia
     */
    public String getUrlLicencia() {
        return urlLicencia;
    }

    /**
     * @param urlLicencia the urlLicencia to set
     */
    public void setUrlLicencia(String urlLicencia) {
        this.urlLicencia = urlLicencia;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

}
