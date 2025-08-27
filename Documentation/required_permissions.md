# Required Permissions for HorceTrainer

This document lists all permissions necessary for the full functionality of the HorceTrainer application, based on the provided Technical Specification (ТЗ).

## Permissions:

- `android.permission.INTERNET`: Required for network access (e.g., for future synchronization features, updates, or external data fetching).
- `android.permission.READ_EXTERNAL_STORAGE`: Required for accessing photos from the device's gallery (for horse profile pictures).
- `android.permission.CAMERA`: Required for taking photos with the device's camera (for horse profile pictures).

**Note:** As of the current implementation, `READ_EXTERNAL_STORAGE` and `CAMERA` permissions are not yet declared in `AndroidManifest.xml` but are required for the photo selection functionality described in the ТЗ.
