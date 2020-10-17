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

# Firebase Database
<!-- _class: lead -->
### Cloud Firestore, Realtime Database
### 허준영(jyheo@hansung.ac.kr)


## Cloud Firestore vs. Realtime Database
- Firebase는 두 가지 데이터베이스를 제공
- Realtime Database가 먼저 생긴 것으로 업데이트가 빈번하게 일어날 때 사용
    - 위치 추적 앱이나 실시간 메시징과 같은 것을 구현한다면 적합
    - 실제로 메시징은 Firebase Messaging을 사용하면 됨
- Cloud Firestore는 데이터가 규모가 크고, 업데이트가 덜 할 때 사용
    - 쇼핑몰 앱을 만든 다면 적합

# Cloud Firestore
<!-- _class: lead -->

## Cloud Firestore
- 특징
    - 유연하고 계층적 데이터 구조
    - 쿼리가 효율적
    - 실시간 업데이트
    - 오프라인 서포트
    - 스케일


## Cloud Firestore - Firebase Console
- https://console.firebase.google.com/
- 프로젝트 생성/선택하고 Cloud Firestore 선택
![](images/firebase_fs_create.png)


## Cloud Firestore - Firebase Console
![w:700px](images/firebase_fs_create_2.png)
- 테스트 모드로 선택, 나중에 security rule 추가


## Cloud Firestore - Firebase Console
![w:700px](images/firebase_fs_create_3.png)
- 서버 지역 선택, Storage에서 이미 선택한 경우 다시 선택 안됨


## Cloud Firestore - Firebase Console
- Rule
![w:900px](images/firebase_fs_rule.png)
    - 인증된 사용자만 접근하도록 수정 ``` if request.auth != null ```


## Cloud Firestore
- Firestore는 계층적 데이터 구조를 사용, Collection과 Document
- Collection은 Document의 집합
- Document에는 Field(키와 값)들과 하위 Collection들의 집합
    - Field의 값으로 문자열, 정수, 실수, 배열, 맵 등이 사용 가능
![w:900px](images/firebase_fs_data.png)


## Cloud Firestore - 안드로이드 앱
- 앱 모듈 build.gradle
```gradle
dependencies {
    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:25.12.0')

    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation 'com.google.firebase:firebase-firestore-ktx'
}
```

## Cloud Firestore - 안드로이드 앱 - 레퍼런스 객체
- Collection이나 Document에 대한 레퍼런스를 얻고,
- Collection 레퍼런스를 통해
    - Document 추가/삭제
- Document 레퍼런스를 통해
    - 필드 추가/변경/삭제
    - Collection 추가/삭제
```kotlin
val db: FirebaseFirestore = Firebase.firestore
val itemsCollectionRef = db.collection("items") // items는 Collection ID
```
- itemCollectionRef.document( **ID** ) 를 하면 items Collection 밑에 있는 Document에 대한 레퍼런스를 의미

## Cloud Firestore - 안드로이드 앱 - 레퍼런스 객체
- collection()과 document() 메소드를 레퍼런스에 적용하여 하위 데이터 레퍼런스를 얻거나
- 경로명을 써서 한번에 레퍼런스를 얻을 수 있음
- 다음 예는 동일한 데이터에 대한 레퍼런스임
    ```kotlin
    db.collection("test")
        .document("ID1")
        .collection("inventory")
        .document("ID1").get().addOnSuccessListener {
            Log.d(TAG, "${it.id}, ${it["name"]}, ${it["quantity"]}")
        }

    db.document("/test/ID1/inventory/ID1").get().addOnSuccessListener {
        Log.d(TAG, "${it.id}, ${it["name"]}, ${it["quantity"]}")
    }
    ```


## Cloud Firestore - 안드로이드 앱 - 데이터 추가
- Collection Ref에 add()나 set()으로 Document 추가
    ```kotlin
    val price = binding.editPrice.text.toString().toInt()
    val autoID = binding.checkAutoID.isChecked
    val itemID = binding.editID.text.toString()
    val itemMap = hashMapOf(
        "name" to name,
        "price" to price
    )
    if (autoID) {  // Document의 ID를 자동으로 생성
        itemsCollectionRef.add(itemMap)
            .addOnSuccessListener { updateList() 
            }.addOnFailureListener {  }
    } else {  // Document의 ID를 itemID의 값으로 지정
        itemsCollectionRef.document(itemID).set(itemMap)
            .addOnSuccessListener { updateList() 
            }.addOnFailureListener {  }
        // itemID에 해당되는 Document가 존재하면 내용을 업데이트
    }
    ```

## Cloud Firestore - 안드로이드 앱 - 데이터 추가
- 데이터 추가된 결과를 firebase console에서 확인
![w:800px](images/firebase_fs_add.png)


## Cloud Firestore - 안드로이드 앱 - 데이터 읽기
- Collection의 Document 모두 읽기
- 레퍼런스에 get() 메소드 이용
- 비동기 연산 결과를 리스너로 받음
    ```kotlin
    private fun updateList() {
        itemsCollectionRef.get().addOnSuccessListener { // it: QuerySnapshot
            val items = mutableListOf<Item>()
            for (doc in it) {
                items.add(Item(doc)) // Item의 생성자가 doc를 받아 처리
            }
            adapter?.updateList(items)
        }
    }
    ```

