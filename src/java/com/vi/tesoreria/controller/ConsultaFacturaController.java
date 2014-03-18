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

@ManagedBean(name="consultaFactController")
@SessionScoped
public class ConsultaFacturaController {
    @EJB
    FacturaService fService;
    @EJB
    TeleceService tService;
    @EJB 
    UsuariosServicesLocal uService;
    Users usr;
    
    
    //Variables de procesamiento
    private List<RegistroFactura> facturas;
    private RegistroPago pago;
    private RegistroFactura factura;
    
    
    
    //Variables de búsqueda
    private String ruc;
    private String no;
    private int noDias;
    
    @PostConstruct
    public void init(){
        facturas = new ArrayList<RegistroFactura>();
        pago = new RegistroPago();
        factura = new RegistroFactura();
        usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
        Set<String> roles = uService.findRolesUser(usr.getUsr());
        if(roles.contains("USUARIOTSR")){
            facturas = fService.findFacturasByRUC(usr.getUsr());
        }
    }
    
    
    public String consultarRuc(){
        facturas = fService.findFacturasByRUC(ruc);
        return null;
    }
    
    
    public String consultarFactura(){
        facturas = fService.findFacturaByNo(no);
        for(RegistroFactura reg : facturas){
            System.out.println(reg.getProveedor().getRazonSocial()+" - "+reg.getEstado().getNombre()+"    <------------------->   ");
        }
        return null;
    }
    
    
    public String consultarFacturaPorVencer(){
        facturas = fService.findFacturasXVencer(noDias);
        return null;
    }
    
    public String navPago(RegistroPago pago){
        this.pago = pago;
        return "/tesoreria/consulta_pago.xhtml";
    }
    
    public String navFactura(RegistroFactura factura){
        this.factura = factura;
        return "/tesoreria/consulta_soporte.xhtml";
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
     * @return the no
     */
    public String getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(String no) {
        this.no = no;
    }


    /**
     * @return the facturas
     */
    public List<RegistroFactura> getFacturas() {
        return facturas;
    }

    /**
     * @return the pago
     */
    public RegistroPago getPago() {
        return pago;
    }

    /**
     * @return the noDias
     */
    public int getNoDias() {
        return noDias;
    }

    /**
     * @param noDias the noDias to set
     */
    public void setNoDias(int noDias) {
        this.noDias = noDias;
    }

    /**
     * @return the factura
     */
    public RegistroFactura getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(RegistroFactura factura) {
        this.factura = factura;
    }
    
    
    
    
}
