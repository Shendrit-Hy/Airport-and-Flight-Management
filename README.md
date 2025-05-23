# Airport-and-Flight-Management

This project is a full-stack web application designed to manage airports, flights, and ticket bookings. It allows users to browse available flights, purchase tickets, and for administrators to manage airport and flight data. One of the key features of this application is its multi-tenant architecture, which means that each airport or organization using the system has access only to its own isolated data.

The backend of the application is developed using Java and Spring Boot, Spring Data JPA for database access, and Spring Security for authentication and authorization. The application uses a PostgreSQL database to store all persistent data. Each record in the database is associated with a tenantId, and tenant isolation is enforced through custom middleware that intercepts incoming requests and sets the tenant context accordingly. This ensures that users only access the data relevant to their own organization or airport.

On the frontend, the application uses React to deliver a dynamic and responsive user interface. Users can view a list of upcoming flights, search for specific flights, and book tickets through a simple and intuitive UI. Admin users can log in to access management panels for adding, updating, or deleting flight and airport data. All interactions between the frontend and backend are handled through RESTful APIs, using Axios to make HTTP requests.

Authentication is implemented using  JWT allowing secure access control for users and administrators. The application includes secure login functionality and role-based access control to differentiate permissions more granularly.

The application is structured using a clean layered architecture, separating concerns between controllers, services, repositories, and data transfer objects (DTOs). This makes the codebase more maintainable and scalable as new features are added.

This project is ideal for airport administrators, travel agents, or service providers who want a scalable, multi-tenant platform to manage flights and ticketing efficiently.

# How to run
To run the project locally, make sure you have Java 17 or higher, Node.js with npm, PostgreSQL, and Maven installed on your machine.

Start by cloning the repository and setting up your PostgreSQL database. Update the database configuration (such as URL, username, and password) in the application.properties or application.yml file located in the backend project directory.

The backend is a Spring Boot application and can be launched directly from your IDE by running the AirportManagementApplication class, which contains the main method. This class acts as the entry point to the application and will start the embedded server (usually on http://localhost:8080).

Once the backend is running, navigate to the frontend directory in a new terminal window. Install the necessary dependencies by running npm install, and then start the development server using npm run dev. By default, the frontend will be available at http://localhost:5173 or another available port.

Make sure the frontend is properly configured to communicate with the backend. With both servers running, you can now open the application in your browser and start using its features—whether you are booking a flight, browsing schedules, or managing airport data as an admin.
