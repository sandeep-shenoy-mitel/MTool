create database if not exists mTool;

use mTool;
DROP TABLE IF EXISTS `releaseList`;
CREATE TABLE `releaseList` (`oid_index` int unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
`releaseName`                             varchar(100) NOT NULL UNIQUE,
`productName`                             varchar(100) NOT NULL,
`status`                                  enum('STARTED','NOT_STARTED','COMPLETED') NOT NULL default 'NOT_STARTED',
`owner`                                   varchar(100) default '',
`startDate`                               timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
`endDate`                                  timestamp  NULL,
`mnodeAvailable`                            enum('YES','NO') NOT NULL default 'NO',
`comments`                                 varchar(500) NOT NULL
)ENGINE=InnoDB;
