#!/usr/bin/env python3

import re, os, json, sys
from traceback import format_exc as traceback_format_exc
from datetime import datetime, timedelta

from .ecuapass_info_cartaporte import CartaporteInfo
from .ecuapass_extractor import Extractor
from .ecuapass_data import EcuData
from .ecuapass_utils import Utils

#----------------------------------------------------------
USAGE = "\
Extract information from document fields analized in AZURE\n\
USAGE: ecuapass_info_cartaportes.py <Json fields document>\n"
#----------------------------------------------------------
# Main
#----------------------------------------------------------
def main ():
	args = sys.argv
	fieldsJsonFile = args [1]
	runningDir = os.getcwd ()
	mainFields = CartaporteInfo.getMainFields (fieldsJsonFile, runningDir)
	Utils.saveFields (mainFields, fieldsJsonFile, "Results")

#----------------------------------------------------------
# Class that gets main info from Ecuapass document 
#----------------------------------------------------------
class CartaporteNTA (CartaporteInfo):
	def __init__ (self, fieldsJsonFile, runningDir):
		super().__init__ (fieldsJsonFile, runningDir)

	def getEmpresaInfo (self):
		return EcuData.getEmpresaInfo ("NTA")

	#-------------------------------------------------------------------
	#-- Get subject info: nombre, dir, pais, ciudad, id, idNro ---------
	#-- NTA format: <Nombre> <Direccion> <ID> <PaisCiudad> -----
	#-- Moved to info_cartaporte
	#-------------------------------------------------------------------
	#-- Get subject info: nombre, dir, pais, ciudad, id, idNro
#	def getSubjectInfo (self, key):
#		subject = {"nombre":None, "direccion":None, "pais": None, 
#		           "ciudad":None, "tipoId":None, "numeroId": None}
#		text	= Utils.getValue (self.fields, key)
#		try:
#			text    = re.sub ("\s*//\s*", "", text)   # For SILOG "//" separator cartaportes
#			text, subject = Extractor.removeSubjectId (text, subject, key)
#			text, subject = Extractor.removeSubjectCiudadPais (text, subject, self.resourcesPath, key)
#			text, subject = Extractor.removeSubjectNombreDireccion (text, subject, key)
#		except:
#			Utils.printException (f"Obteniendo datos del sujeto: '{key}' en el texto: '{text}'")
#
#		return (subject)
#
	#-----------------------------------------------------------
	#-- Get 'total bultos' and 'tipo embalaje' -----------------
	#-- Specialized for NTA
	#-----------------------------------------------------------
	def getBultosInfo (self):
		print ("+++ getBultosInfo NTA")
		bultosInfo = super ().getBultosInfo ()
		try:
			# Embalaje
			text = self.fields ["11_MarcasNumeros_Bultos"]["value"]
			print ("+++ text:", text)
			bultosInfo ["embalaje"] = Extractor.getTipoEmbalaje (text)
		except:
			Utils.printException ("Obteniendo información de 'Bultos'", text)

		return bultosInfo
#--------------------------------------------------------------------
# Call main 
#--------------------------------------------------------------------
if __name__ == '__main__':
	main ()

