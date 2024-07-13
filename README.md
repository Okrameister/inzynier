
# Social Media Application

A social media application built with Spring Boot involves a robust backend architecture that leverages the power of the Spring framework to manage user interactions, content creation, and social networking functionalities. Below is a detailed description of the components and their functionalities.

## Project Structure
- **Controllers**: Handle HTTP requests and define the REST API endpoints.
- **Services**: Contain the business logic and interact with repositories.
- **Repositories**: Interface with the database using Spring Data JPA.
- **Models**: Define the entities (User, Post, Comment, Like, etc.).

## Core Features
### User Management
- **Registration**: Users can sign up by providing necessary details.
- **Login**: Users can log in using their credentials.
- **Profile Management**: Users can update their profile information.

### Post Management
- **Create Post**: Users can create new posts with text, images, or videos.
- **Read Post**: Users can view posts in their feed.
- **Update Post**: Users can edit their posts.
- **Delete Post**: Users can delete their posts.

### Comments and Likes
- **Comment**: Users can comment on posts.
- **Like**: Users can like posts.

### Follow System
- **Follow/Unfollow**: Users can follow or unfollow other users.
- **Followers/Following List**: View lists of followers and following.

## Security
- **JWT Authentication**: Secure the API with JSON Web Tokens for authentication.
- **Authorization**: Role-based access control for different endpoints.

## Error Handling
- **JWT Decoding Exception**: Ensure the JWT token is correctly encoded and doesn't contain invalid characters.
- **HTTP Request Method Not Supported**: Check the API documentation and use the correct HTTP methods for each endpoint.
- **Duplicate Entry in Database**: Ensure unique constraints are respected when inserting data.

## Database Design
### Tables
- **User**: Stores user details.
- **Post**: Stores posts created by users.
- **Comment**: Stores comments on posts.
- **Like**: Stores likes on posts.
- **Follow**: Stores follow relationships between users.