## Cloud Firestore - 안드로이드 앱 - 데이터 읽기
- Document의 필드 읽기
- 레퍼런스에 get() 메소드 이용
- 비동기 연산 결과를 리스너로 받음
    ```kotlin
    private fun queryItem(itemID: String) {
        itemsCollectionRef.document(itemID).get()
            .addOnSuccessListener { // it: DocumentSnapshot
                binding.editID.setText(it.id)
                binding.editItemName.setText(it["name"].toString())
                binding.editPrice.setText(it["price"].toString())
            }.addOnFailureListener {
            }
    }
    ```

## Cloud Firestore - 안드로이드 앱 - 데이터 변경
- Document 레퍼런스에 update(키, 값) 메소드로 변경
    ```kotlin
    private fun updatePrice() {
        val itemID = binding.editID.text.toString()
        val price = binding.editPrice.text.toString().toInt()

        itemsCollectionRef.document(itemID).update("price", price)
            .addOnSuccessListener { queryItem(itemID) }
    }
    ```

## Cloud Firestore - 안드로이드 앱 - 트랜잭션
- 트랜잭션 연산
    ```kotlin
    private fun incrPrice() {
        val itemID = binding.editID.text.toString()

        db.runTransaction { // it: Transaction
            val docRef = itemsCollectionRef.document(itemID)
            val snapshot = it.get(docRef)
            var price = snapshot.getLong("price") ?: 0
            // var price = snapshot["price"].toString().toInt()
            price += 1
            it.update(docRef, "price", price)
        }
            .addOnSuccessListener { queryItem(itemID) }
    }
    ```

## Cloud Firestore - 안드로이드 앱 - 데이터 삭제
- Collection 삭제는 권장하지 않음
- Document 레퍼런스에 delete() 메소드 호출하여 삭제
    - Document 삭제하더라도 하위 Collection은 삭제가 안됨
    ```kotlin
    private fun deleteItem() {
        val itemID = binding.editID.text.toString()

        itemsCollectionRef.document(itemID).delete()
            .addOnSuccessListener { updateList() }
    }
    ```
- Document의 필드를 삭제할 때는 update() 이용
    - 이 때 값을 ```FieldValue.delete()``` 로 지정


## Cloud Firestore - 안드로이드 앱 - 실시간 데이터 변경 추적
- Collection이나 Document가 변경되었을 때 리스너를 통해 알려줌
    - 다른 클라이언트가 변경했을 경우에도 실시간으로 알려줌
    ```kotlin
    // snapshot listener for all items
    snapshotListener = itemsCollectionRef.addSnapshotListener { snapshot, error ->
        binding.textSnapshotListener.text = StringBuilder().apply {
            for (doc in snapshot!!.documentChanges) {
                append("${doc.type} ${doc.document.id} ${doc.document.data}")
            }
        }
    }

    // sanpshot listener for single item
    itemsCollectionRef.document( Document ID ).addSnapshotListener { snapshot, error ->
        Log.d(TAG, "${snapshot?.id} ${snapshot?.data}")
    }
    ...
    snapshotListener?.remove()
    ```

## Cloud Firestore - 안드로이드 앱 - 데이터 조건 검색
- Collection에서 조건을 주고 Document를 검색
    ```kotlin
    private fun queryWhere() {
        val p = 100
        itemsCollectionRef.whereLessThan("price", p).get()
            .addOnSuccessListener {
                val items = arrayListOf<String>()
                for (doc in it) {
                    items.add("${doc["name"]} - ${doc["price"]}")
                }
                AlertDialog.Builder(this)
                    .setTitle("Items which price less than $p")
                    .setItems(items.toTypedArray(), { dialog, which ->  }).show()
            }
            .addOnFailureListener {
            }
    }
    ```

## Cloud Firestore - 안드로이드 앱 - 데이터 조건 검색
- 예제의 whereLessThan 외에도
    - whereEqualTo : ==
    - whereLessThanOrEqualTo : <=
    - whereGreaterThan : >
    - whereGreaterThanOrEqualTo : >=
    - whereNotEqualTo : !=
    - whereArrayContains : 필드 값이 배열이고, 필드에 특정 값 포함 여부
    - whereArrayContainsAny : 필드 값이 배열이고, 인자로 준 배열의 값 중 하나라도 포함하는지 여부
    - whereIn, whereNotIn : 필드 값이 인자로 준 배열에 포함 여부
