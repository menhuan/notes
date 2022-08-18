#!/bin/bash
sshpass -pFeng930409@ rsync -avup --remove-source-files   ubuntu@101.43.210.78:/home/ubuntu/docker-compose/douyin/output/output/  .
