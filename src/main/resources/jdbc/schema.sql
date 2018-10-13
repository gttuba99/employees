DROP TABLE IF EXISTS employees;
CREATE TABLE employees (
  id INTEGER IDENTITY PRIMARY KEY,
  firstName VARCHAR(45),
  middleInitial varchar(3),
  lastName varchar(45),
  dateOfBirth date,
  dateOfEmployment date,
  status INTEGER
);
