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
### In-App Messaging, Cloud Messaging
### 허준영(jyheo@hansung.ac.kr)

# In-App Messaging
<!-- _class: lead -->

## In-App Messaging
- Firebase 콘솔에서 앱에 다이얼로그나 헤드업 알림 같은 형태의 메시지를 만들어서 전달
![](images/firebase_inappmsg_sample.png) ![](images/firebase_inappmsg_sample2.png) ![](images/firebase_inappmsg_sample3.png)


## In-App Messaging - 안드로이드 앱 설정
- build.gradle
    ```gradle
    dependencies {
        // Import the BoM for the Firebase platform
        implementation platform('com.google.firebase:firebase-bom:25.12.0')

        // Declare the dependencies for the In-App Messaging and Analytics libraries
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation 'com.google.firebase:firebase-inappmessaging-display-ktx'
        implementation 'com.google.firebase:firebase-analytics-ktx'
    }
    ```

## In-App Messaging - Firebase Console
- https://console.firebase.google.com/
- 프로젝트 생성/선택하고 In-App Messaging 선택
![w:800px](images/firebase_inappmsg.png)


## In-App Messaging - Firebase Console
- In-App Message 레이아웃 만들기
![w:400px](images/firebase_inappmsg_create.png)  ![w:400px](images/firebase_inappmsg_create2.png)


## In-App Messaging - Firebase Console
- 대상 선택
    - 패키지 이름, 지역 등으로 제한할 수 있음
![w:800px](images/firebase_inappmsg_create3.png)


## In-App Messaging - Firebase Console
- 스케줄, 기타 옵션을 선택하고
- Publish를 누르면 완료
- 앱을 시작하면 오른쪽과 같이 캠페인 메시지가 나타남

![bg right:30% w:250px](images/firebase_inappmsg_test3.png)

## In-App Messaging - Firebase Console
- 하루에 한번만 보낼 수 있기 때문에, 테스트를 위해 FID를 사용함
- FID: FirebaseInstallation ID
- 앱을 실행하면 로그캣에 Info 레벨로 아래와 같은 형태로 FID출력됨
    ```
    I/FIAM.Headless: Starting InAppMessaging runtime with Installation ID 여기에출력되는값
    ```

## In-App Messaging - Firebase Console
- Firebase Console에서 In-App Messaging 선택
    - 한개 이상의 캠페인이 만들어져 있으면 아래와 같은 화면이 나옴
- 목록에서 캠페인 오른쪽 옵션 메뉴를 눌러서
    - Test on device 선택
![](images/firebase_inappmsg_test.png)


## In-App Messaging - Firebase Console
- FID를 입력하고 추가
- Test 버튼 누르면
    - 안드로이드 디바이스에서 홈 버튼을 누르고 최근 앱에서 앱을 선택하면 캠페인 메시지가 다시 나타남
![w:800px](images/firebase_inappmsg_test2.png)


# Cloud Messaging
<!-- _class: lead -->

## Cloud Messaging
- 일반적으로 푸시 알림으로 알려진 서비스
- 클라이언트가 서버로부터 업데이트된 정보를 가져오려면
    - 주기적으로 서버에 접속해서 확인하는 방법
    - 서버가 클라이언트에 접속해서 알려주는 방법(이 방법은 일반적이지 못함, 클라이언트가 접속을 허용하지 않는 경우가 대부분)
- 누가 서버에 접속하는가?
    - 스마트폰의 여러 앱이 각자 알아서 주기적으로 서버에 접속하면?
    - 하나의 알림 서버에 시스템이 접속해서 모든 앱이 필요한 업데이트 정보를 확인해준다면? -> Firebase Cloud Messaging


## Cloud Messaging
- 특정 대상(특정 **토큰** 소유자, 앱 사용자 전체 등)에게 알림을 보낼 수 있음
- 특정 시간을 정해서 보낼 수 있음
- 사용자에게 앱의 사용을 유도하는 용도
    - 이벤트 알림 등
- 채팅 구현에 사용될 수도 있음
- Tools > Firebase 를 하고 Cloud Messaging 선택하고 Add FCM to your app
![](images/firebase/firebasecm.png) ![](images/firebase/firebasecm2.png)


## Manifest에 서비스와 인텐트 필터
- AndroidManifest.xml 에 서비스와 인텐트 필터 추가
    ```xml
    <service
        android:name=".MyFirebaseMessagingService">
        <intent-filter>
            <action android:name="com.google.firebase.MESSAGING_EVENT"/>
        </intent-filter>
    </service>
    ```
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../AndroidManifest.xml#L19](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/AndroidManifest.xml#L19)


## 토큰 받기
- FCM에서 이 앱의 고유한 토큰 받기
    ```java
    FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();

                // Log and toast
                String msg = "FCM token:" + token;
                binding.tvToken.setText(msg);
                Log.d(TAG, msg);
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    ```
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../MainActivity.java#L146](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MainActivity.java#L146)


