# Moto-announcements Microservices

Moto-announcements is my side project created for practicing microservices architecture, combining it with Django and incorporating some machine learning. The idea was to develop a website where users can create announcements for their cars or motorbikes. I trained a convolutional neural network model with TensorFlow to recognize if the picture contains an object of the proper category, ensuring that car announcements could only include car pictures. Additionally, everything was implemented using microservices architecture, just for fun.

## Tech Stack
**Server**: Spring Boot, Django, Django Rest Framework, Spring Cloud, Spring Cloud Gateway, Spring Security, Spring JPA

**Client**: React, Redux Toolkit, React-Router, React-Bootstrap

**Other**: TensorFlow, PostgreSQL, Kafka, Redis, Amazon AWS S3, Websockets, Mailhog

## Functionalities
* Users can register an account
* Users can log in 
* Users can list all announcements
* Users can filter announcements 
* Users can create new announcements
* Users can edit their profiles
* Users can register for an email newsletter
* Users can view details of each announcement
* Users can comment on each announcement
* Users can send messages to authors of announcements (asynchronous chat)

## Architecture
Here is the architecture diagram:
![](https://i.imgur.com/1kXynwN.png)

## Screenshots
Here are a couple of screenshots of the user interface:

* Users can list items to buy on the main page
![](https://i.imgur.com/cNDH64i.png)

* Users can filter announcements with filters
![](https://i.imgur.com/TFJLT6X.png)

* Users can view specific announcements 
![](https://i.imgur.com/UsCGWaP.png)

* Users can view profiles of other users 
![](https://i.imgur.com/op7NG2V.png)

* Users can register for a newsletter to receive email newsletters whenever new announcements containing specific brands or models appear
![](https://i.imgur.com/raIQC6w.png)

## TODO
In the current version, the application is mostly functional, but there are some basic issues. However, the main idea has been completed, so version alpha is considered the final version 😂:

* Improve responsiveness for better adaptability across different devices
* Some very basic functionalities might not be implemented, such as liking answers under announcements
