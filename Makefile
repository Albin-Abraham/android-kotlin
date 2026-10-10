.DEFAULT_GOAL := help

.PHONY: help
help: ## Display all available build and developer automation targets
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-22s\033[0m %s\n", $$1, $$2}'

.PHONY: build
build: ## Assemble debug APK
	./gradlew assembleDebug

.PHONY: test
test: ## Run debug unit tests suite
	./gradlew testDebugUnitTest

.PHONY: test-all
test-all: test ## Run unit tests and static verification checks
	./gradlew check

.PHONY: install
install: build ## Build and install debug APK onto connected device / emulator
	adb install -r app/build/outputs/apk/debug/app-debug.apk

.PHONY: run
run: install ## Install and launch the application on connected device
	adb shell am start -n com.example.myapp/.MainActivity

.PHONY: clean
clean: ## Clean build caches and temporary build artifacts
	./gradlew clean

.PHONY: logs
logs: ## Stream logcat filtered for the ZenOS application
	adb logcat -v time | grep -E "com.example.myapp"

.PHONY: restart
restart: ## Force-stop and restart the application on device
	adb shell am force-stop com.example.myapp
	adb shell am start -n com.example.myapp/.MainActivity
