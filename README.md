# Database-Doodle

## Overview

Database-Doodle is a Java application utilizing Hibernate, and Spring Boot. It provides an intuitive interface for seamlessly connecting and querying databases across various scenarios. This includes joining tables within a single database in a single DBMS system, joining tables on the same DBMS software but different databases on the same device, joining tables across different devices hosting the same type of DBMS (e.g., two devices hosting Oracle databases), and even joining tables across different types of databases (e.g., Oracle and MySQL).

### Example Scenario

For instance, imagine a setup with 10 devices, each hosting different types of databases—Oracle, MySQL, MongoDB, and even CSV files. Database-Doodle can seamlessly join, group, sort, retrieve, filter, and perform other operations across all these datasets. It can export data in various formats like CSV, Oracle, MySQL, or any other supported format and also serve as input for visualization software tools like PowerBI.

## Disclaimers
- The project is currently in the planning phase.
- You can check the progress of the project in the timeline section.

## Features

1. *Database Connectivity*
    - Easily manage connections to diverse databases.
    - Support for databases hosted on separate devices on a network.
    - SQL, NoSQL, and CSV file support.

2. *Query Building*
    - Intuitive drag-and-drop interface for constructing complex queries.
    - Includes clauses like 'sort' and 'group by' for comprehensive query construction.

3. *Result Visualization*
    - Efficient display of query results in a tabular format.
    - Integration capabilities with other systems, such as visualization tools.

## Initial Design Draft

An initial design draft for Database-Doodle is available for review. Please refer to the [Design Draft PDF](Database%20Doodle%20Design%20Draft.pdf) for details.

## Technologies Used

- Java
- Spring Boot
- Hibernate

## Directory Structure

The project is organized into the following structure:

```
Database Doodle/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── Main.java
│   │   │           ├── database/
│   │   │           │   ├── ConnectionManager.java
│   │   │           │   ├── DatabaseReader.java
│   │   │           │   ├── JoinOperations.java
│   │   │           │   ├── joins/
│   │   │           │   │   ├── outer/
│   │   │           │   │   │   ├── OuterJoin.java
│   │   │           │   │   │   ├── LeftOuterJoin.java
│   │   │           │   │   │   ├── RightOuterJoin.java
│   │   │           │   │   │   └── FullOuterJoin.java
│   │   │           │   │   ├── other/
│   │   │           │   │   │   ├── InnerJoin.java
│   │   │           │   │   │   ├── CrossJoin.java
│   │   │           │   │   │   ├── SelfJoin.java
│   │   │           │   │   │   ├── NaturalJoin.java
│   │   │           │   │   │   ├── EquiJoin.java
│   │   │           │   │   |   └── NonEquiJoin.java
│   │   │           │   ├── GroupOperations.java
│   │   │           │   ├── grouping/
│   │   │           │   │   ├── GroupBy.java
│   │   │           │   │   └── Having.java
│   │   │           │   ├── SortOperations.java
│   │   │           │   ├── sorting/
│   │   │           │   │   ├── OrderBy.java
│   │   │           │   │   └── Sort.java
│   │   │           │   ├── aggregation/
│   │   │           │   │   ├── AggregationOperations.java
│   │   │           │   │   ├── Sum.java
│   │   │           │   │   ├── Avg.java
│   │   │           │   │   ├── Count.java
│   │   │           │   │   ├── Max.java
│   │   │           │   │   └── Min.java
│   │   │           │   ├── output/
│   │   │           │   │   ├── OutputHandler.java
│   │   │           │   │   ├── data_export/
│   │   │           │   │   │   ├── CSVExporter.java
│   │   │           │   │   │   ├── ExcelExporter.java
│   │   │           │   │   │   ├── OracleExporter.java
│   │   │           │   │   │   ├── MySQLExporter.java
│   │   │           │   │   ├── software_extension/
│   │   │           │   │   │   ├── PowerBIExporter.java
│   │   │           │   │   │   └── TableauExporter.java
│   │   │           │   ├── saved_connections/
│   │   │           │   │   ├── ConnectionSaver.java
│   │   │           │   │   ├── ConnectionLoader.java
│   │   │           │   │   ├── EncryptionUtil.java
│   │   │           │   │   └── DecryptionUtil.java
│   │   ├── repository/
│   │   │   └── DatabaseRepository.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   ├── templates/
│   │   │   ├── connection.html
│   │   │   ├── canvas.html
│   │   │   ├── output.html
│   │   │   └── export.html
│   │   ├── web/
│   │   │   ├── controller/
│   │   │   │   ├── ConnectionController.java
│   │   │   │   ├── CanvasController.java
│   │   │   │   ├── OutputController.java
│   │   │   │   ├── ExportController.java
│   │   │   ├── service/
│   │   │   │   ├── ConnectionService.java
│   │   │   │   ├── QueryService.java
│   │   │   │   ├── OutputService.java
│   │   │   │   ├── NLPService.java
│   │   │   │   ├── ExportService.java
│   │   │   ├── model/
│   │   │   │   ├── DatabaseConnection.java
│   │   │   │   ├── Query.java
│   │   │   │   ├── Table.java
│   │   │   │   └── PlainTextQuery.java
│   └── test/
│       ├── java/
│       │   └── org/
│       │       └── example/
│       └── resources/
└── pom.xml
```

