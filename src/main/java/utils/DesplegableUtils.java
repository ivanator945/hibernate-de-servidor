package utils;

import java.util.ArrayList;
import dao.IDesplegableDAO;
import dao.IFaltasDAO;
import daoImp.Hib.DesplegablesDAOImplHib;
import daoImp.Hib.FaltasDAOImphib;
import dto.DesplegableDTO;
import dto.FaltaDTO;
import jakarta.servlet.http.HttpServletRequest;

public class DesplegableUtils {

    public static void recuperarDesplegableMunicipios(HttpServletRequest request) {
        IDesplegableDAO desplegableMunicipios = new DesplegablesDAOImplHib();
        request.setAttribute("desplegableMunicipios", desplegableMunicipios.desplegableMunicipios());
    }

    public static void recuperarDesplegableAlumnos(HttpServletRequest request) {
        IDesplegableDAO desplegableAlumnos = new DesplegablesDAOImplHib();
        request.setAttribute("desplegableAlumnos", desplegableAlumnos.desplegableAlumnos());
    }

    public static void recuperarDesplegableAsignaturas(HttpServletRequest request) {
        IDesplegableDAO desplegableAsignaturas = new DesplegablesDAOImplHib();
        request.setAttribute("desplegableAsignaturas", desplegableAsignaturas.desplegableAsignaturas());
    }

    public static void recuperarFaltas(HttpServletRequest request) {
        IFaltasDAO faltasDAO = new FaltasDAOImphib();
        ArrayList<FaltaDTO> listaFaltas = faltasDAO.obtenerTodasFaltas();
        request.setAttribute("listaFaltas", listaFaltas);
    }
}
