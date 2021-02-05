---
marp: true
theme: my-theme
paginate: true
headingDivider: 2
header: 안드로이드 UI 프로그래밍
footer: https://github.com/jyheo/android-kotlin-lecture
backgroundColor: #fff
backgroundImage: url('images/background.png')
---

# UI 프로그래밍
<!-- _class: lead -->
### https://github.com/jyheo/android-kotlin-lecture


## 학습목표
- 레이아웃 XML에서 TextView와 EditText의 외형과 관련된 속성을 이해하고 사용할 수 있다.
- 리소스 문자열을 사용하여 다국어 레이아웃을 만들 수 있다.
- ImageView 위젯을 사용하여 이미지를 표시할 수 있다.
- ScrollView를 사용하여 크기가 큰 위젯이 스크롤되도록 만들 수 있다.
- ViewBinding을 이용하여 위젯 객체를 접근할 수 있다.
- 사용자 버튼 클릭에 따라 위젯의 내용을 동적으로 변경할 수 있다.

https://github.com/jyheo/android-kotlin-lecture/tree/master/examples/prog_ui


## TextView와 EditText
- TextView는 텍스트를 출력하기 위한 위젯
- EditText는 텍스트를 입력받기 위한 위젯
- EditText는 TextView를 상속하기 때문에 TextView의 속성을 그대로 사용
- 텍스트 외형을 속성을 사용해 변경할 수 있음
    - android:text 실제 표시될 텍스트
    - android:textSize 텍스트 크기, 단위는 sp
    - android:textStyle 굵은글씨(bold), 기울임글씨(italic)
    - android:textColor 텍스트 색
    - android:textAlignment 좌, 우, 중앙 정렬
    - android:singleLine 강제로 한 줄 표시
    - android:ellipsize 텍스트 출력할 공간이 부족할 경우 ... 표시 (singleLine과 함께 사용)

---
- EditText에서 주로 사용하는 속성
    - android:ems 입력 받을 글자 수, 즉 EditText의 가로 크기
    - android:hint 입력 내용이 없을 때 표시되는 힌트
    - android:inputType 입력 받을 텍스트 종류(숫자, 패스워드, 이메일, 전화번호 등)
        - 패스워드일 경우 입력한 글자 대신 * 가 표시됨

EditText에서 주로 사용하는 속성들도 모두 TextView에서 정의된 속성이다.

---
- 레이아웃에서 TextView, EditText 속성 예
    ```xml
    <TextView
        android:layout_width="match_parent"     android:layout_height="wrap_content"
        android:layout_marginBottom="5dp"
    →   android:ellipsize="end"
    →   android:singleLine="true"
    →   android:textSize="24sp"
        android:text="@string/long_text"/>

    <TextView
        android:layout_width="match_parent"     android:layout_height="wrap_content"
        android:layout_marginBottom="5dp"
    →   android:textColor="#ff0000"
        android:background="#00ffff"
    →   android:textAlignment="center"
    →   android:textStyle="bold|italic"
    →   android:textSize="20sp"
        android:text="@string/bold_italic" />

    <EditText
        android:layout_width="wrap_content"     android:layout_height="wrap_content"
        android:layout_marginBottom="5dp"
    →   android:ems="10"
    →   android:inputType="textPersonName"
        android:importantForAutofill="no"
    →   android:hint="@string/normal_edit_text" />

    <EditText
        android:layout_width="match_parent"     android:layout_height="wrap_content"
        android:layout_marginBottom="5dp"
    →   android:inputType="textEmailAddress"
        android:importantForAutofill="no"
    →   android:hint="@string/email_edit_text"/>

    <EditText
        android:layout_width="match_parent"     android:layout_height="wrap_content"
        android:layout_marginBottom="5dp"
    →   android:inputType="textPassword"
        android:importantForAutofill="no"
    →   android:hint="@string/password_edit_text"/>
    ```

![bg right:30% w:80%](images/ui/text-attr.png)

https://github.com/jyheo/android-kotlin-lecture/blob/master/examples/prog_ui/app/src/main/res/layout/activity_main.xml#L7-L55


## 리소스 - 문자열
- 앞의 예에서 android:text와 android:hint 속성의 값을 문자열이 아닌 @string/xxxx 를 사용
    - 문자열을 직접 쓸 수도 있지만, 리소스에 정의해 둔 문자열의 ID를 쓰는 것을 권장함
- 이렇게 문자열을 리소스로 관리하고 문자열이 필요한 곳에서 ID를 참조하도록 하면
    - 시스템 언어 설정에 따라 해당 언어가 자동으로 표시되어 다국어 버전 앱 만들 때 유용함
