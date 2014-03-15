
package com.vi.comun.controller; 

import com.vi.comun.locator.ParameterLocator;
import com.vi.util.FacesUtil;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 * @author jerviver21
 */
@ManagedBean(name="logController")
@SessionScoped
public class LogController {
    ParameterLocator locator;
    private StreamedContent file;
    private boolean renderDownload = false;
    private String rutaArchivo;
    private String textoLog;
    
    public String generarLog(){
        Calendar calendario = Calendar.getInstance();
        String ano = String.format("%04d",calendario.get(Calendar.YEAR));
        String mes = String.format("%02d",calendario.get(Calendar.MONTH));
        locator = ParameterLocator.getInstance();
        rutaArchivo = locator.getParameter("rutaLog")+File.separator+"VI_"+ano+mes+".log";
        if(!(new File(rutaArchivo).exists())){
            FacesUtil.addMessage(FacesUtil.ERROR, " No existe el log VI_"+ano+mes+".log");
            return null;
        }
        renderDownload = true;
        return null;
    }
    
    public void descargar(ActionEvent evt)throws Exception{
        if(rutaArchivo == null){
            return;
        }
        FileInputStream stream = new FileInputStream(rutaArchivo);
        file = new DefaultStreamedContent(stream, "application/txt", "log.txt");
        renderDownload = false;
    }
    
    /**
     * @return the file
     */
    public StreamedContent getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(StreamedContent file) {
        this.file = file;
    }
    
    /**
     * @return the renderDownload
     */
    public boolean isRenderDownload() {
        return renderDownload;
    }

    /**
     * @param renderDownload the renderDownload to set
     */
    public void setRenderDownload(boolean renderDownload) {
        this.renderDownload = renderDownload;
    }

    /**
     * @return the textoLog
     */
    public String getTextoLog() {
        return textoLog;
    }

    /**
     * @param textoLog the textoLog to set
     */
    public void setTextoLog(String textoLog) {
        this.textoLog = textoLog;
    }
    
}
