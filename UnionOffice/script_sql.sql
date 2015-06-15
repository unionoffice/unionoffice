create database if not exists union_office_db;
use union_office_db;
create table if not exists `nota_fiscal` (
	numero INTEGER AUTO_INCREMENT PRIMARY KEY,
    serie TINYINT NOT NULL,
    chave VARCHAR(50) NOT NULL,
    data_envio DATETIME NOT NULL
    email_nf VARCHAR(255) NOT NULL);
create table if not exists `pedido` (
	numero VARCHAR(11) PRIMARY KEY,
    cliente VARCHAR(255) NOT NULL,
    contato VARCHAR(255) NOT NULL,
    numero_nf INTEGER DEFAULT NULL,
    email VARCHAR(255) NOT NULL,
    data_envio_receb DATETIME NOT NULL,
    data_envio_satisf DATETIME DEFAULT NULL,
    CONSTRAINT fk_nota FOREIGN KEY(numero_nf) REFERENCES nota_fiscal(numero) ON DELETE CASCADE);
create view if not exists view_pedidos AS
SELECT 	p.numero numero_pedido, 
		p.cliente, p.email, 
		p.data_envio_receb, 
        p.contato,
        p.data_envio_satisf,        
        nf.numero numero_nf,
        nf.serie, 
        nf.chave,
        nf.data_envio,
        nf.email_nf
FROM pedido  p 
LEFT JOIN nota_fiscal nf ON p.numero_nf = nf.numero;
   
    