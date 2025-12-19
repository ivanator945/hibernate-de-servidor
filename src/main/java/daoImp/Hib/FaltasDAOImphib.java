package daoImp.Hib;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import dao.IFaltasDAO;
import dto.FaltaDTO;
import entities.AlumnoEntity;
import entities.AsignaturasEntity;
import entities.FaltasEntity;
import utils.DBUtils;

public class FaltasDAOImphib implements IFaltasDAO {

	@Override
	public ArrayList<FaltaDTO> obtenerTodasFaltas() {
		String jpql = "SELECT new dto.FaltaDTO(f.idFalta, f.fecha) FROM FaltasEntity f";
		Session s = DBUtils.creadorSessionFactory().getCurrentSession();
		s.beginTransaction();
		List<FaltaDTO> lista = s.createQuery(jpql, FaltaDTO.class).getResultList();
		s.getTransaction().commit();
		s.close();
		return new ArrayList<>(lista);
	}

	@Override
	public ArrayList<FaltaDTO> obtenerFaltasPorFiltros(String nombreAlumno, String asignatura, String fecha,
			int justificada) {
		String jpql = "SELECT new dto.FaltaDTO(f.idFalta, a.id, a.nombre, asig.id, asig.nombre, f.fecha, f.justificada) "
				+ "FROM FaltasEntity f JOIN f.alumno a JOIN f.asignatura asig "
				+ "WHERE a.nombre LIKE :nombreAlumno AND asig.nombre LIKE :asignatura AND f.fecha >= :fecha AND f.justificada = :justificada";
		Session s = DBUtils.creadorSessionFactory().getCurrentSession();
		s.beginTransaction();
		Query<FaltaDTO> query = s.createQuery(jpql, FaltaDTO.class)
				.setParameter("nombreAlumno", "%" + nombreAlumno + "%")
				.setParameter("asignatura", "%" + asignatura + "%").setParameter("fecha", fecha)
				.setParameter("justificada", justificada);
		List<FaltaDTO> lista = query.getResultList();
		s.getTransaction().commit();
		s.close();
		return new ArrayList<>(lista);
	}

	@Override
	public ArrayList<FaltaDTO> obtenerFaltasPorFiltrosSinFecha(String nombreAlumno, String asignatura,
			int justificada) {
		String jpql = "SELECT new dto.FaltaDTO(f.idFalta, a.id, a.nombre, asig.id, asig.nombre, f.fecha, f.justificada) "
				+ "FROM FaltasEntity f JOIN f.alumno a JOIN f.asignatura asig "
				+ "WHERE a.nombre LIKE :nombreAlumno AND asig.nombre LIKE :asignatura AND f.justificada = :justificada";
		Session s = DBUtils.creadorSessionFactory().getCurrentSession();
		s.beginTransaction();
		Query<FaltaDTO> query = s.createQuery(jpql, FaltaDTO.class)
				.setParameter("nombreAlumno", "%" + nombreAlumno + "%")
				.setParameter("asignatura", "%" + asignatura + "%").setParameter("justificada", justificada);
		List<FaltaDTO> lista = query.getResultList();
		s.getTransaction().commit();
		s.close();
		return new ArrayList<>(lista);
	}

	@Override
	public int insertarFalta(String idAlumno, String idAsignatura, String fecha, int justificada) {
		Session s = DBUtils.creadorSessionFactory().getCurrentSession();
		s.beginTransaction();
		AlumnoEntity alumno = s.find(AlumnoEntity.class, Integer.parseInt(idAlumno));
		AsignaturasEntity asignatura = s.find(AsignaturasEntity.class, Integer.parseInt(idAsignatura));
		FaltasEntity f = new FaltasEntity(alumno, asignatura, fecha, justificada);
		Integer idPk = (Integer) s.save(f);
		s.getTransaction().commit();
		s.close();
		return idPk;
	}

	@Override
	public int actualizarFalta(String idFalta, String idAlumno, String idAsignatura, String fecha, int justificada) {
		Session s = DBUtils.creadorSessionFactory().getCurrentSession();
		s.beginTransaction();
		FaltasEntity f = s.find(FaltasEntity.class, Integer.parseInt(idFalta));
		AlumnoEntity alumno = s.find(AlumnoEntity.class, Integer.parseInt(idAlumno));
		AsignaturasEntity asignatura = s.find(AsignaturasEntity.class, Integer.parseInt(idAsignatura));
		f.setAlumno(alumno);
		f.setAsignatura(asignatura);
		f.setFecha(fecha);
		f.setJustificada(justificada);
		s.update(f);
		s.getTransaction().commit();
		s.close();
		return f.getIdFalta();
	}

	@Override
	public int borrarFalta(String idFalta) {
		Session s = DBUtils.creadorSessionFactory().getCurrentSession();
		s.beginTransaction();
		FaltasEntity f = s.find(FaltasEntity.class, Integer.parseInt(idFalta));
		s.delete(f);
		s.getTransaction().commit();
		s.close();
		return f.getIdFalta();
	}
}
