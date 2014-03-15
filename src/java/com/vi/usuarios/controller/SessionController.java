package com.vi.usuarios.controller;

import com.vi.comun.services.CommonServicesLocal;
import com.vi.util.FacesUtil;
import com.vi.locator.ComboLocator;
import com.vi.usuarios.dominio.Groups;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServicesLocal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.model.menu.MenuModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author Jerson Viveros Aguirre
 */
@ManagedBean(name="sessionController")
@SessionScoped
public class SessionController {
    ComboLocator locator;
    private MenuModel model;
    private Users usuario;
    @EJB
    private UsuariosServicesLocal usersServices;
    
    @EJB
    private CommonServicesLocal commonService;

    private UsernamePasswordAuthenticationToken principal;
    private Collection<GrantedAuthority> grantedAuthorities;
    
    //PArametros de inicio
    private boolean iniciado = false;


    
    @PostConstruct
    public void init(){
        System.out.println("Iniciando session");
        locator = ComboLocator.getInstance();
        principal = (UsernamePasswordAuthenticationToken)FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();

        grantedAuthorities = principal.getAuthorities();
        Set roles = new HashSet<String>();
        for(GrantedAuthority autoridad: grantedAuthorities){
            roles.add(autoridad.getAuthority());
        }
        String nombreUsuario = principal.getName();
        setUsuario(usersServices.findFullUser(nombreUsuario));
        Set grupos = new HashSet<String>();
        for(Groups g : usuario.getGrupos()){
            grupos.add(g.getCodigo());
        }
        usuario.setRolesUsr(roles);
        usuario.setGruposUsr(grupos);
        restartModel();
        
        //Para el monitoreo en base de datos de usuarios autenticados
        HttpSession sesion = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        sesion.setAttribute("usr", principal.getName());
        commonService.insertAudSesion(principal.getName(), "INICIO");
    }
    

    public void restartModel(){
        setModel(FacesUtil.getMenu(getUsuario().getRecursos()));
    }
    

    /**
     * @return the model
     */
    public MenuModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MenuModel model) {
        this.model = model;
    }

    /**
     * @return the usuario
     */
    public Users getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Users usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the principal
     */
    public UsernamePasswordAuthenticationToken getPrincipal() {
        return principal;
    }

    /**
     * @param principal the principal to set
     */
    public void setPrincipal(UsernamePasswordAuthenticationToken principal) {
        this.principal = principal;
    }

    /**
     * @return the grantedAuthorities
     */
    public Collection<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }

    /**
     * @param grantedAuthorities the grantedAuthorities to set
     */
    public void setGrantedAuthorities(Collection<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    /**
     * @return the iniciado
     */
    public boolean isIniciado() {
        return iniciado;
    }

    /**
     * @param iniciado the iniciado to set
     */
    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }

    
    
}
