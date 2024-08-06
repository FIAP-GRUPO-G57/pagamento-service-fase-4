package br.com.fiap.pagamento.service.infra.db.entities;

import br.com.fiap.pagamento.service.core.domain.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="pagamento")
public class PagamentoEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="valor")
    private BigDecimal valor;

    @Column(name="reference")
    private Long reference;

    @Column(name="qrcode")
    private String qrcode;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(name="gatewayId")
    private Long gatewayId;

    @CreationTimestamp
    private LocalDateTime criacao;

    @UpdateTimestamp
    private LocalDateTime alteracao;
}