- see more in [firebase.google.com](https://firebase.google.com/docs/firestore/query-data/queries?authuser=0)


## Cloud Firestore - 안드로이드 앱 - sort/limit/start,end
- Collection 레퍼런스에 orderBy()로 정렬
- Collection 레퍼런스에 limit()로 리턴 Document 수 제한
- 아래 예시는 name으로 정렬하고 3개까지 리턴
    ```kotlin
    itemsCollectionRef.orderBy("name").limit(3)
        .get()
    ```
- startAt(), startAfter(), endBefore(), endAt()
    - 쿼리에서 특정 값 또는 특정 Document 레퍼런스에서
    - 시작, 이후 부터 시작, 전까지, 까지
    ```kotlin
    itemsCollectionRef.orderBy("name").limit(3).get()
        .addOnSuccessListener { snapshots ->
            val lastRef = snapshots.documents[snapshots.size() - 1]
            itemsCollectionRef.orderBy("name").startAfter(lastRef).limit(3).get()  
        ...
    ```

# Realtime Database
<!-- _class: lead -->

## Realtime Database
- 연결된 모든 클라이언트들이 클라우드 데이터베이스와 싱크를 할 수 있음
    - 읽기는 비동기식, 쓰기는 동기식으로 프로그래밍
- 오프라인이 되더라도 데이터베이스를 사용할 수 있음
- 데이터는 테이블이 아니라 JSON 트리 형태로 저장됨

## Realtime Database - Firebase Console
- https://console.firebase.google.com/
- 프로젝트 생성/선택하고 Realtime Database 선택
![](images/firebase_rtdb_create.png)


## Realtime Database - Firebase Console
![w:700px](images/firebase_rtdb_create_2.png)
- 테스트 모드로 선택, 나중에 security rule 추가

## Realtime Database - 안드로이드 앱
- 앱 모듈 build.gradle
```gradle
dependencies {
    // Import the BoM for the Firebase platform
    implementation platform('com.google.firebase:firebase-bom:25.12.0')

    // Declare the dependency for the Realtime Database library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation 'com.google.firebase:firebase-database-ktx'
}
```

## Realtime Database - 안드로이드 앱 - 데이터 쓰기
- 데이터베이스 레퍼런스를 가져와서 setValue()를 호출하여 씀
    ```java
    public void onWriteData(View v) {
        String db_value = binding.dbValue.getText().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");									

        myRef.setValue(db_value);
    }
    ```
    ![h:250](images/realtimedb.png)
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../MainActivity.java#L111](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MainActivity.java#L111)


## 데이터 읽기
- ValueEventListener를 등록, 해당 값이 변경될 때마다 알려줌
- ValueEventListener를 등록하고 한번만 알려주길 원하면 addListenerForSingleValueEvent()를 사용
- ValueEventListener등록을 취소: removeEventListener()
    ```java
    myRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String value = dataSnapshot.getValue(String.class);
            Log.d(TAG, "Value is: " + value);
        }

        @Override
        public void onCancelled(DatabaseError error) {
            Log.w(TAG, "Failed to read value.", error.toException());					
        }
    });
    ```
    - [github.com/jyheo/android-java-examples/.../FirebaseTest/.../MainActivity.java#L119](https://github.com/jyheo/android-java-examples/blob/master/FirebaseTest/app/src/main/java/com/example/jyheo/firebasetest/MainActivity.java#L119)



## 데이터 구조
- JSON 트리
- child(): 자식 노드의 DatabaseReference를 가져옴
    ```java
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");
    String userid = "aloverlace"
    String username = "Ada Lovelace"
    myRef.child(userid).child("name").setValue(username)
    ```
    ![](images/realtimedb2.png)


## 데이터 구조
- push(), 고유한 아이디를 갖는 자식 노드를 생성함
- Map<String, Object> 형태의 값을 저장하는 예
    ```java
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("posts");
    String key = myRef.push().getKey();
    HashMap<String, Object> postValues = new HashMap<>();
    postValues.put("uid", "aloverlace");
    postValues.put("author", "Ada Lovelace");
    postValues.put("title", "hello post");
    postValues.put("body", "hello body");
    postValues.put("starCount", 0);

    myRef.child(key).setValue(postValues);
    ```

![bg right:30% w:400](images/realtimedb3.png)



## 트랜잭션
- 트랜잭션 처리, runTransaction()
    ```java
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("posts/-Kz4JAW5cxfcezCgBjRi");
    myRef.runTransaction(new Transaction.Handler() {
        @Override
        public Transaction.Result doTransaction(MutableData mutableData) {
             Long starCount = mutableData.child("starCount").getValue(Long.class);
            starCount++;
             mutableData.child("starCount").setValue(starCount);
            return Transaction.success(mutableData);
        }

        @Override
        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
            Log.d(TAG, "Transaction:onComplete:" + databaseError);
        }
    });
    ```


## 데이터 정렬과 필터링
- 데이터 정렬: orderByChild(), orderByKey(), orderByValue()
    ```java
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Query TopPostsQuery = databaseReference.child("posts").orderByChild("starCount");
    TopPostsQuery.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
             for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                // TODO: handle the post
            }
        }
        ...
    }
    ```
- 필터링: limitToFirst(), limitToLast()
    ```java
    // Last 100 posts, these are automatically the 100 most recent
    Query recentPostsQuery = databaseReference.child("posts").limitToFirst(100);
    ```

## 종합 예제
- https://github.com/firebase/quickstart-android/tree/master/database
