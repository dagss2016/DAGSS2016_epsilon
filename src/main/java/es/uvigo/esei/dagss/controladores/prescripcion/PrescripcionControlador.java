/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.controladores.prescripcion;

import es.uvigo.esei.dagss.controladores.cita.CitaControlador;
import es.uvigo.esei.dagss.controladores.medico.MedicoControlador;
import es.uvigo.esei.dagss.dominio.daos.MedicamentoDAO;
import es.uvigo.esei.dagss.dominio.daos.PrescripcionDAO;
import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author rbr
 */
@Named(value = "prescripcionControlador")
@SessionScoped
public class PrescripcionControlador implements Serializable {
    
    //PrescripcionDAO prescripcionDAO;

    List<Prescripcion> prescripciones;
    Prescripcion prescripcionActual;
    String numeroTarjetaSanitaria;
    
    @Inject
    private MedicoControlador medicoControlador;
    
    @Inject
    MedicamentoDAO medicamentoDAO;
    
    @Inject
    private CitaControlador citaControlador;
  
    @EJB
    private PrescripcionDAO prescripcionDAO;
    
    /**
     * Creates a new instance of PrescripcionControlador
     */
    public PrescripcionControlador() {
    }
    @PostConstruct
    public void inicializar() {       
    }
    
    public void prescripcionesPaciente() {
        
        this.prescripciones = prescripcionDAO.buscarPorPaciente(citaControlador.getCitaActual().getPaciente().getId(),medicoControlador.getMedicoActual().getId());
    }
    
    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones;
    }

    public Prescripcion getPrescripcionActual() {
        return prescripcionActual;
    }

    public void setPrescripcionActual(Prescripcion prescripcionActual) {
        this.prescripcionActual = prescripcionActual;
    }

    public String getNumeroTarjetaSanitaria() {
        return numeroTarjetaSanitaria;
    }

    public void setNumeroTarjetaSanitaria(String numeroTarjetaSanitaria) {
        this.numeroTarjetaSanitaria = numeroTarjetaSanitaria;
    }
    
    public void doBuscarPacientePorTarjetaSanitaria() {
        setPrescripciones(prescripcionDAO.buscarPorTarjetaSanitaria(numeroTarjetaSanitaria));
    }
    
    public void doEliminar() {
        prescripcionDAO.eliminar(prescripcionActual);
        prescripciones = prescripcionDAO.buscarPorPaciente(citaControlador.getCitaActual().getPaciente().getId(),medicoControlador.getMedicoActual().getId());
    }
    
    public void doNuevo() {
        prescripcionActual = new Prescripcion(); // Prescripción vacía
        prescripcionActual.setMedico(medicoControlador.getMedicoActual());
        prescripcionActual.setPaciente(citaControlador.getCitaActual().getPaciente());
        /*medicoActual.setFechaAlta(Calendar.getInstance().getTime());
        medicoActual.setUltimoAcceso(medicoActual.getFechaAlta());*/
    }
    
    public void doEditar(Prescripcion prescripcion) {
        prescripcionActual = prescripcion;   // Otra alternativa: volver a refrescarlos desde el DAO
    }
    
    public void doGuardarNuevo() {  
        //Controlamos que la fecha fin no sea menor que fecha inicial
        Date inicio = (Date)prescripcionActual.getFechaInicio();
        Date fin = (Date)prescripcionActual.getFechaFin();
        if (inicio.before(fin)){
             prescripcionActual = prescripcionDAO.crear(prescripcionActual);
             // Actualiza lista 
             prescripciones = prescripcionDAO.buscarPorPaciente(citaControlador.getCitaActual().getPaciente().getId(),medicoControlador.getMedicoActual().getId());
        } else {  
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fecha inicial no puede ser mayor que fecha fin, gañán", ""));
        }
    }
    
    public void doGuardarEditado() {
        //Comprobaciones
        prescripcionActual = prescripcionDAO.actualizar(prescripcionActual);

        // Actualiza lista a mostrar
        prescripciones = prescripcionDAO.buscarPorPaciente(citaControlador.getCitaActual().getPaciente().getId(),medicoControlador.getMedicoActual().getId());        
    }
    
    public String doVolver() {
        return "../index?faces-redirect=true";
    }
    
    public List<Medicamento> getMedicamentos() {
        return medicamentoDAO.buscarTodos();
    }
  
}
