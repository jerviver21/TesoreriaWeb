/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vi.usuarios.controller;

import com.vi.comun.util.Log;
import com.vi.usuarios.services.UsuariosServicesLocal;
import com.vi.util.FacesUtil;
import com.vi.util.SpringUtils;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author jerviver21
 */
@ManagedBean(name="restClaveController")
@SessionScoped
public class RestClaveController {
    
    private String mail;
    private String clave;
    private String cclave;
    private String codigo;
    
    private boolean linkIngreso;
    
    @EJB
    UsuariosServicesLocal usuarioService;
    
    public String restaurarClave(){
        try {
           String claveEncryp = SpringUtils.getPasswordEncoder().encodePassword(clave, null);
           usuarioService.restaurarClave(claveEncryp, clave, codigo); 
           FacesUtil.addMessage(FacesUtil.INFO, "Su clave ha sido modificada!");
           linkIngreso=true;
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, e.getMessage());
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    public String solicitarRestauracion(){
        try {
           usuarioService.solicitarRestauracion(mail); 
           FacesUtil.addMessage(FacesUtil.INFO, "Se ha enviado a su correo electrónico un código de restauración");
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, e.getMessage());
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return mail;
    }

    /**
     * @param mail the mail to set
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * @return the cclave
     */
    public String getCclave() {
        return cclave;
    }

    /**
     * @param cclave the cclave to set
     */
    public void setCclave(String cclave) {
        this.cclave = cclave;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the linkIngreso
     */
    public boolean isLinkIngreso() {
        return linkIngreso;
    }

    /**
     * @param linkIngreso the linkIngreso to set
     */
    public void setLinkIngreso(boolean linkIngreso) {
        this.linkIngreso = linkIngreso;
    }
    
}
