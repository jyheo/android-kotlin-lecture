---
marp: true
paginate: true
theme: my-theme
headingDivider: 2
backgroundColor: 
backgroundImage: url('images/background.png')
---

# Flutter 
<!-- _class: lead -->
### 허준영(jyheo@hansung.ac.kr)


## Flutter 개요
- 모바일, 웹, 데스크톱 응용 프로그램을 위한 UI 툴킷
- 하나의 코드로 다양한 플랫폼 지원
- Android, iOS
- Web, Desktop (Alpha)
- 특징
    - 빠른 개발
    - 네이티브로 컴파일되어 성능 우수
    - Dart라는 언어로 작성

## 개발환경 설치
- 윈도우 10에서 안드로이드 스튜디오가 있는 경우를 가정함
- Flutter SDK 다운로드 & 압축 풀기
    - https://flutter.dev/docs/get-started/install/windows
    - C:\flutter 에 압축 해제
- github에서 받을 수도 있음
    - ```C:\> git clone https://github.com/flutter/flutter.git -b stable ```
- C:\flutter\bin 을 PATH에 넣음
- SDK 환경 체크
    - ```C:\> flutter doctor ```

## 개발환경 설치
- ``` C:\> flutter doctor ```
```bash
C:\Users\jyheo>flutter doctor

  ╔════════════════════════════════════════════════════════════════════════════╗
  ║                 Welcome to Flutter! - https://flutter.dev                  ║
  ║                                                                            ║
  ║ The Flutter tool uses Google Analytics to anonymously report feature usage ║
  ║ statistics and basic crash reports. This data is used to help improve      ║
  ║ Flutter tools over time.                                                   ║
  ║                                                                            ║
  ║ Flutter tool analytics are not sent on the very first run. To disable      ║
  ║ reporting, type 'flutter config --no-analytics'. To display the current    ║
  ║ setting, type 'flutter config'. If you opt out of analytics, an opt-out    ║
  ║ event will be sent, and then no further information will be sent by the    ║
  ║ Flutter tool.                                                              ║
  ║                                                                            ║
  ║ By downloading the Flutter SDK, you agree to the Google Terms of Service.  ║
  ║ Note: The Google Privacy Policy describes how data is handled in this      ║
  ║ service.                                                                   ║
  ║                                                                            ║
  ║ Moreover, Flutter includes the Dart SDK, which may send usage metrics and  ║
  ║ crash reports to Google.                                                   ║
  ║                                                                            ║
  ║ Read about data we send with crash reports:                                ║
  ║ https://flutter.dev/docs/reference/crash-reporting                         ║
  ║                                                                            ║
  ║ See Google's privacy policy:                                               ║
  ║ https://policies.google.com/privacy                                        ║
  ╚════════════════════════════════════════════════════════════════════════════╝
```

## 개발환경 설치
```bash
Doctor summary (to see all details, run flutter doctor -v):
[√] Flutter (Channel stable, 1.20.4, on Microsoft Windows [Version 10.0.18363.1082], locale ko-KR)

[!] Android toolchain - develop for Android devices (Android SDK version 30.0.2)
    ! Some Android licenses not accepted.  To resolve this, run: flutter doctor --android-licenses
[!] Android Studio (version 4.0)
    X Flutter plugin not installed; this adds Flutter specific functionality.
    X Dart plugin not installed; this adds Dart specific functionality.
[!] VS Code (version 1.49.2)
    X Flutter extension not installed; install from
      https://marketplace.visualstudio.com/items?itemName=Dart-Code.flutter
[!] Connected device
    ! No devices available

! Doctor found issues in 4 categories.
```
- 결과를 살펴보고 문제 해결

## 개발환경 설치
- [!] Android Studio (version 4.0)
    - Flutter와 Dart 플러그인 설치
    - Settings > Plugins 에서 Flutter 검색하여 설치
    - 이 때 Dart도 같이 설치하라는 다이얼로그가 뜨면
        ``` The “Flutter” plugin requires "Dart" plugin to be installed. ```
        - 같이 설치함
    - Restart IDE를 눌러 안드로이드 스튜디오 재시작
![](images/flutter_plugin.png)

## 개발환경 설치
- [!] Android toolchain - develop for Android devices (Android SDK version 30.0.2)
    - ! Some Android licenses not accepted.  To resolve this, run: flutter doctor --android-licenses
    - 지시하는데로 ``` flutter doctor --android-licenses ``` 실행하여 accept 함
- [!] VS Code (version 1.49.2)
    - VS Code도 사용하고 싶으면 플러그인 설치하면 됨
- [!] Connected device
    - 안드로이드 에뮬레이터를 실행하거나 안드로이드 스마트폰을 USB로 연결하면 됨

## 개발환경 설치
- 모두 정상적으로 설치된 상황이면
```bash
C:\Users\jyheo>flutter doctor
Doctor summary (to see all details, run flutter doctor -v):
[√] Flutter (Channel stable, 1.20.4, on Microsoft Windows [Version 10.0.18363.1082], locale ko-KR)
[√] Android toolchain - develop for Android devices (Android SDK version 30.0.2)
[√] Android Studio (version 4.0)
[√] VS Code (version 1.49.2)
[√] Connected device (1 available)

• No issues found!
```

## Hello, Flutter
- 안드로이드 스튜디오 실행
- File > New > New Flutter Project
![w:600px](images/flutter_new_project.png)

## Hello, Flutter
- Flutter SDK 위치가 지정이 안되어 있다면 설치한 곳으로 지정
    - 여기에서는 c:\flutter
![w:600px](images/flutter_new_project2.png)

## Hello, Flutter
- Finish 버튼 눌러서 프로젝트 생성
![w:600px](images/flutter_new_project3.png)

## Hello, Flutter
- 안드로이드 스튜디오에서 Run > Run 'main.dart' 로 실행
- 에뮬레이터(또는 디바이스)에 실행되는 것 확인
    - 오른쪽 아래 + 버튼을 누르면 가운데 숫자 증가함
![bg right:25% w:250](images/flutter_demo.png)

## Hello, Flutter
- Hot Reload 테스트
- Flutter SDK는 다른 크로스플랫폼 개발 환경들과 마찬가지로 hot reload를 제공
- 코드를 수정하면 바로 실행 프로그램에 반영되는 것
- lib/main.dart 파일을 오픈 MyHomePage 호출 부분에서 title의 내용을 바꾼 후 저장해보면 바로 실행 화면에 바뀌는 것을 확인 가능
    ```dart
    class MyApp extends StatelessWidget {
        // This widget is the root of your application.
        @override
        Widget build(BuildContext context) {
            return MaterialApp(
            title: 'Flutter Demo',
            theme: ThemeData(
                primarySwatch: Colors.blue, visualDensity: VisualDensity.adaptivePlatformDensity,
            ),
            home: MyHomePage(title: 'Flutter Demo Home Page'),
            );
        }
    }
    ```
## Hello, Flutter
- Hot Reload 테스트
- 'Flutter Demo Home Page' 대시 'Hello, Flutter' 로 변경 후 저장
![bg right:25% w:250](images/flutter_hello.png)
