package ma.hmzelidrissi.bankmanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ma.hmzelidrissi.bankmanagementsystem.dtos.invoice.CreateInvoiceRequestDTO;
import ma.hmzelidrissi.bankmanagementsystem.dtos.invoice.InvoiceResponseDTO;
import ma.hmzelidrissi.bankmanagementsystem.services.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoices")
@Tag(name = "Invoices Management", description = "APIs for managing invoices")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class InvoiceController {
  private final InvoiceService invoiceService;

  @PostMapping
  @Operation(summary = "Create a new invoice")
  @ResponseStatus(HttpStatus.CREATED)
  public InvoiceResponseDTO createInvoice(CreateInvoiceRequestDTO request) {
    return invoiceService.createInvoice(request);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get invoice by ID")
  @ResponseStatus(HttpStatus.OK)
  public InvoiceResponseDTO getInvoiceById(@PathVariable Long id) {
    return invoiceService.getInvoiceById(id);
  }

  @GetMapping("/my")
  @Operation(summary = "Get my invoices")
  @ResponseStatus(HttpStatus.OK)
  public Page<InvoiceResponseDTO> getMyInvoices(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "asc") String sortDir) {
    Sort.Direction direction = Sort.Direction.fromString(sortDir.toLowerCase());
    PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));
    return invoiceService.getMyInvoices(pageRequest);
  }

  @PutMapping("/{id}/pay")
  @Operation(summary = "Pay an invoice")
  @ResponseStatus(HttpStatus.OK)
  public InvoiceResponseDTO payInvoice(@PathVariable Long id) {
    return invoiceService.payInvoice(id);
  }

  @PutMapping("/{id}/cancel")
  @Operation(summary = "Cancel an invoice")
  @ResponseStatus(HttpStatus.OK)
  public InvoiceResponseDTO cancelInvoice(@PathVariable Long id) {
    return invoiceService.cancelInvoice(id);
  }
}
