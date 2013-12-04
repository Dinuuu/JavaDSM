#!/bin/sh

set -x


cd ..


javac dsm/*.java


jar cf dsm/dsm_cliente.jar dsm/Cerrojo.class dsm/Almacen.class dsm/FabricaCerrojos.class dsm/CabeceraObjetoCompartido.class dsm/ObjetoCompartido.class dsm/DSMCerrojo.class

jar cf dsm/dsm_servidor.jar dsm/Cerrojo.class dsm/CerrojoImpl.class dsm/Almacen.class dsm/AlmacenImpl.class dsm/CabeceraObjetoCompartido.class dsm/ObjetoCompartido.class dsm/FabricaCerrojos.class dsm/FabricaCerrojosImpl*.class
