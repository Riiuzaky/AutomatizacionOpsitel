Feature: extraer registros operadores
yo como asesor deseo extraer los registros de operadores de mis clientes

Scenario:
  Given el asesor navego a la url de OSIPTEL
  And Cargo el archivo de excel
  When extraigo documento del usuario
  Then agrego los operadores al documento
