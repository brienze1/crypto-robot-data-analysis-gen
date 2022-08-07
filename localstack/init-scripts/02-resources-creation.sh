#!/bin/bash

echo "-----------------Script-02----------------- [localstack]"

echo "########### Creating SNS ###########"
aws sns create-topic --name cryptoAnalysisTopic --endpoint-url http://localhost:4566

echo "########### Listing SNS ###########"
aws sns list-topics --endpoint-url http://localhost:4566
