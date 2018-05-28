-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-05-2018 a las 03:25:09
-- Versión del servidor: 5.6.26
-- Versión de PHP: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `deteccion`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `antecedente`
--

CREATE TABLE IF NOT EXISTS `antecedente` (
  `idAntecedente` int(100) NOT NULL,
  `sentencias` int(100) DEFAULT NULL,
  `prision` int(100) DEFAULT NULL,
  `reinsidente` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `antecedente`
--

INSERT INTO `antecedente` (`idAntecedente`, `sentencias`, `prision`, `reinsidente`) VALUES
(1, 4, 2, 1),
(2, 1, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `antecedente_delito`
--

CREATE TABLE IF NOT EXISTS `antecedente_delito` (
  `idAntecedente` int(100) DEFAULT NULL,
  `idDelito` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `antecedente_delito`
--

INSERT INTO `antecedente_delito` (`idAntecedente`, `idDelito`) VALUES
(1, 1),
(1, 2),
(1, 4),
(2, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `delito`
--

CREATE TABLE IF NOT EXISTS `delito` (
  `idDelito` int(100) NOT NULL,
  `nombre` varchar(300) NOT NULL,
  `categoria` int(50) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `delito`
--

INSERT INTO `delito` (`idDelito`, `nombre`, `categoria`) VALUES
(1, 'Hurto calificado', 3),
(2, 'Homicidio culposo', 4),
(3, 'Testaferro', 1),
(4, 'Extorcion', 3),
(5, 'Porte ilegal de armas', 1),
(6, 'Estupefacientes', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `idUsuario` int(100) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `documento` varchar(50) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `genero` varchar(30) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `direccion` varchar(300) DEFAULT NULL,
  `imagen` blob,
  `idAntecedente` int(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombres`, `apellidos`, `documento`, `telefono`, `correo`, `genero`, `estado`, `direccion`, `imagen`, `idAntecedente`) VALUES
(111, 'Edison', 'Quijanos', '111', '12345678', 'ed@gmail.com', 'Hombre', 1, 'ninguna', NULL, 2),
(222, 'Hector Felipe', 'Hurtado Acosta', '222', '3118414998', 'hfhurtadoa@correo.udistrital.edu.co', 'Hombre', 1, 'cll 47 # 8', NULL, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `antecedente`
--
ALTER TABLE `antecedente`
  ADD PRIMARY KEY (`idAntecedente`);

--
-- Indices de la tabla `antecedente_delito`
--
ALTER TABLE `antecedente_delito`
  ADD KEY `idAntecedente` (`idAntecedente`,`idDelito`),
  ADD KEY `idDelito` (`idDelito`);

--
-- Indices de la tabla `delito`
--
ALTER TABLE `delito`
  ADD PRIMARY KEY (`idDelito`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`idUsuario`),
  ADD KEY `idAntecedente` (`idAntecedente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `antecedente`
--
ALTER TABLE `antecedente`
  MODIFY `idAntecedente` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `delito`
--
ALTER TABLE `delito`
  MODIFY `idDelito` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=223;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `antecedente_delito`
--
ALTER TABLE `antecedente_delito`
  ADD CONSTRAINT `antecedente_delito_ibfk_1` FOREIGN KEY (`idDelito`) REFERENCES `delito` (`idDelito`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `antecedente_delito_ibfk_2` FOREIGN KEY (`idAntecedente`) REFERENCES `antecedente` (`idAntecedente`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
