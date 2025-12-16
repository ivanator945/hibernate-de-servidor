package daoImp.Hib;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import dao.IAsignaturasDAO;
import dto.AsignaturaDTO;
import entities.AsignaturasEntity;
import utils.DBUtils;

public class AsignaturasDAOImplHib implements IAsignaturasDAO {

    @Override
    public ArrayList<AsignaturaDTO> obtenerTodasAsignaturas() {

        String jpql = "SELECT new dto.AsignaturaDTO(a.id, a.nombre, a.curso, a.tasa, a.activo) "
                + "FROM AsignaturasEntity a "
                + "WHERE a.activo = 1";

        SessionFactory factory = DBUtils.creadorSessionFactory();
        Session s = factory.getCurrentSession();
        s.beginTransaction();

        Query<AsignaturaDTO> query = s.createQuery(jpql, AsignaturaDTO.class);
        List<AsignaturaDTO> lista = query.getResultList();

        s.getTransaction().commit();
        s.close();

        return new ArrayList<>(lista);
    }



    @Override
    public ArrayList<AsignaturaDTO> obtenerAsignaturasPorFiltros(
                String id, String nombre, String curso, String tasa, int activo) {

       
        String jpql = "SELECT new dto.AsignaturaDTO(a.id, a.nombre, a.curso, a.tasa, a.activo) "
                    + "FROM AsignaturasEntity a "
                    + "WHERE CAST(a.id AS string) LIKE :id "
                    + "AND a.nombre LIKE :nombre "
                    + "AND a.curso LIKE :curso "
                    + "AND CAST(a.tasa AS string) LIKE :tasa "
                    + "AND a.activo = :activo";

        SessionFactory factory = DBUtils.creadorSessionFactory();
        Session s = null; 

        try {
            
            s = factory.openSession();
            s.beginTransaction();

            Query<AsignaturaDTO> query = s.createQuery(jpql, AsignaturaDTO.class)
                    .setParameter("id", "%" + id + "%")
                    .setParameter("nombre", "%" + nombre + "%")
                    .setParameter("curso", "%" + curso + "%")
                    .setParameter("tasa", "%" + tasa + "%")
                    .setParameter("activo", activo);

            List<AsignaturaDTO> lista = query.getResultList();

            s.getTransaction().commit();

            return new ArrayList<>(lista);

        } catch (Exception e) {
         
            if (s != null && s.getTransaction().isActive()) {
                s.getTransaction().rollback();
            }
            e.printStackTrace(); 
            return new ArrayList<>();
        } finally {
        
            if (s != null) {
                s.close();
            }
        }
    }

    @Override
    public int insertarAsignatura(String id, String nombre, String curso, String tasa, int activo) {

        SessionFactory factory = DBUtils.creadorSessionFactory();
        Session s = factory.getCurrentSession();
        s.beginTransaction();

        
        AsignaturasEntity a = new AsignaturasEntity(
                nombre,
                curso,
                Double.parseDouble(tasa),
                activo
        );

        Integer idPk = (Integer) s.save(a);

        s.getTransaction().commit();
        s.close();

        return idPk;
    }
    @Override
    public int actualizarAsignatura(String id, String nombre, String curso, String tasa, int activo) {

        SessionFactory factory = DBUtils.creadorSessionFactory();
        Session s = factory.getCurrentSession();
        s.beginTransaction();

        AsignaturasEntity a = s.find(AsignaturasEntity.class, Integer.parseInt(id));

        a.setNombre(nombre);
        a.setCurso(curso);
        a.setTasa(Double.parseDouble(tasa));
        a.setActivo(activo);

        s.update(a);

        s.getTransaction().commit();
        s.close();

        return a.getId();
    }

    @Override
    public int borrarAsignatura(String id) {

        SessionFactory factory = DBUtils.creadorSessionFactory();
        Session s = factory.getCurrentSession();
        s.beginTransaction();

        AsignaturasEntity a = s.get(AsignaturasEntity.class, Integer.parseInt(id));
        a.setActivo(0);

        s.update(a);
        s.getTransaction().commit();
        s.close();

        return a.getId();
    }

    @Override
    public double obtenerTasaAsignatura(String idAsignatura) {

        String jpql = "SELECT a.tasa FROM AsignaturasEntity a WHERE a.id = :id AND a.activo = 1";

        SessionFactory factory = DBUtils.creadorSessionFactory();
        Session s = factory.getCurrentSession();
        s.beginTransaction();

        Query<Double> query = s.createQuery(jpql, Double.class);
        query.setParameter("id", Integer.parseInt(idAsignatura));

        Double tasa = query.uniqueResult();

        s.getTransaction().commit();
        s.close();

        return (tasa != null) ? tasa : 0.0;
    }
}
