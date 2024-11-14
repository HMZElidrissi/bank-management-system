package ma.hmzelidrissi.bankmanagementsystem.repositories;

import ma.hmzelidrissi.bankmanagementsystem.entities.Invoice;
import ma.hmzelidrissi.bankmanagementsystem.enums.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByUserId(Long id, Pageable pageable);

    List<Invoice> findByStatusAndDueDateBefore(InvoiceStatus invoiceStatus, LocalDate now);
}
