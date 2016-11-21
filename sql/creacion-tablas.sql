--
-- Table structure for table `ADMINISTRADOR`
--

DROP TABLE IF EXISTS `ADMINISTRADOR`;
CREATE TABLE `ADMINISTRADOR` (
  `ID` bigint(20) NOT NULL,
  `FECHAALTA` datetime DEFAULT NULL,
  `LOGIN` varchar(25) DEFAULT NULL,
  `NOMBRE` varchar(75) DEFAULT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `TIPO_USUARIO` varchar(20) DEFAULT NULL,
  `ULTIMOACCESO` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `CENTROSALUD`
--

DROP TABLE IF EXISTS `CENTROSALUD`;
CREATE TABLE `CENTROSALUD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(50) NOT NULL,
  `TELEFONO` varchar(9) DEFAULT NULL,
  `CODIGOPOSTAL` varchar(5) NOT NULL,
  `DOMICILIO` varchar(75) NOT NULL,
  `LOCALIDAD` varchar(30) NOT NULL,
  `PROVINCIA` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `CITA`
--

DROP TABLE IF EXISTS `CITA`;
CREATE TABLE `CITA` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DURACION` int(11) DEFAULT NULL,
  `ESTADO` varchar(255) DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `HORA` time DEFAULT NULL,
  `MEDICO_ID` bigint(20) DEFAULT NULL,
  `PACIENTE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_CITA_PACIENTE_ID` (`PACIENTE_ID`),
  KEY `FK_CITA_MEDICO_ID` (`MEDICO_ID`),
  CONSTRAINT `FK_CITA_MEDICO_ID` FOREIGN KEY (`MEDICO_ID`) REFERENCES `MEDICO` (`ID`),
  CONSTRAINT `FK_CITA_PACIENTE_ID` FOREIGN KEY (`PACIENTE_ID`) REFERENCES `PACIENTE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `FARMACIA`
--

DROP TABLE IF EXISTS `FARMACIA`;
CREATE TABLE `FARMACIA` (
  `ID` bigint(20) NOT NULL,
  `FECHAALTA` datetime DEFAULT NULL,
  `NIF` varchar(9) NOT NULL,
  `NOMBREFARMACIA` varchar(75) NOT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `TIPO_USUARIO` varchar(20) DEFAULT NULL,
  `ULTIMOACCESO` datetime DEFAULT NULL,
  `CODIGOPOSTAL` varchar(5) NOT NULL,
  `DOMICILIO` varchar(75) NOT NULL,
  `LOCALIDAD` varchar(30) NOT NULL,
  `PROVINCIA` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `MEDICAMENTO`
--

DROP TABLE IF EXISTS `MEDICAMENTO`;
CREATE TABLE `MEDICAMENTO` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FABRICANTE` varchar(50) NOT NULL,
  `FAMILIA` varchar(50) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `NUMERODOSIS` int(11) DEFAULT NULL,
  `PRINCIPIOACTIVO` varchar(50) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `MEDICO`
--

DROP TABLE IF EXISTS `MEDICO`;
CREATE TABLE `MEDICO` (
  `ID` bigint(20) NOT NULL,
  `APELLIDOS` varchar(50) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `EMAIL` varchar(25) DEFAULT NULL,
  `FECHAALTA` datetime DEFAULT NULL,
  `NOMBRE` varchar(30) NOT NULL,
  `NUMEROCOLEGIADO` varchar(10) NOT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `TELEFONO` varchar(9) DEFAULT NULL,
  `TIPO_USUARIO` varchar(20) DEFAULT NULL,
  `ULTIMOACCESO` datetime DEFAULT NULL,
  `CENTROSALUD_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_MEDICO_CENTROSALUD_ID` (`CENTROSALUD_ID`),
  CONSTRAINT `FK_MEDICO_CENTROSALUD_ID` FOREIGN KEY (`CENTROSALUD_ID`) REFERENCES `CENTROSALUD` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `PACIENTE`
--

DROP TABLE IF EXISTS `PACIENTE`;
CREATE TABLE `PACIENTE` (
  `ID` bigint(20) NOT NULL,
  `APELLIDOS` varchar(50) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  `EMAIL` varchar(25) DEFAULT NULL,
  `FECHAALTA` datetime DEFAULT NULL,
  `NOMBRE` varchar(30) NOT NULL,
  `NUMEROSEGURIDADSOCIAL` varchar(13) NOT NULL,
  `NUMEROTARJETASANITARIA` varchar(10) NOT NULL,
  `PASSWORD` varchar(64) DEFAULT NULL,
  `TELEFONO` varchar(9) DEFAULT NULL,
  `TIPO_USUARIO` varchar(20) DEFAULT NULL,
  `ULTIMOACCESO` datetime DEFAULT NULL,
  `CODIGOPOSTAL` varchar(5) NOT NULL,
  `DOMICILIO` varchar(75) NOT NULL,
  `LOCALIDAD` varchar(30) NOT NULL,
  `PROVINCIA` varchar(30) NOT NULL,
  `MEDICO_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PACIENTE_MEDICO_ID` (`MEDICO_ID`),
  CONSTRAINT `FK_PACIENTE_MEDICO_ID` FOREIGN KEY (`MEDICO_ID`) REFERENCES `MEDICO` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `PRESCRIPCION`
--

DROP TABLE IF EXISTS `PRESCRIPCION`;
CREATE TABLE `PRESCRIPCION` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DOSIS` int(11) DEFAULT NULL,
  `FECHAFIN` date DEFAULT NULL,
  `FECHAINICIO` date DEFAULT NULL,
  `INDICACIONES` varchar(255) DEFAULT NULL,
  `MEDICAMENTO_ID` bigint(20) DEFAULT NULL,
  `MEDICO_ID` bigint(20) DEFAULT NULL,
  `PACIENTE_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_PRESCRIPCION_MEDICO_ID` (`MEDICO_ID`),
  KEY `FK_PRESCRIPCION_PACIENTE_ID` (`PACIENTE_ID`),
  KEY `FK_PRESCRIPCION_MEDICAMENTO_ID` (`MEDICAMENTO_ID`),
  CONSTRAINT `FK_PRESCRIPCION_MEDICAMENTO_ID` FOREIGN KEY (`MEDICAMENTO_ID`) REFERENCES `MEDICAMENTO` (`ID`),
  CONSTRAINT `FK_PRESCRIPCION_MEDICO_ID` FOREIGN KEY (`MEDICO_ID`) REFERENCES `MEDICO` (`ID`),
  CONSTRAINT `FK_PRESCRIPCION_PACIENTE_ID` FOREIGN KEY (`PACIENTE_ID`) REFERENCES `PACIENTE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `RECETA`
--

DROP TABLE IF EXISTS `RECETA`;
CREATE TABLE `RECETA` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CANTIDAD` int(11) DEFAULT NULL,
  `ESTADORECETA` varchar(20) DEFAULT NULL,
  `FINVALIDEZ` date DEFAULT NULL,
  `INICIOVALIDEZ` date DEFAULT NULL,
  `FARMACIADISPENSADORA_ID` bigint(20) DEFAULT NULL,
  `PRESCRIPCION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_RECETA_FARMACIADISPENSADORA_ID` (`FARMACIADISPENSADORA_ID`),
  KEY `FK_RECETA_PRESCRIPCION_ID` (`PRESCRIPCION_ID`),
  CONSTRAINT `FK_RECETA_PRESCRIPCION_ID` FOREIGN KEY (`PRESCRIPCION_ID`) REFERENCES `PRESCRIPCION` (`ID`),
  CONSTRAINT `FK_RECETA_FARMACIADISPENSADORA_ID` FOREIGN KEY (`FARMACIADISPENSADORA_ID`) REFERENCES `FARMACIA` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `USUARIO_GEN`
--

DROP TABLE IF EXISTS `USUARIO_GEN`;
CREATE TABLE `USUARIO_GEN` (
  `GEN_NAME` varchar(50) NOT NULL,
  `GEN_VAL` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`GEN_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
