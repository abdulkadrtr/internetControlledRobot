# Arduino & Phone as an HTTP Server for Remote Control

## SensorShare is an internet-connected robot project that serves as a bridge between Arduino and a phone, establishing a connection between the two.
![Robot](https://github.com/abdulkadrtr/internetControlledRobot/assets/87595266/87f7197f-8d18-49eb-88a3-a27fdda7b15c)
## Project Introduction Video & Demo 

https://youtu.be/pjNkS2eiwVk

### About the project 
SensorShare is a mobile application written in Kotlin that establishes a connection between an Arduino and your phone, creating an innovative project.
This app transforms your phone into an HTTP server using localhost port 8080. By making simple GET requests to `/gps` ,  `/orientation`, `/gyro`, `/accelerometer` and `/light` 
endpoints, you can easily retrieve sensor data from your phone's built-in sensors.

The real magic happens when you send a POST request to the `/control` endpoint. With this request, you can include an integer value called controlSignal in the request body and directly send this value to the Arduino device connected to your phone via an OTG cable. Additionally, the project includes a user-friendly web-based Control Panel designed using HTML, CSS, and JavaScript to facilitate these requests and provide an intuitive interface.

SensorShare opens the doors to creating internet-connected robots without the need to purchase extra sensors. By utilizing the sensors already present in your phone, it offers a cost-effective robotics solution.

Our primary goal is to provide an economical robotics solution for kamikaze robots.

### Project Features

- Phone Sensor Usage: Equip your robot using the sensors already present in your phone, without the need to purchase extra sensors.
- Real-Time Data Monitoring: View and track real-time sensor data of your robot through the user interface of the Control Panel.
- Video Streaming: Watch the live video stream from your robot's (phone's) camera on the Control Panel and remotely observe the surroundings.
- Location Tracking: Monitor the current location of your robot using the map on the Control Panel.
- Arduino Control: Send remote commands to your Arduino device through the Control Panel.
- Remote Access: Access and control your robot from anywhere in the world using a private VPN network.

### Project Setup

- Download the .apk version of the SensorShare application and install it on your phone. Then, enable GPS permissions.
- Connect your Arduino device to your phone using an OTG cable. Make sure that OTG permissions are active on your phone.
- For live video streaming, download the DroidCam app from the Play Store.
- Open the SensorShare application and wait until you see the message `Server Started.` Then, go to the DroidCam app, enter the IP address shown on the screen into the Control Panel, and click the connect button. Once the connection is established, you will receive an informative message on the Control Panel, and it will start updating continuously with new data.
- At this stage, you can customize the project. You can use any Arduino pins you desire and communicate with the Arduino using the "manual signal send" feature. Sample Arduino codes serve as a guide for these processes.

### Robot Model
![robot](https://github.com/abdulkadrtr/internetControlledRobot/assets/87595266/4f791f5c-18c2-4ddd-9585-44dd323387fa)
### Control Panel:
![Screenshot_1](https://github.com/abdulkadrtr/internetControlledRobot/assets/87595266/b74fcf18-eb25-4b8b-b26c-69625de1ee4a)
