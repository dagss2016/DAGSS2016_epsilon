/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.controladores.tratamiento;

import es.uvigo.esei.dagss.controladores.receta.*;
import es.uvigo.esei.dagss.controladores.prescripcion.PrescripcionControlador;
import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.daos.PrescripcionDAO;
import es.uvigo.esei.dagss.dominio.daos.RecetaDAO;
import es.uvigo.esei.dagss.dominio.entidades.EstadoReceta;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Inject;


/**
 *
 * @author rbr
 */
@Named(value = "tratamientoControlador")
@SessionScoped
public class TratamientoControlador implements Serializable {
 
    
    @Inject
    MedicamentoDAO medicamentoDAO;
   
    @Inject
    private PrescripcionControlador prescripcionControlador;
    
    @Inject
    private RecetaControlador recetaControlador;
    
    
    /**
     * Creates a new instance of RecetaControlador
     */
   
    public TratamientoControlador() {
    }
    
    @PostConstruct
    public void inicializar() {
    }
   
    public void doGuardarNuevo() {
        
     
        Prescripcion prescripcionActual = prescripcionControlador.getPrescripcionActual();
        prescripcionControlador.doGuardarNuevo();
        //Calculamos numero de botes de medicamento necesarios
        //Calendar calendario = Calendar.getInstance();
        //Date inicio = (Date)prescripcionActual.getFechaInicio();
        //Date fin = (Date)prescripcionActual.getFechaFin();
        //calendario.setTime(inicio);
        //int numDias = 0;
        //while(!calendario.getTime().after(fin)) {
          //  numDias++;
        //}
        //int dosisTotales = numDias * prescripcionActual.getDosis();
        //int numBotes = (int)Math.round(dosisTotales / prescripcionActual.getMedicamento().getNumeroDosis());
        //for (int i=1;i==numBotes; i++) {*/
            recetaControlador.doNuevo();
            recetaControlador.getRecetaActual().setPrescripcion(prescripcionControlador.getPrescripcionActual());
            recetaControlador.getRecetaActual().setCantidad(1);
            recetaControlador.getRecetaActual().setEstado(EstadoReceta.GENERADA);
            recetaControlador.getRecetaActual().setInicioValidez(prescripcionActual.getFechaInicio());
            recetaControlador.getRecetaActual().setFinValidez(prescripcionActual.getFechaFin());
            recetaControlador.doGuardarNuevo();  
        //}
        
      
        
    }
    
}
