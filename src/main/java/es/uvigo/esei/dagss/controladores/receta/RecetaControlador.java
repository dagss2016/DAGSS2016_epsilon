/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.controladores.receta;

import es.uvigo.esei.dagss.controladores.prescripcion.PrescripcionControlador;
import es.uvigo.esei.dagss.dominio.daos.RecetaDAO;
import es.uvigo.esei.dagss.dominio.entidades.Receta;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;


/**
 *
 * @author rbr
 */
@Named(value = "recetaControlador")
@SessionScoped
public class RecetaControlador implements Serializable {
    
    List<Receta> recetas;
    List<Receta> recetasPrescripcion;
    Receta recetaActual;
    
    @Inject
    private PrescripcionControlador prescripcionControlador;
  
    @EJB
    private RecetaDAO recetaDAO;
    
    /**
     * Creates a new instance of RecetaControlador
     */
    public RecetaControlador() {
    }
    
    @PostConstruct
    public void inicializar() {  
        
        this.recetas = recetaDAO.buscarTodos();
    }
    
    public void buscarPorPrescripcion() {
        
        this.recetasPrescripcion = recetaDAO.buscarPorPrescripcion(prescripcionControlador.getPrescripcionActual().getId());
    }
    
    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public Receta getRecetaActual() {
        return recetaActual;
    }

    public void setRecetaActual(Receta recetaActual) {
        this.recetaActual = recetaActual;
    }
    
    public void doEliminar() {
        recetaDAO.eliminar(recetaActual);
        //recetas = recetaDAO.buscarPorPrescripcion(prescripcionControlador.getPrescripcionActual().getId());
        recetas = recetaDAO.buscarTodos();
    }
    
    public void doNuevo() {
        recetaActual = new Receta(); // Receta vacía
       
    }
    
    public void doEditar(Receta receta) {
        recetaActual = receta;   // Otra alternativa: volver a refrescarlos desde el DAO
    }
    
    public void doGuardarNuevo() {  
        recetaActual = recetaDAO.crear(recetaActual);
        // Actualiza lista 
        //recetas = recetaDAO.buscarPorPrescripcion(prescripcionControlador.getPrescripcionActual().getId());
        recetas = recetaDAO.buscarTodos();
    }
    
    public void doGuardarEditado() {
        //Comprobaciones
        recetaActual = recetaDAO.actualizar(recetaActual);
        //recetas = recetaDAO.buscarPorPrescripcion(prescripcionControlador.getPrescripcionActual().getId());
        recetas = recetaDAO.buscarTodos();
    }
    
    public String doVolver() {
        return "../index?faces-redirect=true";
    }

    public List<Receta> getRecetasPrescripcion() {
        return recetasPrescripcion;
    }

    public void setRecetasPrescripcion(List<Receta> recetasPrescripcion) {
        this.recetasPrescripcion = recetasPrescripcion;
    }
    
    
}
