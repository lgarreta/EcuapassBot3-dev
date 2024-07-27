#!/usr/bin/env python3

import re, os, json, sys
from traceback import format_exc as traceback_format_exc

from .ecuapass_info_manifiesto import ManifiestoInfo
from .ecuapass_data import EcuData
from .ecuapass_utils import Utils
from .ecuapass_extractor import Extractor  # Extracting basic info from text

#----------------------------------------------------------
USAGE = "\
Extract information from document fields analized in AZURE\n\
USAGE: ecuapass_info_manifiesto.py <Json fields document>\n"
#----------------------------------------------------------
def main ():
	args = sys.argv
	fieldsJsonFile = args [1]
	runningDir = os.getcwd ()
	mainFields = ManifiestoInfo.getMainFields (fieldsJsonFile, runningDir)
	Utils.saveFields (mainFields, fieldsJsonFile, "Results")

#----------------------------------------------------------
# Class that gets main info from Ecuapass document 
#----------------------------------------------------------
class ManifiestoLogitrans (ManifiestoInfo):
	def __init__(self, fieldsJsonFile, runningDir):
		super().__init__ (fieldsJsonFile, runningDir)

	#-- Get empresa info
	def getEmpresaInfo (self):
		return EcuData.getEmpresaInfo ("LOGITRANS")

	def __str__(self):
		return f"{self.numero}"

	#-- Get tipo vehículo (VEHICULO/REMOLQUE) according to remolque info
	def getTipoVehiculo  (self, tipo, remolque):
		if tipo == "VEHICULO" and remolque ["placa"]:
			return "TRACTOCAMION"
		elif tipo == "VEHICULO" and not remolque ["placa"]:
			return "CAMION"
		else:
			return None

	#-- None for BYZA
	def getCheckCertificadoRemolque (self, key):
		return None

	#-- None for BYZA 
	def getCargaDescripcion (self):
			return None

	#-- Just "Originario"
	def getPermisosInfo (self):
		info = EcuData.getEmpresaInfo ("LOGITRANS")
		permisos = {"tipoPermisoCI"    : None,
			        "tipoPermisoPEOTP" : None,
			        "tipoPermisoPO"    : "1", 
			        "permisoOriginario": None,
			        "permisoServicios1": info ["permisos"]["servicios1"]
				   }
		return permisos

	#-----------------------------------------------------------
	#-- Get bultos info: cantidad, embalaje, marcas
	#-- Added a number to embalaje to use Extractor
	#-----------------------------------------------------------
	def getBultosInfo (self):
		bultos = Utils.createEmptyDic (["cartaporte", "cantidad", "embalaje", "marcas", "descripcion"])
		text = None
		try:
			bultos ["cartaporte"] = self.getNumeroCartaporte ()

			# Cantidad
			text             = self.fields ["30_Mercancia_Bultos"]["value"]
			bultos ["cantidad"] = Extractor.getNumber (text)

			# Embalaje
			text = self.fields ["31_Mercancia_Embalaje"]["value"]
			bultos ["embalaje"] = Extractor.getTipoEmbalaje ("00 " + text)

			# Marcas 
			bultos ["marcas"] = "SIN MARCAS" 

			# Descripcion
			descripcion = self.fields ["29_Mercancia_Descripcion"]["content"]
			descripcion = self.cleanWaterMark (descripcion)
			bultos ["descripcion"] = self.getMercanciaDescripcion (descripcion)
		except:
			Utils.printException ("Obteniendo información de 'Bultos'", text)

		return bultos
	


#--------------------------------------------------------------------
# Call main 
#--------------------------------------------------------------------
if __name__ == '__main__':
	main ()