## 토큰 업데이트 모니터링
- 토큰 업데이트 모니터링, 토큰이 업데이트될 수 있음
    ```java
    public class MyFirebaseMessagingService extends FirebaseMessagingService { 
        @Override
        public void onNewToken(@NonNull String token) {
            // Get updated InstanceID token.
            Log.d(TAG, "Refreshed token: " + token);

            // TODO: Implement this method to send any registration to your app's servers.
            // sendRegistrationToServer(token);
        }

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            ...
        }
    }
    ```
    - [android-java-examples/.../FirebaseTest/.../MyFirebaseMessagingService.java#L18](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MyFirebaseMessagingService.java#L18)


## 메시지 수신 서비스
- 메시지 수신 서비스
    ```java
    public class MyFirebaseMessagingService extends FirebaseMessagingService {                  
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            Log.d(TAG, "From: " + remoteMessage.getFrom());

            // Check if message contains a data payload.
            if (remoteMessage.getData().size() > 0) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                msgBody = remoteMessage.getNotification().getBody();
                Log.d(TAG, "Message Notification Body: " + msgBody);
            }
        }
    ```
    - [android-java-examples/.../FirebaseTest/.../MyFirebaseMessagingService.java#L28](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MyFirebaseMessagingService.java#L28)


## Firebase 콘솔에서 알림 보내기
- Firebase 콘솔에서 알림 보내기 
    - https://console.firebase.google.com
![w:750](images/firebase/firebaseconsolenoti.png)


## Firebase 콘솔에서 알림 보내기
- Firebase 콘솔에서 알림 보내기 
    - 토큰을 지정하여 특정 대상에게 보낼 수 있음
![w:600](images/firebase/firebaseconsolenoti_token.png)


## Firebase 콘솔에서 알림 보내기
- Firebase 콘솔에서 알림 보내기 
![w:600](images/firebase/firebaseconsolenoti2.png)


## Firebase 콘솔에서 알림 보내기
- Firebase 콘솔에서 알림 보내기 
![w:600](images/firebase/firebaseconsolenoti3.png)


## 앱에서 알림 받기
- 액티비티가 활성화 된 상태일 때
    - FirebaseMessagingService의onMessageReceived()가 호출됨
- 액티비티가 비활성화 된 상태일 때
    - 시스템이 자체적으로 알림을 표시함
    - 알림을 선택하면 액티비티를 활성화 시킴
    ![](images/firebase/firebasereceivenoti.png)


## Cloud Messaging (3rd Party 서버)
- Firebase Cloud Messaging (FCM)
    ![](images/firebase/9XzwPqc.png)
    - 출처: https://guides.codepath.com/android/Google-Cloud-Messaging


## Cloud Messaging (3rd Party 서버)
1. Firebase 서버에 접속하여 토큰을 받음
![w:700](images/firebase/5UPxP3n.png)
    - 출처: https://guides.codepath.com/android/Google-Cloud-Messaging


## Cloud Messaging (3rd Party 서버)
2. 받은 토큰을 가지고 3rd party 서버에 접속
![w:700](images/firebase/ItRPQ7N.png)
    - 출처: https://guides.codepath.com/android/Google-Cloud-Messaging


## Cloud Messaging (3rd Party 서버)
3. 3rd party 서버가 Firebase 서버를 통해 앱으로 푸시 알림 보냄
![w:700](images/firebase/adiFo8w.png)
    - 출처: https://guides.codepath.com/android/Google-Cloud-Messaging


## Cloud Messaging (3rd Party 서버)
- 3rd Party 서버 만들기
    - 앱이 접속하여 메시지를 주고받는 서버
    - 앱은 구글 서버에서 받은 토큰을 이용하여 서버에 등록
    - 서버는 앱에게 푸시 메시지를 보낼 필요가 있을 때(앱이 접속이 안되어 있는 경우 등) 구글 서버를 통해 푸시(앱이 등록한 토큰 이용)를 보냄


## Cloud Messaging (3rd Party 서버)
- Curl을 이용하여 서버 없이 메시징 테스트
    ```
    curl --header "Authorization: key=AIzaSyC3-Rz5MiJmWxBy78io0SG4HYHwPJbTsL0"
        --header Content-Type:"application/json"
        https://fcm.googleapis.com/fcm/send
        -d "{ \"notification\": { \"title\": \“title here\",  \"text\": \“message body here\"  },  \"to\" : \"여기에 토큰이 들어가야 함\"}"
    ```
- **Authorization: key=**는 firebase console (https://console.firebase.google.com) 에서 프로젝트 설정 > 클라우드 메시징에서 확인 가능 (Server key 토큰)
- 데이터 부분에서 **to**의 값은 앱에서 받은 FCM 토큰
    - FirebaseInstanceId.getInstance().getInstanceId() 를 통해 받은 값


## Cloud Messaging (3rd Party 서버)
- **Authorization: key=**
    ![](images/firebase/server_key.png)