## Requirements

Build a HTTP/REST API. You can choose your preferred programming language.

The end point has to return the amount of ERC-20 tokens a token holder has. Path params are the erc-20 contract address and the token holder address. Return is the amount of tokens.

API Spec: https://<HOST>/<ERC-20-Address>/<Token-Holder-Address>

Validate results can be done as well with Etherscan

https://etherscan.io/token/0x60eb57d085c59932d5faa6c6026268a4386927d0#balances


## How to build and start the application
- install java, git  

- clone repository  
    git clonegit@github.com:sadensmol/locgame_interview.git  

- go into the directory  
    cd locgame_interview  

- on linux/unix run  
    ./mvnw spring-boot:run

- on windows  
    mvnw.cmd spring-boot:run  
  
application will be started at localhost:8080