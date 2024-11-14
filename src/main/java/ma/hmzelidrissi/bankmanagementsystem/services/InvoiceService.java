package ma.hmzelidrissi.bankmanagementsystem.services;

import ma.hmzelidrissi.bankmanagementsystem.dtos.invoice.CreateInvoiceRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.invoice.InvoiceResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {
    InvoiceResponseDTO createInvoice(CreateInvoiceRequestDTO request);

    InvoiceResponseDTO getInvoiceById(Long id);

    Page<InvoiceResponseDTO> getMyInvoices(Pageable pageable);

    InvoiceResponseDTO payInvoice(Long id);

    InvoiceResponseDTO cancelInvoice(Long id);

    void processOverdueInvoices();
}