## Timeline

Database-Doodle will be developed in multiple versions using a spiral model of software development:

### Version 1: CLI Database Connection

- [x] **Connect to a database via Command Line Interface (CLI):**
    - [x] Implement database connection configuration.
    - [x] Validate and establish a connection to the specified database.
    - [x] Handle authentication and security protocols if applicable.
    - [x] Ensure error handling for connection failures.
    - [x] `Main.java`
    - [x] `database/ConnectionManager.java`

- [x] **Print all data from the connected database:**
    - [x] Retrieve metadata to list available tables.
    - [x] Iterate through each table and fetch all rows.
    - [x] Format and display data in the CLI interface.
    - [x] `database/DatabaseReader.java`

### Version 2: Complex Queries via CLI

- [ ] **Version 2.1: Joining**
    - [ ] Implement join operations (e.g., inner join, outer join).
    - [ ] Validate and execute join queries.
    - [ ] `database/JoinOperations.java`
    - [ ] `database/joins/InnerJoin.java`
    - [ ] `database/joins/OuterJoin.java`
    - [ ] `database/joins/LeftOuterJoin.java`
    - [ ] `database/joins/RightOuterJoin.java`
    - [ ] `database/joins/FullOuterJoin.java`
    - [ ] `database/joins/CrossJoin.java`
    - [ ] `database/joins/SelfJoin.java`
    - [ ] `database/joins/NaturalJoin.java`
    - [ ] `database/joins/EquiJoin.java`
    - [ ] `database/joins/NonEquiJoin.java`

- [ ] **Version 2.2: Grouping**
    - [ ] Implement grouping operations (e.g., group by, having).
    - [ ] Validate and execute grouping queries.
    - [ ] `database/GroupOperations.java`
    - [ ] `database/grouping/GroupBy.java`
    - [ ] `database/grouping/Having.java`

- [x] **Version 2.3: Sorting**
    - [x] Implement sorting operations (e.g., order by).
    - [x] Validate and execute sorting queries.
    - [x] `database/SortOperations.java`
    - [x] `database/sorting/OrderBy.java`
    - [x] `database/sorting/Sort.java`

- [x] **Version 2.4: Results**
    - [x] Handle query results and output in various formats.
    - [x] `database/output/OutputHandler.java`
    - [x] `database/output/CSVExporter.java`
    - [x] `database/output/ExcelExporter.java`
    - [x] `database/output/OracleExporter.java`
    - [x] `database/output/MySQLExporter.java`

### Version 3: Cross-Device Cross-Database Compatibility via CLI

