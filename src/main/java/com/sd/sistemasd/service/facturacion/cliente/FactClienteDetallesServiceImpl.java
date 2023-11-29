package com.sd.sistemasd.service.facturacion.cliente;

import com.sd.sistemasd.beans.facturacion.cliente.FacturaClienteBean;
import com.sd.sistemasd.beans.facturacion.cliente.FacturacionClienteDetalles;
import com.sd.sistemasd.beans.suscripcion.SuscripcionBean;
import com.sd.sistemasd.dao.facturacionCliente.FacturacionClienteDAO;
import com.sd.sistemasd.dao.facturacionCliente.FacturacionClienteDetallesDAO;
import com.sd.sistemasd.dao.suscripcion.SuscripcionDAO;
import com.sd.sistemasd.dto.facturacion.cliente.FactClienteDetallesDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FactClienteDetallesServiceImpl implements IFactClienteDetallesService{

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


}
