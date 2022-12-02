#!/bin/bash

# mapping local/container and run container in background
docker run -p 25:25 -p 8282:8282 -d smtp_centeno_guidetti/mockmock
