-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-05-2018 a las 05:20:27
-- Versión del servidor: 10.1.30-MariaDB
-- Versión de PHP: 5.6.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
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

CREATE TABLE `antecedente` (
  `idAntecedente` int(100) NOT NULL,
  `sentencias` int(100) DEFAULT NULL,
  `prision` int(100) DEFAULT NULL,
  `reinsidente` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `antecedente_delito`
--

CREATE TABLE `antecedente_delito` (
  `idAntecedente` int(100) DEFAULT NULL,
  `idDelito` int(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `delito`
--

CREATE TABLE `delito` (
  `idDelito` int(100) NOT NULL,
  `nombre` varchar(300) NOT NULL,
  `categoria` int(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `idUsuario` int(100) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `documento` varchar(50) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `genero` varchar(30) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `direccion` varchar(300) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `idAntecedente` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`idUsuario`, `nombres`, `apellidos`, `documento`, `telefono`, `correo`, `genero`, `estado`, `direccion`, `imagen`, `idAntecedente`) VALUES
(1, 'Mariana', 'Rojas2', '54321', '345028', 'mrojas@correo.com', 'Mujer', 1, 'av primera cll 45', NULL, -1),
(2, 'sdsd', 'sdsd', '121212', '444', 'wewe@gmail.com', 'Hombre', 0, 'ssdds', NULL, -1),
(3, 'Luis', 'Martinez', '123456', '3504234', 'lmartin@correo.com', 'Hombre', 0, 'av 45 #1', NULL, -1),
(4, 'Hector Felipe', 'Hurtado Acosta', '1012393974', '3118414998', 'hfhurtadoa@correo.udistrital.edu.co', 'Hombre', 1, 'cll 47 # 8', NULL, -1),
(5, 'sdsd', '1', '5', '3', '4', '5', 1, '6', 'C:\\xampp\\htdocs\\Clientes\\Rostros\\fotos\\5-3.jpg', -1),
(45, 'Kimberly', 'Jhoana', '45', '3', '4', '5', 1, '6', 'C:\\xampp\\htdocs\\Clientes\\Rostros\\fotos\\23231-0.jpg', -1),
(111, 'Edison', '1', '111', '3', '4', '5', 1, '6', 'C:\\xampp\\htdocs\\Clientes\\Rostros\\fotos\\111-0.jpg', -1);

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
  MODIFY `idAntecedente` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `delito`
--
ALTER TABLE `delito`
  MODIFY `idDelito` int(100) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `idUsuario` int(100) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1012393975;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `antecedente_delito`
--
ALTER TABLE `antecedente_delito`
  ADD CONSTRAINT `antecedente_delito_ibfk_1` FOREIGN KEY (`idDelito`) REFERENCES `delito` (`idDelito`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `antecedente_delito_ibfk_2` FOREIGN KEY (`idAntecedente`) REFERENCES `antecedente` (`idAntecedente`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
