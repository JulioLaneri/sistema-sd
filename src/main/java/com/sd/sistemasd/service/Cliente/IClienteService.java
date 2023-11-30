package com.sd.sistemasd.service.Cliente;

import com.sd.sistemasd.dto.cliente.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {
    ClienteDTO createCliente(ClienteDTO clienteDTO);
    ClienteDTO getClienteById(Long id);
    public Page<ClienteDTO> getAllClientes(Pageable pageable);
    ClienteDTO updateCliente(Long id, ClienteDTO clienteDTO);
    void deleteCliente(Long id);
    void enviarCorreoATodos(String subject, String text);
}