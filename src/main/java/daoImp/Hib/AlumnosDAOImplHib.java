package daoImp.Hib;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import dao.IAlumnosDAO;
import dto.AlumnoDTO;
import entities.AlumnoEntity;
import entities.MunicipiosEntity;
import utils.DBUtils;

public class AlumnosDAOImplHib implements IAlumnosDAO {

	@Override
	public int insertarAlumno(String id, String nombre, String apellido, String idMunicipio, int familiaNumerosa,
			int activo) {

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();
		MunicipiosEntity municipio = s.find(MunicipiosEntity.class, Integer.parseInt(idMunicipio));
		AlumnoEntity a = new AlumnoEntity(Integer.parseInt(id), nombre, apellido, familiaNumerosa, activo, municipio);

		Integer idPk = (Integer) s.save(a);

		s.getTransaction().commit();
		s.close();

		return idPk;
	}

	@Override
	public ArrayList<AlumnoDTO> obtenerTodosAlumnos() {
		String hql = "FROM AlumnoEntity";

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();

		Query<AlumnoDTO> query = s.createQuery(hql, AlumnoDTO.class);
		ArrayList<AlumnoDTO> lista = (ArrayList<AlumnoDTO>) query.getResultList();

		s.close();

		return lista;
	}

	@Override
	public ArrayList<AlumnoDTO> obtenerAlumnosPorIdNombreApellido(String id, String nombre, String apellido,
			int familiaNumerosa, int activo) {

		String jpq = "SELECT new dto.AlumnoDTO (a.id, a.nombre, a.apellidos, m.nombre, m.idMunicipio, a.famNumerosa, a.activo)"
				+ " FROM AlumnoEntity a, MunicipiosEntity m " + " WHERE a.municipio.idMunicipio = m.idMunicipio "
				+ "	AND CAST (a.id AS string) LIKE :id " + " AND a.nombre LIKE :nombre "
				+ " AND a.apellidos LIKE :apellido " + " AND a.activo = :activo "
				+ " AND a.famNumerosa = :familiaNumerosa ";

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();

		Query<AlumnoDTO> query = s.createQuery(jpq, AlumnoDTO.class).setParameter("id", "%" + id + "%")
				.setParameter("nombre", "%" + nombre + "%").setParameter("apellido", "%" + apellido + "%")
				.setParameter("activo", activo).setParameter("familiaNumerosa", familiaNumerosa);

		List<AlumnoDTO> lista = query.getResultList();

		s.close();

		return new ArrayList<>(lista);
	}

	@Override
	public int actualizarAlumno(String id, String nombre, String apellido, String idMunicipio, int familiaNumerosa,
			int activo) {

		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();
		s.beginTransaction();

		MunicipiosEntity municipio = s.find(MunicipiosEntity.class, Integer.parseInt(idMunicipio));
		AlumnoEntity a = new AlumnoEntity(Integer.parseInt(id), nombre, apellido, familiaNumerosa, activo, municipio);

		s.update(a);

		s.getTransaction().commit();

		s.close();

		return a.getId();
	}

	@Override
	public int borrarAlumno(String id) {
		SessionFactory factory = DBUtils.creadorSessionFactory();
		Session s = factory.getCurrentSession();

		s.beginTransaction();

		AlumnoEntity a = s.get(AlumnoEntity.class, Integer.parseInt(id));

		a.setActivo(0);

		s.update(a);
		s.getTransaction().commit();
		s.close();

		return a.getId();
	}

	@Override
	public boolean esFamiliaNumerosa(String idAlumno) {

		return false;
	}

	@Override
	public int contarAsignaturasMatriculadas(String idAlumno) {

		return 0;
	}

}
