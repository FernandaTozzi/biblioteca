# 📊 Relatório de Teste de Carga
## Modelo testado : Professor

---

## 🧪 Descrição dos testes

Foram realizados testes de carga utilizando a ferramenta **Locust** para avaliar o desempenho da API, usando dois métodos principais:

1. **POST** (persistência de dados)
2. **GET** (consulta de dados)

A carga foi aplicada de forma progressiva, iniciando com poucos usuários e aumentando até aproximadamente **10.000 usuários simultâneos**.

---

## Comportamento observado

### Método POST 

O método POST apresentou um comportamento relativamente estável durante boa parte do teste.

#### Até ~1.500 usuários:
- Baixo tempo de resposta  
- Estabilidade nas requisições  
- Praticamente nenhuma falha  

#### Acima de 1.500 usuários:
- Aumento gradual no tempo de resposta  
- Entre **3.000 e 5.000 usuários**, início de oscilações  
- Próximo de **10.000 usuários**:
  - P50 entre **2.000 e 3.000 ms**
  - P95 chegando a cerca de **6.000 ms**

---

### Método GET 

O método GET apresentou comportamento mais crítico em comparação ao POST.

#### Até ~2.000 usuários:
- Desempenho aceitável  
- Tempos de resposta controlados  

#### Acima de 2.000 usuários:
- Crescimento contínuo do tempo de resposta  
- A partir de **5.000 usuários**, forte degradação  
- Em **~10.000 usuários:**
  - P95 acima de 200.000 ms
  - Alta instabilidade nos tempos de resposta  
  - Oscilações bruscas no P50  
  - Aumento significativo de falhas 

---

Os gráficos e o código utilizados no teste estão disponíveis na pasta Attachments.



