/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Prescripcion;
import es.uvigo.esei.dagss.dominio.entidades.Tratamiento;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class TratamientoDAO extends GenericoDAO<Prescripcion> {
    
    public List<Tratamiento> buscarPorIDPaciente(Long id) {
        TypedQuery<Tratamiento> q = em.createQuery("SELECT t FROM Tratamiento AS t "
                + "  WHERE t.paciente.id = :id ORDER BY t.fecha ASC", Tratamiento.class);
        q.setParameter("id", id);

        return q.getResultList();
    }
    
}
