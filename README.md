# split-receipt
App for splitting receipt

Made data initialization so that initial data is created for you to test. Class - DataInitializer

There are two examples:
1. Receipt id = 1

Name, id, amount
Alan: 1, 1
Ben: 2, 2
Chris: 3, 3
Dave: 4, 5

2. Receipt id = 2

Name, id, amount
Alan: 1, 0
Ben: 2, 50 000
Chris: 3, 1 000 000

- Endpoint for input creation: POST /receipt/input
- Endpoint to see overall amount of a receipt: GET /receipt/{receipt-id}
- Endpoint to see everyones total amount to a receipt: GET /receipt/inputs/{receipt-id}
- Endpoint to see who owes me and how much: POST /receipt/who-owes-me
{
    "receipt_id":1,
    "user_id":4
}

- Endpoint to see whom I owe money and how much: POST /receipt/i-owe-others
{
    "receipt_id":1,
    "user_id":2
}
