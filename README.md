# pagamento-service-fase-4



# 🛠️ Zap scanning Report

[Geracao do Pagamento](https://rawcdn.githack.com/FIAP-GRUPO-G57/pagamento-service-fase-4/3032293a9359141e770915a3fb421af49ace1ca3/zap-Geracao-Pagamento-Report-.html)

[Confirmacao do Pagamento](https://rawcdn.githack.com/FIAP-GRUPO-G57/pagamento-service-fase-4/105a7c61c5ee14878772f41b1eb63dcb6a57b02c/zap-Confirmacao-Pagamento.html)

# 🛠️ Saga
Foi utilizado o padrão SAGA de coreografia pois acreditamos que as integrações no fluxo de aprovação e rejeição de pagamentos eram mais simples e por este motivo não haveria necessidade de uma orquestração. Utilizamos para isto um tópico SNS com subscrição de filas SQS (Fan out) dos eventos e cada microserviço “Produção” e “Notificação” é responsável por tratar o evento de acordo com sua responsabilidade. O vídeo o desenho de arquitetura corroboram com  nosso posicionamento de arquitetura

# 🛠️ Sonarcloud

[Pagamento](https://sonarcloud.io/summary/overall?id=FIAP-GRUPO-G57_pagamento-service-fase-4)

![image](https://github.com/FIAP-GRUPO-G57/pagamento-service-fase-4/assets/8040205/2180e622-50aa-4d80-85ce-d2d416c1f062)

