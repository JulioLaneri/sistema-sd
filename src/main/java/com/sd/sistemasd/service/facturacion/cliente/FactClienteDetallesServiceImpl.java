package com.sd.sistemasd.service.facturacion.cliente;

import com.sd.sistemasd.beans.facturacion.cliente.FacturaClienteBean;
import com.sd.sistemasd.beans.facturacion.cliente.FacturacionClienteDetalles;
import com.sd.sistemasd.beans.suscripcion.SuscripcionBean;
import com.sd.sistemasd.beans.suscripcion.SuscripcionDetalleBean;
import com.sd.sistemasd.dao.facturacionCliente.FacturacionClienteDAO;
import com.sd.sistemasd.dao.facturacionCliente.FacturacionClienteDetallesDAO;
import com.sd.sistemasd.dao.suscripcion.SuscripcionDAO;
import com.sd.sistemasd.dto.facturacion.cliente.FactClienteDetallesDTO;
import com.sd.sistemasd.dto.facturacion.cliente.FacturacionClienteDTO;
import com.sd.sistemasd.dto.suscripcion.SuscripcionDetalleDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FactClienteDetallesServiceImpl implements IFactClienteDetallesService{
    private static final Logger logger = LoggerFactory.getLogger(FactClienteDetallesServiceImpl.class);

    @Autowired
    FacturacionClienteDAO facturaDAO;

    @Autowired
    SuscripcionDAO suscripcionDAO;

    @Autowired
    FacturacionClienteDetallesDAO detallesDAO;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public FactClienteDetallesDTO create(FactClienteDetallesDTO detallesDTO) {
        FacturacionClienteDetalles detallesBean = toBean(detallesDTO);

        // Obtener la factura asociada
        Long facturaId = detallesDTO.getFacturaid();
        FacturaClienteBean factura = facturaDAO.findById(facturaId)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + facturaId));

        // Establecer la factura en los detalles
        detallesBean.setFacturaCliente(factura);

        FacturacionClienteDetalles newDetalle = detallesDAO.save(detallesBean);
        return toDTO(newDetalle);
    }

    @Override
    public List<FactClienteDetallesDTO> getDetalles(Long id) {
        List<FacturacionClienteDetalles> detalles = detallesDAO.findByFacturaCliente_Facturaid(id);
        return convertToDTOlIST(detalles);
    }


    private FacturacionClienteDetalles toBean(FactClienteDetallesDTO detallesDTO){
        FacturacionClienteDetalles bean = modelMapper.map(detallesDTO, FacturacionClienteDetalles.class);
        SuscripcionBean suscripcionBean = suscripcionDAO.findById(detallesDTO.getSuscripcionid()).orElse(null);
        if(suscripcionBean != null){
            bean.setSuscripcion(suscripcionBean);
        }
        FacturaClienteBean facturaBean = facturaDAO.findById(detallesDTO.getFacturaid()).orElse(null);
        if(facturaBean != null){
            bean.setFacturaCliente(facturaBean);
        }
        return bean;
    }

    private FactClienteDetallesDTO toDTO(FacturacionClienteDetalles detalles){
        FactClienteDetallesDTO dto = modelMapper.map(detalles, FactClienteDetallesDTO.class);

        if(detalles.getSuscripcion() != null){
            dto.setSuscripcionid(detalles.getSuscripcion().getSuscripcionID());
        }

        if(detalles.getFacturaCliente() != null){
            dto.setFacturaid(detalles.getFacturaCliente().getFacturaid());
        }

        return dto;
    }

//    private List<FacturacionClienteDTO> convertToDTOList(List<FacturaClienteBean> facturasBean ){
//        return facturasBean.stream().map(this::toDTO).collect(Collectors.toList());
//    }

private  List<FactClienteDetallesDTO> convertToDTOlIST(List<FacturacionClienteDetalles> detalles){
        return detalles.stream().map(this::toDTO).collect(Collectors.toList());
}



}