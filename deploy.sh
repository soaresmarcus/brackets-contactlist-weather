#!/bin/bash

#Para a execução (use ctrl+c)
trap 'exit 0' 2

echo -e "Preparando ambiente "
mvn clean
echo -e "Compilando projeto"
mvn compile
echo -e "Gerando versao de deploy do ambiente"
mvn package

echo -e "Iniciando aplicacao"
java -jar ./target/prova-0.0.1-SNAPSHOT.jar

echo -e "Concluido"
