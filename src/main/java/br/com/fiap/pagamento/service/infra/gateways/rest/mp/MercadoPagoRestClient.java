package br.com.fiap.pagamento.service.infra.gateways.rest.mp;

import br.com.fiap.pagamento.service.core.domain.entities.PagamentoGateway;
import br.com.fiap.pagamento.service.core.usecases.ports.gateways.PaymentGatewayPort;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.dto.order.CashOut;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.dto.order.Item;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.dto.order.Order;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.dto.order.QuickResponse;
import br.com.fiap.pagamento.service.infra.gateways.rest.mp.dto.payment.PaymentsResponse;
import br.com.fiap.pagamento.service.main.configuration.GatewayPaymentConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class MercadoPagoRestClient implements PaymentGatewayPort {

    public static final String POST_ORDER = "https://api.mercadopago.com/instore/orders/qr/seller/collectors/%d/pos/%s/qrs";
    
    public static final String GET_PAYMENTS = "https://api.mercadopago.com/v1/payments/%d";

	private final GatewayPaymentConfiguration gatewayPayment;

	private final RestTemplate restTemplate;

    public String create(String reference, BigDecimal totalAmount) {
		URI uri = getUri();
		Order order = Order.builder()
					.cashOut(CashOut.builder().amount(new BigDecimal(NumberUtils.INTEGER_ZERO)).build())
					.description(DESCRIPTION).externalReference(reference).items(buildItem(totalAmount))
					.notificationUrl(gatewayPayment.getNotification()).title(TITLE).totalAmount(totalAmount).build();
		HttpEntity<Order> entity = new HttpEntity<>(order, getHttpHeaders(gatewayPayment.getToken()));
		ResponseEntity<QuickResponse> result = restTemplate.postForEntity(uri, entity, QuickResponse.class);
		log.info("response order mp: "+result.toString());
		return result.getBody().getQrData();
    }

	public PagamentoGateway getPayment(Long id) {
		try {
			HttpEntity<Order> entity = new HttpEntity<>(getHttpHeaders(gatewayPayment.getToken()));
			ResponseEntity<PaymentsResponse> result = restTemplate.exchange(String.format(GET_PAYMENTS, id), HttpMethod.GET, entity, PaymentsResponse.class);
			log.info("response payment mp: " + result.toString());

			PaymentsResponse paymentsResponse = result.getBody();

			return PagamentoGateway.builder()
					.idExterno(paymentsResponse.getExternalReference())
					.status(paymentsResponse.getStatus())
					.paymentId(paymentsResponse.getId())
					.build();

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);
        return headers;
    }

	private static final String DESCRIPTION = "This is the Point Mini";
	private static final String TITLE = "Point Mini";
	private static final String CATEGORY = "marketplace";
	private static final String UNIT = "unit";
	private ArrayList<Item> buildItem(BigDecimal totalAmount) {
		ArrayList<Item> itens = new ArrayList<>();
		itens.add(Item.builder().skuNumber(gatewayPayment.getSku()).category(CATEGORY).title(TITLE)
				.description(DESCRIPTION).unitPrice(totalAmount).quantity(NumberUtils.INTEGER_ONE)
				.unitMeasure(UNIT).totalAmount(totalAmount).build());
		return itens;
	}

	private URI getUri() {
		String url = String.format(POST_ORDER, gatewayPayment.getSeller(), gatewayPayment.getPos());
		URI uri = null;
		try {
			uri = new URI(url);
		}catch (URISyntaxException e){
			log.error(e.getMessage());
		}
		return uri;
	}
}
