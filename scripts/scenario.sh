#!/bin/bash

customer_id=$(curl -s -XPOST localhost:8080/customers -H "content-type:text/plain" -d 'alice' | sed -n "s/data://p")
account_id=$(curl -s -XPOST localhost:8080/accounts/customer/$customer_id | sed -n "s/data://p")

other_customer_id=$(curl -s -XPOST localhost:8080/customers -H "content-type:text/plain" -d 'bob' | sed -n "s/data://p")
other_account_id=$(curl -s -XPOST localhost:8080/accounts/customer/$customer_id | sed -n "s/data://p")

echo "customer_id: $customer_id"
echo "account_id: $account_id"

echo "other_customer_id: $other_customer_id"
echo "other_account_id: $other_account_id"

curl -s -XPOST localhost:8080/accounts/$account_id/deposit/1000/EUR
curl -s -XPOST localhost:8080/accounts/$account_id/withdraw/200/EUR
curl -s -XPOST localhost:8080/accounts/$account_id/transfer/$other_account_id/300/EUR
curl -s -XPOST localhost:8080/accounts/$other_account_id/transfer/$account_id/100/EUR

curl -s -XGET localhost:8080/accounts/$account_id| sed -n "s/data://p" | jq
