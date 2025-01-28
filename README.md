Currency Exchange Service
This project provides a Spring Boot-based currency exchange service that calculates payable amounts in different currencies based on live exchange rates and user-specific discounts.

Features
Currency Exchange Integration: Integration with a third-party currency exchange API for real-time rates.
Discount Calculation: Discounts based on user role and customer tenure.
Secure API: Basic Authentication for API security.
Test Coverage: Unit and integration tests with coverage reports.
1. Project Setup
Clone the Repository
Start by cloning the project repository:
git clone https://github.com/your-username/currencyExchange.git

At start we have to do git init for initializing the git and then do initial commit and push changes.
Then create feature branch from main branch and worked on it.

Configure Application Properties
Set up your properties in src/main/resources/application.properties for exchange rate API URL and API keys.

exchange.rate.api.url=https://api.exchangerate-api.com/v4/latest/{base_currency}?apikey={api_key}
api.key=your-api-key-here
2. Running the Application
To run the application, use the following command:
mvn spring-boot:run
Alternatively, you can run the CurrencyExchangeApplication class from your IDE.

3. API Endpoints
The application exposes the following RESTful API endpoints:

POST /api/calculate
Description: Calculates the payable amount for a given bill in a target currency after applying discounts.

Request Body:
{
    "totalAmount": 150.0,
    "userType": "EMPLOYEE",
    "customerTenure": 3,
    "originalCurrency": "USD",
    "targetCurrency": "EUR",
    "items": [
        {
            "name": "Laptop",
            "category": "Electronics",
            "price": 100.0
        },
        {
            "name": "Groceries",
            "category": "Groceries",
            "price": 50.0
        }
    ]
}

Response:
{
    "originalAmount": 150.0,
    "discount": 50.0,
    "currency": "EUR",
    "payableAmount": 95.4693
}

4. Running Tests
Unit Tests - Wriiten Junit test coverage with moxkito for service layer.

Test Coverage
To generate code coverage reports, follow these steps:

Add the JaCoCo Maven Plugin to the pom.xml if not already added:

xml
Copy
Edit
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>2.22.0</version>
  <configuration>
    <useSystemClassloader>false</useSystemClassloader>
    <includes>
      <include>**/*Test.java</include>
    </includes>
  </configuration>
</plugin>
Use the following command to run tests and generate coverage reports:

bash
Copy
Edit
mvn clean test
The reports will be generated in the target/site/jacoco/index.html directory. Open this file in a browser to view the coverage summary.

5. Additional Information
5.1. API Integration
The application integrates with the ExchangeRate-API (or similar service) to fetch the real-time currency exchange rates. The API URL and API key are configured in application.properties.

API URL Format: https://api.exchangerate-api.com/v4/latest/{base_currency}?apikey={api_key}
Exchange Rate Retrieval: The CurrencyExchangeService retrieves exchange rates based on the fromCurrency and toCurrency passed in the request.
5.2. Authentication
The application uses Basic Authentication to secure the API endpoints. You can use the default username user and password password, or change them by modifying the SecurityConfig class.

5.3. Running the Application in Different Environments
Make sure you configure different profiles (e.g., dev, prod) if needed and include any environment-specific properties such as API keys, URLs, or credentials.

6. Troubleshooting
401 Unauthorized: If you get a 401 error, check if the correct username and password are being provided.
API Key Issues: Ensure your API key for the exchange rate API is valid and properly configured in the application.properties.
