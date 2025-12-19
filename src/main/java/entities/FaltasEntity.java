package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "faltas")
public class FaltasEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfaltas")
	private int idFalta;

	@ManyToOne
	@JoinColumn(name = "alumno")
	private AlumnoEntity alumno;

	@ManyToOne
	@JoinColumn(name = "asignatura")
	private AsignaturasEntity asignatura;

	@Column(name = "fecha")
	private String fecha;

	@Column(name = "justificada")
	private int justificada;

	public FaltasEntity() {
	}

	public FaltasEntity(AlumnoEntity alumno, AsignaturasEntity asignatura, String fecha, int justificada) {
		this.alumno = alumno;
		this.asignatura = asignatura;
		this.fecha = fecha;
		this.justificada = justificada;
	}

	public int getIdFalta() {
		return idFalta;
	}

	public AlumnoEntity getAlumno() {
		return alumno;
	}

	public void setAlumno(AlumnoEntity alumno) {
		this.alumno = alumno;
	}

	public AsignaturasEntity getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(AsignaturasEntity asignatura) {
		this.asignatura = asignatura;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getJustificada() {
		return justificada;
	}

	public void setJustificada(int justificada) {
		this.justificada = justificada;
	}
}
