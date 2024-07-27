:: Create executable from python script

pyinstaller --add-data "ecuapassdocs/resources/images;images" --add-data "ecuapassdocs/resources/data_cartaportes/*.txt";"ecuapassdocs/resources/data_cartaportes/" --add-data "ecuapassdocs/resources/data_manifiestos/*.txt";"ecuapassdocs/resources/data_manifiestos/" --add-data "ecuapassdocs/resources/docs/*.png";"ecuapassdocs/resources/docs/" --add-data "ecuapassdocs/resources/docs/*.pdf";"ecuapassdocs/resources/docs/" --add-data "ecuapassdocs/resources/docs/*.json";"ecuapassdocs/resources/docs/" ecuapass_server.py

