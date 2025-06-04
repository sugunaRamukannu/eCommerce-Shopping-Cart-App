# eCommerce-Shopping-Cart-App

To enable the SMS OTP feature:
Sign up for an SMS service provider (we integrated Twilio) and generate:
        1.Account SID
        2.Auth Token
        3.Registered Phone Number
Add these credentials to your application.properties or environment variables:
**twilio.account.sid=your_account_sid
twilio.auth.token=your_auth_token
twilio.phone.number=your_twilio_number**

Note : Before running the application, make sure to load the data from the provided CSV files into the respective MySQL tables:

product.csv → Load data into the product table

product_category.csv → Load data into the product_category table
