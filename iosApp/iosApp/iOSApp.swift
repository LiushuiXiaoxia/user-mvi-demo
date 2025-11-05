import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        // Platform_iosKt.initKmpApp()
        Platform_iosKt.doInitKmpApp()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
