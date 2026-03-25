# Relatorio sobre carga

## Foi implementando um teste de carga, onde começou com 100 o limite, indo até 10.000 pois foi obsrvado o começo de falha ao chegar aos limite 10.000, colocando a requisição de ser 100 usuários por segundo, ao chegar em 1500 começou aparecer lentidão e instablidade, porém não ocorreu falha, o post teve request maior de 7036 sendo 1329 fails com 56.1 current Failures,s

### A aplicação começou a apresentar degradação de desempenho ao chegar no limite estabelecido aue foi de 10000

### O volume de requisições executadas até esse ponto foi de 10.000, começou com 100, 500, 1000 e 10.000

### Quais operações foram afetadas: De começo foi o post, mas afetou o get também

### Na minha opnião, foi bem suportado o teste, para uma aplicação simples, onde em conversa com outro integrantes, o post foi que mais teve falha e não demorou para realizar o teste.
# Imagens contendo o gráfico apresentando as oscilações dos parâmetros utilizados estão presentes na pasta imagens e o código está no arquivo testeAluno.py.