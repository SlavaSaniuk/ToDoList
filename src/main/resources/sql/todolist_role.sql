CREATE TABLE `todolist`.`roles` (
                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                    `role_name` VARCHAR(60) NOT NULL,
                                    `ref_role_owner` BIGINT NOT NULL,
                                    PRIMARY KEY (`id`),
                                    INDEX `role_owner_ref_idx` (`ref_role_owner` ASC) VISIBLE,
                                    CONSTRAINT `role_owner_ref`
                                        FOREIGN KEY (`ref_role_owner`)
                                            REFERENCES `todolist`.`users` (`id`)
                                            ON DELETE NO ACTION
                                            ON UPDATE NO ACTION);
