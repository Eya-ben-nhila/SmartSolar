# SmartSolar MQTT Integration

This document describes the MQTT integration that enables real-time communication between your ESP32 solar panel system and the SmartSolar Android app.

## Overview

The integration replaces mock data with real-time data from your ESP32 broker code. The app now connects to the HiveMQ broker and subscribes to the same topics that your ESP32 publishes to.

## MQTT Topics

The app subscribes to the following topics (matching your ESP32 code):

- `esp32/ina226/courant` - Current measurement (A)
- `esp32/tension` - Voltage measurement (V)
- `esp32/ina226/puissance` - Power measurement (W)
- `esp32/fil_de_securite` - Security wire status
- `esp32/mpu6050/alerte` - Movement detection alerts
- `esp32/tensionBA` - Battery voltage (V)
- `esp32/chargeBA` - Battery charge percentage (%)
- `batterie/pompe` - Pump powered by battery
- `pv/pompe` - Pump powered by PV
- `pompe` - Pump stopped

## Architecture

### 1. MqttService
- Handles MQTT connection to `broker.hivemq.com:1883`
- Manages subscriptions to all topics
- Provides real-time data updates via callbacks
- Automatically reconnects on connection loss

### 2. DataManager
- Manages the MQTT service lifecycle
- Provides StateFlow streams for reactive UI updates
- Converts raw MQTT data to typed values
- Handles data validation and error cases

### 3. Activity Integration
- Activities observe StateFlow streams for real-time updates
- UI elements update automatically when new data arrives
- Connection status indicators show MQTT connection state
- Alert indicators for security and movement events

## Features

### Real-time Data Display
- **Dashboard**: Shows current power generation, battery status, and connection status
- **PV Activity**: Displays voltage, current, and power measurements
- **Battery Activity**: Shows battery voltage, charge percentage, and level indicators
- **Security**: Real-time security wire and movement detection alerts

### Connection Management
- Automatic connection to HiveMQ broker
- Connection status indicators
- Automatic reconnection on connection loss
- Error handling and logging

### Alert System
- Visual indicators for security alerts (red background)
- Movement detection alerts
- Pump status alerts
- Connection status alerts

## Setup Instructions

1. **ESP32 Configuration**: Ensure your ESP32 is running the provided broker code and connected to the same HiveMQ broker.

2. **Network Requirements**: The app requires internet access to connect to the MQTT broker.

3. **Permissions**: The app automatically requests necessary permissions for internet access.

## Usage

1. **Start the App**: The MQTT service starts automatically when the app launches.

2. **Monitor Connection**: Check the connection status in the dashboard.

3. **View Real-time Data**: Navigate to different activities to see real-time updates:
   - Dashboard: Overview of all systems
   - PV Activity: Solar panel measurements
   - Battery Activity: Battery status and charge level
   - Security Activity: Security alerts and status

4. **Alerts**: Watch for visual indicators when alerts are received from the ESP32.

## Troubleshooting

### Connection Issues
- Check internet connectivity
- Verify ESP32 is running and connected to the same broker
- Check app logs for connection errors

### Data Not Updating
- Verify ESP32 is publishing data to the correct topics
- Check MQTT broker connectivity
- Restart the app to reinitialize the MQTT service

### Performance Issues
- The app uses efficient StateFlow streams for updates
- MQTT connection is managed in a background service
- UI updates are handled on the main thread

## Technical Details

### Dependencies
- `org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5`
- `org.eclipse.paho:org.eclipse.paho.android.service:1.1.1`
- `kotlinx-coroutines-android:1.7.3`

### Architecture Patterns
- Service-based MQTT client
- StateFlow for reactive data streams
- Repository pattern with DataManager
- Observer pattern for UI updates

### Error Handling
- Graceful connection loss handling
- Automatic reconnection attempts
- Data validation and fallback values
- Comprehensive logging for debugging

## Future Enhancements

- Local data caching for offline viewing
- Historical data storage
- Push notifications for critical alerts
- Custom MQTT broker configuration
- Data export functionality
