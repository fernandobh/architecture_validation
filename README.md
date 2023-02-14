# Aplicação de teste para performance e monitoramento

# Antes de tudo
## É necessário docker para rodar este teste
## Se o seu SO for Windows, o make pode ser que não exista , então deve-se seguir os comandos de dentro do make.

## Primeiro passo
```
make prepare
```

## Rodando a aplicação com o melhor tamanho de heap
```
make up-normal
```

## Rodando a aplicação com a menor tamanho de heap
```
make up-smallest
```

## Rodando a aplicação com tamanho pequeno de heap
```
make up-small
```

## Rodando teste com Jmeter
```
make SAIDA=[NOME-SAIDA] jm-test
```