- [x] **Enhance CLI for cross-device/database compatibility:**
    - [x] Extend CLI capabilities to accommodate connections to multiple databases (e.g., Oracle, MySQL).
    - [x] Implement backend logic using Hibernate to manage connections and optimize query performance across databases.
    - [x] Develop features to handle cross-database joins and ensure compatibility of data types and query execution.
    - [x] `database/ConnectionManager.java` (handle multiple databases)
    - [x] `database/DatabaseReader.java` (iterate over multiple databases)

### Version 4: GUI for Database Operations

- [ ] **Replace CLI with a graphical user interface (GUI):**
    - [ ] Design the GUI layout for database connection setup.
    - [ ] Implement input fields for database credentials (ID, password, connection info, port number, etc.).
    - [ ] Develop backend logic to handle GUI events (e.g., button clicks, form submissions).
    - [ ] `web/`
        - [ ] `controller/ConnectionController.java`
        - [ ] `controller/CanvasController.java`
        - [ ] `controller/OutputController.java`
        - [ ] `service/ConnectionService.java`
        - [ ] `service/QueryService.java`
        - [ ] `service/OutputService.java`
        - [ ] `model/DatabaseConnection.java`
        - [ ] `model/Query.java`
        - [ ] `model/Table.java`
        - [ ] `repository/DatabaseRepository.java`
        - [ ] `templates/`
            - [ ] `connection.html`
            - [ ] `canvas.html`
            - [ ] `output.html`
        - [ ] `resources/`
            - [ ] `application.properties`
            - [ ] `schema.sql`
            - [ ] `data.sql`
        - [ ] `test/`
            - [ ] `java/`
            - [ ] `resources/`
        - [ ] `build.gradle`

### Version 5: GUI Aesthetics Refinement

- [ ] **Refine the GUI aesthetics:**
    - [ ] Introduce animations to enhance user interaction.
    - [ ] Identify key user interactions (e.g., button clicks, table movements) for animation triggers.
    - [ ] Implement CSS or JavaScript animations to provide visual feedback and improve usability.
    - [ ] Conduct user interface design

reviews to identify areas for improvement.
- [ ] Update GUI layout, colors, fonts, and icons to align with modern design standards.

### Version 6: Generate Canvas from Plain Text Queries

- [ ] **Implement on-device LLM and NLP to generate canvas from plain text queries:**
    - [ ] Integrate a lightweight language model for natural language processing.
    - [ ] Develop backend logic to convert plain text queries into visual representations on the canvas.
    - [ ] Enhance the drag-and-drop interface to accommodate generated visual elements.
    - [ ] Ensure accurate and efficient parsing of user input to generate relevant visual components.
    - [ ] `web/controller/CanvasController.java`
    - [ ] `web/service/QueryService.java`
    - [ ] `web/service/NLPService.java`
    - [ ] `web/model/PlainTextQuery.java`
    - [ ] `web/templates/canvas.html`

### Version 7: Output to PowerBI and Tableau

- [ ] **Integrate output capabilities to PowerBI and Tableau:**
    - [ ] Research and develop methods to export data directly to PowerBI and Tableau.
    - [ ] Ensure compatibility with the data formats required by these tools.
    - [ ] Implement backend logic to handle data transformation and export.
    - [ ] Add GUI elements for users to select export options to PowerBI and Tableau.
    - [ ] **Note:** This feature is exploratory and subject to change based on feasibility and user feedback.
    - [ ] `web/controller/ExportController.java`
    - [ ] `web/service/ExportService.java`
    - [ ] `database/output/software_extension/PowerBIExporter.java`
    - [ ] `database/output/software_extension/TableauExporter.java`
    - [ ] `web/templates/export.html`

## Contributing

We welcome contributions from the community. Please follow these guidelines:

1. Create an issue before starting work to avoid duplication.
2. Fork the repository and create a new branch for your feature or bug fix.
3. Submit a pull request to the appropriate branch (e.g., Spring-Boot-PR, Hibernate-PR, etc.).
4. Include relevant tests and ensure all tests pass before submitting the PR.

For more details, refer to the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
