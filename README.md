# SmartSolar - Android Solar Panel Monitoring App

A comprehensive Android application for monitoring and managing solar panel systems with real-time MQTT communication capabilities.

## 🌟 Features

### Core Functionality
- **Real-time Solar Panel Monitoring**: Track power generation, battery levels, and system status
- **MQTT Integration**: Real-time communication with ESP32-based solar panel controllers
- **Multi-language Support**: English and French interfaces
- **Responsive Dashboard**: Live updates of solar panel metrics
- **Battery Management**: Monitor battery charge levels and status
- **Security Monitoring**: Real-time security system status
- **Pump Control**: Monitor and control water pump systems
- **PV System Tracking**: Detailed photovoltaic system metrics

### Technical Features
- **Background MQTT Service**: Persistent connection for real-time data
- **Mock Data Fallback**: Continues operation when MQTT connection is unavailable
- **StateFlow Integration**: Reactive UI updates with Kotlin coroutines
- **Modern Android Architecture**: Built with latest Android development practices

## 📱 Screenshots

The app includes the following main screens:
- **Dashboard**: Overview of all system metrics
- **Battery**: Detailed battery monitoring and management
- **PV System**: Photovoltaic panel performance tracking
- **Security**: Security system status and controls
- **Pump**: Water pump monitoring and control
- **History**: Historical data and trends
- **Profile**: User account management
- **Notifications**: System alerts and notifications

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 24+ (Android 7.0)
- Kotlin 1.8+
- Gradle 7.0+

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/SmartSolar.git
   cd SmartSolar
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the SmartSolar folder and select it

3. **Build and Run**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use:
   ```bash
   ./gradlew assembleDebug
   ```

### APK Installation

Download the latest APK from the releases section or build it yourself:

```bash
./gradlew assembleDebug
```

The APK will be located at: `app/build/outputs/apk/debug/app-debug.apk`

## 🔧 MQTT Configuration

### ESP32 Integration
The app is designed to communicate with ESP32-based solar panel controllers via MQTT protocol.

**Topics:**
- `smartsolar/power` - Power generation data
- `smartsolar/battery` - Battery status and charge level
- `smartsolar/security` - Security system status
- `smartsolar/pump` - Pump system status
- `smartsolar/pv` - PV system voltage and current

### Broker Configuration
- **Primary**: `tcp://broker.hivemq.com:1883`
- **Fallback**: `tcp://test.mosquitto.org:1883`
- **Local**: Configure for your ESP32's IP address

### Connection Modes
1. **Real MQTT**: Connects to actual ESP32 broker
2. **Mock Data**: Generates realistic test data when MQTT unavailable
3. **Fallback**: Automatic switching between modes

## 🏗️ Architecture

### Key Components
- **MqttService**: Background service for MQTT communication
- **DataManager**: Singleton for centralized data management
- **Activities**: UI components for different app sections
- **StateFlow**: Reactive data streams for UI updates

### Dependencies
```kotlin
// MQTT Communication
implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5'
implementation 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'

// Coroutines
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3'
```

## 📊 Data Flow

```
ESP32 → MQTT Broker → MqttService → DataManager → UI Components
                ↓
         Mock Data Generator (fallback)
```

## 🔍 Troubleshooting

### Common Issues

1. **MQTT Connection Fails**
   - Check network connectivity
   - Verify broker URL and port
   - Ensure ESP32 is running and connected

2. **App Shows "Disconnected"**
   - This is normal when using mock data mode
   - Check logs for connection attempts
   - Verify internet permissions

3. **Build Errors**
   - Clean and rebuild project
   - Update Android Studio and SDK
   - Check Gradle version compatibility

### Logging
Enable detailed logging by filtering Logcat with tag: `MqttService`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- ESP32 MQTT broker code provided by project collaborator
- Eclipse Paho MQTT client library
- Android development community

## 📞 Support

For support and questions:
- Create an issue in this repository
- Check the troubleshooting section
- Review the MQTT integration documentation

---

**SmartSolar** - Empowering solar energy monitoring with modern Android technology.
