package com.sd.sistemasd.controllers.cliente;

import com.sd.sistemasd.controllers.suscripcion.SuscripcionController;
import com.sd.sistemasd.dto.cliente.ClienteDTO;
import com.sd.sistemasd.service.Cliente.IClienteService;
import com.sd.sistemasd.utils.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(SuscripcionController.class);

    @Autowired
    private IClienteService clienteService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ResponseEntity<?> createCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            ClienteDTO createdCliente = clienteService.createCliente(clienteDTO);
            return new ResponseEntity<>(createdCliente, HttpStatus.CREATED);
        }catch (Exception e){
            logger.error("Error al crear el cliente", e);
            return new ResponseEntity<>("error500", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ClienteDTO getClienteById(@PathVariable Long id) {
        logger.warn("Guardando en cache el Cliente con id = " + id);
        return clienteService.getClienteById(id);
    }

    @GetMapping("/page/{page}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public List<ClienteDTO> getAllClientes(@PathVariable(name = "page") int page) {
        int size = Setting.PAGE_SIZE;
        Pageable pageable = PageRequest.of(page, size);
        return clienteService.getAllClientes(pageable).getContent();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public ClienteDTO updateCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        logger.warn("Actualizando en cache el Cliente con id = " + id);
        return clienteService.updateCliente(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLEADO')")
    public void deleteCliente(@PathVariable Long id) {
        logger.warn("Borrando de cache el Cliente con id = " + id);
        clienteService.deleteCliente(id);
    }
}