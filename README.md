# Electrical Vehicle Charging Station Management Web Service
As the growth of electrical vehicles there is a need for the X company to manage its charging
stations.so this web service can help us.
This project has been developed with Java langueage and Spring Framework.

## How to run the project
To run this Spring project you have to install Java version 11 and Maven version 3(build automation tool) and a relational dataBase(like MySql).
After installations and cloning the project there is a file named `application.properties` at */src/main/resources.

you have to add your database credentials in this file.
now you can run the project:

* You can run with an IDEA or command line.
go to projects directory and run commands below:
```
$ mvn clean install
```
Or(if tests failed)
```
$ mvn package -Dmaven.test.skip
```
then
```
$ ./mvnw spring-boot:run
```
After running the project all tables will be created automatically.

## Project Architecture
This projects architecture is based on spring mvc. so the packages are as follows:

- [ ] configuration
- [ ] controller
- [ ] model 
- [ ] repository
- [ ] service

## Database Tables 
There are two entities in this project, Company and Station.
* The Company fields: Id, name, parentCompany, subCompanies, stations
* The Station fields: Id, name, latitude, longitude, company.

first table is a self join table so in project we have two tables for Company table:
> &#x1F534; company

| id | name | parent_id |
| :- | :--: | --------: |
| 1  |   A  |    null   |
| 2  |   B  |   1       |
| 3  |   C  |   2       |
| 4  |   D  |   2       |

> &#x1F534; sub_company

| company_id | sub_id |
| :--------- | -----: |
|     1      |   2    |
|     2      |   3    |
|     2      |   4    |

company tables relation with sub_company table is one to many relation and parent column has many to one relation with company table.
second table is station table and it has many to one relation with company table:
> &#x1F534; station

| id | latitude | longitude | name | company_id |
| :- | :------: | :-------: | :--: | ---------: |
| 1  |51.360433 |35.713941  |Station-1|  1      |  


## EndPoints:
Each company can have multiple sub companies (as branches), e.g., Company A can
have sub companies of B,C and B can have sub company D. Also each company manages a
subset of charging stations. 
You test API's with postman collection of this project : https://www.getpostman.com/collections/07d4e525cc9bedd68022
We have two controller file `CompanyController` and `StationController` so we are going to test them:

## CompanyController:
> &#x1F535; AddCompany 
```
METHOD: POST  => /api/company/add
BODY: 
{
    "name":"B",
    "parentCompany":"A",
    "subCompany":["C","D"]
}
```
Result :
```
STATUS CODE: 201(created)
BODY: company added successfully  
```
--------------------------------------------------------------------------------------------------------------------------------
> &#x1F535; AddStation
```
METHOD: POST  => /api/station/add
BODY: 
{
    "name":"Station-1",
    "latitude":51.360433,
    "longitude":35.713941,
    "companyName":"A"
}
```
Result :
```
STATUS CODE: 201(created)
BODY: station added successfully  
```
--------------------------------------------------------------------------------------------------------------------------------
> &#x1F535; UpdateCompany
```
METHOD: PUT  => /api/company/update/{company_id}
BODY: 
{
    "name":"B",
    "parentCompany":"C",
    "subCompany":["Z","F"]
}
```
Result :
```
STATUS CODE: 200(ok)
BODY: operation updated successfully  
```
--------------------------------------------------------------------------------------------------------------------------------
> &#x1F535; UpdateStation
```
METHOD: PUT  => /api/station/update/{station_id}
BODY: 
{
    "name":"Station-1",
    "latitude":51.361737,
    "longitude":35.714060,
    "companyName":"B"
}
```
Result :
```
STATUS CODE: 200(ok)
BODY: operation updated successfully  
```
--------------------------------------------------------------------------------------------------------------------------------
> &#x1F535; DeleteCompany
```
METHOD: DELETE  => /api/company/delete/{company_id}
```
Result :
```
STATUS CODE: 200(ok)
BODY: operation deleted successfully  
```
--------------------------------------------------------------------------------------------------------------------------------
> &#x1F535; DeleteStation
```
METHOD: DELETE  => /api/station/delete/{station_id}
```
Result :
```
STATUS CODE: 200(ok)
BODY: operation deleted successfully  
```
--------------------------------------------------------------------------------------------------------------------------------
> &#x1F535; GetAllCompanyInfo
```
METHOD: GET  => /api/company/get_all_info
```
Result :
```
STATUS CODE: 200(ok)
BODY: [
    {
        "id": 1,
        "name": "A",
        "stations": [
            {
                "id": 1,
                "name": "Station-1",
                "latitude": 51.360433,
                "longitude": 35.713941
            }
        ],
        "subCompaniesInfo": [
            {
                "id": 2,
                "name": "B"
            },
            {
                "id": 3,
                "name": "C"
            }
        ]
    }
}
```
--------------------------------------------------------------------------------------------------------------------------------
And so many endPoints => you can test them with postman.

Ask me your questions : zohreh.s.m.a.n@gmail.com


