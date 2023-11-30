package com.sd.sistemasd.service.Cliente;

import com.sd.sistemasd.beans.cliente.ClienteBean;
import com.sd.sistemasd.dao.cliente.ClienteDAO;
import com.sd.sistemasd.dto.cliente.ClienteDTO;
import com.sd.sistemasd.service.Email.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteDAO clienteDAO;
    @Autowired
    private IEmailService emailService;
    @Override
    @Transactional
    public ClienteDTO createCliente(ClienteDTO clienteDTO) {
        ClienteBean cliente = new ClienteBean();
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setCedula(clienteDTO.getCedula());
        cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        cliente.setTelefono(clienteDTO.getTelefono());
        cliente = clienteDAO.save(cliente); // Guardar en la base de datos
        return convertToDTO(cliente);
    }

    @Override
    @Cacheable(cacheManager = "cacheManagerNoTTL",cacheNames = "sistema-sd", key = "'cliente_' + #id")
    @Transactional
    public ClienteDTO getClienteById(Long id) {
        ClienteBean cliente = clienteDAO.findById(id).orElse(null);
        if (cliente != null) {
            return convertToDTO(cliente);
        }
        else {
            return null;
        }
    }

    @Override
    @Cacheable(cacheNames = "sistema-sd")
    @Transactional
    public Page<ClienteDTO> getAllClientes(Pageable pageable) {
        Page<ClienteBean> clientesPage = clienteDAO.findAll(pageable);
        return clientesPage.map(this::convertToDTO);
    }


    @Override
    @CachePut(cacheNames = "sistema-sd", key = "'cliente_' + #id")
    @Transactional
    public ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO) {
        ClienteBean existingCliente = clienteDAO.findById(id).orElse(null);
        if (existingCliente != null) {
            existingCliente.setNombre(clienteDTO.getNombre());
            existingCliente.setCedula(clienteDTO.getCedula());
            existingCliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
            existingCliente.setTelefono(clienteDTO.getTelefono());

            existingCliente = clienteDAO.save(existingCliente); // Actualizar en la base de datos

            return convertToDTO(existingCliente);
        }
        return null;
    }

    @Transactional
    public void enviarCorreoATodos(String subject, String text) {
        // Obtener todos los clientes de la base de datos
        List<ClienteBean> clientes = clienteDAO.findAll();
        List<String> direccionesCorreo = new ArrayList<>();

        // Iterar sobre cada cliente y agregar la dirección de correo a la lista
        for (ClienteBean cliente : clientes) {
            String asunto = subject + " - Cliente: " + cliente.getNombre();
            String texto = text + "\n\nCliente: " + cliente.getNombre();
            String correoCliente = cliente.getCorreoElectronico();
            if (correoCliente != null && !correoCliente.isEmpty()) {
                direccionesCorreo.add(correoCliente);
            }
        }
        // Verificar que hay al menos una dirección de correo antes de intentar enviar el correo
        if (!direccionesCorreo.isEmpty()) {
            // Convertir la lista de direcciones de correo a un array
            String[] direccionesArray = direccionesCorreo.toArray(new String[0]);

            String asunto = subject + " - Cliente: " + clientes.get(0).getNombre();
            String texto= text + "\n\nCliente: " + clientes.get(0).getNombre();

            // Enviar el correo a todas las direcciones de correo al mismo tiempo
            emailService.sendMailToMultipleAddresses(direccionesArray, asunto, texto);
        }
    }

    @Override
    @CacheEvict(cacheNames = "sistema-sd", key = "'cliente_' + #id" )
    @Transactional
    public void deleteCliente(Long id) {
        clienteDAO.deleteById(id);
    }

    private ClienteDTO convertToDTO(ClienteBean cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setClienteId(cliente.getClienteID());
        dto.setNombre(cliente.getNombre());
        dto.setCedula(cliente.getCedula());
        dto.setCorreoElectronico(cliente.getCorreoElectronico());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }


}