# UPI Payment Integration In Android Studio

Creating a simple application project in which there will be displayed multiple edit text fields in which we will be allowing users to enter the details for making a payment. After clicking on make payment button we will display a dialog box to our users for making payment through different apps which are installed in users device.
here we are using EasyUPI library for making UPI Payment.

### Introduction
- This library acts as a mediator between your app and UPI apps (which are already installed in device).
- This library is based on these specifications provided by NPCI (National Payments Corporation of India).
- This library is based on Android Intent based operations.
- Whenever payment is initiated, it simply sends the Intent to the UPI apps installed on device.
- When transaction is done, response is returned from library.
- Payment will be only processed when payee is having a **valid merchant account of UPI. Otherwise, payment won’t be successful. This won’t work for general UPI payee user**.

### Step by Step Implementation

1. Create a New Project in Android Studio.
2. Adding a dependency for easy payment gateway in android.

   ```
   implementation("dev.shreyaspatil.EasyUpiPayment:EasyUpiPayment:3.0.3")
   ```
   
3. Adding internet permissions in AndroidManifest.xml. Navigate to app > AndroidManifest.xml and add the below code to it.

   ```
   <uses-permission android:name="android.permission.INTERNET" />
   ```

4. Working with the activity_main.xml file. Navigate to the app > res > layout > activity_main.xml and add the below code to that file.
5. Working with the MainActivity.java file.

   
