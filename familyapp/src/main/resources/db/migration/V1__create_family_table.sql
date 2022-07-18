CREATE TABLE family (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    family_name VARCHAR(20) NOT NULL,
    nr_of_infants INT NOT NULL,
    nr_of_children INT NOT NULL,
    nr_of_adults INT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;