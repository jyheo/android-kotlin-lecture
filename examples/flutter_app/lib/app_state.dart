import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

// 앱 상태를 저장하기 위한 클래스
class AppState extends ChangeNotifier {
  int state = 0;
  String _currentUser = "";

  AppState() {
    SharedPreferences.getInstance().then((prefs) {
      state = prefs.getInt('state') ?? 0;
      _currentUser = prefs.getString('current_user') ?? 'John Doe';
    });
  }

  void increaseState() {
    state++;
    SharedPreferences.getInstance().then((prefs) {
      prefs.setInt('state', state);
    });
    notifyListeners();
  }

  String get currentUser => _currentUser;
  set currentUser(name) {
    _currentUser = name;
    SharedPreferences.getInstance().then((prefs) {
      prefs.setString('current_user', _currentUser);
    });
    notifyListeners();
  }
}