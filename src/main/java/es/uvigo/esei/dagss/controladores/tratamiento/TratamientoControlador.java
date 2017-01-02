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
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        //Calculamos numero de dias
        Date inicio = (Date)prescripcionActual.getFechaInicio();
        Date fin = (Date)prescripcionActual.getFechaFin();
        int numDias = calculaDias(inicio,fin);
        //Calculamos numero de botes necesarios
      
        int dosisTotales = numDias * prescripcionActual.getDosis();
        int numBotes = (int)Math.round(dosisTotales / prescripcionActual.getMedicamento().getNumeroDosis());
        for (int i=1;i<=numBotes; i++) {
            Receta receta = new Receta();
            receta.setPrescripcion(prescripcionActual);
            receta.setCantidad(1);
            receta.setEstado(EstadoReceta.GENERADA);
            receta.setInicioValidez(prescripcionActual.getFechaInicio());
            receta.setFinValidez(prescripcionActual.getFechaFin());
            prescripcionControlador.getPrescripcionActual().anadirReceta(receta);
        }        
        prescripcionControlador.doGuardarNuevo();
    }
    
    //Cuenta dias entre dos fechas.
    public int calculaDias(Date inicio,Date fin) {    
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(inicio);
        int dias = 1; //Se inicializa a 1 para que cuente el dia actual
        while(!calendario.getTime().after(fin)) {
            calendario.add(Calendar.DAY_OF_MONTH, 1); 
            dias++;
        }      
        return dias;        
    }
    
}
