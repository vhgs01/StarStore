# StarStore
O projeto consiste em criar uma loja de itens de Star Wars que o usuário é capaz de adicionar os itens desejados em um carrinho de compras e finalizar a compra com uma simulação de transação e-commerce.

Para obter os itens da loja, sua aplicação deverá realizar uma chamada `GET` na URL https://private-841202-stonechallenge.apiary-mock.com/products

A lista de itens deve exibir as seguintes informações:
+ Nome [title]
+ Preço [price]
+ Vendedor [seller]
+ Foto do item [thumbnailHd]

Após o usuário adicionar todos os itens no carrinho, ele deverá finalizar a compra.
Para finalizar a compra, aconselhamos que use o [Apiary](https://apiary.io) como API nessa etapa.

###### Simulação E-commerce

Sua aplicação deve realizar um `POST` com os seguintes atributos:
+ Número do cartão (máximo de 16 números - XXXX XXXX XXXX XXXX)
+ Nome do portador do cartão
+ Vencimento do cartão (MM/yy)
+ CVV (código encontrado na parte traseira do cartão)
+ Valor da transação (total dos itens no carrinho)

``` json
{  
   "card_number":"1234123412341234",
   "value":7990,
   "cvv":789,
   "card_holder_name":"Luke Skywalker",
   "exp_date":"12/24"
}
```

###### Banco de dados
Todas as transações realizadas devem ser salvas em um banco interno com os seguintes campos:

+ Valor
+ Data e hora
+ Últimos 4 dígitos do cartão
+ Nome do portador do cartão

###### Lista de Transações
A aplicação deverá conter uma tela para exibir as transações que foram salvas em seu banco de dados.


### Instruções
Ao finalizar, você pode nos mandar um link do repositório no GitHub/BitBucket. Caso haja alguma configuração adicional para a execução do app, deixe as instruções em um `README.md` no repositório
