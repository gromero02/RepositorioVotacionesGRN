CREATE DATABASE IF NOT EXISTS  CENSO_POR_COMUNIDADES;
USE CENSO_POR_COMUNIDADES;

/*Se crea la tabla PORCENTAJES_RANGOEDAD para almacenar la informacion del numero de habitantes que existe en cada comunidad*/

CREATE TABLE PORCENTAJES_RANGOEDAD (
	NOMBRE_COMUNIDAD VARCHAR(30) NOT NULL COMMENT 'Nombre de la comunidad autonoma',
	RANGO_1_9 INT(3) NOT NULL COMMENT 'Porcentaje de habitantes cuya edad esta comprendida entre 1 y 9 años',
	RANGO_10_17 INT(3) NOT NULL COMMENT 'Porcentaje de habitantes cuya edad esta comprendida entre 10 y 17 años',
    RANGO_18_25 INT(3) NOT NULL COMMENT 'Porcentaje de habitantes cuya edad esta comprendida entre 18 y 25 años',
    RANGO_26_40 INT(3) NOT NULL COMMENT 'Porcentaje de habitantes cuya edad esta comprendida entre 26 y 40 años',
    RANGO_41_65 INT(3) NOT NULL COMMENT 'Porcentaje de habitantes cuya edad esta comprendida entre 41 y 65 años',
    RANGO_MAS_66 INT(3) NOT NULL COMMENT 'Porcentaje de habitantes cuya edad es superior o igual a 66 años',
    TOTAL_HABITANTES INT(8) NOT NULL COMMENT 'Numero total de habitantes que tiene la comunidad',
    CONSTRAINT PK_PORCENTAJES_RANGOEDAD PRIMARY KEY (NOMBRE_COMUNIDAD)
);

/*Se insertan los datos para la comunidad de Andalucia*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Andalucia', 10, 8, 14, 28, 25, 15, 8427407);

/*Se insertan los datos para la comunidad de Aragon*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Aragon', 5, 7, 12, 35, 34, 7, 1326261);

/*Se insertan los datos para la comunidad de Asturias*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Asturias', 14, 5, 9, 30, 17, 25, 1011792);

/*Se insertan los datos para la comunidad de Baleares*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Baleares', 13, 12, 20, 21, 23, 11, 1173008);

/*Se insertan los datos para la comunidad de Canarias*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Canarias', 9, 10, 27, 27, 18, 9, 2172944);

/*Se insertan los datos para la comunidad de Cantabria*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Cantabria', 8, 12, 22, 25, 17, 16, 584507);

/*Se insertan los datos para la comunidad de Castilla La Mancha*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Castilla La Mancha', 10, 15, 18, 26, 17, 14, 2059562);

/*Se insertan los datos para la comunidad de Castilla y León*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Castilla y Leon', 5, 16, 24, 17, 19, 19, 2383139);

/*Se insertan los datos para la comunidad de Catalunia*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Catalunia', 15, 14, 27, 16, 18, 10, 7763362);

/*Se insertan los datos para la comunidad de Comunidad Valenciana*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Comunidad Valenciana', 10, 8, 14, 28, 25, 15, 5058138);

/*Se insertan los datos para la comunidad de Extremadura*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Extremadura', 7, 9, 26, 15, 20, 23, 1059501);

/*Se insertan los datos para la comunidad de Galicia*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Galicia', 4, 11, 25, 18, 21, 21, 2695645);

/*Se insertan los datos para la comunidad de Madrid*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Madrid', 10, 14, 31, 17, 16, 12, 6751251);

/*Se insertan los datos para la comunidad de Murcia*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Murcia', 7, 9, 23, 26, 20, 15, 1518486);

/*Se insertan los datos para la comunidad de Navarra*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Navarra', 6, 8, 14, 30, 17, 25, 661537);

/*Se insertan los datos para la comunidad de Pais Vasco*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Pais Vasco', 5, 10, 25, 24, 17, 19, 2213993);

/*Se insertan los datos para la comunidad de La Rioja*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('La Rioja', 8, 12, 22, 24, 18, 16, 319796);

/*Se insertan los datos para la comunidad de Ceuta*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Ceuta', 15, 13, 27, 24, 16, 5, 86517);

/*Se insertan los datos para la comunidad de Melilla*/
INSERT INTO PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD, RANGO_1_9, RANGO_10_17, RANGO_18_25, RANGO_26_40, RANGO_41_65, RANGO_MAS_66, TOTAL_HABITANTES)
VALUES ('Melilla', 17, 18, 29, 14, 14, 8, 86261);



	