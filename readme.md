# BillingApp 💸

![BillingApp](https://img.shields.io/badge/BillingApp-v1.0-blue)  
BillingApp is a web application for managing billing and orders, tailored for small businesses or e-commerce. Built with Spring Boot and React, it features role-based access: admins manage categories, items, and users, while regular users can explore items, create orders, view their dashboard, and process payments via Razorpay. Protected endpoints ensure secure access to administrative functions.

## ✨ Features

- **Role-Based Access** 🔒: Admins manage categories, items, and users; users explore, order, and pay.
- **Category & Item Management** 📋: Admins can add or delete categories and items.
- **Order Management** 🛒: Users can create orders and view details.
- **Payment Integration** 💳: Process payments securely with Razorpay.
- **Dashboard** 📊: Users can view order summaries and stats.
- **Responsive Interface** 🎨: Seamless experience across devices with React.

## 🛠️ Tech Stack

- **Frontend**: React ⚛️, BootStrap CSS 🎨
- **Backend**: Spring Boot 🌱, Java ☕, Lombok 🛠️
- **Database**: MySQL 🗄️
- **Payment Gateway**: Razorpay 💳
- **Other Tools**: Maven 📦, Git 🗃️, Postman 📬

## 📋 Entities

**Note**: Categories and Items are managed by admins and not directly accessible to users via API.

- **Category** 📋
  - `id`: Unique identifier (Long)
  - `name`: Category name (String)
  - Relationships: One-to-Many with `Items`

- **Item** 📦
  - `id`: Unique identifier (Long)
  - `name`: Item name (String)
  - `price`: Item price (Double)
  - `categoryId`: Reference to Category (Long)
  - Relationships: Many-to-One with `Category`

- **Order** 🛒
  - `id`: Unique identifier (Long)
  - `userId`: Reference to User (Long)
  - `total`: Total amount (Double)
  - `createdAt`: Order creation date (LocalDateTime)
  - `status`: Order status (String/Enum)
  - Relationships: One-to-Many with `OrderItem` (managed relation)

- **OrderItem** 📋
  - `id`: Unique identifier (Long)
  - `orderId`: Reference to Order (Long)
  - `itemId`: Reference to Item (Long)
  - `quantity`: Item quantity (Integer)
  - Relationships: Many-to-One with `Order`

- **User** 👤
  - `id`: Unique identifier (Long)
  - `username`: Username (String)
  - `password`: Password (String, hashed)
  - `email`: Email address (String)
  - `role`: User role (String/Enum, e.g., ADMIN, USER)

## 🌐 API Endpoints

Base URL: `/api/v1.0`

| **Method** | **Endpoint**                     | **Description**                        | **Access**            |
|------------|----------------------------------|----------------------------------------|-----------------------|
| POST       | `/login`                        | Authenticate a user                    | All users             |
| GET        | `/admin/categories`             | Retrieve all categories                | Admin only            |
| GET        | `/admin/categories/{category_id}` | Get category by ID                   | Admin only            |
| DELETE     | `/admin/categories/{category_id}` | Delete category by ID                | Admin only            |
| GET        | `/admin/items`                  | Retrieve all items                     | Admin only            |
| DELETE     | `/admin/items/{itemId}`         | Delete item by ID                      | Admin only            |
| POST       | `/orders`                       | Create a new order                     | Authenticated users   |
| GET        | `/orders/{orderId}`             | Get order by ID                        | Authenticated users   |
| GET        | `/orders/latest`                | Get the latest order                   | Authenticated users   |
| POST       | `/payments/create-order`        | Create a Razorpay payment order        | Authenticated users   |
| POST       | `/payments/verify`              | Verify a Razorpay payment              | Authenticated users   |
| POST       | `/admin/register`               | Register a new user                    | Admin only            |
| DELETE     | `/admin/users/{id}`             | Delete a user by ID                    | Admin only            |

## 🎮 Usage

### For Admins
- **Manage Categories** 📋: Add or delete categories via the admin panel (uses `/admin/categories` endpoints).
- **Manage Items** 📦: Add or delete items under categories (uses `/admin/items` endpoints).
- **Create Users** 👤: Register new users (uses `/admin/register`).
- **Delete Users** 🚫: Remove users as needed (uses `/admin/users/{id}`).

### For Users
- **Login** 🔑: Authenticate using `/login` to access the application.
- **Explore Page** 🔍: View available items and categories (fetched indirectly via the frontend; direct API access to `/admin/categories` and `/admin/items` is restricted).
- **Create Order** 🛒: Place an order with selected items (uses `/orders`).
- **Process Payment** 💳: Pay for the order via Razorpay (uses `/payments/create-order` and `/payments/verify`).
- **View Dashboard** 📊: See order summaries and stats on the dashboard (frontend feature).
- **View Order Details** 📜: Check order history and details (uses `/orders/{orderId}` and `/orders/latest`).

## 🚀 Installation

Follow these steps to set up the project locally:

1. **Prerequisites**:
   - Java 17+ ☕
   - Node.js 18+ 📦
   - MySQL 8.0+ 🗄️
   - Maven 📦

2. **Clone the Repository**:
   ```bash
   git clone https://github.com/GURUPRASATH-OG/BillingApp.git
   cd BillingApp
   ```

3. **Set Up the Backend**:
   - Navigate to the backend directory (e.g., `backend`):
     ```bash
     cd BillinApplication
     ```
   - Configure MySQL and Razorpay:
     - Create a database named `billingapp`.
     - Update `src/main/resources/application.properties`:
       ```properties
       spring.datasource.url=jdbc:mysql://localhost:3306/billingapp
       spring.datasource.username=your_username
       spring.datasource.password=your_password
       spring.jpa.hibernate.ddl-auto=update
       razorpay.api.key=your_razorpay_key
       razorpay.api.secret=your_razorpay_secret
       ```
   - Build and run the Spring Boot application:
     ```bash
     mvn clean install
     mvn spring-boot:run
     ```

4. **Set Up the Frontend**:
   - Navigate to the frontend directory (e.g., `frontend`):
     ```bash
     cd BillingSoftware
     ```
   - Install dependencies and start the React app:
     ```bash
     npm install
     npm start
     ```

5. **Access the Application**:
   - Backend API: `http://localhost:8080/api/v1.0`
   - Frontend: `http://localhost:5173`



## 🏗️ Project Structure

- **Backend** (Spring Boot):
  - `entity`: JPA entities (e.g., Category, Item, Order, OrderItem, User)
  - `dto`: Data Transfer Objects for API requests/responses
  - `mapper`: Mapping between entities and DTOs
  - `repository`: Spring Data JPA repositories
  - `service`: Business logic and service layers
  - `controller`: REST API controllers
  - `util`: for Jwt
  - `filter`: for custom Jwt Filter.
  - `config`: for spring security,static resource.
  - Uses Lombok to reduce boilerplate code

- **Frontend** (React):
  - Component-based structure with BootStrap CSS for styling
  - API integration with backend via Axios or Fetch

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the repository 🍴.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes and commit (`git commit -m "Add your feature"`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a pull request 📥.

Please ensure your code adheres to the project’s coding standards and includes tests.

## 📜 License

This project is licensed under the [MIT License](LICENSE).

## 📬 Contact

For questions or feedback, reach out to [GURUPRASATH-OG](https://github.com/GURUPRASATH-OG) or open an issue on this repository.

---

Happy billing! 🎉
