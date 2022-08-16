CREATE TABLE tasks (id INT PRIMARY KEY AUTO_INCREMENT NOT NULL, name VARCHAR(255) NOT NULL, description VARCHAR(255), created DATE NOT NULL, completion DATE);

CREATE TABLE `todolist`.`user` (
                                   `id` INT NOT NULL AUTO_INCREMENT,
                                   `name` VARCHAR(255) NOT NULL,
                                   PRIMARY KEY (`id`))
    COMMENT = '			';

ALTER TABLE `todolist`.`tasks`
    ADD COLUMN `fkOwner` INT NOT NULL AFTER `completion`,
    ADD INDEX `fkOwner_idx` (`fkOwner` ASC) VISIBLE;
;
ALTER TABLE `todolist`.`tasks`
    ADD CONSTRAINT `fkOwner`
        FOREIGN KEY (`fkOwner`)
            REFERENCES `todolist`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `todolist`.`account` (
                                      `id` INT NOT NULL AUTO_INCREMENT,
                                      `email` VARCHAR(255) NOT NULL,
                                      `password` VARCHAR(255) NOT NULL,
                                      `fkUserOwner` INT NOT NULL,
                                      PRIMARY KEY (`id`),
                                      UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
                                      INDEX `fkUserOwner_idx` (`fkUserOwner` ASC) VISIBLE,
                                      CONSTRAINT `fkUserOwner`
                                          FOREIGN KEY (`fkUserOwner`)
                                              REFERENCES `todolist`.`user` (`id`)
                                              ON DELETE NO ACTION
                                              ON UPDATE NO ACTION)
    COMMENT = 'Users accounts/';

ALTER TABLE `todolist`.`user`
    ADD COLUMN `fkAccount` INT NOT NULL AFTER `name`,
    ADD INDEX `fkAccount_idx` (`fkAccount` ASC) VISIBLE;
;
ALTER TABLE `todolist`.`user`
    ADD CONSTRAINT `fkAccount`
        FOREIGN KEY (`fkAccount`)
            REFERENCES `todolist`.`account` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE;
