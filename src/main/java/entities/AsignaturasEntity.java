package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "asignaturas")
public class AsignaturasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "curso")
    private String curso;

    @Column(name = "tasa")
    private double tasa;

    @Column(name = "activo")
    private int activo;

    public AsignaturasEntity() {
    }

    
    public AsignaturasEntity(String nombre, String curso, double tasa, int activo) {
        this.nombre = nombre;
        this.curso = curso;
        this.tasa = tasa;
        this.activo = activo;
    }
    
   
    public AsignaturasEntity(int id, String nombre, String curso, double tasa, int activo) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
        this.tasa = tasa;
        this.activo = activo;
    }


    public int getId() {
        return id;
    }


    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasa) {
        this.tasa = tasa;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "AsignaturasEntity [id=" + id + ", nombre=" + nombre + ", curso=" + curso + ", tasa=" + tasa + ", activo=" + activo + "]";
    }
}
