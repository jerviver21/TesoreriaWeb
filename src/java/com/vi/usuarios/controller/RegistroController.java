
package com.vi.usuarios.controller;

import com.paideia.tesoreria.dominio.Empresa;
import com.paideia.tesoreria.services.EmpresaService;
import com.vi.comun.util.Log;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServicesLocal;
import com.vi.util.FacesUtil;
import com.vi.util.SpringUtils;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;

/**
 * @author Jerson Viveros
 */
@ManagedBean(name="registroController")
@SessionScoped
public class RegistroController {
    @EJB
    private EmpresaService service;
    private Users usuarioRegistrar;
    private Empresa empresa;
    
    private boolean linkIngreso = false;

    private boolean activado;
    private String mensaje;
    private String nroUsuario;
    
    @PostConstruct
    public void init(){
        usuarioRegistrar = new Users();
        empresa = new Empresa();
    }

    
    public String registrar(){
        try {
            usuarioRegistrar.setPwd(SpringUtils.getPasswordEncoder().encodePassword(usuarioRegistrar.getClave(), null));
            service.registrarEmpresa(empresa, usuarioRegistrar);
            FacesUtil.addMessage(FacesUtil.INFO,"Revisaremos sus datos y nos pondremos en contacto, para darle a conocer esta herramienta!");
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR,"Error al crear el usuario!");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
            return "/registro.xhtml";
        }
        return "/index.xhtml";
    }
    
    
        

    /**
     * @return the usuarioRegistrar
     */
    public Users getUsuarioRegistrar() {
        return usuarioRegistrar;
    }

    /**
     * @param usuarioRegistrar the usuarioRegistrar to set
     */
    public void setUsuarioRegistrar(Users usuarioRegistrar) {
        this.usuarioRegistrar = usuarioRegistrar;
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

    /**
     * @return the activado
     */
    public boolean isActivado() {
        return activado;
    }

    /**
     * @param activado the activado to set
     */
    public void setActivado(boolean activado) {
        this.activado = activado;
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
     * @return the nroUsuario
     */
    public String getNroUsuario() {
        return nroUsuario;
    }

    /**
     * @param nroUsuario the nroUsuario to set
     */
    public void setNroUsuario(String nroUsuario) {
        this.nroUsuario = nroUsuario;
    }

    /**
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    
    
}

/*public void dispUsuario(){
    FacesContext fc = FacesContext.getCurrentInstance();
    if(!service.isUsuarioDisponible(usuarioRegistrar.getEmail())){
        System.out.println("Usuario ya está registrado!!!");
        FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Este email ya se encuentra registrado!");
        fc.addMessage("form1:mail", fm);
    }
}*/