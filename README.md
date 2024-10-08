# CineCIC 🍿
A CineMAX é uma empresa de cinemas surgida na década de 70. Para estabelecer um cartão de visitas, a marca se destacava por manter um padrão tradicional de época em seus cinemas, que não sofreram alterações significativas ao longo dos anos. Entretanto, percebendo o potencial das vendas online, e o fato de que o apelo histórico da CineMAX está perdendo muita força nesta década, o CEO da empresa resolveu contratar o grupo 14 para desenvolver um sistema moderno de vendas de ingressos para seus cinemas, visando a modernização do setor de logística da empresa.

## Sobre o Projeto

O grupo 14 propõe o **CineCIC**, uma plataforma integrada para vendas de ingressos online. Desenvolvido em Java, o CineCIC objetiva viabilizar a interação entre o sistema e o usuário, possibilitando não apenas a venda de ingressos, mas também o gerenciamento das sessões de cinema. Além disso, o CineCIC possui funcionalidades de alta demanda, como o sistema de meia-entrada e descontos para venda de ingressos.

## Estrutura do Projeto

Este repositório contém o código fonte do CineCIC, dividido nas seguintes fases principais:

1. **Proposta e Diagrama de Classes:** Conceito inicial do sistema e a estrutura de classes.
2. **Apresentação da Proposta e do Diagrama de Classes:** Validação do design inicial e feedback.
3. **Telas e Implementação das Classes:** Desenvolvimento da interface gráfica usando Java Swing e implementação do backend em Java.
4. **Apresentação das Telas e das Classes:** Demonstração do funcionamento do sistema, destacando a integração entre a interface gráfica e a lógica de negócios.

## Regras de Negócio

O sistema CineCIC deve respeitar as seguintes regras de negócio estabelecidas pela CineMAX:

1. **Horário de Funcionamento:**
Os cinemas do CineMAX operam das 12h às 22h. Portanto, o cadastro de sessões deve ocorrer exclusivamente dentro desse intervalo de tempo.

2. **Desconto em Horários Especiais:**
Devido à queda nas vendas entre 12h e 15h, há um desconto de 25% aplicado a ingressos de sessões que se iniciam nesse intervalo.

3. **Política de Meia-Entrada:**
As políticas de meia-entrada devem ser aplicadas separadamente para cada item do pedido. A verificação dos critérios legais para meia-entrada será realizada na entrada das sessões, não sendo parte do escopo do CineCIC.

## Ambiente de Desenvolvimento

Este projeto foi desenvolvido utilizando a JDK 22 da Oracle. Certifique-se de que a mesma versão da JDK esteja instalada em sua máquina para evitar problemas de compatibilidade.

- **JDK:** Oracle JDK 22
- **IDEs:** IntelliJ IDEA e Apache NetBeans

## Contribuição

Esse projeto foi desenvolvido pelo grupo 14 na turma 3 da disciplina Técnicas de Programação 1 no semestre 2024.1. As contribuições foram feitas por todos os membros do grupo como parte do processo de aprendizado e desenvolvimento prático.


