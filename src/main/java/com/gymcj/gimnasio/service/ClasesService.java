package com.gymcj.gimnasio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gymcj.gimnasio.entity.ClasesModel;
import com.gymcj.gimnasio.entity.Usuario;
import com.gymcj.gimnasio.repository.ClasesRepository;
import com.gymcj.gimnasio.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClasesService {

    @Autowired
    private ClasesRepository clasesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ClasesModel> getClases() {
        return clasesRepository.findAll();
    }

    public ClasesModel addClase(String imagen, String nombreClase, String subClases) {

        ClasesModel nuevaClase = ClasesModel.builder()
                .imagen(imagen)
                .nombreClase(nombreClase)
                .subClases(subClases)
                .build();

        return clasesRepository.save(nuevaClase);
    }

    public ClasesModel updateClase(Long id, ClasesModel claseActualizada) {
        ClasesModel clase = clasesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La clase con el id: " + id + " no existe o ha sido eliminada"));

        clase.setImagen(claseActualizada.getImagen());
        clase.setNombreClase(claseActualizada.getNombreClase());
        clase.setSubClases(claseActualizada.getSubClases());

        return clasesRepository.save(clase);
    }

    public void deleteClase(Long id) {
        ClasesModel clase = clasesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La clase con el id: " + id + " no existe o ha sido eliminada"));

        for (Usuario usuario : clase.getUsuarios()) {
            usuario.getClases().remove(clase);
            usuarioRepository.save(usuario);
        }

        clasesRepository.delete(clase);
    }
}