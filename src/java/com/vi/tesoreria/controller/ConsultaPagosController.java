package com.vi.tesoreria.controller;

import com.paideia.tesoreria.dominio.RegistroFactura;
import com.paideia.tesoreria.dominio.RegistroPago;
import com.paideia.tesoreria.services.FacturaService;
import com.paideia.tesoreria.services.TeleceService;
import com.vi.usuarios.controller.SessionController;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServicesLocal;
import com.vi.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author jerviver21
 */

@ManagedBean(name="consultaPagosController")
@SessionScoped
public class ConsultaPagosController {
    @EJB
    TeleceService tService;
    @EJB 
    UsuariosServicesLocal uService;
    Users usr;
    
    
    //Variables de procesamiento
    private List<RegistroPago> pagos;
    private RegistroPago pago;
    
    
    
    //Variables de búsqueda
    private String ruc;
    
    @PostConstruct
    public void init(){
        setPagos(new ArrayList<RegistroPago>());
        pago = new RegistroPago();

        usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
        Set<String> roles = uService.findRolesUser(usr.getUsr());
        if(roles.contains("USUARIOTSR")){
            setPagos(tService.findPagosByRuc(usr.getUsr()));
        }
    }
    
    
    public String consultarRuc(){
        setPagos(tService.findPagosByRuc(ruc));
        return null;
    }
    
    
    public String navPago(RegistroPago pago){
        this.pago = pago;
        return "/tesoreria/consulta_pago.xhtml";
    }
    
    public String navDetraccion(){
        return "/tesoreria/consulta_detraccion.xhtml";
    }
    
    
    

    /**
     * @return the ruc
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * @param ruc the ruc to set
     */
    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    

    /**
     * @return the pago
     */
    public RegistroPago getPago() {
        return pago;
    }

    /**
     * @return the pagos
     */
    public List<RegistroPago> getPagos() {
        return pagos;
    }

    /**
     * @param pagos the pagos to set
     */
    public void setPagos(List<RegistroPago> pagos) {
        this.pagos = pagos;
    }


    
}
