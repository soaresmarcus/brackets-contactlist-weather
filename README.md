# brackets-contactlist-weather

Application Suite: Brackets validation, Contact list and Weather applications

## Quick start:
1 - Execute o script "deploy.sh" na raiz projeto:

```sh
./deploy.sh
```

2 - Após feito o deploy, acesso a aplicação http://localhost:8080



#Contact List
1 - A API pode ser consumida através de aplicativos como o PostMan, SoapUi ou CURL. Abaixo os exemplos de consumo
via CURL:

###Criar Pessoa
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"name": "Marcio Alberto", "cpf": "015.457.478-44", "birthDate": "19871016", "motherName": "Maria Alcides", "fatherName": "Alberto Medeiros", "gender": "M", "address": "Rua Andrades, 150 apto. 304, Funcionários"}' \
  http://localhost:8080/contactlist/person/create

###Todas as pessoas
curl --header "Content-Type: application/json" \
  --request GET \
http://localhost:8080/contactlist/person/get/all

###Atualizar pessoa
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"motherName": "Maria Alcides Rabelo II"}' \
  http://localhost:8080/contactlist/person/update/id/ID_DA_PESSOA

###Deletar pessoa por id
curl --header "Content-Type: application/json" \
  --request GET \
    http://localhost:8080/contactlist/person/delete/id/ID_DA_PESSOA



###Criar contato
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"type": "phone","value": "(38) 99988-4441","personId": "ID_DA_PESSOA"}' \
  http://localhost:8080/contactlist/contact/create

###Todos os contatos
curl --header "Content-Type: application/json" \
  --request GET \
    http://localhost:8080/contactlist/contact/get/all

###Atualizar contato
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"type": "phone","value": "(38) 77777-0000"}' \
  http://localhost:8080/contactlist/contact/update/id/ID_DO_CONTATO

###Deletar contato por id
curl --header "Content-Type: application/json" \
  --request GET \
    http://localhost:8080/contactlist/contact/delete/id/ID_DO_CONTATO




# Weather
1 - Para rodar a aplicação é necessário a instalação do NodeJs e Angular.

NodeJs: https://github.com/nodesource/distributions
Angular 4: https://coursetro.com/posts/code/55/How-to-Install-an-Angular-4-App

2 - Rode o comando no terminal para iniciar a aplicação:

```sh
cd ./weather && ng serve
```
3 - Acesse http://localhost:4200/
