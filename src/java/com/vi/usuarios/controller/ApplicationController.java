
package com.vi.usuarios.controller;

import com.vi.comun.locator.ParameterLocator;
import com.vi.comun.services.CommonServicesLocal;
import com.vi.locator.ComboLocator;
import com.vi.util.FacesUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * @author Jerson Viveros
 */
@ManagedBean(name="applicationController" ,eager=true)
@ApplicationScoped
public class ApplicationController {
    ParameterLocator paramLocator;
    ComboLocator comboLocator;
    
    //Roles que condicionan los componentes de la vista
    private String ROL_MASTER;
    
    //Define la plantilla y layout de la aplicacion
    private String estiloA1 = "estilo1A1.css";
    private String estiloA2 = "estilo3A2.css";
    private String estiloB1 = "estilo3B1.css";
    private String estiloB2 = "estilo1B2.css";
    private String estiloC1 = "estilo1A1.css";
    private String plan1 = "plantillaA1.xhtml";
    private String plan2 = "plantillaB2.xhtml";
    private String plan3 = "plantillaC1.xhtml";
    private String theme = "glass-x";
    private String url;
    
    
    
    
    private Map<String, String> themes; 
    private List<SelectItem> plantillas;
    private List<SelectItem> estilos;
    
    

    @EJB
    private CommonServicesLocal commonServices;

    

    @PostConstruct
    public void init(){
        System.out.println("Iniciando la aplicación...");
        paramLocator = ParameterLocator.getInstance();
        comboLocator = ComboLocator.getInstance();
        paramLocator.setCommonFacade(commonServices);
        comboLocator.setCommonFacade(commonServices);
        
        
        commonServices.updateEstructuraMenus();
        System.out.println("Menus actualizados...");
        //clasificadoTimer.initTimer();
        //System.out.println("Timer iniciado...");
        url = paramLocator.getParameter("url");

        ROL_MASTER = paramLocator.getParameter("rolMaster");
        
        themes = new TreeMap<String, String>();  
        themes.put("Afterdark", "afterdark");  
        themes.put("Casablanca", "casablanca");  
        themes.put("South-Street", "south-street");  
        themes.put("UI-Lightness", "ui-lightness");  
        themes.put("Blue-Sky", "bluesky"); 
        themes.put("Afternoon", "afternoon"); 
        themes.put("Cupertino", "cupertino"); 
        themes.put("Redmond", "redmond"); 
        themes.put("Glass-X", "glass-x"); 
        
        
        Map mPlans = new HashMap();
        mPlans.put("plantillaA1.xhtml", "Plantilla A1");
        mPlans.put("plantillaA2.xhtml", "Plantilla A2");
        mPlans.put("plantillaB1.xhtml", "Plantilla B1");
        mPlans.put("plantillaC1.xhtml", "Plantilla C1");
        plantillas = FacesUtil.getSelectsItem(mPlans);
        
       
        
    }
    
    
    public void cambiarTema(ValueChangeEvent event){
        theme = (String)event.getNewValue();
    }
    
    public String preCargar(){
        return null;
    }
    
    public String cambiarLookandfeel(){
        return null;
    }


    /**
     * @return the ROL_MASTER
     */
    public String getROL_MASTER() {
        return ROL_MASTER;
    }   

   



    /**
     * @return the themes
     */
    public Map<String, String> getThemes() {
        return themes;
    }

    /**
     * @param themes the themes to set
     */
    public void setThemes(Map<String, String> themes) {
        this.themes = themes;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }


    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    

    /**
     * @return the plan1
     */
    public String getPlan1() {
        return plan1;
    }

    /**
     * @param plan1 the plan1 to set
     */
    public void setPlan1(String plan1) {
        this.plan1 = plan1;
    }

    /**
     * @return the plan2
     */
    public String getPlan2() {
        return plan2;
    }

    /**
     * @param plan2 the plan2 to set
     */
    public void setPlan2(String plan2) {
        this.plan2 = plan2;
    }

    /**
     * @return the plan3
     */
    public String getPlan3() {
        return plan3;
    }

    /**
     * @param plan3 the plan3 to set
     */
    public void setPlan3(String plan3) {
        this.plan3 = plan3;
    }

    /**
     * @return the plantillas
     */
    public List<SelectItem> getPlantillas() {
        return plantillas;
    }

    /**
     * @param plantillas the plantillas to set
     */
    public void setPlantillas(List<SelectItem> plantillas) {
        this.plantillas = plantillas;
    }

    /**
     * @return the estilos
     */
    public List<SelectItem> getEstilos() {
        return estilos;
    }

    /**
     * @param estilos the estilos to set
     */
    public void setEstilos(List<SelectItem> estilos) {
        this.estilos = estilos;
    }

    /**
     * @return the estiloA1
     */
    public String getEstiloA1() {
        return estiloA1;
    }

    /**
     * @param estiloA1 the estiloA1 to set
     */
    public void setEstiloA1(String estiloA1) {
        this.estiloA1 = estiloA1;
    }

    /**
     * @return the estiloA2
     */
    public String getEstiloA2() {
        return estiloA2;
    }

    /**
     * @param estiloA2 the estiloA2 to set
     */
    public void setEstiloA2(String estiloA2) {
        this.estiloA2 = estiloA2;
    }

    /**
     * @return the estiloB1
     */
    public String getEstiloB1() {
        return estiloB1;
    }

    /**
     * @param estiloB1 the estiloB1 to set
     */
    public void setEstiloB1(String estiloB1) {
        this.estiloB1 = estiloB1;
    }

    /**
     * @return the estiloC1
     */
    public String getEstiloC1() {
        return estiloC1;
    }

    /**
     * @param estiloC1 the estiloC1 to set
     */
    public void setEstiloC1(String estiloC1) {
        this.estiloC1 = estiloC1;
    }

    /**
     * @return the estiloB2
     */
    public String getEstiloB2() {
        return estiloB2;
    }

    /**
     * @param estiloB2 the estiloB2 to set
     */
    public void setEstiloB2(String estiloB2) {
        this.estiloB2 = estiloB2;
    }



    

}
