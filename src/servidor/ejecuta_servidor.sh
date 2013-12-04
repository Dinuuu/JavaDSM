#!/bin/sh

set -x

java -Djava.security.policy=servidor.permisos -cp .:dsm_servidor.jar ServidorDSM $*
