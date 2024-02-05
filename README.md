
# Micronaut Inventory Project

This Micronaut project demonstrates the usage of two APIs: one for adding coffee mugs and another for filtering coffee mugs.

## How to Create and Run the Jar
### Clone the repository:

```bash
git clone https://github.com/rohit-sawant/micronaut-inventory-project.git
```
### Navigate to the project directory:
```bash
cd micronaut-inventory-project
```

### Build the project using Gradle:
Make sure you check all properties are right in `src/test/resources/application-test.properties` and `src/main/resources/applicaiton.properties` 
```bash
gradlew build
```

### Run the project:

#### With Default Properties
```bash
java -jar build/libs/demo1-0.1-all.jar
```
#### Using external application.properties and using environment variables
```bash
java -Dmicronaut.config.files=application.properties -DMICRONAUT_SERVER_PORT=8081 -DMICRONAUT_DATASOURCE_DATABASE_NAME=FPL2 -jar demo1-0.1-all.jar
```

The Micronaut application will start, and you can access the APIs at http://localhost:8081.


## APIs

### Add Coffee Mug

Endpoint: `/coffee_mug`

Method: `POST`

Request body:
```json
{
    "quantity": 4,
    "count": 31,
    "skuCode": "CM002",
    "displayName": "cofffe mug 2",
    "price": 30.00,
    "coffeeMugType": "mug type 2"
}
```

#### `curl` Command
```bash
curl --location 'http://localhost:8081/coffee_mug' \
--header 'Content-Type: application/json' \
--data '{
    "quantity": 4,
    "count": 31,
    "skuCode": "CM002",
    "displayName": "cofffe mug 2",
    "price": 30.00,
    "coffeeMugType": "mug type 2"
}'
```

Response body:
```json
{
    "id": 82,
    "quantity": 4,
    "count": 31,
    "skuCode": "CM002",
    "displayName": "cofffe mug 2",
    "price": 30.00,
    "coffeeMugType": {
            "id": 29,
            "name": "mug type 2"
        }
}
```


### Filter Coffee Mug

Endpoint: `/coffee_mug`

Method: `GET`

#### Parameters
- `priceRange`: Set the price range(`2.0-3.5`).
- `displayName`: Filter by display name(`Espresso`).
- `mugType`: Filter by mug type (`Espresso`).
- `filterOperation`: Apply filter operation.
    - `AND` : to have all the filters matched.
    - `OR`  : to have any of the filters matched.

#### `curl` Command
```bash
curl --location 'http://localhost:8081/coffee_mug?priceRange=2-3&filterOperation=AND&displayName=cup&mugType=espresso'
```

Response body:
```json
[
    {
        "id": 110,
        "quantity": 10,
        "count": 100,
        "skuCode": "SKU001",
        "displayName": "Espresso Cup 1",
        "price": 2.50,
        "coffeeMugType": {
            "id": 30,
            "name": "Espresso"
        }
    },
    {
        "id": 111,
        "quantity": 8,
        "count": 80,
        "skuCode": "SKU002",
        "displayName": "Cappuccino Cup 1",
        "price": 3.00,
        "coffeeMugType": {
            "id": 30,
            "name": "Espresso"
        }
    }
]
```


### Filter Coffee Mug with Pagination

Endpoint: `/coffee_mug/pagination`

Method: `GET`

#### Parameters
- `priceRange`: Set the price range(`2.0-3.5`).
- `displayName`: Filter by display name(`Espresso`).
- `mugType`: Filter by mug type (`Espresso`).
- `filterOperation`: Apply filter operation.
    - `AND` : to have all the filters matched.
    - `OR`  : to have any of the filters matched.
- `page` : Set the page number(`0`).
- `size` : Set the size per page(`1`).

#### `curl` Command
```bash
curl --location 'http://localhost:8081/coffee_mug/pagination?priceRange=2-3&filterOperation=AND&displayName=cup&mugType=espresso&page=0&size=1'
```

Response body:
```json
{
    "content": [
        {
            "id": 110,
            "quantity": 10,
            "count": 100,
            "skuCode": "SKU001",
            "displayName": "Espresso Cup 1",
            "price": 2.50,
            "coffeeMugType": {
                "id": 30,
                "name": "Espresso"
            }
        }
    ],
    "pageable": {
        "size": 1,
        "number": 0,
        "sort": {}
    },
    "totalSize": 2
}
```






