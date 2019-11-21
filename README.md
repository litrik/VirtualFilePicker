Small sample Android app that shows it is not possible to open a Google Drawing using the Google Drive file picker of Android's Storage Access Framework.

When you try to open a Google Doc as PDF, it works just fine:

```
I: Showing file picker for mime type application/*
I: Trying to load Google drawing at content://com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3D38
I: getStreamTypes() returned possible mime types: application/pdf
I: Trying openTypedAssetFileDescriptor() with mime type application/pdf
I: Inputstream opened succesfully
```

When you try to open a Google Drawing as PDF, it fails:

```
I: Showing file picker for mime type application/*
I: Trying to load Google drawing at content://com.google.android.apps.docs.storage/document/acc%3D1%3Bdoc%3D30
I: getStreamTypes() returned possible mime types: application/pdf
I: Trying openTypedAssetFileDescriptor() with mime type application/pdf
I: ERROR: StorageFileLoadException[download_unavailable]
I: ¯\_(ツ)_/¯
```
