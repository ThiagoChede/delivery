Feature: delivery resources

  Scenario: Create delivery return success
    When I make a POST call to "/delivery" endpoint with post body:
    """
    {
      "carga": {
        "id": 0,
        "peso": 0
      },
      "id": 1,
      "veiculo": {
        "ano": "2019",
        "cor": "azul",
        "descricao": "do ano",
        "id": 0,
        "marca": "chevrolet",
        "modelo": "prisma",
        "placa": "asd-123",
        "renavam": "234234"
      }
    }
    """
    Then response status code should be 201
