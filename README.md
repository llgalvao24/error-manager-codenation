# Central de Erros - API

## CircleCI Master Status
[![CircleCI](https://circleci.com/gh/llgalvao24/error-manager-codenation/tree/master.svg?style=svg)](https://circleci.com/gh/llgalvao24/error-manager-codenation/tree/master)

## Indice

* [Sobre o projeto](#sobre-o-projeto)
  * [Principais frameworks utilizados](#principais-frameworks-utilizados)
  * [Banco de dados](#banco-de-dados)
  * [Documentação](#documentação)
  * [Link API Heroku](#link-api)
  * [Slides](#slides)
  * [CircleCI Status](#circleci-master-status)

## Sobre o projeto

Projeto desenvolvido durante a aceleração AceleraDev-Java, oferecida pela [Codenation](https://codenation.dev/) em parceria com a [CI&T](https://br.ciandt.com/).

A proposta consiste em fazer uma aplicação para gerenciar log de erros, e sua arquitetura deve ser composta por:

* criar endpoints para serem usados pelo frontend da aplicação;
* criar um endpoint que será usado para gravar os logs de erro em um banco de dados relacional;
* a API deve ser segura, permitindo acesso apenas com um token de autenticação válido.


### Principais frameworks utilizados
* [SpringBoot](https://spring.io/)
* [Flyway](https://flywaydb.org/)
* [MapStruct](https://mapstruct.org/)

### Banco de dados
Banco de dados utilizado para persistência de dados da API.
* [postgresql](https://www.postgresql.org/)

### Documentação
Documentação da API.
* [Documentação](http://error-manager-codenation.herokuapp.com/swagger-ui.html)

### Link API
Link da API hospedada no Heroku.
* [Error Manager API](http://error-manager-codenation.herokuapp.com/)

### Slides
Slides utilizados para a apresentação.
* [Slides apresentação](https://docs.google.com/presentation/d/1MpI1OKCI2onfxcHp-Y7NfeanDduXJFLB6SHj5jTGRvk/edit?usp=sharing)