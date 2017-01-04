/*
 Proyecto Java EE, DAGSS-2014
 */
package es.uvigo.esei.dagss.dominio.daos;

import es.uvigo.esei.dagss.dominio.entidades.Medicamento;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

@Stateless
@LocalBean
public class MedicamentoDAO extends GenericoDAO<Medicamento> {

    public List<Medicamento> buscarByNombre(String patron) {
        TypedQuery<Medicamento> q = em.createQuery("SELECT m FROM Medicamento AS m "
                + "  WHERE (m.nombre LIKE :patron) OR "
                + "        (m.fabricante LIKE :patron) OR (m.principioActivo LIKE :patron) OR (m.familia LIKE :patron) ", Medicamento.class);
        q.setParameter("patron", "%" + patron + "%");
        return q.getResultList();
    }
}
