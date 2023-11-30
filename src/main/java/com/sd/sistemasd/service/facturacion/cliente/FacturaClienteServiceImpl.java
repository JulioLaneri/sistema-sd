package com.sd.sistemasd.service.facturacion.cliente;

import com.sd.sistemasd.beans.cliente.ClienteBean;
import com.sd.sistemasd.beans.facturacion.cliente.FacturaClienteBean;
import com.sd.sistemasd.beans.facturacion.cliente.FacturacionClienteDetalles;
import com.sd.sistemasd.dao.cliente.ClienteDAO;
import com.sd.sistemasd.dao.facturacionCliente.FacturacionClienteDAO;
import com.sd.sistemasd.dao.facturacionCliente.FacturacionClienteDetallesDAO;
import com.sd.sistemasd.dto.facturacion.cliente.FactClienteDetallesDTO;
import com.sd.sistemasd.dto.facturacion.cliente.FacturacionClienteDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacturaClienteServiceImpl implements IFacturaClienteService{

    @Autowired
    FacturacionClienteDAO facturaDAO;

    @Autowired
    FactClienteDetallesServiceImpl detallesService;

    @Autowired
    ClienteDAO clienteDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FacturacionClienteDetallesDAO detallesDAO;

    @Override
    public FacturacionClienteDTO create(FacturacionClienteDTO facturacionClienteDTO) {
        FacturaClienteBean facturaBean = toBean(facturacionClienteDTO);
        FacturaClienteBean facturaSave = facturaDAO.save(facturaBean);

        List<FacturacionClienteDetalles> detalles = facturaSave.getDetalles();
        if (detalles != null) {
            for (FacturacionClienteDetalles detalle : detalles) {
                detalle.setFacturaCliente(facturaSave);
            }
        }

        detallesDAO.saveAll(detalles);

        return toDTO(facturaSave);
    }


    @Override
    public FacturacionClienteDTO getById(Long id) {
        Optional<FacturaClienteBean> facturaBean = facturaDAO.findById(id);
        if(facturaBean.isPresent()){
            return facturaBean.map(this::toDTO).orElse(null);
        }else{
            return null;
        }
    }

    @Override
    public Page<FacturacionClienteDTO> getAll(Pageable pageable) {
        Page<FacturaClienteBean> facturasBean = facturaDAO.findAllWithDetalles(pageable);
        return facturasBean.map(this::toDTOWithDetails);
    }


    private FacturacionClienteDTO toDTOWithDetails(FacturaClienteBean facturaBean) {
        FacturacionClienteDTO dto = toDTO(facturaBean);
        List<FactClienteDetallesDTO> detallesDTOList = facturaBean.getDetalles().stream()
                .map(this::toDetallesDTO)
                .collect(Collectors.toList());
        dto.setDetalles(detallesDTOList);
        return dto;
    }

    private FactClienteDetallesDTO toDetallesDTO(FacturacionClienteDetalles detalles) {
        return modelMapper.map(detalles, FactClienteDetallesDTO.class);
    }


    @Override
    public void delete(Long id) {
        facturaDAO.deleteById(id);
    }

    private FacturaClienteBean toBean(FacturacionClienteDTO facturaDTO){
        FacturaClienteBean bean = modelMapper.map(facturaDTO, FacturaClienteBean.class);
        ClienteBean cliente = clienteDAO.findById(facturaDTO.getClienteid()).orElse(null);
        if (cliente != null) {
            bean.setCliente(cliente);
        }
        return bean;
    }

    private FacturacionClienteDTO toDTO(FacturaClienteBean facturaBean){
        FacturacionClienteDTO dto = modelMapper.map(facturaBean, FacturacionClienteDTO.class);
        if (facturaBean.getCliente() != null) {
            dto.setClienteid(facturaBean.getCliente().getClienteID());
        }
        return dto;
    }

    private List<FacturacionClienteDTO> convertToDTOList(List<FacturaClienteBean> facturasBean ){
        return facturasBean.stream().map(this::toDTO).collect(Collectors.toList());
    }


}