- 문자열 리소스 XML
    - src\main\res\values\strings.xml
    ```xml
    <resources>
        <string name="app_name">prog_ui</string>
        <string name="bold_italic">bold and italic</string>
    </resources>
    ```
    - 다른 리소스 XML에서 @string/app_name 으로 참조할 수 있음

---
- 다국어 지원을 위해 언어마다 문자열 리소스를 정의
    - 기본 언어 문자열 정의는 src\main\res\values\strings.xml
    - 특정 언어(한국어)의 문자열 정의는 src\main\res\values-ko-rKR\strings.xml
    - 안드로이드 스튜디오의 문자열 에디터를 사용하면 손쉽게 여러 언어 문자열 관리할 수 있음
        - strings.xml을 오픈하고 오른쪽 위의 [Open editor] 링크를 클릭하면 Translations Editor가 나타남
        ![](images/ui/string-editor.png)
        - 빨간 표시된 버튼을 눌러서 언어 추가

---
- 시스템 언어 설정에 따라 문자열에 다르게 표시됨
![](images/ui/text-attr.png) ![](images/ui/text-attr-ko.png)


## ImageView
- 이미지를 표시하는 위젯
- 이미지 크기가 ImageView 보다 크거나 작을 경우 scaleType을 지정하여 표시되는 이미지 크기 변경
- ScaleType 종류
    - center, centerCrop, centerInside, fitCenter, fitEnd, fitStart, fitXY, matrix
    - 오른쪽 예시 설명
        - 위쪽 9개는 ImageView 크기 보다 큰 그림
        - 아래 9개는 ImageView 크기 보다 작은 그림
        - 왼쪽 위 부터 순서대로 디폴트(fitCenter), center, centerCrop, centerInside, fitCenter, fitEnd, fitStart, fitXY, matrix 순서로 scaleType을 지정

![bg right:35% h:90%](images/ui/scaletype.png)

---
- ImageView에서 보여줄 이미지는 app:srcCompat 속성으로 지정
- 이미지는 src\main\res\drawable\ 밑에 복사 함
    - 만일 src\main\res\drawable\bench_under_tree.jpg 이미지 파일이 있다면 다음과 같이 지정한다.
        ```xml
        <ImageView
            android:layout_width="@dimen/imgsize"
            android:layout_height="@dimen/imgsize"
            android:layout_marginEnd="5dp"
            android:background="#C6B0B0"
        →   app:srcCompat="@drawable/bench_under_tree" />
        ```
        - 여기에서 이미지의 가로, 세로 크기를 @dimen/imgsize 라고 했는데, 이는 src\main\res\values\dimens.xml 에 아래와 같이 정의 하여 사용한 것임
            ```xml
            <resources>
                <dimen name="imgsize">115dp</dimen>
            </resources>
            ```

예전에는 android:src를 사용했으나, Jetpack을 통해 벡터 이미지 지원이 가능하도록 하기 위해 app:srcCompat 속성으로 지정하는 것을 권장한다.

Jetpack 라이브러리는 안드로이드 버전에 따라 추가되는 새로운 기능 일부를 이전 버전에서도 사용 가능하도록 라이브러리 형태로 제공하는 것이다. 네임 스페이스가 androidx로 시작하기 때문에 androidx로 부르기도 한다. Jetpack 이전에는 Support Library라는 이름으로 불렸었다.

---
```xml
<TableLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="10dp">

    <TableRow
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="10dp">

    →   <ImageView
            android:layout_width="@dimen/imgsize"
            android:layout_height="@dimen/imgsize"
            android:layout_marginEnd="5dp"
            android:background="#C6B0B0"
    →       app:srcCompat="@drawable/bench_under_tree" />

    →   <ImageView
            android:layout_width="@dimen/imgsize"
            android:layout_height="@dimen/imgsize"
            android:layout_marginEnd="5dp"
            android:background="#C6B0B0"
            android:scaleType="center"
    →       app:srcCompat="@drawable/bench_under_tree" />

    →   <ImageView
            android:layout_width="@dimen/imgsize"
            android:layout_height="@dimen/imgsize"
            android:background="#C6B0B0"
            android:scaleType="centerCrop"
    →       app:srcCompat="@drawable/bench_under_tree" />
    </TableRow>
    ... 이하 생략 ...
```
![bg right:35% h:90%](images/ui/scaletype.png)

## ScrollView
- 자식 뷰가 클 경우 스크롤 가능하도록 만듬
- 세로 스크롤 동작이 기본
- 가로 스크롤을 하려면 HorizontalScrollView를 사용


## ViewBinding
- findViewById() 의 단점은 사용할 때 마다 UI 트리에서 해당 위젯을 찾아야 함
- viewBinding을 이용하면 찾을 필요 없이 해당 객체를 바로 접근할 수 있음
- 컴파일시에 해당 위젯이 없으면 오류가 나기 때문에 동적 오류 방지


## RadioButton

## Button