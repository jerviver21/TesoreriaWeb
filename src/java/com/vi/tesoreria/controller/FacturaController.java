package com.vi.tesoreria.controller;

import com.paideia.tesoreria.dominio.Proveedor;
import com.paideia.tesoreria.dominio.RegistroFactura;
import com.paideia.tesoreria.services.ProveedorService;
import com.vi.comun.util.Log;
import com.vi.locator.ComboLocator;
import com.vi.usuarios.controller.SessionController;
import com.vi.usuarios.dominio.Users;
import com.vi.usuarios.services.UsuariosServices;
import com.vi.usuarios.services.UsuariosServicesLocal;
import com.vi.util.FacesUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.FileUploadEvent;

/**
 * @author Jerson Viveros
 */
@ManagedBean(name="facturaController")
@SessionScoped
public class FacturaController {
    //Datos de la Factura
    private Proveedor provedor;
    private RegistroFactura factura;
    
    //Objetos para guardar información de listas desplegables
    private List<SelectItem> conceptos;
    private List<SelectItem> acuerdos;
    private List<SelectItem> cuentas = new ArrayList<SelectItem>();
    
    private int idConcepto;
    private int idAcuerdo;
    
    //Servicios
    @EJB
    ProveedorService pService;

    @EJB
    UsuariosServicesLocal uService;
    
    //Otros objetos necesarios
    ComboLocator comboLocator;
    //Para solicitar actualizacion de datos a usuarios proveedores
    private boolean solicitarActualizacion = false;
    private boolean usrTesorero = false;
    
    @PostConstruct
    public void init(){
        comboLocator = ComboLocator.getInstance();
        Users usr = ((SessionController)FacesUtil.getManagedBean("#{sessionController}")).getUsuario();
        Set<String> roles = uService.findRolesUser(usr.getUsr());
        setProvedor(pService.findProveedorByRuc(usr.getUsr()));
        setFactura(new RegistroFactura());
        conceptos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_CONCEPTO));
        acuerdos = FacesUtil.getSelectsItem(comboLocator.getDataForCombo(ComboLocator.COMB_ID_ACUERDO));
        
        if(provedor != null){
            getFactura().setProveedor(getProvedor());
            cuentas = FacesUtil.getSelectsItem(provedor.getCuentas());
        }else if(!roles.contains("TESORERO")){
            solicitarActualizacion = true;
        }
        
        if(roles.contains("TESORERO")){
            provedor = new Proveedor();
            usrTesorero = true;
        }

    }
    
    //Validacion
    public void consultarProvedor(){
        setProvedor(pService.findProveedorByRuc(provedor.getRuc()));
        FacesContext fc = FacesContext.getCurrentInstance();
        if(provedor == null){
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No está cargado este RUC en el sistema", "No está cargado este RUC en el sistema");
            fc.addMessage("form:ruc", fm);
        }else{
            getFactura().setProveedor(getProvedor());
            cuentas = FacesUtil.getSelectsItem(provedor.getCuentas());
        }
    }
    
    public String guardar(){
        try {
            FacesUtil.addMessage(FacesUtil.INFO,"Factura Cargada con exito!!");
        }catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR,"Error al guardar la factura");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }
    
    
    
    //Método de cargar imagenes
    public void cargarSoporte(FileUploadEvent event){
        try {
            factura.setExtension(event.getFile().getFileName().replaceAll( ".*\\.(.*)", "$1"));
            factura.setImg(event.getFile().getInputstream());
        } catch (Exception e) {
            FacesUtil.addMessage(FacesUtil.ERROR, "Error al cargar el soporte de la Factura");
            Log.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }  
    }

    /**
     * @return the conceptos
     */
    public List<SelectItem> getConceptos() {
        return conceptos;
    }

    /**
     * @return the acuerdos
     */
    public List<SelectItem> getAcuerdos() {
        return acuerdos;
    }

    /**
     * @return the idConcepto
     */
    public int getIdConcepto() {
        return idConcepto;
    }

    /**
     * @param idConcepto the idConcepto to set
     */
    public void setIdConcepto(int idConcepto) {
        this.idConcepto = idConcepto;
    }

    /**
     * @return the idAcuerdo
     */
    public int getIdAcuerdo() {
        return idAcuerdo;
    }

    /**
     * @param idAcuerdo the idAcuerdo to set
     */
    public void setIdAcuerdo(int idAcuerdo) {
        this.idAcuerdo = idAcuerdo;
    }

    /**
     * @return the provedor
     */
    public Proveedor getProvedor() {
        return provedor;
    }

    /**
     * @param provedor the provedor to set
     */
    public void setProvedor(Proveedor provedor) {
        this.provedor = provedor;
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

    /**
     * @return the solicitarActualizacion
     */
    public boolean isSolicitarActualizacion() {
        return solicitarActualizacion;
    }

    /**
     * @param solicitarActualizacion the solicitarActualizacion to set
     */
    public void setSolicitarActualizacion(boolean solicitarActualizacion) {
        this.solicitarActualizacion = solicitarActualizacion;
    }

    /**
     * @return the usrTesorero
     */
    public boolean isUsrTesorero() {
        return usrTesorero;
    }

    /**
     * @param usrTesorero the usrTesorero to set
     */
    public void setUsrTesorero(boolean usrTesorero) {
        this.usrTesorero = usrTesorero;
    }
    
    public String getDisableRuc() {
        return usrTesorero?"false":"true";
    }

    /**
     * @return the cuentas
     */
    public List<SelectItem> getCuentas() {
        return cuentas;
    }

}
