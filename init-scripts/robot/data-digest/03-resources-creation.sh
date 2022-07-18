#!bin/bash

echo "-----------------Script-02----------------- [data-digest]"

echo "########### Create Admin IAM Role ###########"
aws iam create-role --role-name admin-role --path / --assume-role-policy-document file:./admin-policy.json --endpoint-url http://localstack:4566

echo "########### Make S3 bucket ###########"
aws s3 mb s3://lambda-functions --endpoint-url http://localstack:4566

echo "########### Copy the lambda function to the S3 bucket ###########"
aws s3 cp crypto-robot-data-digest.zip s3://lambda-functions --endpoint-url http://localstack:4566

echo "########### Create the lambda dataDigestLambda ###########"
aws lambda create-function \
  --endpoint-url http://localstack:4566 \
  --function-name dataDigestLambda \
  --role arn:aws:iam::000000000000:role/admin-role \
  --code S3Bucket=lambda-functions,S3Key=crypto-robot-data-digest.zip \
  --handler ./dist/main/delivery/handler/handler.execute \
  --runtime nodejs10.x \
  --description "SQS Lambda handler for test sqs." \
  --timeout 60 \
  --memory-size 128

echo "########### Map the cryptoAnalysisQueue to the dataDigestLambda lambda function ###########"
aws lambda create-event-source-mapping \
  --function-name dataDigestLambda \
  --batch-size 1 \
  --event-source-arn "arn:aws:sqs:sa-east-1:000000000000:cryptoAnalysisQueue" \
  --endpoint-url http://localstack:4566
