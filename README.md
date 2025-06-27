# EasyShop

## Description of the Project

EasyShop is a Java Spring Boot e-commerce backend application designed to manage product categories, products, user shopping carts, user profiles, and order checkout functionality. 

This application provides RESTful APIs for managing an online store's inventory and user shopping experience.

Intended users are developers building or testing e-commerce websites, administrators managing product inventory, and end-users who shop online.

The main purpose of EasyShop is to demonstrate a secure, role-based backend system that supports product browsing, cart management, and order processing, while enforcing user roles for administrative actions./Users/oumoudiallo/Desktop/Screenshot 2025-06-27 at 8.06.14 AM.png

The application aims to solve common challenges in online retail by providing a clear separation of concerns, proper authentication/authorization, and persistence of user shopping cart data


## User Stories

List the user stories that guided the development of your application. Format these stories as: "As a [type of user], I want [some goal] so that [some reason]."

- As a user, I want to search for products by name or keyword so that I can quickly find specific items.
- As a user, I want to view products grouped by category so that I can browse items that interest me.
- As a user, I want to filter products by attributes like price or brand so that I can narrow down my options.

## Setup

### Prerequisites

- IntelliJ IDEA: Download and install from https://www.jetbrains.com/idea/download/. 
- Java SDK: Ensure Java SDK (version 17 or compatible) is installed and configured in IntelliJ. 
- MySQL Database: A running MySQL server with the EasyShop schema created and configured.
- 
### Running the Application in IntelliJ

Follow these steps to get your application running within IntelliJ IDEA:

1. Clone the GitHub repository containing the EasyShop project. 
2. Open IntelliJ IDEA and select Open, then navigate to the project folder. 
3. Allow IntelliJ to index the project and download dependencies. 
4. Configure the database connection in application.properties or application.yml as per your local MySQL settings. 
5. Locate the main Spring Boot class EasyshopApplication.java. 
6. Right-click and select Run 'EasyshopApplication.main()'. 
7. Use Postman or any REST client to test API endpoints.
8. 
## Technologies Used

- Java 17 
- Spring Boot 2.7.x 
- Spring Security with JWT for authentication and authorization 
- MySQL database 
- Spring Data JDBC / JDBC Template for database access 
- Postman for API testing

## Demo

Include screenshots or GIFs that show your application in action. Use tools like [Giphy Capture](https://giphy.com/apps/giphycapture) to record a GIF of your application.

![](/Users/oumoudiallo/Desktop/Screenshot 2025-06-27 at 8.06.14 AM.png)

![Application Screenshot](path/to/your/screenshot.png)
![](![img.png](img.png))

## Most Difficult Code to Work With
The most challenging part of EasyShop was implementing the Shopping Cart feature with JWT-secured user sessions. 

Handling the persistence of cart items per user, ensuring correct increment/decrement of product quantities, and managing concurrency was tricky.

Integrating security to restrict cart operations to authenticated users while providing clear REST endpoints required extensive debugging and testing.

Another complex area was fixing product duplication bugs during product updates — ensuring updates modify existing records rather than inserting duplicates.

## Future Work

Outline potential future enhancements or functionalities you might consider adding:

- Complete Phase 4 user profile management, allowing users to update their profile information. 
- Implement Phase 5 checkout process fully with order creation and payment integration. 
- Add pagination and sorting capabilities for product listings. 
- Develop a front-end client (React/Angular) to consume the API. 
- Improve security with refresh tokens and more granular role-based access control. 
- Add product reviews and ratings features.

## Resources

List resources such as tutorials, articles, or documentation that helped you during the project.

- Potato Sensei
- https://github.com/RayMaroun/yearup-spring-section-10-2025/tree/master/pluralsight
- A council of my peers, they were really amazing moral support.

## Team Members

- **Oumou Diallo** -  Backend development, security, and shopping cart implementation.


## Thanks

Express gratitude towards those who provided help, guidance, or resources:

- Thank you to Raymond Maroun for continuous guidance and support throughout the project. 
- A special thanks to my peers for insightful discussions and feedback.
 