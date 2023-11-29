package com.sd.sistemasd.service.Cliente;

import com.sd.sistemasd.beans.cliente.ClienteBean;
import com.sd.sistemasd.dao.cliente.ClienteDAO;
import com.sd.sistemasd.dto.cliente.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private ClienteDAO clienteDAO;
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