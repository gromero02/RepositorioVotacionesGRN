-- ----------------------------------------------------------------------------------------------------------------------------------------------------------------
-- TABLA DE VOTOS GENERALES

USE CENSO_POR_COMUNIDADES;

CREATE TABLE VOTACION (
	
		ID_VOTACION INT NOT NULL AUTO_INCREMENT COMMENT 'Identificador de las votaciones',
		NOMBRE_COMUNIDAD VARCHAR(30) NOT NULL COMMENT 'Nombre de la comunidad autonoma',
    
		-- VOTOS DE LAS PERSONAS ENTRE 18 Y 25 AÑOS
			PP_18_25 INT(3) NULL COMMENT 'Votos al PP de las personas entre 18 y 25 años',
			PSOE_18_25 INT(3) NULL COMMENT 'Votos al PSOE de las personas entre 18 y 25 años',
			PODEMOS_18_25 INT(3) NULL COMMENT 'Votos al PODEMOS de las personas entre 18 y 25 años',
			VOX_18_25 INT(3) NULL COMMENT 'Votos al VOX de las personas entre 18 y 25 años',
        
        -- VOTOS DE LAS PERSONAS ENTRE 26 Y 40 AÑOS
			PP_26_40 INT(3) NULL COMMENT 'Votos al PP de las personas entre 26 y 40 años',
			PSOE_26_40 INT(3) NULL COMMENT 'Votos al PSOE de las personas entre 26 y 40 años',
			PODEMOS_26_40 INT(3) NULL COMMENT 'Votos al PODEMOS de las personas entre 26 y 40 años',
			VOX_26_40 INT(3) NULL COMMENT 'Votos al VOX de las personas entre 26 y 40 años',
            
		-- VOTOS DE LAS PERSONAS ENTRE 41 Y 65 AÑOS
			PP_41_65 INT(3) NULL COMMENT 'Votos al PP de las personas entre 41 y 65 años',
			PSOE_41_65 INT(3) NULL COMMENT 'Votos al PSOE de las personas entre 41 y 65 años',
			PODEMOS_41_65 INT(3) NOT NULL COMMENT 'Votos al PODEMOS de las personas entre 41 y 65 años',
			VOX_41_65 INT(3) NOT NULL COMMENT 'Votos al VOX de las personas entre 41 y 65 años',  
   
		-- VOTOS DE LAS PERSONAS MAS DE 66 AÑOS
			PP_MAS_66 INT(3) NULL COMMENT 'Votos al PP de las personas con más de 66 años',
			PSOE_MAS_66 INT(3) NULL COMMENT 'Votos al PSOE de las personas con más de 66 años',
			PODEMOS_MAS_66 INT(3) NULL COMMENT 'Votos al PODEMOS de las personas con más de 66 años',
			VOX_MAS_66 INT(3) NULL COMMENT 'Votos al VOX de las personas con más de 66 años',
            
    CONSTRAINT PK_VOTACION PRIMARY KEY (ID_VOTACION),
    CONSTRAINT FK_VOTACION_NOMBRE_COMUNIDAD FOREIGN KEY (NOMBRE_COMUNIDAD) REFERENCES PORCENTAJES_RANGOEDAD (NOMBRE_COMUNIDAD)
);