/*
 Proyecto Java EE, DAGSS-2014
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Receta;
import java.util.Calendar;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class RecetaDAO extends GenericoDAO<Receta> {

    public List<Receta> buscarPorPrescripcion(Long id) {
        TypedQuery<Receta> q = em.createQuery("SELECT r FROM Receta AS r "
                + "  WHERE r.prescripcion.id = :prescripcion_Id", Receta.class);
        q.setParameter("prescripcion_Id", id);
        return q.getResultList();
    }

    // Completar aqui
    public List<Receta> recetasValidas() {
        TypedQuery<Receta> q = em.createQuery("SELECT r FROM Receta AS r "
                + "  WHERE r.inicioValidez <= :hoy AND r.finValidez >= :hoy" + " ORDER BY r.prescripcion.id ", Receta.class);
        q.setParameter("hoy", Calendar.getInstance().getTime());
        return q.getResultList();
    }
}
