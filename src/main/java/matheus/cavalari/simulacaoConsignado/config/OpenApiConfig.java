package matheus.cavalari.simulacaoConsignado.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Matheus",
                        email = "matheuscav@hotmail.com"
                ),
                description = "O microserviço Simulação Consignado realiza simulações de empréstimos consignados, calculando valores e prazos com base nas informações dos clientes.",
                title = "Simulacao Consignado",
                version = "1.0"
        )
)
public class OpenApiConfig {
}
