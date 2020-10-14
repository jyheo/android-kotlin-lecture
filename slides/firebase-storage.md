---
marp: true
theme: my-theme
paginate: true
headingDivider: 2
header: 
footer: https://github.com/jyheo/android-lecture
backgroundColor: #fff
backgroundImage: url('images/background.png')
---

# Firebase
<!-- _class: lead -->
### Storage, RemoteConfig
### 허준영(jyheo@hansung.ac.kr)


# Firebase Storage
<!-- _class: lead -->



## Storage
- 이미지, 오디오, 비디오, 사용자가 생성한 데이터 등을 저장하기 위한 공간
    - 유료로 사용하면 백업 기능도 제공
- Firebase 콘솔(https://console.firebase.google.com/)에서 프로젝트 선택
    - 안드로이드 앱 개발 환경과 Firebase 연결이 완료된 상태에서 시작
    - 연결 방법은 Firebase-Auth 강의 자료 참고
![w:900px](images/firebase_storage.png)


## Storage - Firebase Console
![w:500px](images/firebase_storage_start.png) ![w:520px](images/firebase_storage_start2.png)
- 리전 선택, 나중에 변경이 불가함

## Storage - Firebase Console
- 콘솔에서 파일 업로드
![w:900px](images/firebase_storage_upload.png)

## Storage - Firebase Console
- 파일 상세 보기
![w:900px](images/firebase_storage_file.png)


## Storage - Firebase Console
- 접근 규칙(Rules): 스토리지의 파일 접근 권한 설정
![w:900px](images/firebase_storage_rule.png)


## Storage - Firebase Console
- 접근 규칙(Rules): 스토리지의 파일 접근 권한 기본 설정
    ```
    rules_version = '2';
    service firebase.storage {
        match /b/{bucket}/o {
            match /{allPaths=**} {
            allow read, write: if request.auth != null;
            }
        }
    }
    ```
    - 인증 후에 Storage에 접근이 가능함
    - ``` match 패턴 ``` 패턴에 맞는 경로에 대해 { } 안의 내용을 적용
    - 기본 버킷의 경로는 ``` /b/android-kotlin-lecture.appspot.com/o ``` 임
        - 따라서 위의 예는 기본 버킷 내의 모든 경로를 의미함 
    - ``` if request.auth != null ``` 를 제거하면 인증없이 접근 가능


## Storage - 안드로이드 앱
- 모듈의 build.gradle
    ```gradle
    dependencies {
        // Import the BoM for the Firebase platform
        implementation platform('com.google.firebase:firebase-bom:25.12.0')

        // Declare the dependency for the Cloud Storage library
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation 'com.google.firebase:firebase-storage-ktx'
    }
    ```


## Storage - 안드로이드 앱
- 스토리지 객체 가져오기
```kotlin
class StorageActivity : AppCompatActivity() {
    lateinit var storage: FirebaseStorage
    lateinit var binding: ActivityStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.currentUser ?: finish()  // if not authenticated, finish this activity

        storage = Firebase.storage
```
- Firebase 인증이 된 경우에만 Storage에 접근하도록 Rule이 설정되어 있기 때문에
    - 인증이 안된 경우에는 종료하도록 함


## Storage - 안드로이드 앱
- 스토리지의 레퍼런스 객체
    ```kotlin
    val storageRef = storage.reference // reference to root
    val imageRef1 = storageRef.child("images/computer_sangsangbugi.jpg")
    val imageRef2 = storage.getReferenceFromUrl(
        "gs://android-kotlin-lecture.appspot.com/images/computer_sangsangbugi.jpg"
    )
    ```
    - ```.child(경로) ``` : 현재 위치에서 하위 디렉토리의 상대 경로에 대한 레퍼런스
    - ```.parent ``` : 현재 위치에서 부모 디렉토리에 대한 레퍼런스


## Storage - 안드로이드 앱
- 스토리지에서 이미지 가져와서 표시하기
    ```kotlin
    private fun displayImageRef(imageRef: StorageReference?, view: ImageView) {
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.setImageBitmap(bmp)
        }?.addOnFailureListener {
            // Failed to download the image
        }
    }
    ```


## Storage - 안드로이드 앱
- 스토리지에 파일 업로드
    ```kotlin
    private fun uploadFile(file_id: Long?, fileName: String?) {
        file_id ?: return
        val contentUri = ContentUris.withAppendedId(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, file_id)

        val imageRef : StorageReference? = storage.reference.child("업로드 경로/파일명")
        imageRef?.putFile(contentUri)?.addOnCompleteListener {
            if (it.isSuccessful) {
                // upload success
                Snackbar.make(binding.root, "Upload completed.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    ```
    - 이 예제에서 file_id와 fileName은 contentResolver를 사용하여 MediaStore.Images 에서 가져온 것
    - [firebasetest/StorageActivity.kt](https://github.com/jyheo/android-kotlin-lecture/blob/master/examples/firebasetest/app/src/main/java/com/example/firebasetest/StorageActivity.kt) 의 uploadDialog() 참조


##  Storage - 안드로이드 앱
- 전체 예제 코드
    - https://github.com/jyheo/android-kotlin-lecture/tree/master/examples/firebasetest

![bg right:40% w:250px](images/firebase_storage_download.png) ![bg right:40% w:250px](images/firebase_storage_upload_in_app.png)


# Firebase Remote Config
<!-- _class: lead -->

## Remote Config
- 앱의 동작을 원격 클라우드에서 변경할 수 있음
![](images/firebaserc.png) ![](images/firebaserc2.png)


## Remote Config
- 
    ```java
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            return;
        }

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600) // For development only not for production!, default is 12 hours
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);        
    }
    ```
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../MainActivity.java#L54](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MainActivity.java#L54)


## Remote Config
- 기본 설정 파일 만들기
    - New > Android Resource File
    - R.xml.remote_config_defaults
    ![w:700px](images/firebasercdefault.png)


## Remote Config
- res/xml/remote_config_defaults.xml
    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <defaultsMap xmlns:android="http://schemas.android.com/apk/res/android">
        <entry>
            <key>your_price</key>
            <value>100</value>
        </entry>
        <entry>
            <key>cheat_enabled</key>
            <value>false</value>
        </entry>
    </defaultsMap>
    ```
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../remote_config_defaults.xml](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/res/xml/remote_config_defaults.xml)


## Remote Config
- Firebase에서 설정 가져와서 적용
    ```java
    public void onFetchButton(View v) {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                        } else {
                            Log.d(TAG, "Fetch failed");
                        }
                        displayConfig();  // 가져온 설정 읽기(다음 슬라이드)
                    }
                });
    }
    ```
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../MainActivity.java#L95](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MainActivity.java#L95)


## Remote Config
- 가져온 설정 읽기
    ```java
    private void displayConfig() {
        boolean cheat_enabled = mFirebaseRemoteConfig.getBoolean("cheat_enabled");
        binding.textViewCheat.setText("cheat_enabled=" + cheat_enabled);
        long price = mFirebaseRemoteConfig.getLong("your_price");
        binding.textViewPrice.setText("your_price is " + price);
    }
    ```
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../MainActivity.java#L88](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MainActivity.java#L88)


## Remote Config
- Firebase 콘솔에서 설정 만들기
![](images/firebaseconsolerc.png)


