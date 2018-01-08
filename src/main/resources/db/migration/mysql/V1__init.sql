CREATE TABLE `competicao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `visitante` varchar(100) NOT NULL,
  `adversario` varchar(100) NOT NULL,
  `local` varchar(100) NOT NULL,
  `modalidade` varchar(100) NOT NULL,
  `etapa` varchar(100) NOT NULL,
  `data_inicial` datetime NOT NULL,
  `data_final` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	