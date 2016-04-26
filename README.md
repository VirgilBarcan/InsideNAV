# InsideNAV
This is my Bachelor degree project.

The project's goal is to provide navigation facilities inside buildings or at any time when no GPS signal is available.
The project is build as an Android application, using the Android system.

The application will get all data from the smartphone's sensors and process it in such a way that a
prediction of the current position will be given as an output.

###The sensors that will be take into account are:
 * Accelerometer
 * Gyroscope
 * Magnetometer

Besides those sensors, the app will use:
 * GPS, when available, in order to correct eventual errors
 * WiFi, when available, in order to correct eventual errors
 * Bluetooth (some sensors exist that can be used to locate a device via Bluetooth)

Each sensor data is collected by a specialised class, which in turn sends it to processing.
###The processing part of the application will contain different algorithms:
 * Step counter
 * Step distance estimation
 * Kalman filter
 * Triangulation

###The processed data is then send to the frontend and displayed to the user.


###Bio
https://www.ece.nus.edu.sg/stfpage/elesohws/PerCom_Pedestrian_Tracking.pdf
