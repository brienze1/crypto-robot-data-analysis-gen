#!/bin/bash

echo "-----------------Script-02----------------- [data-digest]"

echo "########### Check if queue was created ###########"
until curl http://localstack:4566/health --silent; do
  echo "Localstack not up yet"
  sleep 1
done

echo "########### Check if queue was created ###########"
until aws sqs get-queue-attributes \
  --queue-url http://localhost:4566/000000000000/cryptoAnalysisQueue \
  --endpoint-url http://localstack:4566; do
  sleep 1
done

echo "########### Check if topic was created ###########"
until aws sns get-topic-attributes \
  --topic-arn arn:aws:sns:sa-east-1:000000000000:cryptoAnalysisTopic \
  --endpoint-url http://localstack:4566; do
  sleep 1
done
