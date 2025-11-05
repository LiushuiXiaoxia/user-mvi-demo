import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        SharedPlatformAppKt.initKmpApp()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
