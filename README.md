# samba_statistics
Desafio Técnico | Samba Tech | 26/09/2019

## Tecnologias Utilizadas

* Java 8 SDK
* Grails 3.3.10 (Groovy 2.4.17)
* Spock Framework (Unit Test)
* CircleCI (CI/CD)

## Especificações das APIs



#### POST /videos
API para Receber novos Videos

##### Header:
Utilizar Basic Authentication
```
Authorization: Basic c2FtYmF0ZWNoOnNhbWJhdGVjaA==
```

##### Body:
```
{
    “duration” : 123.4,
    “timestamp” : 1569550777458
} 
```
##### Response
* 201 - Sucesso, Video adicionado a coleção
* 204 - Falha, Video com Timestamp mais antigo que 60 segundos



#### DELETE /videos
API para Limpar todos os Videos

##### Header:
Utilizar Basic Authentication
```
Authorization: Basic c2FtYmF0ZWNoOnNhbWJhdGVjaA==
```

##### Response
* 204 - Sucesso, Videos apagados



#### GET /statistics
API para Retornar as Estatisticas baseadas nos ultimos 60 segundos

##### Header:
Utilizar Basic Authentication
```
Authorization: Basic c2FtYmF0ZWNoOnNhbWJhdGVjaA==
```

##### Response
JSON:
```
{
    "sum" : 1000,
    "avg" : 100,
    "min" : 50,
    "max" : 200,
    "count" : 10
}
```
HTTP status code:
* 200 - Sucesso


## Código Implementado
Ao entrar um novo Video na Aplicação via POST, este Video passa por uma validaç]ap de seus campos, se foram preenchidos e se sua timestamp é anterior ao momento atual, caso ele passe na validação o fluxo segue, e este Video será adicionado em um ConcurrentSkipListMap (videoMap), onde a chave é um Long (o timestamp do Video) e o valor é o próprio Video. 

Ao realizar uma chamada na Aplicação via DELETE, o  ConcurrentSkipListMap (videoMap) será esvaziado, assim chamando o método clear().

Após um tempo a aplicação on-line e feito algumas chamadas para adicionar novos Videos, tendo estes Videos como válidos, o  ConcurrentSkipListMap possui alguns valores.

Ao chamar a API /statistics, é realizado uma busca no “videoMap” pelos Videos inseridos nos ultimos 60 segundos, utilizando a função “tailMap”, sendo assim, retornado um SubMap, com este SubMap será utilizado apenas os seus valores (Video), que é passado para um stream e transformado em List, assim tendo uma lista de Videos dos ultimos 60 segundos. Esta lista então é passada para um stream preenchera um DoubleSummaryStatistics, já com todas as informações calculadas. O resultado então é convertido e apresentado como response do Endpoint.

## Testes Unitários
Para rodar os Testes o seguinte comando pode ser utilizado em um Terminal:
```
$ ./gradlew test
```

##### VideoSpec
Verifica a validação dos Videos, se os campos estão preenchidos e se seu timestamp é valido.

##### VideoControllerSpec
Verifica pelo HTTP Status Code a entrada de um Video Válido e um Inválido passado pela API /videos via POST.
Também verifica pelo HTTP Status Code a API /videos via DELETE.

##### StatisticsControllerSpec
Verifica o cenário, aonde há a inserção de sete Videos válidos, e realiza a chamada da API /statistics via GET, verificando assim o seu HTTP Status Code e os Valores retornados no response.


## Ambiente da Aplicação
Foi utilizado a Amazon Web Services para rodar a aplicação, foi utilizado o AWS Elastic Beanstalk.

## Continuos Integration / Continuos Delivery
Para realizar o CI/CD do fonte, foi utilizado o CircleCI. Durante o build são executados os testes unitários e o "war" produzido é feito o deploy no Beanstalk.


## Acesso a Aplicação
CNAME do meu Dominio apontando para o Beanstalk
```
http://sambatech.mbhosts.com/
``` 
URL do Beanstalk
```
http://sambatech-statistics.us-east-2.elasticbeanstalk.com/
```