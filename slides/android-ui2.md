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
- TextView
- 리소스 문자열
- ImageView
- ScrollView
- ViewBinding
- 동적 View 업데이트


## TextView


## 리소스 - 문자열
- 문자열을 리소스로 관리
- 시스템 언어 설정에 따라 해당 언어가 자동으로 표시되어 다국어 버전 앱 만들 때 유용함



## ImageView
- 이미지를 표시하는 위젯
- 이미지 크기가 위젯 보다 크거나 작을 경우 scaleType을 지정하여 표시되는 이미지 크기 변경
- ScaleType 에 따른 이미지 표시


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